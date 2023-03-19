package com.br.common.converter;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class CustomDozerJdk8BeanMapper extends DozerBeanMapper {
    public <T> List<T> mapAsList(Iterable<?> sources, Class<T> destinationClass) {

        ArrayList<T> targets = new ArrayList<>();
        if (sources == null) {
            return targets;
        }
        for (Object source : sources) {
            targets.add(map(source, destinationClass));
        }
        return targets;
    }
}
