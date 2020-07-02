package graph;

public class Vertex {
    private int name;
    private int sort_index;
    private boolean visited = false;
    public int[] vNext; // Потомки

    public void setIndex(int index){
        sort_index = index;
    }
    //private ColorVertex clr; потом для визуализации

    public void changeVisit(){
        if (visited)
            visited = false;
        else
            visited = true;
    }



    public boolean isVisited(){
        if(visited)
            return true;
        else
            return false;
    }

}
