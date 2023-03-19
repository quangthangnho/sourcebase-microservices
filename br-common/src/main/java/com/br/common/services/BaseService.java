package com.br.common.services;

import com.br.common.converter.CustomDozerBeanMapper;
import com.br.common.converter.CustomDozerJdk8BeanMapper;
import com.br.common.dto.ReturnPaginationDTO;
import com.br.common.exception.CustomException;
import com.br.common.exception.ErrorCode;
import com.br.common.exception.ErrorCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public abstract class BaseService<E, ID, R extends JpaRepository<E, ID>> {
    @Autowired
    public CustomDozerBeanMapper customDozerBeanMapper;

    @Autowired
    public CustomDozerJdk8BeanMapper customDozerJdk8BeanMapper;
    protected final R repo;
    protected final Logger log;

    protected BaseService(R repo) {
        log = LoggerFactory.getLogger(getClass());
        this.repo = repo;
    }

    public static Pageable getPageable(int pageIndex, int pageSize) {
        return PageRequest.of(Math.max(pageIndex - 1, 0), pageSize);
    }

    public static Pageable getPageable(int pageIndex, int pageSize, Sort sort) {
        return PageRequest.of(Math.max(pageIndex - 1, 0), pageSize, sort);
    }

    public E save(E entity) {
        return repo.save(entity);
    }

    public List<E> saveAll(List<E> entities) {
        return repo.saveAll(entities);
    }

    public Optional<E> get(ID id) {
        return repo.findById(id);
    }

    public E getOrElseThrow(ID id) {
        return get(id)
                .orElseThrow(() -> new ErrorCodeException(errorCodeNotFoundEntity()));
    }

    public E delete(E entity) {
        repo.delete(entity);
        return entity;
    }

    public List<E> getAll() {
        return repo.findAll();
    }

    public E deleteIfExisted(ID id) {
        Optional<E> optional = get(id);
        if (optional.isPresent()) {
            return null;
        }
        E entity = optional.get();
        return delete(entity);
    }

    public E updateOnField(ID id, Consumer<E> fieldConsumer) {
        E entity = getOrElseThrow(id);
        return update(entity, fieldConsumer);
    }

    public E update(E entity, Consumer<E> fieldConsumer) {
        fieldConsumer.accept(entity);
        save(entity);
        return entity;
    }

    public Page<E> query(int pageSize, int pageIndex) {
        var offset = (pageIndex - 1) * pageSize;
        var pageable = PageRequest.of(Math.max(offset, 0), pageSize);
        return repo.findAll(pageable);
    }

    public Page<E> query(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public List<E> findAll(int pageSize, int pageIndex) {
        return findAll(getPageable(pageIndex, pageSize));
    }

    public List<E> findAll(Pageable pageable) {
        List<E> results = new LinkedList<>();

        Page<E> page = query(pageable);
        while (page.hasContent()) {
            results.addAll(page.getContent());
            Pageable nextPageable = page.getPageable().next();
            page = query(nextPageable);
        }
        return results;
    }

    public boolean existById(ID id) {
        return repo.existsById(id);
    }

    public void existByIdOrElseThrow(ID id) {
        if (!repo.existsById(id)) {
            throw new ErrorCodeException(errorCodeNotFoundEntity());
        }
    }

    protected abstract ErrorCode errorCodeNotFoundEntity();

    protected CustomException getException(int code, String message) {
        return new CustomException(code, message);
    }

    protected <T> T getEntityById(JpaRepository repository, Long id) {
        if (id == null || Objects.equals(0L, id))
            return null;

        Optional entityOpt = repository.findById(id);
        if (!entityOpt.isPresent()) {
            return null;
        }
        return (T) entityOpt.get();
    }

    public static <T> ReturnPaginationDTO<T> getPaginationResult(List<T> content, int page, int totalPages, long totalElements){
        ReturnPaginationDTO<T> result= new ReturnPaginationDTO<T>();
        result.setContent(content);
        result.setPageNumber(page);
        result.setTotalPages(totalPages);
        result.setTotalElements((int)totalElements);
        return result;
    }

}
