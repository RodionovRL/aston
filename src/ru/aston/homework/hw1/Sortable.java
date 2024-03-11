package ru.aston.homework.hw1;

public interface Sortable<E extends Comparable<E>> {
    void sort(RuList<E> list);
}
