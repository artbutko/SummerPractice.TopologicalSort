package graph;

/**
 * @Dimon&Toshi: Класс сортировки;
 * @Toshi:
 * Добавил цвета и выявление цикла в графе.
 * Так же добавить проверку на лес.
 * P.S.: Надо бы придумать как обрабатывать ошибки и выводить их для пользователя, если есть замечания - пишите.
 */

import java.io.*;
import java.util.*;

public class Algorithm {
    public void helpSort(int vCur, Graph gr, Stack stack){      //Вспомогательная рекурсивная функция для топологической сортировки
        if (gr.g.get(vCur).getColor() == vColor.BLACK) {
            return;
        }
        else if(gr.g.get(vCur).getColor() == vColor.GREY){      //Если вершина серая, то значит цикл в графе, сортировка невозможна
            gr.gHasError(typeOfError.LOOP);
            //foo(); для адекватной обработки ошибок и выхода и рекурсии, хз как реализовать
        }
        else {
            gr.g.get(vCur).changeColor();
            if (gr.g.get(vCur).vNext.length == 0) {             //Если нет пути (конец пути), то пушим эту вершину в стек
                gr.g.get(vCur).changeColor();
                stack.push(vCur);
                return;
            }
            else {
                for (int k: gr.g.get(vCur).vNext){              //Дальнейший проход по потомкам(вроде так называется)
                    helpSort(k, gr, stack);
                }
                gr.g.get(vCur).changeColor();
                stack.push(stack);                              //Если потомки закончились, пушим эту вершину в стек
            }
        }
    }

    public void sort(Stack stack,Graph gr, int vertexCount){    //Головная функция топологической сортировки, вызывающая рекурсивную функцию
        for (int k: gr.g.keySet()) {
            if(gr.g.get(k).getColor() == vColor.WHITE)
                helpSort(k, gr, stack);
        }

        int index = 0;
        while(!stack.isEmpty()){                                //Снимаем со стека элементы и назначаем им индексы их порядка
            gr.g.get(stack.pop()).setIndex(index);
            index++;
        }
    }

}
