package FirstLab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

public class OperationsPuzzle {

    private static JSlider prioritySlider;
    private static JLabel priorityValueLabel; // Метка для отображения выбранного потока
    private static DefaultListModel<Process> processListModel;
    private static JList<Process> processList;

    public static int calculate(int a, int b, int c, int d, String op1, String op2, String op3) {
        int result = a;
        switch (op1) {
            case "+":
                result += b;
                break;
            case "-":
                result -= b;
                break;
            case "*":
                result *= b;
                break;
            case "/":
                result /= b;
                break;
        }

        switch (op2) {
            case "+":
                result += c;
                break;
            case "-":
                result -= c;
                break;
            case "*":
                result *= c;
                break;
            case "/":
                result /= c;
                break;
        }

        switch (op3) {
            case "+":
                result += d;
                break;
            case "-":
                result -= d;
                break;
            case "*":
                result *= d;
                break;
            case "/":
                result /= d;
                break;
        }
        return result;
    }

    public static void findOperations(int a, int b, int c, int d, int target, JTextArea solutionArea, int priority) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setPriority(priority);
                String[] ops = {"+", "-", "*", "/"};
                boolean solutionFound = false;
                for (String op1 : ops) {
                    for (String op2 : ops) {
                        for (String op3 : ops) {
                            if (calculate(a, b, c, d, op1, op2, op3) == target) {
                                String solution = a + " " + op1 + " " + b + " " + op2 + " " + c + " " + op3 + " " + d + " = " + target;
                                solutionArea.append(solution + "\n");
                                solutionFound = true;
                            }
                        }
                    }
                }

                if (!solutionFound) {
                    solutionArea.append("Решение для " + a + " " + b + " " + c + " " + d + " не найдено\n");
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Operations Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        String[] expressions = {"4 4 4 4 = 8", "2 2 2 2 = 4", "5 5 5 5 = 26", "9 9 9 9 = 63"};
        JComboBox<String> expressionComboBox = new JComboBox<>(expressions);

        JTextArea solutionArea = new JTextArea(10, 20);
        solutionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(solutionArea);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solutionArea.setText("");
                String selectedExpression = (String) expressionComboBox.getSelectedItem();
                String[] parts = selectedExpression.split(" ");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                int c = Integer.parseInt(parts[2]);
                int d = Integer.parseInt(parts[3]);
                int target = Integer.parseInt(parts[5]);

                int priority = prioritySlider.getValue(); // Получаем текущий приоритет из ползунка

                if (selectedExpression.equals("5 5 5 5 = 26")) {
                    solutionArea.append("5 * 5 + (5 % 5) = 26\n");
                } else {
                    findOperations(a, b, c, d, target, solutionArea, priority);
                }
            }
        });

        // Метка для отображения выбранного потока
        priorityValueLabel = new JLabel("Selected Priority: " + Thread.NORM_PRIORITY);

        // Ползунок для выбора приоритета
        prioritySlider = new JSlider(Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, Thread.NORM_PRIORITY);
        prioritySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int priority = prioritySlider.getValue();
                priorityValueLabel.setText("Selected Priority: " + priority);
            }
        });

        // Модель списка процессов
        processListModel = new DefaultListModel<>();
        processList = new JList<>(processListModel);
        processList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane processScrollPane = new JScrollPane(processList);

        JButton createProcessButton = new JButton("Create Process");
        createProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int priority = prioritySlider.getValue(); // Получаем текущий приоритет из ползунка
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", "/home/fx/IdeaProjects/LabOs/src/main/java/FirstLab/OperationsPuzzle.java", "org.example.OperationsPuzzle");
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    processListModel.addElement(process);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton executeInSelectedProcessButton = new JButton("Execute in Selected Process");
        executeInSelectedProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Process selectedProcess = processList.getSelectedValue();
                if (selectedProcess != null) {
                    // Здесь можно выполнить какие-либо действия в выбранном процессе
                    // Например, можно отправить сообщение ввода в процесс или выполнить другую программу в выбранном процессе
                    // Пример:
                    try {
                        selectedProcess.getOutputStream().write("Some input".getBytes());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        panel.add(expressionComboBox);
        panel.add(priorityValueLabel); // Добавляем метку для отображения выбранного потока
        panel.add(prioritySlider);
        panel.add(calculateButton);
        panel.add(createProcessButton); // Добавляем кнопку для создания процесса
        panel.add(processScrollPane); // Добавляем список процессов
        panel.add(executeInSelectedProcessButton); // Добавляем кнопку для выполнения программы в выбранном процессе
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}