package pw.phylame.commons.vdm;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public interface VdmEntry {
    String getName();

    String getComment();

    long lastModified();

    boolean isDirectory();
}
