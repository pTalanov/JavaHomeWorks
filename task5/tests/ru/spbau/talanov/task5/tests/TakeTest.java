package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Take;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class TakeTest {

    @Test
    public void emptySequenceTest() {
        Take<Integer> take = new Take<>();
        List<Integer> result = take.call(Collections.<Integer>emptyList(), 10);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void notEnoughElementsTest() {
        List<Integer> testData = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5));
        Take<Integer> take = new Take<>();

        List<Integer> result = take.call(testData, testData.size() + 100);

        Assert.assertNotNull(result);
        Assert.assertEquals(testData.size(), result.size());
    }

    @Test
    public void enoughElementsTest() {
        List<Integer> testData = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5));
        Take<Integer> take = new Take<>();

        List<Integer> result = take.call(testData, testData.size() - 2);

        Assert.assertNotNull(result);
        Assert.assertEquals(testData.size() - 2, result.size());

        for (int i = 0; i < result.size(); i++) {
            Assert.assertEquals(testData.get(i), result.get(i));
        }
    }
}
