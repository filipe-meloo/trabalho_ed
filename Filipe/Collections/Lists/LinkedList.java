package Collections.Lists;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NoSuchElementException;

import java.util.Iterator;

public class LinkedList<T> implements ListADT<T>, Iterable<T> {
    protected Node<T> head, tail;
    protected int size, modCount;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    /**
     * Removes and returns the first element in the list.
     *
     * @return The first element in the list.
     * @throws EmptyCollectionException if the list is empty.
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T removed = head.getElement();
        head = head.getNext();
        size--;
        modCount++;
        return removed;
    }

    /**
     * Removes and returns the last element in the list.
     *
     * @return The last element in the list.
     * @throws EmptyCollectionException if the list is empty.
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T removed = tail.getElement();
        Node<T> current = head;
        while (current.getNext() != tail) {
            current = current.getNext();
        }
        current.setNext(null);
        tail = current;
        size--;
        modCount++;
        return removed;
    }

    /**
     * Removes a specified element from the list.
     *
     * @param element The element to be removed.
     * @return The removed element.
     * @throws EmptyCollectionException if the list is empty.
     * @throws NoSuchElementException   if the specified element is not found in the list.
     */
    public T remove(T element) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        if (!contains(element)) {
            throw new NoSuchElementException();
        }
        T removed;
        Node<T> current = head;
        if (current.getElement().equals(element)) {
            removed = removeFirst();
        } else if (tail.getElement().equals(element)) {
            removed = removeLast();
        } else {
            while (!current.getNext().getElement().equals(element)) {
                current = current.getNext();
            }
            removed = current.getNext().getElement();
            current.setNext(current.getNext().getNext());
            size--;
            modCount++;
        }
        return removed;
    }

    /**
     * Returns the first element in the list.
     *
     * @return The first element in the list.
     * @throws EmptyCollectionException if the list is empty.
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return head.getElement();
    }

    /**
     * Returns the last element in the list.
     *
     * @return The last element in the list.
     * @throws EmptyCollectionException if the list is empty.
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return tail.getElement();
    }

    /**
     * Checks if the list contains a specified element.
     *
     * @param target The element to check for in the list.
     * @return true if the element is found, false otherwise.
     * @throws EmptyCollectionException if the list is empty.
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        Node<T> current = head;
        while (current != null) {
            if (current.getElement().equals(target)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Returns the size of the list.
     *
     * @return The size of the list.
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Clears the list by setting head, tail, and size to null/0.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return A string representation of the list.
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            Node<T> current = head;
            while (current != null) {
                result += current.getElement() + " ";
                current = current.getNext();
            }
        }
        return result + "}";
    }

    /**
     * Returns a string representation of the list using a recursive helper method.
     *
     * @return A string representation of the list.
     */
    public String print() {
        return getClass().getSimpleName() + " { " + print(head) + "}";
    }

    /**
     * Recursive helper method for creating a string representation of the list.
     *
     * @param current The current node in the recursive traversal.
     * @return A string representation of the list.
     */
    private String print(Node<T> current) {
        if (current == null) {
            return "";
        }
        return current.getElement() + " " + print(current.getNext());
    }
    /**
     * Converts the list to an array.
     *
     * @return An array containing the elements of the list.
     */

    public T[] toArray() {
        T[] result = (T[]) new Comparable[size()];
        Node<T> current = head;
        for (int i = 0; i < size(); i++) {
            result[i] = current.getElement();
            current = current.getNext();
        }
        return result;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index.
     * @throws EmptyCollectionException if the list is empty.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public T get(int index) throws EmptyCollectionException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element The element to be added to the list.
     */
    @Override
    public void add(T element) {
        if (isEmpty()) {
            head = tail = new Node<>(element);
        } else {
            tail.setNext(new Node<>(element));
            tail = tail.getNext();
        }
        size++;
        modCount++;

    }

    public void set(int index, T target) throws EmptyCollectionException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        current.setElement(target);
    }

    public void replace(T existingElement, T newElement) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) throw new EmptyCollectionException();
        if (!contains(existingElement)) throw new NoSuchElementException();
        Node<T> current = head;
        while (current != null) {
            if (current.getElement().equals(existingElement)) {
                current.setElement(newElement);
            }
            current = current.getNext();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<T>() {};
    }

    private abstract class BasicIterator<E> implements Iterator<T> {
        private Node<T> current;
        private int expectedModCount;
        private boolean okToRemove;

        public BasicIterator() {
            this.current = head;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T next = current.getElement();
            current = current.getNext();
            okToRemove = true;
            return next;
        }

        public void remove() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            LinkedList.this.remove(current.getElement());
            expectedModCount++;
            okToRemove = false;
        }
    }
}
