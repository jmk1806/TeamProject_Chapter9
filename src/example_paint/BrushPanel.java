package example_paint;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.Vector;
import javax.swing.*;

public class BrushPanel extends JPanel {

    public Vector<Point> pointVector = new Vector<>();

    public int thickness = 1;
    public int size = 1;

    private Color brushColor;

    public BrushPanel() {
        this.setBackground(Color.WHITE);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pointVector.add(e.getPoint());
            }

            public void mouseReleased(MouseEvent e) {
                pointVector.add(new Point(0, 0));
            }

            public void mouseClicked(MouseEvent e) {
                getGraphics().fillOval(e.getX(), e.getY(), size, size);
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Graphics graphics = getGraphics();
                Graphics2D graphics2D = (Graphics2D)graphics;

                pointVector.add(e.getPoint());

                Point startPoint = pointVector.elementAt(pointVector.size() - 2);
                Point endPoint = pointVector.elementAt(pointVector.size() - 1);

                graphics.setColor(brushColor);
                graphics2D.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, 0));
                graphics2D.draw(new Line2D.Double(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY()));
            }
        });
    }

    public void setColor(Color newColor) {
        this.brushColor = newColor;
    }

    public Color getColor(Color backgroundColor) {
        return brushColor;
    }
}