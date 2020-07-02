package graph;

/**
 * @author Класс сортировки;
 * В дальнейшем заменить isVisited/Visited на Color. Для визуализации и выявления ацикличности графа.
 * Так же добавить проверку на лес.
 */

import java.io.*;
import java.util.*;


public class Algorithm {
    public void helpSort(int vCur, Graph gr, Stack stack){      //Вспомогательная рекурсивная функция для топологической сортировки
        if (gr.g.get(vCur).isVisited()) {
            return;
        }
        else {
            gr.g.get(vCur).changeVisit();
            if (gr.g.get(vCur).vNext.length == 0) {             //Если нет пути (конец пути), то пушим эту вершину в стек
                stack.push(vCur);
                return;
            }
            else {
                for (int k: gr.g.get(vCur).vNext){              //Дальнейший проход по потомкам(вроде так называется)
                    helpSort(k, gr, stack);
                }
                stack.push(stack);                              //Если потомки закончились, пушим эту вершину в стек
            }
        }
    }

    public void sort(Stack stack,Graph gr, int vertexCount){    //Головная функция топологической сортировки, вызывающая рекурсивную функцию
        for (int k: gr.g.keySet()) {
            if(!gr.g.get(k).isVisited())
                helpSort(k, gr, stack);
        }

        int index = 0;
        while(!stack.isEmpty()){                                //Снимаем со стека элементы и назначаем им индексы их порядка
            gr.g.get(stack.pop()).setIndex(index);
            index++;
        }
    }

}
