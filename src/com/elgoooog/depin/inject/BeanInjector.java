package com.elgoooog.depin.inject;

import com.elgoooog.depin.Beans;
import com.elgoooog.depin.model.Bean;
import com.elgoooog.depin.model.InjectedField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Nicholas Hauschild
 *         Date: 2/25/11
 *         Time: 8:30 PM
 */
public class BeanInjector implements DepInInjector {
    public void inject(Beans beans) {
        for (Bean bean : beans) {
            Field[] fields = bean.getBeanClass().getDeclaredFields();
            for(Field field : fields) {
                Annotation injectAnnotation = field.getAnnotation(Inject.class);
                if(injectAnnotation != null) {
                    Inject inject = (Inject) injectAnnotation;
                    bean.addInjectedField(new InjectedField(field, beans.getBean(inject.value())));
                }
            }
        }
    }
}
