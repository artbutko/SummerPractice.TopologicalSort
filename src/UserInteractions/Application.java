package UserInteractions;

import javax.swing.*;

public class Application
{
    public static void main(String[] args)
    {
        String def = JOptionPane.showInputDialog("Введите строку:\n", "({A, B, C, D, E, F, G, H}, {(C;D),(C;E),(B;D),(A;E),(D;F),(D;G),(D;H),(A;H),(E;G)})");
        /*
        "пример 1: ({A, B, C, D, E, F, G, H}, {(C;D),(C;E),(B;D),(A;E),(D;F),(D;G),(D;H),(A;H),(E;G)})\n" +
        "пример 2: ({D, E, C, B, A, F},{(D;B),(D;C),(E;C),(E;F),(B;A),(A;F)})\n"
         */
        InputByString input = new InputByString(def);
        JOptionPane.showConfirmDialog(null, input.result, "Результат сортировки", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}