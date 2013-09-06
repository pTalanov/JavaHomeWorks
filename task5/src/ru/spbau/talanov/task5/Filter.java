package ru.spbau.talanov.task5;

import java.util.ArrayList;
import java.util.List;

/**
 * A function that filters input sequence.
 *
 * @param <T> input sequence element type.
 */
public final class Filter<T> extends Function2<Predicate<? super T>, Iterable<? extends T>, List<T>> {

    /**
     * Filters input sequence using passed predicate. Values for which predicate was true are returned as a list.
     *
     * @param p   a predicate.
     * @param seq a sequence.
     * @return a list containing all elements for which predicate is true.
     */
    @Override
    public List<T> call(Predicate<? super T> p, Iterable<? extends T> seq) {
        List<T> result = new ArrayList<>();

        for (T next : seq) {
            if (p.eval(next)) {
                result.add(next);
            }
        }

        return result;
    }

}
