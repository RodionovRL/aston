package ru.aston.homework.hw1;

import java.util.Arrays;
import java.util.Comparator;

public class RuArrayList<E> implements RuList<E> {
    /**
     * Размер RuArrayList по умолчанию
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * Количество элементов в массиве
     */
    private int size;
    private Object[] elements;

    /**
     * Если при создании RuArrayList не указан его начальный размер, то задаётся размер по умолчанию
     */
    public RuArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Создание ruArrayList с заданным начальным размером.
     *
     * @param capacity - размер ruArrayList
     */
    public RuArrayList(int capacity) {
        if (capacity <= 0) {
            capacity = DEFAULT_CAPACITY;
        }
        this.elements = new Object[capacity];
    }

    /**
     * Создание RuArrayList уже заполненного значениями.
     */
    public RuArrayList(E[] elements) {
        this.elements = elements;
        this.size = elements.length;
    }

    /**
     * Добавление элемента в конец списка.
     * Если размер списка равен кол-ву данных size - это означает, что он заполнен и тогда
     * список увеличивается методом grow().
     *
     * @param e - добавляемый элемент
     * @return true возвращается после добавление элемента.
     */
    @Override
    public boolean add(E e) {
        if (elements.length == size) {
            elements = grow();
        }
        elements[size] = e;
        size++;
        return true;
    }

    /**
     * Добавление элемента в определённое место списка.
     * Перед добавлением через метод checkIndex() производится проверка номера позиции,
     * на которую будет добавлен элемент.
     *
     * @param e   - добавляемый элемент
     * @param pos - позиция в массиве, на которую будет добавлен элемент
     * @return true возвращается после добавление элемента.
     * @throws IndexOutOfBoundsException - в случае если индекс за пределами массива.
     */
    @Override
    public boolean add(E e, int pos) {
        checkIndexForWrite(pos);
        Object[] elementsTmp = this.elements;
        if (elementsTmp.length == size) {
            elementsTmp = grow();
        }

        System.arraycopy(elementsTmp, pos, elementsTmp, pos + 1, size - pos);

        elementsTmp[pos] = e;
        size++;
        return true;
    }

    /**
     * Замена элемента по индексу.
     * Перед добавлением через метод checkIndex() производится проверка индекса,
     * на которую будет добавлен элемент.
     *
     * @param e     - новый элемент
     * @param index - позиция в массиве, на которую будет добавлен элемент
     * @return возвращается значение, которое было в этом месте до замены.
     * @throws IndexOutOfBoundsException - в случае если индекс за пределами массива.
     */
    @Override
    public E set(E e, int index) {
        checkIndexForRead(index);
        E oldValue = getValue(index);
        elements[index] = e;
        return oldValue;
    }

    /**
     * Получение элемента по индексу
     *
     * @param index - индекс элемента, который
     * @return - возвращается содержимое соответствующей ячейки
     * @throws IndexOutOfBoundsException - в случае если индекс за пределами массива.
     */
    @Override
    public E get(int index) {
        checkIndexForRead(index);
        return getValue(index);
    }

    /**
     * Метод возвращает массив элементов значения которого равны значениям текущего ruArrayList
     *
     * @param arr - массив, который нужно заполнить значениями текущего ruArrayList
     * @return массив элементов
     * @throws ArrayStoreException  если тип данных arr не является супертипом для типа параметров текущего ruArrayList
     * @throws NullPointerException если переданный в метод массив равен null
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] arr) {
        if (arr.length < size) {
            return (T[]) Arrays.copyOf(elements, size, arr.getClass());
        }
        System.arraycopy(elements, 0, arr, 0, size);
        return arr;
    }

    /**
     * Метод возвращает число записей имеющихся в массиве. (Не путать с размером внутреннего массива).
     *
     * @return - число записей в листе.
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * Удаление элемента из листа по индексу. Элемент удаляется из массива вместе с полем, в котором он был.
     * Массив при этом схлопывается на одну ячейку.
     *
     * @param index - индекс элемента, который должен быть удалён из листа.
     * @return метод возвращает удалённых элемент.
     * @throws IndexOutOfBoundsException - в случае если индекс за пределами массива.
     */
    @Override
    public E remove(int index) {
        checkIndexForRead(index);
        E removed = getValue(index);
        final Object[] elementsTmp = elements;
        final int newSize = size - 1;
        if (newSize > index) {
            System.arraycopy(elementsTmp, index + 1, elementsTmp, index, newSize - index);
        }
        size = newSize;
        elementsTmp[size] = null;
        return removed;
    }

    /**
     * Очистка листа. Лист заполняется null значениями.
     */
    @Override
    public void clear() {
        while (size > 0) {
            elements[--size] = null;
        }
    }

    /**
     * Метод сортировки листа стандартным способом Arrays.sort
     *
     * @param comparator - компаратор для определения правил сортировки.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<E> comparator) {
        Arrays.sort((E[]) elements, 0, size, comparator);
    }

    /**
     * Метод предназначенный для оформления массива для перевода в строку.
     *
     * @return - строковое представление данных массива.
     */
    @Override
    public String toString() {
        return "RuArrayList{"
                + Arrays.toString(elements)
                + "}";
    }

    /**
     * Метод увеличения размера внутреннего массива.
     * Массив всегда увеличивается на значение DEFAULT_CAPACITY / 2.
     *
     * @return возвращается увеличенный массив заполненный теми же данными, что и массив до увеличения.
     */
    private Object[] grow() {
        return elements = Arrays.copyOf(elements, elements.length + DEFAULT_CAPACITY / 2);
    }

    /**
     * Проверка наличия в массиве элемента с заданным индексом.
     *
     * @param index - значение проверяемого индекса.
     * @throws IndexOutOfBoundsException - в случае если индекс за пределами массива.
     */
    private void checkIndexForWrite(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("индекс за пределами массива: index = %d, size = %d", index, size));
        }
    }

    private void checkIndexForRead(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    String.format("индекс за пределами массива: index = %d, size = %d", index, size));
        }
    }

    @SuppressWarnings("unchecked")
    private E getValue(int pos) {
        return (E) elements[pos];
    }
}
