package pw.phylame.commons.spi;

import lombok.NonNull;
import lombok.val;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.phylame.commons.CollectionUtils;
import pw.phylame.commons.NotImplementedException;
import pw.phylame.commons.Reflections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public class ServiceManager<T extends KeyedService> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private ServiceLoader<T> serviceLoader;

    private final HashSet<T> services = new HashSet<>();

    private final HashMap<String, T> registry = new HashMap<>();

    private boolean loaded = false;

    public ServiceManager(Class<T> type) {
        this(type, null);
    }

    public ServiceManager(Class<T> type, ClassLoader loader) {
        this.serviceLoader = ServiceLoader.load(type, loader != null ? loader : Reflections.currentClassLoader());
    }

    public final T get(@NonNull String key) {
        var service = registry.get(key);
        if (service != null) {
            return service;
        } else if (!loaded) {
            reload();
        }
        return services.stream()
                .filter(s -> {
                    val keys = s.getKeys();
                    if (CollectionUtils.isEmpty(keys)) {
                        throw new NotImplementedException(s.getClass().getName() + ".getKeys() returned null or empty set");
                    }
                    return keys.contains(key);
                })
                .findFirst()
                .orElse(null);
    }

    public final T set(String key, T service) {
        if (service == null) {
            return registry.remove(key);
        } else {
            return registry.put(key, service);
        }
    }

    public final void reload() {
        services.clear();
        try {
            for (val service : serviceLoader) {
                services.add(service);
            }
        } catch (ServiceConfigurationError e) {
            log.debug("Error when loading services", e);
        }
        loaded = true;
    }
}
