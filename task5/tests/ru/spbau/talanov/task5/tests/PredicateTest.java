package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Function;
import ru.spbau.talanov.task5.Predicate;

public final class PredicateTest {

    @Test
    public void testCall() {
        Predicate<Boolean> p = Predicate.fromFunction(Function.<Boolean>id());
        Assert.assertNotNull(p.call(true));
        Assert.assertNotNull(p.call(false));
    }

    @Test
    public void testEval() {
        Predicate<Boolean> p = Predicate.fromFunction(Function.<Boolean>id());
        Assert.assertEquals(p.call(true), p.eval(true));
        Assert.assertEquals(p.call(false), p.eval(false));
    }

    @Test
    public void testNot() {
        Predicate<Boolean> p = Predicate.fromFunction(Function.<Boolean>id());

        Assert.assertEquals(!p.call(true), p.not().call(true));
        Assert.assertEquals(!p.call(false), p.not().call(false));
    }

    @Test
    public void testAnd() {
        Assert.assertTrue(Predicate.alwaysTrue().and(Predicate.alwaysTrue()).eval(null));
        Assert.assertFalse(Predicate.alwaysFalse().and(Predicate.alwaysTrue()).eval(null));
        Assert.assertFalse(Predicate.alwaysTrue().and(Predicate.alwaysFalse()).eval(null));
        Assert.assertFalse(Predicate.alwaysFalse().and(Predicate.alwaysFalse()).eval(null));
    }

    @Test
    public void testOr() {
        Assert.assertTrue(Predicate.alwaysTrue().or(Predicate.alwaysTrue()).call(null));
        Assert.assertTrue(Predicate.alwaysTrue().or(Predicate.alwaysFalse()).call(null));
        Assert.assertTrue(Predicate.alwaysFalse().or(Predicate.alwaysTrue()).call(null));
        Assert.assertFalse(Predicate.alwaysFalse().or(Predicate.alwaysFalse()).call(null));
    }

    @Test
    public void testAlwaysTrue() {
        Assert.assertNotNull(Predicate.alwaysTrue());

        Assert.assertTrue(Predicate.alwaysTrue().call(null));
        Assert.assertTrue(Predicate.alwaysTrue().call(new Object()));
    }

    @Test
    public void testAlwaysFalse() {
        Assert.assertNotNull(Predicate.alwaysFalse());

        Assert.assertFalse(Predicate.alwaysFalse().call(null));
        Assert.assertFalse(Predicate.alwaysFalse().call(new Object()));
    }

    @Test
    public void testEquals() {
        Assert.assertNotNull(Predicate.equals(null));
        Assert.assertNotNull(Predicate.equals(1));

        Assert.assertTrue(Predicate.equals(null).call(null));
        Assert.assertFalse(Predicate.equals(null).call(new Object()));

        Assert.assertTrue(Predicate.equals(new Integer(42)).call(42));
        Assert.assertFalse(Predicate.equals(new Integer(42)).call(1));
    }

    @Test
    public void testLess() {
        Assert.assertNotNull(Predicate.less(null));
        Assert.assertNotNull(Predicate.less(1));

        Assert.assertTrue(Predicate.less(10).call(15));
        Assert.assertFalse(Predicate.less(15).call(10));
        Assert.assertFalse(Predicate.less(0).call(0));
    }
}

