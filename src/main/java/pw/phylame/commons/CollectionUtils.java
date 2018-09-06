package pw.phylame.commons;

import lombok.NonNull;
import lombok.val;
import pw.phylame.commons.value.Keyed;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wp <phylame@163.com>
 * @date 2018/06/2018
 */
public final class CollectionUtils {
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> c) {
        return c != null && !c.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return m == null || m.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> m) {
        return m != null && !m.isEmpty();
    }

    public static <E> Optional<E> firstOf(Collection<E> c) {
        if (isEmpty(c)) {
            return Optional.empty();
        } else if (c instanceof List<?>) {
            return Optional.ofNullable(((List<E>) c).get(0));
        } else {
            val it = c.iterator();
            return it.hasNext() ? Optional.ofNullable(it.next()) : Optional.empty();
        }
    }

    public static <E> Optional<E> anyOf(List<E> list) {
        return isNotEmpty(list)
                ? Optional.ofNullable(list.get(ThreadLocalRandom.current().nextInt(list.size())))
                : Optional.empty();
    }

    public static void copy(Properties props, Map<? super String, ? super String> map) {
        if (!props.isEmpty()) {
            for (val entry : props.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    public static <K, T extends Keyed<K>> void copy(Iterable<T> items, Map<K, T> map) {
        for (T item : items) {
            map.put(item.getKey(), item);
        }
    }

    public static <E> Iterable<E> iterable(@NonNull Iterator<E> i) {
        return () -> i;
    }

    public static <E> Iterator<E> iterator(@NonNull Enumeration<E> e) {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return e.hasMoreElements();
            }

            @Override
            public E next() {
                return e.nextElement();
            }
        };
    }

    public static <E> Set<E> setOf(E a) {
        return Collections.singleton(a);
    }

    public static <E> Set<E> setOf(E a, E b) {
        return Collections.unmodifiableSet(hashSetOf(a, b));
    }

    public static <E> Set<E> setOf(E a, E b, E c) {
        return Collections.unmodifiableSet(hashSetOf(a, b, c));
    }

    public static <E> Set<E> setOf(E a, E b, E c, E d) {
        return Collections.unmodifiableSet(hashSetOf(a, b, c, d));
    }

    public static <E> Set<E> setOf(E a, E b, E c, E d, E f) {
        return Collections.unmodifiableSet(hashSetOf(a, b, c, d, f));
    }

    @SafeVarargs
    public static <E> Set<E> setOf(E... a) {
        return Collections.unmodifiableSet(hashSetOf(a));
    }

    public static <E> HashSet<E> hashSetOf(E a) {
        val set = new HashSet<E>();
        set.add(a);
        return set;
    }

    public static <E> HashSet<E> hashSetOf(E a, E b) {
        val set = new HashSet<E>();
        set.add(a);
        set.add(b);
        return set;
    }

    public static <E> HashSet<E> hashSetOf(E a, E b, E c) {
        val set = new HashSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        return set;
    }

    public static <E> HashSet<E> hashSetOf(E a, E b, E c, E d) {
        val set = new HashSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        set.add(d);
        return set;
    }

    public static <E> HashSet<E> hashSetOf(E a, E b, E c, E d, E f) {
        val set = new HashSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        set.add(d);
        set.add(f);
        return set;
    }

    @SafeVarargs
    public static <E> HashSet<E> hashSetOf(E... a) {
        val set = new HashSet<E>();
        Collections.addAll(set, a);
        return set;
    }

    public static <E> TreeSet<E> treeSetOf(E a) {
        val set = new TreeSet<E>();
        set.add(a);
        return set;
    }

    public static <E> TreeSet<E> treeSetOf(E a, E b) {
        val set = new TreeSet<E>();
        set.add(a);
        set.add(b);
        return set;
    }

    public static <E> TreeSet<E> treeSetOf(E a, E b, E c) {
        val set = new TreeSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        return set;
    }

    public static <E> TreeSet<E> treeSetOf(E a, E b, E c, E d) {
        val set = new TreeSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        set.add(d);
        return set;
    }

    public static <E> TreeSet<E> treeSetOf(E a, E b, E c, E d, E f) {
        val set = new TreeSet<E>();
        set.add(a);
        set.add(b);
        set.add(c);
        set.add(d);
        set.add(f);
        return set;
    }

    @SafeVarargs
    public static <E> TreeSet<E> treeSetOf(E... a) {
        val set = new TreeSet<E>();
        Collections.addAll(set, a);
        return set;
    }

    public static <E> List<E> listOf(E a) {
        return Collections.singletonList(a);
    }

    public static <E> List<E> listOf(E a, E b) {
        return Collections.unmodifiableList(arrayListOf(a, b));
    }

    public static <E> List<E> listOf(E a, E b, E c) {
        return Collections.unmodifiableList(arrayListOf(a, b, c));
    }

    public static <E> List<E> listOf(E a, E b, E c, E d) {
        return Collections.unmodifiableList(arrayListOf(a, b, c, d));
    }

    public static <E> List<E> listOf(E a, E b, E c, E d, E f) {
        return Collections.unmodifiableList(arrayListOf(a, b, c, d, f));
    }

    @SafeVarargs
    public static <E> List<E> listOf(E... a) {
        return Arrays.asList(a);
    }

    public static <E> ArrayList<E> arrayListOf(E a) {
        val list = new ArrayList<E>();
        list.add(a);
        return list;
    }

    public static <E> ArrayList<E> arrayListOf(E a, E b) {
        val list = new ArrayList<E>();
        list.add(a);
        list.add(b);
        return list;
    }

    public static <E> ArrayList<E> arrayListOf(E a, E b, E c) {
        val list = new ArrayList<E>();
        list.add(a);
        list.add(b);
        list.add(c);
        return list;
    }

    public static <E> ArrayList<E> arrayListOf(E a, E b, E c, E d) {
        val list = new ArrayList<E>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        return list;
    }

    public static <E> ArrayList<E> arrayListOf(E a, E b, E c, E d, E f) {
        val list = new ArrayList<E>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(f);
        return list;
    }

    @SafeVarargs
    public static <E> ArrayList<E> arrayListOf(E... a) {
        val list = new ArrayList<E>();
        Collections.addAll(list, a);
        return list;
    }

    public static <E> LinkedList<E> linkedListOf(E a) {
        val list = new LinkedList<E>();
        list.add(a);
        return list;
    }

    public static <E> LinkedList<E> linkedListOf(E a, E b) {
        val list = new LinkedList<E>();
        list.add(a);
        list.add(b);
        return list;
    }

    public static <E> LinkedList<E> linkedListOf(E a, E b, E c) {
        val list = new LinkedList<E>();
        list.add(a);
        list.add(b);
        list.add(c);
        return list;
    }

    public static <E> LinkedList<E> linkedListOf(E a, E b, E c, E d) {
        val list = new LinkedList<E>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        return list;
    }

    public static <E> LinkedList<E> linkedListOf(E a, E b, E c, E d, E f) {
        val list = new LinkedList<E>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(f);
        return list;
    }

    @SafeVarargs
    public static <E> LinkedList<E> linkedListOf(E... a) {
        val list = new LinkedList<E>();
        Collections.addAll(list, a);
        return list;
    }
}
