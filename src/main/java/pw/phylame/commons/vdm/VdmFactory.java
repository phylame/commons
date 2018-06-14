package pw.phylame.commons.vdm;

import pw.phylame.commons.setting.Settings;
import pw.phylame.commons.spi.KeyedService;

import java.io.IOException;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public interface VdmFactory extends KeyedService {
    VdmReader getReader(Object input, Settings settings) throws IOException;

    VdmWriter getWriter(Object output, Settings settings) throws IOException;
}
