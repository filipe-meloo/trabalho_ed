public class Graph<T> implements GraphADT<T> {
    protected static final int DEFAULT_CAPACITY = 10;
    protected static final int INDEX_NOT_FOUND = -1;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    protected T[] vertices;

    public Graph() {
        numVertices = 0;
        adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public void addVertex() {
        ensureCapacity();
        vertices[numVertices] = null;
        resetAdjacencyMatrix(numVertices);
        numVertices++;
    }

    public void addVertex(T vertex) {
        ensureCapacity();
        vertices[numVertices] = vertex;
        resetAdjacencyMatrix(numVertices);
        numVertices++;
    }

    private void ensureCapacity() {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
    }

    private void resetAdjacencyMatrix(int vertexIndex) {
        Arrays.fill(adjMatrix[vertexIndex], 0, numVertices + 1, false);
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[i][vertexIndex] = false;
        }
    }

    public void removeVertex(T vertex) {
        int index = getIndex(vertex);
        if (index != INDEX_NOT_FOUND) {
            removeVertex(index);
        }
    }

    public void removeVertex(int index) {
        if (isIndexValid(index)) {
            numVertices--;
            System.arraycopy(vertices, index + 1, vertices, index, numVertices - index);
            updateAdjMatrixAfterVertexRemoval(index);
        }
    }

    private void updateAdjMatrixAfterVertexRemoval(int index) {
        for (int i = index; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i + 1], 0, adjMatrix[i], 0, numVertices + 1);
        }
        for (int i = 0; i <= numVertices; i++) {
            System.arraycopy(adjMatrix[i], index + 1, adjMatrix[i], index, numVertices - index);
        }
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < numVertices;
    }

    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    public void addEdge(int index1, int index2) {
        if (isIndexValid(index1) && isIndexValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    public int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public void removeEdge(T vertex1, T vertex2) {
        removeEdge(getIndex(vertex1), getIndex(vertex2));
    }

    public void removeEdge(int index1, int index2) {
        if (isIndexValid(index1) && isIndexValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }

    public Iterator<T> iteratorBFS(T startVertex) {
        return iteratorBFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorBFS(int startIndex) {
        if (!isIndexValid(startIndex)) {
            return new ArrayUnorderedList<T>().iterator();
        }
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = newVisitedArray();

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            int x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorDFS(T startVertex) {
        return iteratorDFS(getIndex(startVertex));
    }

    public Iterator<T> iteratorDFS(int startIndex) {
        if (!isIndexValid(startIndex)) {
            return new ArrayUnorderedList<T>().iterator();
        }
        LinkedStack<Integer> traversalStack = new LinkedStack<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();
        boolean[] visited = newVisitedArray();

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            int x = traversalStack.peek();
            boolean found = false;

            for (int i = 0; i < numVertices && !found; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    private void initializeVisitedArray(boolean[] visited) {
        Arrays.fill(visited, false);
    }

    private boolean[] newVisitedArray() {
        boolean[] visited = new boolean[numVertices];
        initializeVisitedArray(visited);
        return visited;
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(targetVertex));
    }

    public Iterator<T> iteratorShortestPath(int startIndex, int targetIndex) {
        ArrayUnorderedList<T> pathList = new ArrayUnorderedList<>();
        if (!isIndexValid(startIndex) || !isIndexValid(targetIndex)) {
            return pathList.iterator();
        }

        Iterator<Integer> shortestPathIndices = iteratorShortestPathIndices(startIndex, targetIndex);
        while (shortestPathIndices.hasNext()) {
            pathList.addToRear(vertices[shortestPathIndices.next()]);
        }
        return pathList.iterator();
    }

    protected Iterator<Integer> iteratorShortestPathIndices(int startIndex, int targetIndex) {
        int currentIndex = startIndex;
        int[] distances = new int[numVertices];
        int[] predecessors = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<Integer> pathIndices = new ArrayUnorderedList<>();

        if (!isIndexValid(startIndex) || !isIndexValid(targetIndex) || startIndex == targetIndex) {
            return pathIndices.iterator();
        }

        boolean[] visited = initializeBooleanArray(false);

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        distances[startIndex] = 0;
        predecessors[startIndex] = INDEX_NOT_FOUND;

        while (!traversalQueue.isEmpty() && currentIndex != targetIndex) {
            currentIndex = traversalQueue.dequeue();
            updateAdjacentVerticesForShortestPath(currentIndex, traversalQueue, visited, distances, predecessors);
        }

        if (currentIndex != targetIndex) {
            return pathIndices.iterator(); // no path found
        }

        return buildPathFromPredecessors(targetIndex, predecessors, pathIndices);
    }

    private void updateAdjacentVerticesForShortestPath(int vertexIndex, LinkedQueue<Integer> queue, boolean[] visited,
                                                       int[] distances, int[] predecessors) {
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[vertexIndex][i] && !visited[i]) {
                distances[i] = distances[vertexIndex] + 1;
                predecessors[i] = vertexIndex;
                queue.enqueue(i);
                visited[i] = true;
            }
        }
    }

    private Iterator<Integer> buildPathFromPredecessors(int targetIndex, int[] predecessors, ArrayUnorderedList<Integer> pathIndices) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        int currentIndex = targetIndex;
        stack.push(currentIndex);

        while ((currentIndex = predecessors[currentIndex]) != INDEX_NOT_FOUND) {
            stack.push(currentIndex);
        }

        while (!stack.isEmpty()) {
            pathIndices.addToRear(stack.pop());
        }

        return pathIndices.iterator();
    }

    private boolean[] initializeBooleanArray(boolean initialValue) {
        boolean[] array = new boolean[numVertices];
        Arrays.fill(array, initialValue);
        return array;
    }

    public int shortestPathLength(T startVertex, T targetVertex) {
        return shortestPathLength(getIndex(startVertex), getIndex(targetVertex));
    }

    public int shortestPathLength(int startIndex, int targetIndex) {
        if (!isIndexValid(startIndex) || !isIndexValid(targetIndex)) {
            return 0;
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);
        int pathLength = 0;

        if (it.hasNext()) {
            it.next(); // Skip the first index
        }

        while (it.hasNext()) {
            pathLength++;
            it.next();
        }

        return pathLength;
    }

    protected void expandCapacity() {
        vertices = expandArray(vertices, numVertices * 2);
        adjMatrix = expandAdjacencyMatrix(adjMatrix, numVertices * 2);
    }

    private T[] expandArray(T[] original, int newSize) {
        T[] largerArray = (T[]) new Object[newSize];
        System.arraycopy(original, 0, largerArray, 0, numVertices);
        return largerArray;
    }

    private boolean[][] expandAdjacencyMatrix(boolean[][] original, int newSize) {
        boolean[][] largerMatrix = new boolean[newSize][newSize];
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(original[i], 0, largerMatrix[i], 0, numVertices);
        }
        return largerMatrix;
    }

    public int shortestPathLength(T startVertex, T targetVertex) {
        return shortestPathLength(getIndex(startVertex), getIndex(targetVertex));
    }

    public int shortestPathLength(int startIndex, int targetIndex) {
        if (!isIndexValid(startIndex) || !isIndexValid(targetIndex)) {
            return 0;
        }

        Iterator<Integer> it = iteratorShortestPathIndices(startIndex, targetIndex);
        int length = 0;

        if (!it.hasNext()) {
            return 0;
        }
        it.next(); // skip the first index

        while (it.hasNext()) {
            length++;
            it.next();
        }

        return length;
    }

    public boolean isEmpty() {
        return numVertices == 0;
    }

    public boolean isConnected() {
        if (isEmpty()) return false;

        return iteratorBFS(0).stream().count() == numVertices;
    }

    public int size() {
        return numVertices;
    }

    @Override
    public void clear() {
        numVertices = 0;
        Arrays.fill(vertices, null);
        for (boolean[] row : adjMatrix) {
            Arrays.fill(row, false);
        }
    }

    public String toString() {
        if (numVertices == 0) {
            return "Graph is empty";
        }

        StringBuilder result = new StringBuilder();
        result.append("Adjacency Matrix\n----------------\nindex\t");

        for (int i = 0; i < numVertices; i++) {
            result.append(i).append(i < 10 ? " " : "");
        }
        result.append("\n\n");

        for (int i = 0; i < numVertices; i++) {
            result.append(i).append("\t");
            for (int j = 0; j < numVertices; j++) {
                result.append(adjMatrix[i][j] ? "1 " : "0 ");
            }
            result.append("\n");
        }

        result.append("\n\nVertex Values\n-------------\nindex\tvalue\n\n");
        for (int i = 0; i < numVertices; i++) {
            result.append(i).append("\t").append(vertices[i]).append("\n");
        }

        return result.toString();
    }

    public T[] getVertices() {
        return Arrays.copyOf(vertices, numVertices);
    }
}