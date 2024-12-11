package Collections.Queues;

import Collections.Lists.Node;
import Collections.Exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    @Override
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.setNext(newNode);
        }
        rear = newNode;
        size++;
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue is empty, cannot dequeue.");
        }
        T removedElement = front.getElement();
        front = front.getNext();
        size--;
        return removedElement;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue is empty, no first element.");
        }
        return front.getElement();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder queueString = new StringBuilder(getClass().getSimpleName() + " { ");
        Node<T> current = front;
        if (!isEmpty()) {
            queueString.append(buildQueueElementsString(current));
        }
        return queueString.append("}").toString();
    }

    private String buildQueueElementsString(Node<T> current) {
        StringBuilder elementsString = new StringBuilder();
        do {
            elementsString.append(current.getElement()).append(" ");
        } while ((current = current.getNext()) != null);
        return elementsString.toString();
    }
}