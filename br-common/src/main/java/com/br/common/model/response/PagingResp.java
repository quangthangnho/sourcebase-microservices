package com.br.common.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagingResp<E> {
    private long total;

    private int pageIndex;

    private int pageSize;

    private List<E> data;

    public PagingResp(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PagingResp(long total,
                      int pageIndex,
                      int pageSize) {
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PagingResp(long total,
                      int pageIndex,
                      int pageSize,
                      List<E> data) {
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.data = data;
    }
}
