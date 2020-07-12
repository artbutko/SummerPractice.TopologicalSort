package Digraph;

import java.awt.*;
import java.util.*;

/**
 * Класс состояний программы для отрисовки графов
 * @author NamYoSeb, Sidtheslooth20
 * @version 1.0
 */

public class States
{
    public final ArrayList<ArrayList<Color>> colors;
    private int current = 0;

    public States()
    {
        this.colors = new ArrayList<>();
    }

    public void addState(ArrayList<Color> colorState)
    {
        colors.add(colorState);
    }

    public ArrayList<Color> getState()
    {
        return colors.get(current);
    }

    public void nextState()
    {
        if(current + 1 < colors.size())
            current++;
    }

    public void prevState()
    {
        if(current > 0)
            current--;
    }
}
