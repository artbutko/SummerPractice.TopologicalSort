package Digraph;

/**
 * Класс графа.
 * Реализованы структуры данных и стандартные методы для работы с графом:
 *  Для хранения графа используется структура Map<String, Vertex> graph
 *  Для хранения ребер используется структура ArrayList<Edge> edges
 *  Методы:
 *      isEmpty(); getMap(); getElement(); addVertex(); addEdge(); removeEdge;
 *      removeVertex(); getVertexes(); getEdges();
 * А так же были написаны псевдо обработчик ошибок gHasError();
 * @author NamYoSeb
 * @version 0.1
 * #TODO Обработка ошибок
 */

import java.lang.*;
import java.util.*;

public class Digraph
{
    /**
     * Поле графа
     */
    private Map<String, Vertex> graph;

    /**
     * Поле ребер
     */
    private final ArrayList<Edge> edges;

    private States states;
    /**
     * Конструктор класса
     * #TODO default constructor
     */
    public Digraph()
    {
        graph = new HashMap<String, Vertex>();
        edges = new ArrayList<Edge>();
        states = new States();
    }

    public States getStates(){
        return states;
    }

    public void setState(){
        graph = states.getState(0);
    }

    public boolean replace(){return true;} // Метод для "Песочницы"

    /**
     * Метод, проверяющий граф на пустоту
     */
    public boolean isEmpty()
    {
        return graph.isEmpty();
    }

    /**
     * Метод получение графа (словаря)
     */
    public Map<String, Vertex> getMap()
    {
        return graph;
    }

    /**
     * Метод получение элемента графа (элемента по ключу словаря)
     */
    public Vertex getElement(String key)
    {
        return graph.get(key);
    }

    /**
     * Метод добавления вершины в граф
     */
    public void addVertex(String name)
    {
        if (name != null) {
            Vertex vertex = new Vertex(name);
            graph.put(name, vertex);
        }
    }

    /**
     * Метод добавления ребра в граф и список ребер
     */
    public void addEdge(String vFrom, String vTo)
    {
        Vertex vertexFrom = this.getElement(vFrom);
        Vertex vertexTo = this.getElement(vTo);
        Edge edge = new Edge(vertexFrom, vertexTo);
        edges.add(edge);
        vertexFrom.addVNext(vertexTo);
    }

    public void addEdge(Edge edge)
    {
        Edge newEdge = new Edge(edge.vGetSource(), edge.vGetStock());
        edges.add(newEdge);

    }

    /**
     * Метод удаления ребра в графе
     */
    public void removeEdge(Vertex vertexFrom, Vertex vertexTo)
    {
        this.getElement(vertexFrom.getName()).removeVNext(vertexTo);
        for(Edge edge: this.edges){
            if(edge.vGetSource() == vertexFrom & edge.vGetStock() == vertexTo){
                edge.vGetSource().removeVNext(edge.vGetStock());
                edges.remove(edge);
                break;
            }
        }
    }




    /**
     * Метод удаления вершины в графе
     */
    public void removeVertex(Vertex vertex)
    {
        int size = edges.size();

        for(String key : graph.keySet()){
            for(Vertex v : getElement(key).getVNext()){
                if(v == vertex){
                    getElement(key).removeVNext(vertex);
                    System.out.println("HI");
                    break;
                }

            }
        }

        for(int i = 0; i < size; i++){
            if(!removeIncEdge(vertex))
                break;
        }


        graph.remove(vertex.getName());

    }

    private boolean removeIncEdge(Vertex vertex) {
        for (Edge edge : edges) {
            if (edge.vGetSource() == vertex || edge.vGetStock() == vertex) {
                edges.remove(edge);
                return true;
            }
        }
        return false;
    }


    /**
     * Метод получения списка вершин графа
     */
    public ArrayList<String> getVertexes()
    {
        return new ArrayList<String>(graph.keySet());
    }


    public ArrayList<Edge> getEdges(){
        return edges;
    }
    /**
     * Метод получения списка ребер графа
     */
    public ArrayList<String> getArrayEdges()
    {
        ArrayList<String> edges = new ArrayList<>();
        for(Edge edge: this.edges){
            edges.add(edge.vGetSource().getName() + ";" + edge.vGetStock().getName());
        }
        return edges;
    }

    /**
     * Псевдо метод для обработки ошибок
     */
    public void gHasError(ErrorType e)
    {
        if(e == ErrorType.FOREST){
            System.out.println("TIPO GRAPH IS A FOREST");
        }

        else if(e == ErrorType.LOOP){
            System.out.println("TIPO GRAPH ETO LOOP");
        }
    }


}
