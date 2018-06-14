package pw.phylame.commons.setting;

import lombok.*;
import pw.phylame.commons.value.Keyed;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/13
 */
@Getter
@Builder
@ToString
public class Definition implements Keyed<String> {
    @NonNull
    private String key;

    @NonNull
    private Class<?> type;

    private Object initial;

    private String description;

    private Predicate<Object> constraint;

    @Singular
    private List<Dependency> dependencies;
}
