package com.br.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author an.cantuong
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Combine array arguments to array object.
     *
     * @param args can be collection, array, or single object.
     */
    public static Object[] combine(Object... args) {
        var combined = new ArrayList<>();
        for (var obj : args) {
            if (obj instanceof Collection<?>) {
                combined.addAll((Collection<?>) obj);
            } else if (obj.getClass().isArray()) {
                combined.addAll(Collections.singletonList(obj));
            } else {
                combined.add(obj);
            }
        }
        return combined.toArray();
    }

    /**
     * Check given collection is null or empty.
     * If true throw exception NotFoundException.
     */
    public static <T extends Collection<?>> T requireNonNullAndEmpty(T collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return collection;
    }

    public static <T> String join(Collection<T> collection,
                                  Function<? super T, String> keyExtractor,
                                  String delim) {
        return join(collection.spliterator(), keyExtractor, delim);
    }

    /**
     * Join collection with keyExtractor to String with delimiter.
     *
     * @param spliterator  input.
     * @param keyExtractor mapper function to String.
     * @param delim        delimiter.
     * @return joined String.
     */
    public static <T> String join(Spliterator<T> spliterator,
                                  Function<? super T, String> keyExtractor,
                                  String delim) {
        return StreamSupport.stream(spliterator, false)
                .map(keyExtractor)
                .collect(Collectors.joining(delim));
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> collection,
                                            Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends V> valueMapper) {
        return isEmpty(collection) ? Collections.emptyMap()
                : collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
    }

    /**
     * Return true if c1, c2 has common element(s).
     */
    public static boolean containAny(Collection<?> c1, Collection<?> c2) {
        return !Collections.disjoint(c1, c2);
    }

    public static <T, R> List<R> toList(Collection<T> collection, Function<T, R> func) {
        return isEmpty(collection) ? Collections.emptyList() : toList(collection.spliterator(), func);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T> List<T> toFilterList(Collection<T> collection, Predicate<T> predicate) {
        return isEmpty(collection) ? Collections.emptyList() : toList(collection.spliterator(), predicate);
    }

    /**
     * collect to list with distinct by <code> keyExtractor</code>  from collection.
     */
    public static <T> List<T> toDistinctList(Collection<T> collection, Function<? super T, ?> keyExtractor) {
        return isEmpty(collection) ? Collections.emptyList() : toList(collection.spliterator(), distinctByKey(keyExtractor));
    }

    /**
     * collect to list with distinct by <code> keyExtractor</code>  from spliterator.
     */
    public static <T> List<T> toDistinctList(Spliterator<T> spliterator, Function<? super T, ?> keyExtractor) {
        return toList(spliterator, distinctByKey(keyExtractor));
    }

    /**
     * collect to list with filter from spliterator.
     */
    public static <T, R> List<R> toList(Spliterator<T> spliterator, Function<T, R> func) {
        return toList(spliterator, f -> true, func);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T, R> List<R> toList(Collection<T> collection,
                                        Predicate<T> predicate,
                                        Function<T, R> func) {
        return isEmpty(collection) ? Collections.emptyList() : toList(collection.spliterator(), predicate, func);
    }

    /**
     * collect to list with filter and mapper from spliterator.
     *
     * @param spliterator spliterator.
     * @param predicate   filter.
     * @param func        mapper func.
     */
    public static <T, R> List<R> toList(Spliterator<T> spliterator,
                                        Predicate<T> predicate,
                                        Function<T, R> func) {
        return spliteratorToList(func).apply(spliterator, predicate);
    }

    /**
     * collect to list with filter from collection.
     */
    public static <T> List<T> toList(Spliterator<T> spliterator,
                                     Predicate<T> predicate) {
        return StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * collect to list with filter from collection.
     */
    private static <T, R> BiFunction<Spliterator<T>, Predicate<T>, List<R>> spliteratorToList(Function<T, R> func) {
        return (spliterator, predicate) -> StreamSupport.stream(spliterator, false)
                .filter(predicate)
                .map(func)
                .collect(Collectors.toList());
    }

    public static <T> Stream<T> streamOf(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> parallelStreamOf(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), true);
    }

    /**
     * @param keyExtractor function to get property of object by key.
     * @param <T>          type of object.
     * @return function check distinct value from keyExtractor.
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
