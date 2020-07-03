package graph;

/**
 * @Dimon&Toshi: класс вершин
 * @Toshi Добавил цвета и методы под них
 */

enum vColor{
    WHITE,
    GREY,
    BLACK;
}

public class Vertex {

    private int name;
    private int sort_index;
    private vColor color = vColor.WHITE;
    public int[] vNext; // Потомки



    public void setIndex(int index){
        sort_index = index;
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
