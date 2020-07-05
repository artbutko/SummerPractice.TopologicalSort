package graph;
/**
@Dimon&Toshi Класс для визуализации перехода по ребрам;
 */
public class Edge {
    private Vertex vFrom;
    private Vertex vTo;

    public Edge(Vertex vFrom, Vertex vTo){
        this.vFrom = vFrom;
        this.vTo = vTo;
    }

    public Vertex getVFrom() {
        return vFrom;
    }

    public Vertex getVTo() {
        return vTo;
    }
}
