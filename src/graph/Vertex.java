package graph;

/**
 * Класс вершин
 * Реализованы структура данных и методы для работы с вершинами.
 * Для хранения имени вершины используется поле String name
 * Для хранения индекса(порядка) после сортировки используется поле int sortIndex
 * Для хранения цвета вершины используется vColor color, который является классом перечислений enum
 * Для хранения "потомков" используется структура ArrayList<Vertex> vNext
 * Методы:
 *      getName(); addVNext(); removeVNext(); getVNext(); setIndex(); changeColor(); getColor();
 * @author NamYoSeb
 * @version 0.1
 * P.S.: Есть сомнения с добавлением и удалением элементов, так как про новую версию сборки Java
 * мало доступной информации.
 */

import java.util.*;

public class Vertex{

    /**
     * Поле хранения имени вершины
     */
    private String name;

    /**
     * Поле хранения порядка после сортировки
     */
    private int sortIndex;

    /**
     * Поле для цвета вершины
     */
    private vColor color = vColor.WHITE;

    /**
     * Поле для хранения потомков
     */
    private ArrayList<Vertex> vNext; // Потомки

    /**
     * Конструктор класса
     */
    public Vertex(String name){
        this.name = name;
        sortIndex = 0;
        color = vColor.WHITE;
        vNext = new ArrayList<Vertex>();
    }

    /**
     * Метод получения имени вершины
     */
    public String getName(){
        return name;
    }

    /**
     * Метод добавления потомка вершины
     */
    public void addVNext(Vertex vertex){
        vNext.add(vertex);
    }

    /**
     * Метод удаления потомка вершины
     */
    public void removeVNext(Vertex vertex){
                vNext.remove(vertex);
    }

    /**
     * Метод получения списка потомков вершины
     */
    public ArrayList<Vertex> getVNext(){
        return vNext;
    }

    /**
     * Метод присваивания номера порядка вершины
     */
    public void setIndex(int index){
        sortIndex = index;
    }

    /**
     * Метод изменения цвета вершины
     */
    public void changeColor(){
        if (color == vColor.WHITE)
            color = vColor.GREY;
        else if (color == vColor.GREY)
            color = vColor.BLACK;
    }

    /**
     * Метод получения цвета вершины
     */
    public vColor getColor(){
        if (color == vColor.WHITE)
             return vColor.WHITE;
        else if (color == vColor.GREY)
            return vColor.GREY;
        else
            return vColor.BLACK;
    }
}
