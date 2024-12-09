package Collections.Lists;

import Collections.Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list data structure that supports constant time insertion and removal of elements at the beginning and end of the list.
 *
 * @param <T> the type of elements in the list
 */
public class DoublyLinkedList<T> implements ListADT<T>, Iterable<T> {

    /**
     * The node class for the doubly linked list.
     *
     * @param <T> the type of elements in the list
     */
    protected static class DoublyNode<T> {
        private T element;
        private DoublyNode<T> previous;
        private DoublyNode<T> next;

        /**
         * Constructs a new DoublyNode with the given element.
         *
         * @param element the element to store in the node
         */
        public DoublyNode(T element) {
            this.element = element;
        }

        /**
         * Returns the element stored in the node.
         *
         * @return the element stored in the node
         */
        public T getElement() {
            return element;
        }

        /**
         * Sets the element stored in the node.
         *
         * @param element the element to store in the node
         */
        public void setElement(T element) {
            this.element = element;
        }

        /**
         * Returns the previous node in the doubly linked list.
         *
         * @return the previous node in the doubly linked list
         */
        public DoublyNode<T> getPrevious() {
            return previous;
        }

        /**
         * Sets the previous node in the doubly linked list.
         *
         * @param previous the previous node in the doubly linked list
         */
        public void setPrevious(DoublyNode<T> previous) {
            this.previous = previous;
        }

        /**
         * Returns the next node in the doubly linked list.
         *
         * @return the next node in the doubly linked list
         */
        public DoublyNode<T> getNext() {
            return next;
        }

        /**
         * Sets the next node in the doubly linked list.
         *
         * @param next the next node in the doubly linked list
         */
        public void setNext(DoublyNode<T> next) {
            this.next = next;
        }
    }

    /**
     * The head node of the doubly linked list.
     */
    protected DoublyNode<T> head;

    /**
     * The tail node of the doubly linked list.
     */
    protected DoublyNode<T> tail;

    /**
     * The number of elements in the list.
     */
    protected int count;

    /**
     * A counter used to detect changes to the list during iteration.
     */
    protected int modCount;

    /**
     * Constructs an empty doubly linked list.
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
        this.modCount = 0;
    }

    /**
     * Removes and returns the first element in the list.
     *
     * @return the first element in the list
     * @throws EmptyCollectionException if the list is empty
     */
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T removed = head.getElement();
        head = head.getNext();
        count--;
        if (isEmpty()) {
            tail = null;
        } else {
            head.setPrevious(null);
        }
        modCount++;
        return removed;
    }

    /**
     * Removes and returns the last element in the list.
     *
     * @return the last element in the list
     * @throws EmptyCollectionException if the list is empty
     */
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        T removed = tail.getElement();
        tail = tail.getPrevious();
        count--;
        if (isEmpty()) {
            head = null;
        } else {
            tail.setNext(null);
        }
        modCount++;
        return removed;
    }

    /**
     * Removes the first occurrence of the given element from the list.
     *
     * @param element the element to remove
     * @return true if the element was removed, false if it was not present in the list
     * @throws EmptyCollectionException if the list is empty
     * @throws NoSuchElementException   if the element is not present in the list
     */
    public T remove(T element) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        boolean found = false;
        DoublyNode<T> current = head;
        while (current != null && !found) {
            if (element.equals(current.getElement())) {
                found = true;
            } else {
                current = current.getNext();
            }
        }
        if (!found) {
            throw new NoSuchElementException();
        }
        if (size() == 1) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = current.getNext();
            head.setPrevious(null);
        } else if (current.equals(tail)) {
            tail = current.getPrevious();
            tail.setNext(null);
        } else {
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }
        count--;
        modCount++;
        return current.getElement();
    }

    /**
     * Returns the first element in the list.
     *
     * @return the first element in the list
     * @throws EmptyCollectionException if the list is empty
     */
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return head.getElement();
    }

    /**
     * Returns the last element in the list.
     *
     * @return the last element in the list
     * @throws EmptyCollectionException if the list is empty
     */
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return tail.getElement();
    }

    /**
     * Returns true if the list contains the given element, false otherwise.
     *
     * @param target the element to search for
     * @return true if the list contains the given element, false otherwise
     * @throws EmptyCollectionException if the list is empty
     */
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        DoublyNode<T> current = head;
        while (current != null) {
            if (target.equals(current.getElement())) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Returns true if the list is empty, false otherwise.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " { ";
        if (!isEmpty()) {
            DoublyNode<T> current = head;
            while (current != null) {
                result += current.getElement() + " ";
                current = current.getNext();
            }
        }
        return result + "}";
    }

    /**
     * Returns the element at the given index.
     *
     * @param index the index of the element to return
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        DoublyNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    /**
     * Adds the given element to the end of the list.
     *
     * @param element the element to add
     */
    public void add(T element) {
        if (isEmpty()) {
            head = tail = new DoublyNode<>(element);
        } else {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        count++;
        modCount++;
    }


    /**
     * Reverses the order of the elements in this list.
     *
     * @return a new list containing the elements of this list in reverse order
     */
    public DoublyLinkedUnorderedList<T> reverse() {
        DoublyLinkedUnorderedList<T> result = new DoublyLinkedUnorderedList<>();
        DoublyNode<T> current = head;
        while (current != null) {
            result.addToFront(current.getElement());
            current = current.getNext();
        }
        return result;
    }

    public String printForward(DoublyNode<T> node) {
        if (node == null) return "";
        return node.getElement() + " " + printForward(node.getNext());
    }


    public String printBackwards(DoublyNode<T> node) {
        if (node == null) return "";
        return node.getElement() + " " + printBackwards(node.getPrevious());
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<T>() {
        };
    }

    public abstract class BasicIterator<E> implements Iterator<T> {
        protected DoublyNode<T> current;
        protected int expectedModCount;
        protected boolean okToRemove;

        public BasicIterator() {
            this.current = head;
            this.expectedModCount = modCount;
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration without advancing the iterator.
         *
         * @return the next element in the iteration
         * @throws ConcurrentModificationException  if the list has been modified
         *                                          since the iterator was created
         * @throws java.util.NoSuchElementException if there are no more elements in the list
         */
        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T result = current.getElement();
            current = current.getNext();
            okToRemove = true;
            return result;
        }

        /**
         * Removes the element at the current position of the iterator and
         * advances the iterator.
         *
         * @throws ConcurrentModificationException if the list has been modified
         *                                         since the iterator was created
         * @throws IllegalStateException           if the iterator has not been
         *                                         initialized or the next method has
         *                                         already been called after the last
         *                                         call to the remove method
         */
        @Override
        public void remove() {
            if (modCount != this.expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new java.util.NoSuchElementException();
            }
            DoublyLinkedList.this.remove(current.getPrevious().getElement());
            expectedModCount++;
            okToRemove = false;
        }
    }
}
