package Digraph;

/**
 * Класс вершин
 * Реализованы структура данных и методы для работы с вершинами.
 * Для хранения имени вершины используется поле String name
 * Для хранения индекса(порядка) после сортировки используется поле int sortIndex
 * Для хранения цвета вершины используется VertexColor color, который является классом перечислений enum
 * Для хранения "потомков" используется структура ArrayList<Vertex> vNext
 * Методы:
 *      getName(); addVNext(); removeVNext(); getVNext(); setIndex(); changeColor(); getColor();
 * @author NamYoSeb
 * @version 0.1
 * P.S.: Есть сомнения с добавлением и удалением элементов, так как про новую версию сборки Java
 * мало доступной информации.
 */

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Vertex
{
    /**
     * Поле хранения имени вершины
     */
    private final String name;

    /**
     * Поле хранения порядка после сортировки
     */
    private int sortIndex;

    /**

    /**
     * Поле для хранения потомков
     */
    private final ArrayList<Vertex> vNext;

    //РИСОВАШКА, ОФОРМИЛ ПОКА ТАК, ПОТОМУ ЧТО ЧЕРЕЗ 3 ЧАСА НА РАБОТУ,
    //МОГУ НЕ УСПЕТЬ ИЗ ЗА ЭТОГО СДЕЛАТЬ СВОЮ ЧАСТЬ, ЗАВТРА ПЕРЕОФОРМЛЮ
    //---------------------------------------------------------------
    private Point coordinate;
    private Color color;
    private boolean selected = false;
    private Rectangle selectionGrid = new Rectangle();
    private int radius;

    public Vertex(Point coordinate, int radius, Color color, String name){
        this.coordinate = coordinate;
        this.radius = radius;
        this.color = color;
        this.name = name;
        vNext = new ArrayList<Vertex>();
        setBoundary(selectionGrid);
    }


    //Квадрат в которую будет вписана окружность, или сетка выбора
    private void setBoundary(Rectangle selectionGrid){
        selectionGrid.setBounds(coordinate.x - 10, coordinate.y - 10, 20,20);
    }

    public void draw(Graphics g){
        g.setColor(this.color);
        g.drawOval(selectionGrid.x, selectionGrid.y, selectionGrid.width, selectionGrid.height);
        if (selected) {
            g.setColor(Color.darkGray);
            g.drawRect(selectionGrid.x, selectionGrid.y, selectionGrid.width, selectionGrid.height);
        }
    }

    public Point getLocation(){
        return coordinate;
    }

   public boolean contains(Point coordinate){
        return selectionGrid.contains((coordinate));
   }

   public boolean isSelected(){
        return selected;
   }

   public void setSelected(boolean selected){
        this.selected = selected;
   }

   public static void getSelected(ArrayList<Vertex> vertexesList, ArrayList<Vertex> selected){
        selected.clear();
        for(Vertex v: vertexesList){
            if(v.isSelected()){
                selected.add(v);
            }
        }
   }

   public static void selectNone(ArrayList<Vertex> vertexesList){
        for(Vertex v : vertexesList){
            v.setSelected(false);
        }
   }

   public static boolean selectOne(ArrayList<Vertex> vertexesList, Point coordinate){
        for (Vertex v : vertexesList){
            if(v.contains(coordinate)){
                if (!v.isSelected()){
                    Vertex.selectNone(vertexesList);
                    v.setSelected(true);
                }
                return true;
            }
        }
        return false;
   }

    public static void selectRect(ArrayList<Vertex> list, Rectangle rectangle){
        for (Vertex v : list){
            v.setSelected(rectangle.contains(v.coordinate));
        }
    }

    public static void selectToggle(ArrayList<Vertex> list, Point coordinate) {
        for (Vertex v : list) {
            if (v.contains(coordinate)) {
                v.setSelected(!v.isSelected());
            }
        }
    }

    public static void updatePosition(ArrayList<Vertex> list, Point coordinate) {
        for (Vertex v : list) {
            if (v.isSelected()) {
                v.coordinate.x += coordinate.x;
                v.coordinate.y += coordinate.y;
                v.setBoundary(v.selectionGrid);
            }
        }
    }

    public static void updateRadius(ArrayList<Vertex> list, int radius) {
        for (Vertex v : list) {
            if (v.isSelected()) {
                v.radius = radius;
                v.setBoundary(v.selectionGrid);
            }
        }
    }


    public static void updateColor(ArrayList<Vertex> list, Color color) {
        for (Vertex v : list) {
            if (v.isSelected()) {
                v.color = color;
            }
        }
    }
    
    //-----------------------------------------------------------------
    /**
     * Конструктор класса
     */
    public Vertex(String name)
    {
        this.name = name;
        sortIndex = 0;
        color = Color.WHITE;
        vNext = new ArrayList<Vertex>();
        setBoundary(selectionGrid);
    }

    /**
     * Метод получения имени вершины
     */
    public String getName()
    {
        return name;
    }

    /**
     * Метод добавления потомка вершины
     */
    public void addVNext(Vertex vertex)
    {
        vNext.add(vertex);
    }

    /**
     * Метод удаления потомка вершины
     */
    public void removeVNext(Vertex vertex)
    {
        vNext.remove(vertex);
    }

    /**
     * Метод получения списка потомков вершины
     */
    public ArrayList<Vertex> getVNext()
    {
        return vNext;
    }

    /**
     * Метод присваивания номера порядка вершины
     */
    public void setIndex(int index)
    {
        sortIndex = index;
    }

    /**
     * Метод изменения цвета вершины
     */
    public void changeColor()
    {
        if (color == Color.WHITE) 
            color = Color.GRAY;
        else color = Color.BLACK;
    }

    /**
     * Метод получения цвета вершины
     */
    public Color getColor()
    {
        return color;
    }
}
