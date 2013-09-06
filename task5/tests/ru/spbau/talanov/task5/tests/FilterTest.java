package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Filter;
import ru.spbau.talanov.task5.Predicate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FilterTest {

    @Test
    public void emptySequenceTest() {
        Filter<Integer> filter = new Filter<>();

        List<Integer> result = filter.call(Predicate.alwaysTrue(), Collections.<Integer>emptyList());

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void filteringTest() {
        List<String> testData = Collections.unmodifiableList(Arrays.asList("even", "even", "odd", "even"));
        List<String> filteredTestData = Collections.unmodifiableList(Arrays.asList("odd"));

        Filter<String> f = new Filter<>();

        List<String> result = f.call(new Predicate<String>() {
            @Override
            public boolean eval(String arg) {
                return !"even".equals(arg);
            }
        }, testData);

        Assert.assertNotNull(result);
        Assert.assertEquals(filteredTestData.size(), result.size());

        for (int i = 0; i < filteredTestData.size(); i++) {
            Assert.assertEquals(filteredTestData.get(i), result.get(i));
        }
    }

}
