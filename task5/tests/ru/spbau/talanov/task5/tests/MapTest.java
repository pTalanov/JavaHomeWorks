package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Function;
import ru.spbau.talanov.task5.Map;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class MapTest {

    @Test
    public void emptySequenceTest() {
        Map<String, Integer> m = new Map<>();

        List<Integer> result = m.call(new Function<String, Integer>() {
            @Override
            public Integer call(String arg) {
                return arg.length();
            }
        }, Collections.<String>emptyList());

        Assert.assertNotNull(result);
        Assert.assertTrue(0 == result.size());
    }

    @Test
    public void mapsCorrectlyTest() {
        List<String> testData = Collections.unmodifiableList(Arrays.asList("one", "two", "three", "four"));
        Map<String, Integer> m = new Map<>();

        List<Integer> lengths = m.call(new Function<String, Integer>() {
            @Override
            public Integer call(String arg) {
                return arg.length();
            }
        }, testData);

        Assert.assertNotNull(lengths);
        Assert.assertEquals(testData.size(), lengths.size());

        for (int i = 0; i < testData.size(); i++) {
            Assert.assertEquals(new Integer(testData.get(i).length()), lengths.get(i));
        }
    }

}
