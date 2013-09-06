package ru.spbau.talanov.task5;

import java.util.ArrayList;
import java.util.List;

/**
 * A function which maps each element of input sequence using passed function to element of output list.
 *
 * @param <F> input sequence element type.
 * @param <T> output list element type.
 */
public final class Map<F, T> extends Function2<Function<? super F, ? extends T>, Iterable<? extends F>, List<T>> {

    /**
     * Maps each element of input sequence to element of output list.
     *
     * @param mapper   a function which should be used to map elements.
     * @param iterable input sequence.
     * @return a list of mapped items.
     */
    @Override
    public List<T> call(Function<? super F, ? extends T> mapper, Iterable<? extends F> iterable) {
        List<T> result = new ArrayList<>();

        for (F element : iterable) {
            result.add(mapper.call(element));
        }

        return result;
    }

}
