package Collections.Lists;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NoSuchElementException;

public class LinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T> {

    /**
     * Adds an element to the front of the list.
     *
     * @param element The element to be added to the front of the list.
     */
    @Override
    public void addToFront(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Adds an element to the rear (end) of the list.
     *
     * @param element The element to be added to the rear of the list.
     */
    @Override
    public void addToRear(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
        modCount++;
    }

    /**
     * Adds an element after a specified target element.
     *
     * @param element The element to be added to the list.
     * @param target  The element after which the new element should be added.
     * @throws EmptyCollectionException if the list is empty.
     * @throws NoSuchElementException   if the target element is not found in the list.
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) throw new EmptyCollectionException();
        if (!contains(target)) throw new NoSuchElementException();
        Node<T> newNode = new Node<>(element);
        Node<T> current = head;
        while (!current.getElement().equals(target)) {
            current = current.getNext();
        }
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        if (newNode.getNext() == null) {
            tail = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Inverts the order of elements in the list.
     */
    public void invert() {
        this.head = invert(head);
    }

    /**
     * Recursive helper method to invert the order of elements in the list.
     *
     * @param node The current node in the recursive traversal.
     * @return The new head of the inverted list.
     */
    private Node<T> invert(Node<T> node) {
        if (node == null || node.getNext() == null) return node;
        Node<T> newHead = invert(node.getNext());
        node.getNext().setNext(node);
        node.setNext(null);
        return newHead;
    }
}
