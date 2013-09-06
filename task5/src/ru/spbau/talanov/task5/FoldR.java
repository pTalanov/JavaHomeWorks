package ru.spbau.talanov.task5;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;

/**
 * * A foldr function. Accumulates values stored in list traversing it from tail to head using accumulator function.
 *
 * @param <T> list elements` superclass.
 * @param <R> result type.
 */
public final class FoldR<T, R> extends Function2<Function2<? super T, ? super R, R>, List<? extends T>, R> {

    @NotNull
    private final R initialValue;

    /**
     * Constructs a new FoldR object using passed value as initial value.
     *
     * @param initialValue initial value to use in accumulator function.
     */
    public FoldR(@NotNull R initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * Iterates in reverse order over passed list accumulating result value using passed function and initial value.
     *
     * @param acc  an accumulator function.
     * @param list a list.
     * @return accumulated result or null if passed list is empty.
     * @throws NullPointerException if passed list and/or accumulator function is null.
     */
    @Override
    public R call(Function2<? super T, ? super R, R> acc, List<? extends T> list) {
        R result = initialValue;
        ListIterator<? extends T> i = list.listIterator(list.size());

        while (i.hasPrevious()) {
            result = acc.call(i.previous(), result);
        }

        return result;
    }

}
