package Digraph;

/**
 * Класс ориентированного графа.
 * Реализованы структуры данных и стандартные методы для работы с графом:
 *  Для хранения графа используется структура Map<String, Vertex> graph
 *  Для хранения ребер используется структура ArrayList<Edge> edges
 *  Методы:
 *      isEmpty(); getMap(); getElement(); addVertex(); addEdge(); removeEdge;
 *      removeVertex(); getVertexes(); getEdges();
 * @author NamYoSeb
 * @version final
 */

import java.lang.*;
import java.util.*;

public class Digraph
{
    /** Поле графа */
    private final Map<String, Vertex> graph;

    /** Поле ребер */
    private final ArrayList<Edge> edges;

    /** Поле состояний */
    public States states;

    /** Поле для проверки на цикл */
    public boolean  isLoop = false;

    /** Конструктор класса */
    public Digraph()
    {
        graph = new HashMap<String, Vertex>();
        edges = new ArrayList<Edge>();
        states = new States();
    }

    /** Метод, проверяющий граф на пустоту */
    public boolean isEmpty()
    {
        return graph.isEmpty();
    }

    /** Метод получение графа (словаря)*/
    public Map<String, Vertex> getMap()
    {
        return graph;
    }

    /** Метод получение элемента графа (элемента по ключу словаря)
     * @param key -- ключ вершины в графе */
    public Vertex getElement(String key)
    {
        return graph.get(key);
    }

    /** Метод добавления вершины в граф
     * @param name -- имя новой вершины*/
    public void addVertex(String name)
    {
        if (name != null)
        {
            Vertex vertex = new Vertex(name);
            graph.put(name, vertex);
        }
    }

    /** Метод добавления вершины в граф
     * @param vertex -- новая вершина*/
    public void addVertex(Vertex vertex)
    {
        graph.put(vertex.getName(),vertex);
    }

    /** Метод добавления ребра в граф и список ребер
     * @param vSource -- вершина-исток
     * @param vStock -- вершина-сток */
    public void addEdge(String vSource, String vStock)
    {
        Vertex vertexFrom = this.getElement(vSource);
        Vertex vertexTo = this.getElement(vStock);
        Edge edge = new Edge(vertexFrom, vertexTo);
        edges.add(edge);
        vertexFrom.addVNext(vertexTo);
    }

    /** Метод добавления ребра в граф и список ребер
     * @param edge -- ребро графа */
    public void addEdge(Edge edge)
    {
        Edge newEdge = new Edge(edge.vGetSource(), edge.vGetStock());
        edges.add(newEdge);

    }

    /** Метод удаления ребра в графе
     * @param vSource -- вершина-исток
     * @param vStock -- вершина-сток */
    public void removeEdge(Vertex vSource, Vertex vStock)
    {
        this.getElement(vSource.getName()).removeVNext(vStock);
        for(Edge edge: this.edges)
            if(edge.vGetSource() == vSource & edge.vGetStock() == vStock)
            {
                edge.vGetSource().removeVNext(edge.vGetStock());
                edges.remove(edge);
                break;
            }
    }

    /** Метод удаления вершины в графе
     * @param vertex -- вершина для удаления */
    public void removeVertex(Vertex vertex)
    {
        int size = edges.size();

        for(String key : graph.keySet())
            for(Vertex v : getElement(key).getVNext())
                if(v == vertex)
                {
                    getElement(key).removeVNext(vertex);
                    break;
                }

        for(int i = 0; i < size; i++)
            if(!removeIncEdge(vertex))
                break;

        graph.remove(vertex.getName());

    }

    /** Метод удаления ицидентных ребер
     * @param vertex -- вершина для удаления ребер*/
    private boolean removeIncEdge(Vertex vertex)
    {
        for (Edge edge : edges)
            if (edge.vGetSource() == vertex || edge.vGetStock() == vertex)
            {
                edges.remove(edge);
                return true;
            }
        return false;
    }

    /** Метод получения списка вершин графа */
    public ArrayList<Vertex> getVertexes()
    {
        return new ArrayList<Vertex>(graph.values());
    }

    /** Метод получения списка ребер графа */
    public ArrayList<Edge> getEdges()
    {
        return edges;
    }

    /** Псевдо метод для обработки ошибок */
    public void isALoop()
    {
       isLoop = true;
       System.out.println("isALoop");
    }
}
