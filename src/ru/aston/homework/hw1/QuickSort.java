package ru.aston.homework.hw1;

public class QuickSort<E extends Comparable<E>> implements Sortable<E> {

    @Override
    public void sort(RuList<E> list) {
        quickSort(list, 0, list.getSize() - 1);
    }

    private void quickSort(RuList<E> list, int low, int high) {
        if (list.getSize() == 0) {
            return;
        }

        if (low >= high) {
            return;
        }

        int middle = low + (high - low) / 2;
        E pivot = list.get(middle);

        int i = low;
        int j = high;
        while (i <= j) {
            while (list.get(i).compareTo(pivot) < 0) {
                i++;
            }
            while (list.get(j).compareTo(pivot) > 0) {
                j--;
            }

            if (i <= j) {
                E swap = list.set(list.get(j), i);
                list.set(swap, j);
                i++;
                j--;
            }
        }

        if (low < j) {
            quickSort(list, low, j);
        }

        if (high > i) {
            quickSort(list, i, high);
        }
    }
}
