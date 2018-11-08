package DrawingBoard;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.awt.geom.*;

public class Tools extends JFrame {
	public Tools() {
		super("�׸��� ���������ž�ä�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container container = getContentPane();
		JPanel workingArea = new BrushPanel();
		JPanel menu = new MenuPanel(this);

		container.add(workingArea, BorderLayout.CENTER);
		container.add(menu, BorderLayout.NORTH);

		addKeyListener(new KeyAdapterColor());

		setFocusable(true);
		requestFocus();

		setSize(1000, 700);
		setVisible(true);
	}
	class KeyAdapterColor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyChar()) {

			case '1':
				BrushPanel.selectedColor=Color.RED;
				break;

			case '2':
				BrushPanel.selectedColor=Color.ORANGE;
				break;

			case '3':
				BrushPanel.selectedColor=Color.YELLOW;
				break;

			case '4':
				BrushPanel.selectedColor=Color.GREEN;
				break;

			case '5':
				BrushPanel.selectedColor=Color.CYAN;
				break;

			case '6':
				BrushPanel.selectedColor=Color.BLUE;
				break;

			case '7':
				BrushPanel.selectedColor=Color.MAGENTA;
				break;

			case '8':
				BrushPanel.selectedColor=Color.PINK;
				break;

			case '9':
				BrushPanel.selectedColor=Color.LIGHT_GRAY;
				break;

			case '0':
				BrushPanel.selectedColor=Color.GRAY;
				break;
			case '-':
				BrushPanel.selectedColor=Color.DARK_GRAY;
				break;
			case '=':
				BrushPanel.selectedColor=Color.BLACK;
				break;
			}

		}

	}

	public static void main(String[] args) {
		new Tools();
	}
}

class BrushPanel extends JPanel {
	Vector<Point> pointList = new Vector<Point>();
	static int thickness = 1, size=1;

	static Color selectedColor;
	Graphics g;
	Graphics2D g2;

	public BrushPanel() {
		setBackground(Color.WHITE);
		DrawingHandler listener = new DrawingHandler();

		addMouseListener(listener);
		addMouseMotionListener(listener);
	}

	class DrawingHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Point newPoint = e.getPoint();
			pointList.add(newPoint);
		}

		public void mouseReleased(MouseEvent e) {
			Point stopPoint = new Point(0, 0);
			pointList.add(stopPoint);
		}

		public void mouseDragged(MouseEvent e) {
			g=getGraphics();
			g2=(Graphics2D) g;
			
			Point newPoint = e.getPoint();
			pointList.add(newPoint);

			Point start = pointList.elementAt(pointList.size() - 2);
			Point end = pointList.elementAt(pointList.size() - 1);
			
			g.setColor(selectedColor);
			g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, 0));
			g2.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
		}
		public void mouseClicked(MouseEvent e) {
			g=getGraphics();
			
			g.fillOval(e.getX(), e.getY(), size, size);
		}
	}

}

class MenuPanel extends JPanel {
	public MenuPanel(Tools p) {
		setBackground(Color.GRAY);
		JButton chosenColor=new JButton(" ");
		JButton brushButton = new JButton("Brush");
		JButton eraserButton = new JButton("Eraser");
		JButton boldButton = new JButton("Light");
		
		add(chosenColor);
		add(brushButton);
		add(eraserButton);
		add(boldButton);
		
		brushButton.addActionListener(new BrushHandler(p));
		eraserButton.addActionListener(new EraserHandler());
		boldButton.addActionListener(new ThicknessHandler(p));
		chosenColor.setBackground(BrushPanel.selectedColor);
	}
	
	class BrushHandler implements ActionListener{
		Tools tools;
		public BrushHandler(Tools tools) {
			this.tools=tools;
		}
		public void actionPerformed(ActionEvent e) {
			BrushPanel.selectedColor=Color.BLACK;
			tools.requestFocus();
		}
	}
	
	class EraserHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			BrushPanel.selectedColor=Color.WHITE;
		}
	}

	class ThicknessHandler implements ActionListener {
		Tools tools;
		public ThicknessHandler(Tools tools) {
			this.tools=tools;
		}
		public void actionPerformed(ActionEvent e) {
			JButton g = (JButton) e.getSource();
			if (g.getText().equals("Light")) {
				BrushPanel.thickness = 5;
				BrushPanel.size=3;
				g.setText("Medium");
				
			} else if (g.getText().equals("Medium")) {
				BrushPanel.thickness = 10;
				BrushPanel.size=5;
				g.setText("Bold");
				
			} else if (g.getText().equals("Bold")) {
				BrushPanel.thickness = 20;
				BrushPanel.size=10;
				g.setText("Extra");
				
			} else if (g.getText().equals("Extra")) {
				BrushPanel.thickness = 1;
				BrushPanel.size=1;
				g.setText("Light");
			}
			tools.requestFocus();
		}
	}
}