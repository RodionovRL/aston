package ru.aston.homework.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuickSortTest {
    String[] testArr = new String[20];
    RuList<String> testRuArrayList = new RuArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 17; i++) {
            testArr[i] = "test" + i;
        }
    }
    @Test
    void sort() {
        Sortable<String> ruQuickSort = new QuickSort<>();
        for (int i = 0; i < 17; i++) {
            testRuArrayList.add(testArr[i]);
        }

        Arrays.sort(testArr, 0, 17, ((String::compareTo)));

        ruQuickSort.sort(testRuArrayList);

        assertAll(
                () -> assertArrayEquals(testArr, testRuArrayList.toArray(new String[20]))
        );
    }
}
