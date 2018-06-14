package pw.phylame.commons;

import lombok.NonNull;
import lombok.val;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wp <phylame@163.com>
 * @date 2018/05/18
 */
public interface Hierarchical<T extends Hierarchical<T>> extends Iterable<T> {
    int size();

    T getParent();

    T get(int index);

    default boolean isLeaf() {
        return size() == 0;
    }

    @SuppressWarnings("unchecked")
    default T getFirstLeaf() {
        return isLeaf() ? (T) this : get(0).getFirstLeaf();
    }

    @SuppressWarnings("unchecked")
    default T getLastLeaf() {
        return isLeaf() ? (T) this : get(size() - 1).getLastLeaf();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    default int getDepth() {
        if (size() == 0) {
            return 0;
        }
        int depth = 0;
        for (T child : this) {
            depth = Math.max(depth, child.getDepth());
        }
        return depth + 1;
    }

    default boolean isRoot() {
        return getParent() == null;
    }

    default boolean isSibling(@NonNull T obj) {
        return getParent() == obj.getParent();
    }

    @SuppressWarnings("unchecked")
    default boolean isAncestor(@NonNull T obj) {
        return obj.isOffspring((T) this);
    }

    default boolean isOffspring(@NonNull T obj) {
        T parent = obj.getParent();
        while (parent != null) {
            if (parent == this) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    default T getAncestor(T top) {
        T parent = (T) this;
        while (parent.getParent() != top) {
            parent = parent.getParent();
        }
        return parent;
    }

    default T getRoot() {
        return getAncestor(null);
    }

    @SuppressWarnings("unchecked")
    default List<T> getPath(T top) {
        val list = new LinkedList<T>();
        T parent = (T) this;
        while (parent != top) {
            // top not in hierarchy tree
            if (parent == null) {
                return Collections.emptyList();
            }
            list.addFirst(parent);
            parent = parent.getParent();
        }
        return list;
    }

    default List<T> toRoot() {
        return getPath(null);
    }

    default T locate(@NonNull int[] indices) {
        T child = null;
        for (int index : indices) {
            child = (child == null ? this : child).get(index < 0 ? index + size() : index);
        }
        return child;
    }

    default T locate(@NonNull Iterable<Integer> indices) {
        T child = null;
        for (int index : indices) {
            child = (child == null ? this : child).get(index < 0 ? index + size() : index);
        }
        return child;
    }
}
