package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import composite.FreeDrawing;

public class Canvas extends JPanel {

	private static final long serialVersionUID = -4471075989653608834L;
	private int x, y;
	private Color color;
	private Cursor cursor;
	private FreeDrawing freeDraw;

	private class MyMouseListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(cursor);

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			x = arg0.getX();
			y = arg0.getY();

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			freeDraw.add(new Point(x,y));
			repaint();

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public Canvas() {
		setBackground(Color.WHITE);
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		addMouseMotionListener(ml);
		color = Color.BLACK;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("pen-15.png");
		cursor = toolkit.createCustomCursor(image, new Point(0, 0), "pen");
		freeDraw = new FreeDrawing();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.setColor(Color.WHITE);
		// g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(color);
		g.fillRect(x, y, 10, 10);
		freeDraw.drawShape(g);
	}

}
