public class HNode {
    private Vertex vertex;
    private LinkedList path;
    private int cost;
    private int time;
    private int distance;
    private int currCost;

    public HNode(Vertex capital, LinkedList path, int cost, int time, int distance, int currCost) {
        this.vertex = capital;
        this.path = path;
        this.cost = cost;
        this.time = time;
        this.distance = distance;
        this.currCost = currCost;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public LinkedList getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public int getDistance() {
        return distance;
    }

    public int getCurrentCost() {
        return currCost;
    }

    public void setCurrentCost(int currCost) {
        this.currCost = currCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPath(LinkedList path) {
        this.path = path;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }
}
