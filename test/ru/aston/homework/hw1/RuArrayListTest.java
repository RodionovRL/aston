package ru.aston.homework.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class RuArrayListTest {

    String[] testArr = new String[20];
    String[] resultArr = new String[20];
    RuList<String> testRuArrayList = new RuArrayList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 17; i++) {
            testArr[i] = "test" + i;
        }
    }

    @Test
    void createRuArrayListWithNotDefaultCapacity() {
        Object[] objects = new Object[25];
        RuList<String> ruArrayList1 = new RuArrayList<>(25);
        RuList<String> ruArrayList2 = new RuArrayList<>(testArr);
        RuList<String> ruArrayList3 = new RuArrayList<>();
        RuList<String> ruArrayList4 = new RuArrayList<>(0);
        RuList<String> ruArrayList5 = new RuArrayList<>(-1);


        assertAll(
                () -> assertArrayEquals(objects, ruArrayList1.toArray(new Object[25])),
                () -> assertArrayEquals(testArr, ruArrayList2.toArray(new String[ruArrayList2.getSize()])),
                () -> assertArrayEquals(new String[0], ruArrayList3.toArray(new String[ruArrayList3.getSize()])),
                () -> assertArrayEquals(new String[0], ruArrayList4.toArray(new String[ruArrayList4.getSize()])),
                () -> assertArrayEquals(new String[0], ruArrayList5.toArray(new String[ruArrayList5.getSize()]))

        );
    }

    @Test
    void addElementsOneByOneInBounds() {
        for (int i = 0; i < 10; i++) {
            testRuArrayList.add(testArr[i]);
            assertEquals(testRuArrayList.get(i), (testArr[i]));
        }
    }

    @Test
    void addElementsOneByOneOutOfBounds() {
        for (int i = 0; i < 15; i++) {
            testRuArrayList.add(testArr[i]);
            assertEquals(testRuArrayList.get(i), (testArr[i]));
        }
    }

    @Test
    void setElementByIndex() {
        for (int i = 1; i < 10; i++) {
            testRuArrayList.add(testArr[i]);
        }
        String testElement = "don't worry";
        assertAll(
                () -> assertDoesNotThrow(() -> (testRuArrayList.set(testElement, 5))),
                () -> assertEquals(testElement, testRuArrayList.get(5)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.set("really?", 500)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.set("really?", -1)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> testRuArrayList.set("really?", testRuArrayList.getSize()))
        );
    }

    @Test
    void addElementByIndexInArrayBounds() {
        String firstElement = "первый нах";
        testRuArrayList.add(firstElement, 0);

        for (int i = 1; i < 10; i++) {
            testRuArrayList.add("old element " + i);
        }

        int sizeBefore = testRuArrayList.getSize();

        assertAll(
                () -> assertEquals(testRuArrayList.get(0), firstElement),
                () -> assertDoesNotThrow(() -> (testRuArrayList.add("i'm added", 5))),
                () -> assertTrue(() -> ((sizeBefore + 1) == testRuArrayList.getSize())),
                () -> assertDoesNotThrow(
                        () -> (testRuArrayList.add("i'm added too", testRuArrayList.getSize() - 1))),
                () -> assertTrue(() -> ((sizeBefore + 2) == testRuArrayList.getSize())),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> testRuArrayList.add("really?", testRuArrayList.getSize() + 1)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> testRuArrayList.add("really?", 500)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> testRuArrayList.add("really?", -1))

        );
    }

    @Test
    void getElementByIndex() {
        for (int i = 0; i < 10; i++) {
            testRuArrayList.add("old element " + i);
        }
        String testText = "it's me";
        testRuArrayList.set(testText, 5);

        String result = testRuArrayList.get(5);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(testText, result),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.get(500)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.get(-5)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.get(10)),
                () -> assertThrows(IndexOutOfBoundsException.class,
                        () -> testRuArrayList.get(testRuArrayList.getSize()))
        );

    }

    @Test
    void removeElementByIndex() {
        for (int i = 0; i < 10; i++) {
            testRuArrayList.add("not for remove " + i);
        }

        String testText5 = "remove me 5";
        testRuArrayList.set(testText5, 5);

        String result5 = testRuArrayList.remove(5);

        assertAll(
                () -> assertNotNull(result5),
                () -> assertEquals(testText5, result5),
                () -> assertEquals(testRuArrayList.get(5), "not for remove 6"),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.remove(500)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.remove(-5)),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> testRuArrayList.remove(10))
        );
    }

    @Test
    void testClear() {
        for (int i = 0; i < 20; i++) {
            testRuArrayList.add(testArr[i]);
        }

        assertAll(
                () -> assertEquals(testRuArrayList.getSize(), 20),
                () -> assertArrayEquals(testArr, testRuArrayList.toArray(resultArr))
        );

        testRuArrayList.clear();

        assertAll(
                () -> assertEquals(testRuArrayList.getSize(), 0),
                () -> assertArrayEquals(new Object[20], testRuArrayList.toArray(new Object[20]))
        );
    }

    @Test
    void testToArray() {
        for (int i = 0; i < 20; i++) {
            testRuArrayList.add(testArr[i]);
        }

        assertAll(
                () -> assertDoesNotThrow(() -> testRuArrayList.toArray(new String[20])),
                () -> assertDoesNotThrow(() -> testRuArrayList.toArray(new String[10])),
                () -> assertArrayEquals(testArr, testRuArrayList.toArray(new String[20])),
                () -> assertArrayEquals(testArr, testRuArrayList.toArray(new String[10]))
        );
    }

    @Test
    void testToString() {
        for (int i = 0; i < 20; i++) {
            testRuArrayList.add(testArr[i]);
        }

        assertEquals("RuArrayList{" + Arrays.toString(testArr) + "}", testRuArrayList.toString());
    }

    @Test
    void testSort() {
        for (int i = 0; i < 17; i++) {
            testRuArrayList.add(testArr[i]);
        }

        Arrays.sort(testArr, 0, 17, (Comparator.reverseOrder()));

        testRuArrayList.sort((Comparator.reverseOrder()));

        assertAll(
                () -> assertArrayEquals(testArr, testRuArrayList.toArray(new String[20]))
        );
    }
}