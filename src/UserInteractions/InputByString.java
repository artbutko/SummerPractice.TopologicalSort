package UserInteractions;

import Algorithm.Algorithm;
import Digraph.Digraph;

import javax.swing.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс ввода графа при помощи определяющей строки.
 * @author artembutko
 * @version 0.1
 */

public class InputByString
{

    /** Результирующая строка */
    public final String result;

    /** Поле определения Графа*/
    private final String definition;

    /** Экземпляр ориентированного графа */
    private final Digraph graph;

    /** Конструктор класса, можно пока создавать граф здесь
     * @param definition -- определение графа*/
    public InputByString(String definition)
    {
        this.graph = new Digraph();
        this.definition = definition.replaceAll("\\s+","");
        if (check())
        {
            parse();
            result = getInformation();
        }
        else result = "Неверная строка";
    }

    /** Проверка корректности определения графа */
    private boolean check()
    {
        return definition.matches("\\(\\{(\\w+\\,)*(\\w+){1}\\}\\,\\{(\\(\\w+\\;\\w+\\)\\,)*\\(\\w+\\;\\w+\\)\\}\\)$");
    }

    /** Парсер строки для создания графа из полученных данных */
    private void parse()
    {
        String[] vertexes = definition.substring(2, definition.indexOf('}')).split(",");
        String[] edges = definition.substring(definition.indexOf('}') + 3, definition.length() - 2).split(",");
        for (String vertex : vertexes)
            graph.addVertex(vertex);
        Arrays.stream(edges).map(edge -> edge.replaceAll("\\(|\\)", "")).forEach(edge -> graph.addEdge(edge.split(";")[0], edge.split(";")[1]));
    }

    private String getInformation()
    {
        Algorithm alg = new Algorithm(graph);
        return "Результат сортировки: " + alg.sort();
    }
}
