public class Graph {
    private int numVertices;
    private Hash hash;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        hash = new Hash(numVertices);
    }

    public void addVertex(Vertex v) {
        hash.add(v);
    }

    public Vertex getVertex(Vertex v) {
        return hash.get(v);
    }

    public Vertex getVertix(String capital) {
        return hash.get(capital);
    }

    public boolean contains(Vertex v) {
        return hash.contains(v);
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public Hash getHash() {
        return hash;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    public void printGraph() {
        for (int i = 0; i < numVertices; i++) {
            if (hash.getHash()[i] != null) {
                System.out.println(hash.getHash()[i]);
            }
        }
    }

    /*
        * This method returns the shortest path between two vertices based on the filter
        * provided. The filter can be "Time", "Cost" or "Distance".
    * */
    public HNode getResult(Vertex src, Vertex dest, String filter) {
        return dijkstra(src, dest, filter);
    }

    /*
     * This method returns the shortest path between two vertices based on the specified filter.
     * The filter can be "Time", "Cost", or "Distance".
     */
    private HNode dijkstra(Vertex src, Vertex dest, String filter) {
        Heap heap = new Heap(numVertices + 1);
        LinkedList path = new LinkedList();
        path.addLast(new Node(new Edge(src, src, 0, 0, 0)));
        HNode first = new HNode(src, path, 0, 0, 0, 0);
        heap.insert(first);

        while (!heap.isEmpty()) {
            HNode curr = heap.deleteMin();
            if (curr.getVertex().getCapital().getCapitalName().equals(dest.getCapital().getCapitalName())) {
                hash.setVerticesUnknown();
                return curr;
            }
            if (curr.getVertex().isKnown()) {
                continue;
            }
            curr.getVertex().setKnown(true);
            LinkedList edges = curr.getVertex().getEdge();
            Node node = edges.getFront();

            while (node != null) {
                if (!node.getElement().getDestination().isKnown()) {
                    LinkedList newPath = new LinkedList();
                    newPath.addAll(curr.getPath());
                    newPath.addLast(new Node(node.getElement()));

                    int newCost = curr.getCost() + node.getElement().getCost();
                    int newTime = curr.getTime() + node.getElement().getTime();
                    int newDistance = curr.getDistance() + node.getElement().getDistance();
                    int priority = 0;

                    if(filter.equals("Time"))
                        priority = newTime;
                    else if(filter.equals("cost"))
                        priority = newCost;
                    else
                        priority = newDistance;


                    HNode newNode = new HNode(node.getElement().getDestination(), newPath, newCost, newTime, newDistance, priority);
                    heap.insert(newNode);
                }
                node = node.getNext();
            }
        }
        hash.setVerticesUnknown();
        return null;
    }
}