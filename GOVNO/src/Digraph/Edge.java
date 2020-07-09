package Digraph;

import java.awt.*;

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
    private Vertex vFrom;
    private Vertex vTo;

    /**
     * Конструктор класса
     */
    public Edge(Vertex vFrom, Vertex vTo)
    {
        this.vFrom = vFrom;
        this.vTo = vTo;
    }
    /**
     * Метод получения вершины откуда
     */
    public Vertex getVFrom()
    {
        return vFrom;
    }
    /**
     * Метод получение вершины куда
     */
    public Vertex getVTo()
    {
        return vTo;
    }

    //-------------------------------------------

    public void draw(Graphics g) {
        Point p1 = vFrom.getLocation();
        Point p2 = vTo.getLocation();
        g.setColor(Color.darkGray);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
}
