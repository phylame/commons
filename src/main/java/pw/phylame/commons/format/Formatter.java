package pw.phylame.commons.format;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public interface Formatter<T> {
    String render(T obj);

    T parse(String str);
}
