package UserInteractions;

import Algorithm.Algorithm;
import Digraph.Digraph;

import java.util.Arrays;
import java.util.Scanner;

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

    private final Digraph graph;

    /** Конструктор класса, можно пока создавать граф здесь */
    public InputByString()
    {
        this.input = new Scanner(System.in);
        this.definition = new String("");
        this.graph = new Digraph();
    }

    /** Функция ввода определения графа */
    public void defOfGraph()
    {
        System.out.print("Give definition of the Oriented Graph.\n" +
                "example 1: ({A, B, C, D, E, F, G, H}, {(C;D),(C;E),(B;D),(A;E),(D;F),(D;G),(D;H),(A;H),(E;G)})\n" +
                "example 2: ({D, E, C, B, A, F},{(D;B),(D;C),(E;C),(E;F),(B;A),(A;F)})\n" +
                "input : ");
        definition = input.nextLine().replaceAll("\\s+","");
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
            graph.addVertex(vertex);
        Arrays.stream(edges).map(edge -> edge.replaceAll("\\(|\\)", "")).forEach(edge -> graph.addEdge(edge.split(";")[0], edge.split(";")[1]));
    }

    public void getInformation()
    {
        Algorithm alg = new Algorithm(graph);
        System.out.println("Result of Topological Sort: " + alg.sort());
    }
}
