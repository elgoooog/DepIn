package com.elgoooog.depin;

import com.elgoooog.depin.model.Bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:41 AM
 */
public class Beans implements Iterable<Bean> {
    private Map<String, Bean> beanModel = new HashMap<String, Bean>();

    public void addBean(String id, Bean bean) {
        beanModel.put(id, bean);
    }

    public Bean getBean(String id) {
        return beanModel.get(id);
    }

    @Override
    public Iterator<Bean> iterator() {
        return beanModel.values().iterator();
    }
}
