package pw.phylame.commons;

import lombok.NonNull;
import lombok.val;

public interface Hierarchical<T extends Hierarchical<T>> extends Iterable<T> {
    int size();

    T getParent();

    T get(int index);

    default boolean isRoot() {
        return getParent() == null;
    }

    default boolean isAncestor(@NonNull T child) {
        T parent = child.getParent();
        while (parent != null) {
            if (parent == this) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    default T locate(@NonNull int[] indices) {
        T child = null;
        for (val index : indices) {
            child = (child != null ? child : this).get(index < 0 ? index + size() : index);
        }
        return child;
    }

    default int getDepth() {
        if (size() == 0) {
            return 0;
        }
        int depth = 0;
        for (val child : this) {
            depth = Math.max(depth, child.getDepth());
        }
        return depth + 1;
    }
}
