package Collections.Lists;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NoSuchElementException;

/**
 * ArrayUnorderedList class implements the UnorderedListADT interface.
 * It is an ordered list that allows duplicates and does not maintain the order of elements.
 * The elements are stored in an array and can be accessed using an integer index.
 * The size of the list is maintained as an instance variable, and the capacity is increased as needed.
 * The ArrayUnorderedList class provides methods for adding elements to the front, back, and after a specified element.
 * It also provides methods for searching for an element and removing an element from the list.
 * The ArrayUnorderedList class is designed to be efficient and memory-efficient, and it provides constant-time performance for most operations.
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    /**
     * Default constructor creates an empty ArrayUnorderedList.
     */
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Adds an element to the front of the list.
     * If the list is full, the capacity is increased before adding the element.
     * @param element the element to be added to the front of the list
     */
    @Override
    public void addToFront(T element) {
        if (size() == list.length) expandCapacity();
        for (int shift = size(); shift > 0; shift--) {
            list[shift] = list[shift - 1];
        }
        list[0] = element;
        size++;
        modCount++;
    }

    /**
     * Adds an element to the back of the list.
     * If the list is full, the capacity is increased before adding the element.
     * @param element the element to be added to the back of the list
     */
    @Override
    public void addToRear(T element) {
        if (size() == list.length) expandCapacity();
        list[size()] = element;
        size++;
        modCount++;
    }

    /**
     * Adds an element after a specified element in the list.
     * If the list is full, the capacity is increased before adding the element.
     * @param element the element to be added after the specified element
     * @param target the element after which the new element is to be added
     * @throws EmptyCollectionException if the list is empty
     * @throws NoSuchElementException if the target element is not found in the list
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) throw new EmptyCollectionException();
        if (size() == list.length) expandCapacity();
        int index = 0;
        while (index < size() && !target.equals(list[index])) {
            index++;
        }
        if (index == size) {
            throw new NoSuchElementException();
        }
        for (int shift = size; shift > index; shift--) {
            list[shift] = list[shift - 1];
        }
        list[index + 1] = element;
        size++;
        modCount++;
    }
}

