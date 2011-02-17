package com.elgoooog.depin.parser.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 2/17/11
 *         Time: 12:41 AM
 */
public class Beans {
    private static Map<String, Bean> beanModel;

    static {
        beanModel = new HashMap<String, Bean>();
    }

    private Beans() {
        // no instances
    }

    public static void addBean(String id, Bean bean) {
        beanModel.put(id, bean);
    }

    public static Bean getBean(String id) {
        return beanModel.get(id);
    }

    public static Collection<Bean> allBeans() {
        return Collections.unmodifiableCollection(beanModel.values());
    }
}
