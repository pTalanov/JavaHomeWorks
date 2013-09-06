package ru.spbau.talanov.task5;

import org.jetbrains.annotations.NotNull;

/**
 * A one argument predicate.
 */
public abstract class Predicate<T> extends Function<T, Boolean> {

    /**
     * Evaluates this predicate against passed argument calling eval and boxing it's return value.
     *
     * @param arg predicate argument.
     * @return value of this predicate on passed argument. Either true or false, never null.
     */
    @Override
    public final Boolean call(T arg) {
        return eval(arg);
    }

    /**
     * Evaluates this predicate against passed argument.
     *
     * @param arg predicate argument.
     * @return value of this predicate evaluated against passed argument.
     */
    public abstract boolean eval(T arg);

    /**
     * Get a negation of this predicate.
     *
     * @return a negation of this predicate.
     */
    public final Predicate<T> not() {
        final Predicate<T> thisPredicate = this;
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return !thisPredicate.eval(arg);
            }
        };
    }

    /**
     * Get a logical 'and' of this predicate and the predicate passed as an argument.
     * Conjunction evaluation is lazy, that is second predicate won't be evaluated if first predicate value is false.
     *
     * @param secondPredicate a second argument of conjunction.
     * @return a conjunction of this predicate and an argument.
     */
    public final Predicate<T> and(@NotNull final Predicate<? super T> secondPredicate) {
        final Predicate<T> firstPredicate = this;
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return firstPredicate.eval(arg) && secondPredicate.eval(arg);
            }
        };
    }

    /**
     * Get a logical 'or' of this predicate and the predicate passed as an argument.
     * Disjunction evaluation is lazy, that is second predicate won't be evaluated if first predicate value is true.
     *
     * @param secondPredicate a second argument of disjunction.
     * @return a disjunction of this predicate and an argument.
     */
    public final Predicate<T> or(@NotNull final Predicate<? super T> secondPredicate) {
        final Predicate<T> firstPredicate = this;
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return firstPredicate.eval(arg) || secondPredicate.eval(arg);
            }
        };
    }

    /**
     * Get a predicate which always returns true.
     *
     * @param <T> predicate argument type.
     * @return a predicate which always returns true.
     */
    @NotNull
    public static <T> Predicate<T> alwaysTrue() {
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return true;
            }
        };
    }

    /**
     * Get a predicate which always returns false.
     *
     * @param <T> predicate argument type.
     * @return a predicate which always returns false.
     */
    @NotNull
    public static <T> Predicate<T> alwaysFalse() {
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return false;
            }
        };
    }

    /**
     * Get a predicate which checks if value's compareTo method returns 0.
     * If passed value to compare with is null the predicate returned uses reference comparison.
     *
     * @param value a value to compare with.
     * @param <T>   predicate argument type.
     * @return a predicate which checks it's argument's equality to 'value' parameter.
     */
    public static <T> Predicate<T> equals(final Comparable<? super T> value) {
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return null == value ? arg == value : (0 == value.compareTo(arg));
            }
        };
    }

    /**
     * Returns a predicate which checks if it's value is less than argument of the predicate.
     *
     * @param value a value to compare with.
     * @param <T>   predicate argument type.
     * @return a predicate which checks if it's argument is less than 'value' parameter.
     */
    public static <T> Predicate<T> less(final Comparable<? super T> value) {
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return value.compareTo(arg) < 0;
            }
        };
    }

    /**
     * Constructs predicate based on function.
     *
     * @param fun function to be used as predicate.
     * @param <T> predicate argument type.
     * @return a predicate which exactly the same as passed function.
     */
    public static <T> Predicate<T> fromFunction(@NotNull final Function<T, Boolean> fun) {
        return new Predicate<T>() {
            @Override
            public boolean eval(T arg) {
                return fun.call(arg);
            }
        };
    }
}
