package Collections.Lists;

import Collections.Exceptions.NonComparableElementException;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NonComparableElementException {
        if (!(element instanceof Comparable)) throw new NonComparableElementException();
        if (size() == list.length) expandCapacity();
        int index = size; // Set index to the end of the list
        while (index > 0 && ((Comparable<T>) element).compareTo(list[index - 1]) < 0) {
            // Move elements to the right until the correct position is found
            list[index] = list[index - 1];
            index--;
        }
        list[index] = element; // Insert the new element
        size++;
        modCount++;
    }
}
