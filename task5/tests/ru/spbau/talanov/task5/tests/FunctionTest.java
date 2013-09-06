package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Function;
import ru.spbau.talanov.task5.Predicate;

public final class FunctionTest {

    @Test
    public void testCall() {
        Function<Integer, Integer> f = new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer arg) {
                return arg * 6;
            }
        };

        Assert.assertEquals(f.call(10), new Integer(60));
    }

    @Test
    public void testThenFunction() {
        Function<Integer, Integer> mul6 = new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer arg) {
                return arg * 6;
            }
        };
        Function<Integer, Integer> sq = new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer arg) {
                return arg * arg;
            }
        };

        Assert.assertNotNull(mul6.then(sq));
        Assert.assertEquals(mul6.then(sq).call(10), sq.call(mul6.call(10)));
    }

    @Test
    public void testThenPredicate() {
        Function<Integer, Integer> mul3 = new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer arg) {
                return arg * 3;
            }
        };
        Predicate<Integer> equals3 = Predicate.equals(3);

        Assert.assertNotNull(mul3.then(equals3));
        Assert.assertEquals(mul3.then(equals3).call(10), equals3.call(mul3.call(10)));
        Assert.assertEquals(mul3.then(equals3).call(1), equals3.call(mul3.call(1)));
    }

}
