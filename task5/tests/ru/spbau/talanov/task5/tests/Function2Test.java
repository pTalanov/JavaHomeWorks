package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Function;
import ru.spbau.talanov.task5.Function2;

public final class Function2Test {

    @Test
    public void testCall() {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer arg1, Integer arg2) {
                return arg1 + arg2;
            }
        };

        Assert.assertEquals(sum.call(2, 2), new Integer(4));
    }

    @Test
    public void testThen() {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer arg1, Integer arg2) {
                return arg1 + arg2;
            }
        };
        Function<Integer, Integer> plus3 = new Function<Integer, Integer>() {
            @Override
            public Integer call(Integer arg) {
                return arg + 3;
            }
        };

        Assert.assertNotNull(sum.then(plus3));
        Assert.assertEquals(sum.then(plus3).call(10, 20), new Integer(33));
    }

    @Test
    public void testBind1() {
        Function2<Integer, Integer, Integer> minus = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer arg1, Integer arg2) {
                return arg1 - arg2;
            }
        };
        Function<Integer, Integer> twoMinus = minus.bind1(2);

        Assert.assertNotNull(twoMinus);
        Assert.assertEquals(twoMinus.call(50), minus.call(2, 50));
    }

    @Test
    public void testBind2() {
        Function2<Integer, Integer, Integer> div = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer arg1, Integer arg2) {
                return arg1 / arg2;
            }
        };
        Function<Integer, Integer> div2 = div.bind2(2);

        Assert.assertNotNull(div2);
        Assert.assertEquals(div2.call(50), div.call(50, 2));
    }
}
