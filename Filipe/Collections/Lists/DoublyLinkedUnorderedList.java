package Collections.Lists;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Exceptions.NoSuchElementException;

public class DoublyLinkedUnorderedList<T> extends DoublyLinkedList<T> implements UnorderedListADT<T> {

    public DoublyLinkedUnorderedList() {
        super();
    }

    /**
     * Adds an element to the front of the list.
     *
     * @param element The element to be added to the front of the list.
     */
    @Override
    public void addToFront(T element) {
        if (isEmpty()) {
            head = tail = new DoublyNode<>(element);
        } else {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        count++;
        modCount++;
    }

    /**
     * Adds an element to the rear (end) of the list.
     *
     * @param element The element to be added to the rear of the list.
     */
    @Override
    public void addToRear(T element) {
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
     * Adds an element after a specified target element.
     *
     * @param element The element to be added to the list.
     * @param target  The element after which the new element should be added.
     * @throws EmptyCollectionException if the list is empty.
     * @throws NoSuchElementException   if the target element is not found in the list.
     */
    @Override
    public void addAfter(T element, T target) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        DoublyNode<T> current = head;
        while (current != null && !current.getElement().equals(target)) {
            current = current.getNext();
        }
        if (current == null) throw new NoSuchElementException();
        if (current == tail) {
            addToRear(element);
        } else {
            DoublyNode<T> newNode = new DoublyNode<>(element);
            newNode.setNext(current.getNext());
            newNode.setPrevious(current);
            current.getNext().setPrevious(newNode);
            current.setNext(newNode);
            count++;
            modCount++;
        }
    }
}
