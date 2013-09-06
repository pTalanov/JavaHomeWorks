package ru.spbau.talanov.task5;

/**
 * A one argument function.
 *
 * @param <T> function argument type.
 * @param <R> function return value type.
 */
public abstract class Function<T, R> {

    /**
     * Call this function using passed argument.
     *
     * @param arg function argument.
     * @return a result of the function's application to passed argument.
     */
    public abstract R call(T arg);

    /**
     * Returns a composition of this function and a function passed as an argument.
     *
     * @param fun  a function to compose with.
     * @param <R2> function's return type.
     * @return a function which is a composition of this function and a function passed as an argument.
     */
    public final <R2> Function<T, R2> then(final Function<? super R, R2> fun) {
        final Function<T, R> thisFunction = this;
        return new Function<T, R2>() {
            @Override
            public R2 call(T arg) {
                return fun.call(thisFunction.call(arg));
            }
        };
    }


    /**
     * Returns an identity function.
     *
     * @return a function which always returns passed argument.
     */
    public static <A> Function<A, A> id() {
        return new Function<A, A>() {
            @Override
            public A call(A arg) {
                return arg;
            }
        };
    }
}
