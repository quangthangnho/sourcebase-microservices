package com.br.authentication.config;

import com.br.common.converter.CustomDozerBeanMapper;
import com.br.common.converter.CustomDozerJdk8BeanMapper;
import com.br.common.converter.CustomModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class DozerConfig {
    @Bean
    public CustomDozerBeanMapper dozerBean() {
        List<String> mappingFiles = Collections.singletonList("dozer-configration-mapping.xml");
        CustomDozerBeanMapper dozerBean = new CustomDozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }

    @Bean
    public CustomModelMapper customModelMapper() {
        CustomModelMapper modelMapper = new CustomModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public CustomDozerJdk8BeanMapper customDozerJdk8BeanMapper() {
        List<String> mappingFiles = Collections.singletonList("dozerJdk8Converters.xml");
        CustomDozerJdk8BeanMapper dozerBean = new CustomDozerJdk8BeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }
}
