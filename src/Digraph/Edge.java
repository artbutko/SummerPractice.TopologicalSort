package Digraph;
/**
 Класс ребер.
 Реализация класса ребер, в основном для работы с GUI
 @author NamYoSeb
 @version 0.1
 */

public class Edge
{
    /**
     Поля вершин ребра, откуда и куда, соответственно
     */
    private final Vertex vSource;
    private final Vertex vStock;

    /**
     * Конструктор класса
     */
    public Edge(Vertex vSource, Vertex vStock)
    {
        this.vSource = vSource;
        this.vStock = vStock;
    }
    /**
     * Метод получения вершины откуда
     */
    public Vertex vGetSource()
    {
        return vSource;
    }

    /**
     * Метод получение вершины куда
     */
    public Vertex vGetStock()
    {
        return vStock;
    }
}
