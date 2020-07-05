package UserInteractions;

import java.util.Scanner;
import graph.*;
/**
 * Класс ввода графа при помощи определяющей строки.
 * @author artembutko
 * @version 0.1
 * #TODO поле графа, создание графа
 */

public class InputByString
{
    /** Поле ввода */
    private final Scanner input;

    /** Поле определения Графа*/
    private String definition;

    Graph graph;

    /** Конструктор класса, можно пока создавать граф здесь */
    public InputByString()
    {
        this.input = new Scanner(System.in);
        this.definition = new String("");
        this.graph = new Graph();
    }

    /** Функция ввода определения графа */
    public void defOfGraph()
    {
        System.out.print("Give definition of the Oriented Graph.\n" +
                "example: ({1,2,3},{(1;2),(2;3)})\n" +
                "input : ");
        definition = input.nextLine().replaceAll("\\s+","");
        System.out.println(definition);
        if (check()) parse();
        else System.out.print("wrong string format");
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
        {
            System.out.println(vertex);
            /*
            #TODO Граф.addVertex(v: vertex);
             */
            graph.addVertex(vertex);
        }
        for (String edge : edges)
        {
            edge = edge.replaceAll("\\(|\\)", "");
            graph.addEdge(edge.split(";")[0], edge.split(";")[1]);


            //System.out.println(edge);
            /*
            #TODO Граф.addEdge(vFrom: edge.split(";")[0], vTo: edge.split(";")[1]);
             */
        }
    }
}
