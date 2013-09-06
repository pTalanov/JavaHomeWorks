package ru.spbau.talanov.task5;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * A comparator which uses mapping to comparable implementation for passed type to perform comparison.
 *
 * @param <T> a type which can be compared by this comparator.
 * @param <A> a type which is used for comparison after mapping takes place.
 */
public final class MappingComparator<T, A extends Comparable<? super A>>
        extends Function2<T, T, Integer> implements Comparator<T> {

    private final Function<? super T, A> mapper;

    /**
     * Constructs a new MappingComparator object.
     *
     * @param map a map to use.
     * @throws IllegalArgumentException if passed map is null.
     */
    public MappingComparator(@NotNull Function<? super T, A> map) {
        mapper = map;
    }


    /**
     * Maps passed arguments to specified type and compares them using compareTo method.
     *
     * @param arg1 first argument.
     * @param arg2 second argument.
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second.
     */
    @Override
    public Integer call(T arg1, T arg2) {
        return compare(arg1, arg2);
    }

    /**
     * Maps a not null argument to Comparable and uses it's compareTo method for comparison.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(T o1, T o2) {
        return mapper.call(o1).compareTo(mapper.call(o2));
    }

}
