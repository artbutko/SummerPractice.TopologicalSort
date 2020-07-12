package Algorithm;

/**
 * Алгоритм Тарьяна;
 * Сначала должна вызваться головная функция sort(), которая впервую очередь запускает непосещенную
 * вершину в вспомогательную рекурсивную функцию helpSort(), которая делает основную работу:
 *      1)Проверяет цвет вершины:
 *          А) Если вершина черная, значит она уже посещена, следовательно выход из рекурсии,
 *          Б) Если вершина серая, значит граф имеет цикл, следовательно сортировка не возможна
 *          В) Если вершина белая, то:
 *              а) Меняем ее цвет на серый,
 *              б) Если вершина является концом (не имеет следующей вершины(потомка)), меняем ее
 *              на черный цвет и пушим в стек,
 *              в) Проходимся алгоритмом в следующий элемент, который она имеет (потомкам).
 *              г) При выходе из рекурсии, предыдущая вершина перекрашивается в черный.
 *              д) Вершина заносится в стек
 * Так же головная функция рассортировывает данные из стека, и присвает ей индекс в порядке выхода
 * из него, тем самым задает ей порядок.
 * @author sidtheslooth20, artbutko, NamYoSeb
 * @version 0.1
 * P.S.:Алгоритм пока не тестировался.
 * #TODO Обработка ошибок
 */

import Digraph.*;

import java.awt.*;
import java.lang.*;
import java.util.*;

public class Algorithm
{
    private final Stack<Vertex> vStack;
    private final Digraph digraph;
    ArrayList<String> sortResult;

    public Algorithm(Digraph digraph)
    {
        this.digraph = digraph;
        vStack = new Stack<Vertex>();
        sortResult = new ArrayList<String>();
    }

    /**
     * Вспомогательная рекурсивная функция для топологической сортировки
     * @param vCurrent -- текущая вершина
     */
    private void helpSort(Vertex vCurrent)
    {
        if(vCurrent.getColor() == Color.GRAY)
        {
            /* Если вершина серая, то значит цикл в графе, сортировка невозможна */
            digraph.gHasError(ErrorType.LOOP);
        }
        else if (vCurrent.getColor() != Color.BLACK)
        {
            vCurrent.changeColor();
            /* Если нет пути (конец пути), то пушим эту вершину в стек */
            if (vCurrent.getVNext().size() != 0) {
                /* Дальнейший проход по потомкам */
                for (Vertex child : vCurrent.getVNext())
                    helpSort(child);
            }
            vCurrent.changeColor();
            vStack.push(vCurrent);
        }
    }

    /**
     * Головная функция топологической сортировки, вызывающая рекурсивную функцию
     * @return Массив имен вершин.
     */
    public ArrayList<String> sort()
    {
        for (String key: digraph.getMap().keySet())
        {
            Vertex vertex = digraph.getElement(key);
            if(vertex.getColor() == Color.WHITE)
                helpSort(vertex);
        }
<<<<<<< Updated upstream
=======
        if(digraph.isLoop)
        {
            System.out.println("EMPTY");
            for (String key: digraph.getMap().keySet())
            {
                digraph.getElement(key).setColor(Color.WHITE);
            }
            digraph.isLoop = false;
            return null;
        }
>>>>>>> Stashed changes
        while(!vStack.isEmpty())  sortResult.add(vStack.pop().getName());
        System.out.println(sortResult);
        return sortResult;
    }

}