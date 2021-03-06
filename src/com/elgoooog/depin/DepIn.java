package com.elgoooog.depin;

import com.elgoooog.depin.inject.BeanInjector;
import com.elgoooog.depin.inject.DepInInjector;
import com.elgoooog.depin.model.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * DepIn is the main class with which one can access the beans created by the configuration file.
 *
 * @author Nicholas Hauschild
 *         Date: 2/7/11
 *         Time: 11:56 PM
 */
public class DepIn {
    private DepInFileLoader fileLoader;
    private DepInInjector injector;
    private Beans beans;

    public DepIn(DepInFileLoader loader, DepInInjector beanInjector) {
        fileLoader = loader;
        injector = beanInjector;
        beans = new Beans();
    }

    public DepIn() {
        this(new DepInFileLoader(), new BeanInjector());
    }

    public Object get(String id) {
        Bean bean = beans.getBean(id);
        if(bean == null) {
            throw new RuntimeException("No such bean (" + id + ") in configuration");
        }

        return bean.getInstance();
    }

    public void loadConfiguration() {
        String configFile = System.getProperty("depinConfigurationFile");

        if(configFile != null) {
            loadConfiguration(configFile);
        } else {
            loadConfiguration("config/depin.xml");
        }
    }

    public void loadConfiguration(String file) {
        loadConfiguration(new File(file));
    }

    public void loadConfiguration(File file) {
        try {
            loadConfiguration(new FileInputStream(file));
        } catch(FileNotFoundException e) {
            throw new RuntimeException("could not find file: "+ file.getAbsolutePath());
        }
    }

    public void loadConfiguration(InputStream is) {
        fileLoader.load(is, beans);
        injector.inject(beans);
    }
}
