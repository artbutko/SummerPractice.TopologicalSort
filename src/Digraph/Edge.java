package Digraph;

/**
 Класс ребер.
 Реализация класса ребер, в основном для работы с GUI
 @author NamYoSeb
 @version final
 */

public class Edge
{
    /**
     Поля вершин ребра, откуда и куда, соответственно
     */
    private Vertex vSource;
    private Vertex vStock;

    /**
     * Конструктор класса
     */
    public Edge(Vertex vSource, Vertex vStock)
    {
        this.vSource = vSource;
        this.vStock = vStock;
    }

    public  boolean isSelfEdge()
    {
        return vSource == vStock;
    }

    public void vSetSource(Vertex Source){
        vSource = Source;
    }

    public void vSetStock(Vertex Stock){
        vStock = Stock;
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
