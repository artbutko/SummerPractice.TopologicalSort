package graph;

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
 * @author sidtheslooth20
 * @version 0.1
 * P.S.:Алгоритм пока не тестировался.
 * #TODO Обработка ошибок
 */

import java.lang.*;
import java.util.*;

public class Algorithm {

    public void helpSort(Vertex vCurrent, Graph graph, Stack stack){      //Вспомогательная рекурсивная функция для топологической сортировки
        if (vCurrent.getColor() == vColor.BLACK) {
            return;
        }
        else if(vCurrent.getColor() == vColor.GREY){      //Если вершина серая, то значит цикл в графе, сортировка невозможна
            graph.gHasError(typeOfError.LOOP);
            //foo(); для адекватной обработки ошибок и выхода и рекурсии, хз как реализовать
        }
        else {
           vCurrent.changeColor();
            if (vCurrent.getVNext().size() == 0) {             //Если нет пути (конец пути), то пушим эту вершину в стек
                vCurrent.changeColor();
                stack.push(vCurrent);
                return;
            }
            else {
                for (Vertex k: vCurrent.getVNext()){              //Дальнейший проход по потомкам(вроде так называется)
                    helpSort(k, graph, stack);
                }
                vCurrent.changeColor();
                stack.push(vCurrent);                              //Если потомки закончились, пушим эту вершину в стек
            }
        }
    }

    public void sort(Stack stack,Graph graph){    //Головная функция топологической сортировки, вызывающая рекурсивную функцию
        for (String k: graph.getMap().keySet()) {
            Vertex element = graph.getElement(k);
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
