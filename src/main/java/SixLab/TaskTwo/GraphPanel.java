package SixLab.TaskTwo;

import javax.swing.*;
import java.awt.*;

class GraphPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillOval(50, 150, 50, 50);  // Кружочек FROM
        g.fillOval(250, 150, 50, 50); // Кружочек TO
        g.setColor(Color.BLACK);
        g.drawString("FROM", 60, 180);
        g.drawString("TO", 270, 180);

        // Стрелочка от FROM к TO
        g.drawLine(100, 175, 250, 175);
        int[] xPoints1 = {250, 240, 240};
        int[] yPoints1 = {175, 170, 180};
        g.fillPolygon(xPoints1, yPoints1, 3);
        g.drawString("Thread 1", 150, 165);

        // Стрелочка от TO к FROM
        g.drawLine(88, 190, 255, 190);
        int[] xPoints2 = {88, 98, 98};
        int[] yPoints2 = {190, 185, 195};
        g.fillPolygon(xPoints2, yPoints2, 3);
        g.drawString("Thread 2", 150, 215);
    }
}
