package SixLab.TaskTwo;

import javax.swing.*;

public class DeadlockGraph extends JFrame {

    public DeadlockGraph() {
        setTitle("Deadlock Graph");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DeadlockGraph();
        });
    }
}

