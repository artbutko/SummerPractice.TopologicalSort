package graph;

/**
 * @Dimon&Toshi: Класс сортировки;
 * @Toshi:
 * Добавил цвета и выявление цикла в графе.
 * Так же добавить проверку на лес.
 * P.S.: Надо бы придумать как обрабатывать ошибки и выводить их для пользователя, если есть замечания - пишите.
 */

import java.io.*;
import java.lang.*;
import java.util.*;

public class Algorithm {
    public void helpSort(Vertex vCur, Graph graph, Stack stack){      //Вспомогательная рекурсивная функция для топологической сортировки
        if (vCur.getColor() == vColor.BLACK) {
            return;
        }
        else if(vCur.getColor() == vColor.GREY){      //Если вершина серая, то значит цикл в графе, сортировка невозможна
            graph.gHasError(typeOfError.LOOP);
            //foo(); для адекватной обработки ошибок и выхода и рекурсии, хз как реализовать
        }
        else {
           vCur.changeColor();
            if (vCur.getVNext().size() == 0) {             //Если нет пути (конец пути), то пушим эту вершину в стек
                vCur.changeColor();
                stack.push(vCur);
                return;
            }
            else {
                for (Vertex k: vCur.getVNext()){              //Дальнейший проход по потомкам(вроде так называется)
                    helpSort(k, graph, stack);
                }
                vCur.changeColor();
                stack.push(vCur);                              //Если потомки закончились, пушим эту вершину в стек
            }
        }
    }

    public void sort(Stack stack,Graph graph, int vertexCount){    //Головная функция топологической сортировки, вызывающая рекурсивную функцию
        for (String k: graph.getMap().keySet()) {
            Vertex element = graph.getElem(k);
            if(element.getColor() == vColor.WHITE)
                helpSort(element, graph, stack);
        }

        int index = 0;
        while(!stack.isEmpty()){                            //Снимаем со стека элементы и назначаем им индексы их порядка
            graph.getMap().get(stack.pop()).setIndex(index);
            index++;
        }
    }

}
