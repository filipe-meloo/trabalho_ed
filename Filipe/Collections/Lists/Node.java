package Collections.Lists;

/**
 * Node represents a node in a linked collection.
 *
 * @param <T> the generic type of data element in this node
 */
public class Node<T> {
    private T element;
    private Node<T> next;

    /**
     * Constructs a node with the specified element.
     *
     * @param element The element to be stored in the node.
     */
    public Node(T element) {
        this.element = element;
        this.next = null;
    }

    /**
     * Gets the element stored in the node.
     *
     * @return The element stored in the node.
     */
    public T getElement() {
        return this.element;
    }

    /**
     * Sets the element stored in the node.
     *
     * @param element The element to be set in the node.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Gets the next node in the linked list.
     *
     * @return The next node in the linked list.
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Sets the next node in the linked list.
     *
     * @param nextNode The next node in the linked list.
     */
    public void setNext(Node<T> nextNode) {
        this.next = nextNode;
    }
}
