package ru.spbau.talanov.task5;

/**
 * A two arguments function.
 *
 * @param <T1> first argument type.
 * @param <T2> second argument type.
 * @param <R>  return value type.
 */
public abstract class Function2<T1, T2, R> {

    /**
     * Calls this function using passed arguments.
     *
     * @param arg1 first argument.
     * @param arg2 second argument.
     * @return a result of this function's  application to passed arguments.
     */
    public abstract R call(T1 arg1, T2 arg2);

    /**
     * Returns a composition of this function and a function passed as an argument.
     *
     * @param fun  a function to compose with.
     * @param <R2> function's return type.
     * @return a function which is a composition of this function and a function passed as an argument.
     */
    public final <R2> Function2<T1, T2, R2> then(final Function<? super R, R2> fun) {
        final Function2<T1, T2, R> thisFunction = this;
        return new Function2<T1, T2, R2>() {
            @Override
            public R2 call(T1 arg1, T2 arg2) {
                return fun.call(thisFunction.call(arg1, arg2));
            }
        };
    }

    /**
     * Get this function with first argument substituted by passed argument.
     *
     * @param arg1 first function argument.
     * @return a one argument function produced by binding first argument of this function to passed value.
     */
    public final Function<T2, R> bind1(final T1 arg1) {
        return new Function<T2, R>() {
            @Override
            public R call(T2 arg2) {
                return Function2.this.call(arg1, arg2);
            }
        };
    }

    /**
     * Get this function with second argument substituted by passed argument.
     *
     * @param arg2 second function argument.
     * @return a one argument function produced by binding second argument of this function to passed value.
     */
    public final Function<T1, R> bind2(final T2 arg2) {
        return new Function<T1, R>() {
            @Override
            public R call(T1 arg1) {
                return Function2.this.call(arg1, arg2);
            }
        };
    }

}
