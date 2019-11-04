//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.alicyu.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.spring.mapper.ClassPathMapperScanner;
import tk.mybatis.spring.mapper.MapperFactoryBean;

public class MyMapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private Environment environment;

    public MyMapperScannerRegistrar() {
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        scanner.setMapperProperties(this.environment);
        if(this.resourceLoader != null) {
            scanner.setResourceLoader(this.resourceLoader);
        }

        Class annotationClass = annoAttrs.getClass("annotationClass");
        if(!Annotation.class.equals(annotationClass)) {
            scanner.setAnnotationClass(annotationClass);
        }

        Class markerInterface = annoAttrs.getClass("markerInterface");
        if(!Class.class.equals(markerInterface)) {
            scanner.setMarkerInterface(markerInterface);
        }

        Class generatorClass = annoAttrs.getClass("nameGenerator");
        if(!BeanNameGenerator.class.equals(generatorClass)) {
            scanner.setBeanNameGenerator((BeanNameGenerator)BeanUtils.instantiateClass(generatorClass));
        }

        Class mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if(!MapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
            scanner.setMapperFactoryBean((org.mybatis.spring.mapper.MapperFactoryBean)BeanUtils.instantiateClass(mapperFactoryBeanClass));
        }

        scanner.setSqlSessionTemplateBeanName(annoAttrs.getString("sqlSessionTemplateRef"));
        scanner.setSqlSessionFactoryBeanName(annoAttrs.getString("sqlSessionFactoryRef"));
        ArrayList basePackages = new ArrayList();
        String[] var10 = annoAttrs.getStringArray("value");
        int var11 = var10.length;

        int var12;
        String clazz;
        for(var12 = 0; var12 < var11; ++var12) {
            clazz = var10[var12];
            if(StringUtils.hasText(clazz)) {
                basePackages.add(parsePlaceHolder(clazz));
            }
        }

        var10 = annoAttrs.getStringArray("basePackages");
        var11 = var10.length;

        for(var12 = 0; var12 < var11; ++var12) {
            clazz = var10[var12];
            if(StringUtils.hasText(clazz)) {
                basePackages.add(parsePlaceHolder(clazz));
            }
        }

        Class[] var14 = annoAttrs.getClassArray("basePackageClasses");
        var11 = var14.length;

        for(var12 = 0; var12 < var11; ++var12) {
            Class var15 = var14[var12];
            basePackages.add(ClassUtils.getPackageName(var15));
        }

        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
    private String parsePlaceHolder(String pro) {
        if (pro != null && pro.contains(PropertySourcesPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX)) {
            String value = environment.getProperty(pro.substring(2, pro.length() - 1));
            if (null == value) {
                throw new IllegalArgumentException("property " + pro + " can not find!!!");
            }
            return value;
        }
        return pro;
    }
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
