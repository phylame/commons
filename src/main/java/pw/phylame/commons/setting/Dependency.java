package pw.phylame.commons.setting;

import lombok.NonNull;
import lombok.Value;
import pw.phylame.commons.value.Keyed;

import java.util.function.Predicate;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/13
 */
@Value
public class Dependency implements Keyed<String> {
    @NonNull
    private String key;

    @NonNull
    private Predicate<Object> condition;
}
