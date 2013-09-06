package ru.spbau.talanov.task5.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({FunctionTest.class,
        Function2Test.class,
        MappingComparatorTest.class,
        PredicateTest.class,
        TakeTest.class,
        FilterTest.class})
public final class FuncLibSuite {
}
