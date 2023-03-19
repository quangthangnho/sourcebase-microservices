package com.br.common.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class PagingReq {

    private int pageIndex = 1;
    private int pageSize = 20;

    /**
     * field1:asc,field2:desc
     */
    private String sort;

    public Sort getSortable() {
        try {
            if (StringUtils.hasLength(sort)) {
                var splits = sort.split(",");
                var orders = new ArrayList<Sort.Order>();
                for (var sortStr : splits) {
                    var values = sortStr.split(":");
                    if (values[1].equals("asc")) {
                        orders.add(Sort.Order.asc(values[0]));
                    } else {
                        orders.add(Sort.Order.desc(values[0]));
                    }
                }
                return Sort.by(orders);
            }
        } catch (Exception ignored) {
        }

        return Sort.unsorted();
    }
}
