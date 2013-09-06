package ru.spbau.talanov.task5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A function which returns a list of at most n first elements of a sequence.
 */
public final class Take<T> extends Function2<Iterable<? extends T>, Integer, List<T>> {

    /**
     * Get first <code>count</code> elements of a sequence.
     *
     * @param items a sequence of elements.
     * @param count a count of elements to take.
     * @return a list containing at most count items.
     *         It contains less than count objects only if input sequence does not contain enough elements.
     * @throws IllegalArgumentException if count is negative.
     */
    @Override
    public List<T> call(Iterable<? extends T> items, Integer count) {
        if (count < 0) {
            throw new IllegalArgumentException("Elements count must be positive");
        }

        List<T> result = new ArrayList<>();
        Iterator<? extends T> i = items.iterator();

        while (i.hasNext() && count != 0) {
            result.add(i.next());
            count--;
        }

        return result;
    }

}
