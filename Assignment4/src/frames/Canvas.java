package frames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import composite.ComplexShape;
import composite.FilledOval;
import composite.FilledRectangle;
import composite.FreeDrawing;
import composite.Oval;
import composite.Rectangle;
import composite.Shape;

public class Canvas extends JPanel {

	private static final long serialVersionUID = -4471075989653608834L;
	private int x1, y1, x2, y2;
	private Color color;
	private Cursor cursor;
	private ComplexShape scene;
	private int dragShapeSize;
	private BasicStroke stroke;

	public static enum BasicShapeType {
		FILLED_OVAL, FILLED_RECTANGLE, FILLED_ROUND_RECTANGLE, FREE_DRAWING, OVAL, RECTANGLE, ROUND_RECTANGLE, LINE
	};

	private BasicShapeType currentShapeType;
	private Shape uncompletedShape;
	private FreeDrawing uncompletedFreeShape;

	private class MyMouseListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseEntered(MouseEvent arg0) {
			setCursor(cursor);

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			x1 = arg0.getX();
			y1 = arg0.getY();
			if (currentShapeType.equals(BasicShapeType.FREE_DRAWING)) {
				uncompletedFreeShape = new FreeDrawing(color);
			}

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			x2 = e.getX();
			y2 = e.getY();
			Shape current;
			if (currentShapeType.equals(BasicShapeType.FREE_DRAWING)) {
				current = uncompletedFreeShape;
			} else {
				current = createShape();
			}
			scene.add(current);
			uncompletedShape = null;
			uncompletedFreeShape = null;
			repaint();

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			x2 = e.getX();
			y2 = e.getY();
			if (currentShapeType.equals(BasicShapeType.FREE_DRAWING)) {
				uncompletedFreeShape.add(new Point(x2, y2));
			} else {
				uncompletedShape = createShape();
			}
			repaint();

		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

	}

	public Canvas() {
		setBackground(Color.WHITE);
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		addMouseMotionListener(ml);
		color = new Color(100, 100, 100);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("pen-15.png");
		cursor = toolkit.createCustomCursor(image, new Point(0, 0), "pen");
		scene = new ComplexShape();
		currentShapeType = BasicShapeType.FILLED_OVAL;
		dragShapeSize=10;
		stroke = new BasicStroke(5);

	}

	public composite.Shape createShape() {
		switch (currentShapeType) {
		case FILLED_OVAL:
			return new composite.FilledOval(x1, y1, x2, y2, color);
		case FILLED_RECTANGLE:
			return new composite.FilledRectangle(x1, y1, x2, y2, color);
		case FILLED_ROUND_RECTANGLE:
			return new composite.FilledRoundRectangle(x1, y1, x2, y2, color);
		case OVAL:
			return new composite.Oval(x1, y1, x2, y2, color);
		case RECTANGLE:
			return new composite.Rectangle(x1, y1, x2, y2, color);
		case ROUND_RECTANGLE:
			return new composite.RoundRectangle(x1, y1, x2, y2, color);
		case LINE:
			return new composite.StraightLine(x1, y1, x2, y2, color);

		default:
			return null;
		}

	}

	public BasicShapeType getCurrentShapeType() {
		return currentShapeType;
	}

	public void setCurrentShapeType(BasicShapeType currentShapeType) {
		this.currentShapeType = currentShapeType;
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
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(stroke);
		scene.drawShape(g2d);

		if (uncompletedShape != null) {
			uncompletedShape.drawShape(g);
		}
		if (uncompletedFreeShape != null) {
			uncompletedFreeShape.drawShape(g);
		}

	}

	public void addDropShape(String label) {
		switch (label){
		case "Circle": 
			scene.add(new Oval(x2-dragShapeSize/2, y2-dragShapeSize/2, x2+dragShapeSize/2, y2+dragShapeSize/2, color));
			break;
		case "Filled Circle": 
			scene.add(new FilledOval(x2-dragShapeSize/2, y2-dragShapeSize/2, x2+dragShapeSize/2, y2+dragShapeSize/2, color));
			break;
		case "Square": 
			scene.add(new Rectangle(x2-dragShapeSize/2, y2-dragShapeSize/2, x2+dragShapeSize/2, y2+dragShapeSize/2, color));
			break;
		case "Filled Square": 
			scene.add(new FilledRectangle(x2-dragShapeSize/2, y2-dragShapeSize/2, x2+dragShapeSize/2, y2+dragShapeSize/2, color));
			break;
		}
		repaint();
		
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getDragShapeSize() {
		return dragShapeSize;
	}

	public void setDragShapeSize(int dragShapeSize) {
		this.dragShapeSize = dragShapeSize;
	}

	public ComplexShape getScene() {
		return scene;
	}

	public void setScene(ComplexShape scene) {
		this.scene = scene;
		repaint();
	}

	public void clearScene() {
		scene.clear();
		repaint();
		
	}
	
	public void setBurshSize(int size){
		stroke=new BasicStroke(size);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
