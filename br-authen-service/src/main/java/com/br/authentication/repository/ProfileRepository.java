package com.br.authentication.repository;

import com.br.authentication.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    @Query(value = "select p from Profile p" +
            " inner join User u on p.userId.id = u.id " +
            " where u.email = :email ")
    ProfileEntity findByEmail(String email);

    @Query(value = "select p from Profile p" +
            " inner join User u on p.userId.id = u.id" +
            " where u.id = :id ")
    ProfileEntity findUserById(Long id);

    @Query(value = "select p from Profile p  " +
            " inner join User u on p.userId.id = u.id" +
            " where UPPER(u.phone) = UPPER(:phone)  ")
    ProfileEntity findByPhone(String phone);

}
