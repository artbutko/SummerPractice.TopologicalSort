package graph;

/**
 * @Dimon&Toshi: класс вершин
 * @Toshi Добавил цвета и методы под них
 */
import java.util.*;

public class Vertex{

    private String name;
    private int sortIndex;
    private vColor color = vColor.WHITE;
    private ArrayList<Vertex> vNext; // Потомки

    public Vertex(String name){
        this.name = name;
        sortIndex = 0;
        color = vColor.WHITE;
        vNext = new ArrayList<Vertex>();
    }


    public String getName(){
        return name;
    }

    //Может быть ошибка, хз
    public void addVNext(Vertex vertex){
        vNext.add(vertex);
    }

    public void removeVNext(Vertex vertex){
                vNext.remove(vertex);
    }

    public ArrayList<Vertex> getVNext(){
        return vNext;
    }

    public void setIndex(int index){
        sortIndex = index;
    }

    public void changeColor(){
        if (color == vColor.WHITE)
            color = vColor.GREY;
        else if (color == vColor.GREY)
            color = vColor.BLACK;
    }

    public vColor getColor(){
        if (color == vColor.WHITE)
             return vColor.WHITE;
        else if (color == vColor.GREY)
            return vColor.GREY;
        else
            return vColor.BLACK;
    }


}
