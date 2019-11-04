
/**
 * @Description  自定义MapperScan实现属性能用spring占位符
 * @Author       Peihan.Zhang
 * @CreateTime  2019/11/4
 * @Version     0.0.1
 * @Email       zhangpeihan@jchzbj.com
 * @param
 */
package com.alicyu.config;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScannerRegistrar;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({MyMapperScannerRegistrar.class})
public @interface MapperScan {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    Class<? extends Annotation> annotationClass() default Annotation.class;

    Class<?> markerInterface() default Class.class;

    String sqlSessionTemplateRef() default "";

    String sqlSessionFactoryRef() default "";

    Class<? extends MapperFactoryBean> factoryBean() default MapperFactoryBean.class;
}
