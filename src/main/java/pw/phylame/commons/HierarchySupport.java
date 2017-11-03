package pw.phylame.commons;

import lombok.Getter;
import lombok.NonNull;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static pw.phylame.commons.Validate.require;

public abstract class HierarchySupport<T extends HierarchySupport<T>> implements Hierarchical<T> {
    @Getter
    protected T parent;

    protected final ArrayList<T> children = new ArrayList<>();

    @Override
    public int size() {
        return children.size();
    }

    public void append(@NonNull T item) {
        children.add(ensureSolitary(item));
    }

    public void insert(int index, @NonNull T item) {
        children.add(index, ensureSolitary(item));
    }

    public void extend(@NonNull Iterable<T> items) {
        for (val item : items) {
            append(item);
        }
    }

    @Override
    public T get(int index) {
        return children.get(index);
    }

    public int indexOf(@NonNull T item) {
        if (item.parent != this) {
            return -1;
        }
        for (int i = 0, end = children.size(); i < end; ++i) {
            if (children.get(i) == item) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(@NonNull T item) {
        return indexOf(item) != -1;
    }

    public boolean replace(@NonNull T item, @NonNull T target) {
        val index = indexOf(item);
        if (index == -1) {
            return false;
        }
        replaceAt(index, target);
        return true;
    }

    public T replaceAt(int index, @NonNull T item) {
        val old = children.set(index, ensureSolitary(item));
        old.parent = null;
        return old;
    }

    public void swap(int from, int to) {
        Collections.swap(children, from, to);
    }

    public boolean remove(@NonNull T item) {
        val index = indexOf(item);
        if (index == -1) {
            return false;
        }
        removeAt(index);
        return true;
    }

    public T removeAt(int index) {
        val old = children.remove(index);
        old.parent = null;
        return old;
    }

    public void clear() {
        for (val item : children) {
            item.parent = null;
        }
        children.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return children.iterator();
    }

    @SuppressWarnings("unchecked")
    private T ensureSolitary(T item) {
        require(item != this, "Cannot add self to child list");
        require(item != parent, "Cannot add parent to child list");
        require(item.parent == null, "Item has been in certain hierarchy");
        require(!item.isAncestor((T) this), "Cannot add ancestor to child list");
        item.parent = (T) this;
        return item;
    }
}
