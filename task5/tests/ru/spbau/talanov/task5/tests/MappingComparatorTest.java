package ru.spbau.talanov.task5.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.talanov.task5.Function;
import ru.spbau.talanov.task5.MappingComparator;

public final class MappingComparatorTest {

    @Test
    public void compareTest() {
        MappingComparator<String, Integer> comparator =
                new MappingComparator<>(new Function<String, Integer>() {
                    @Override
                    public Integer call(final String arg) {
                        return null == arg ? 0 : arg.length();
                    }
                });

        Assert.assertTrue(comparator.compare("123456789", "0") > 0);
        Assert.assertTrue(comparator.compare("0", "123456789") < 0);
        Assert.assertTrue(comparator.compare("0", "0") == 0);
    }

}
