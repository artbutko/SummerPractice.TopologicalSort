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
    private final Map<String, Vertex> graph;

    /**
     * Поле ребер
     */
    private final ArrayList<Edge> edges;

    /**
     * Конструктор класса
     * #TODO default constructor
     */
    public Digraph()
    {
        graph = new HashMap<String, Vertex>();
        edges = new ArrayList<Edge>();
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

    /**
     * Метод удаления ребра в графе
     */
    public void removeEdge(Vertex vertexFrom, Vertex vertexTo)
    {
        this.getElement(vertexFrom.getName()).removeVNext(vertexTo);
    }

    /**
     * Метод удаления вершины в графе
     */
    public void removeVertex(Vertex vertex)
    {
        graph.remove(vertex.getName());
    }

    /**
     * Метод получения списка вершин графа
     */
    public ArrayList<String> getVertexes()
    {
        ArrayList<String> vertexes = new ArrayList<String>();
        for(String vertex: graph.keySet())
        {
            vertexes.add(vertex);
        }
        return vertexes;
    }

    /**
     * Метод получения списка ребер графа
     */
    public ArrayList<String> getEdges()
    {
        ArrayList<String> edges = new ArrayList<>();
        for(Edge edge: this.edges){
            edges.add(edge.getVFrom().getName() + ";" + edge.getVTo().getName());
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
