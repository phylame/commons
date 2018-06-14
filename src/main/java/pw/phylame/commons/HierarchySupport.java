package pw.phylame.commons;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static lombok.AccessLevel.PROTECTED;
import static pw.phylame.commons.Validate.require;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
public abstract class HierarchySupport<T extends HierarchySupport<T>> implements Hierarchical<T> {
    @Getter
    @Setter(PROTECTED)
    private T parent;

    private ArrayList<T> children = new ArrayList<>();

    public final void append(@NonNull T item) {
        children.add(ensureSolitary(item));
    }

    public final void insert(int index, @NonNull T item) {
        children.add(index, ensureSolitary(item));
    }

    public final void extend(@NonNull Iterable<T> items) {
        for (T item : items) {
            append(item);
        }
    }

    @Override
    public final int size() {
        return children.size();
    }

    @Override
    public final T get(int index) {
        return children.get(index);
    }

    public final int indexOf(T item) {
        return item.getParent() == this ? children.indexOf(item) : -1;
    }

    public final boolean contains(T item) {
        return indexOf(item) != -1;
    }

    public final boolean replace(T item, T target) {
        val index = indexOf(item);
        if (index == -1) {
            return false;
        }
        replaceAt(index, target);
        return true;
    }

    public final T replaceAt(int index, T item) {
        val last = children.set(index, ensureSolitary(item));
        last.setParent(null);
        return last;
    }

    public final boolean remove(T item) {
        val index = indexOf(item);
        if (index == -1) {
            return false;
        }
        removeAt(index);
        return true;
    }

    public final T removeAt(int index) {
        val last = children.remove(index);
        last.setParent(null);
        return last;
    }

    public final void swap(int from, int to) {
        Collections.swap(children, from, to);
    }

    public final void clear() {
        for (T child : children) {
            child.setParent(null);
        }
        children.clear();
    }

    @Override
    public final Iterator<T> iterator() {
        return children.iterator();
    }

    @SuppressWarnings("unchecked")
    private T ensureSolitary(T item) {
        require(item != this, "Cannot add self to child list");
        require(item != getParent(), "Cannot add parent to child list");
        require(item.getParent() == null, "Item has been in certain hierarchy");
        require(!item.isOffspring((T) this), "Cannot add ancestor to child list");
        item.setParent((T) this);
        return item;
    }
}
