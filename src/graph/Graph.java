package graph;

/**
 * @Toshi:
 * Надо бы сделать поле g, сделать приватным и придумать методы получения значений, как думаете?
 */

import java.lang.*;
import java.util.*;



public class Graph {


    private Map<String, Vertex> graph;                     // Надо бы сделать поле приватным и реализовать методы, как думаете?
    private ArrayList<Edge> edges;

    public Graph(){}

    public boolean addVertex(int v){return true;}
    public boolean removeVertex(int v){return true;}
    public boolean addEdge(int vFrom, int vTo){return true;}
    public boolean removeEdge(int vFrom, int vTo){return true;}
    public boolean replace(){return true;} // Метод для "Песочницы"
    public boolean isEmpty(){return true;}

    public Map<String, Vertex> getMap(){
        return graph;
    }

    public Vertex getElem(String key){
        return graph.get(key);
    }


    public void addVertex(String name){
        Vertex vertex = new Vertex(name);
        graph.put(name, vertex);
    }

    public void addEdge(String vFrom, String vTo){
        Vertex vertexFrom = this.getElem(vFrom);
        Vertex vertexTo = this.getElem(vTo);
        Edge edge = new Edge(vertexFrom, vertexTo);
        edges.add(edge);
        vertexFrom.addVNext(vertexTo);
    }

    public void removeEdge(Vertex vertexFrom, Vertex vertexTo){
        this.getElem(vertexFrom.getName()).removeVNext(vertexTo);
    }

    public void removeVertex(Vertex vertex){
        graph.remove(vertex.getName());
    }

    public ArrayList<String> getVertexes(){
        ArrayList<String> vertexes = new ArrayList<String>();
        for(String vertex: graph.keySet()){
            vertexes.add(vertex);
        }
        return vertexes;
    }

    public ArrayList<String> getEdges(){
        ArrayList<String> edges = new ArrayList<>();
        for(Edge edge: this.edges){
            edges.add(edge.getVFrom().getName() + ";" + edge.getVTo().getName());
        }
        return edges;
    }

    public void gHasError(typeOfError e){               //Псевдо обработчик ошибок
        if(e == typeOfError.FOREST){
            System.out.println("TIPO GRAPH IS A FOREST");
        }

        else if(e == typeOfError.LOOP){
            System.out.println("TIPO GRAPH ETO LOOP");
        }
    }


}
