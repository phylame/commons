package pw.phylame.commons;

import lombok.val;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class Reflections {
    public static String currentThreadName() {
        return Thread.currentThread().getName();
    }

    public static String currentClassName() {
        val elements = Thread.currentThread().getStackTrace();
        return elements[elements.length - 1].getClassName();
    }

    public static String currentMethodName() {
        val elements = Thread.currentThread().getStackTrace();
        return elements[elements.length - 1].getMethodName();
    }

    public static ClassLoader currentClassLoader() {
        val loader = Thread.currentThread().getContextClassLoader();
        return loader != null ? loader : ClassLoader.getSystemClassLoader();
    }
}
