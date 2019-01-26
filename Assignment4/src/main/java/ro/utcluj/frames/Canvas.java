package ro.utcluj.frames;

import ro.utcluj.composite.Rectangle;
import ro.utcluj.composite.Shape;
import ro.utcluj.composite.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Canvas extends JPanel {

	private static final long serialVersionUID = -4471075989653608834L;
	private              int  x1, y1, x2, y2;
	private       Color          color;
	private final Cursor         cursor;
	private       ComplexShape   scene;
	private       int            dragShapeSize;
	private       BasicStroke    stroke;
	private       boolean        frameScene;
	private       BasicShapeType currentShapeType;
	private       Shape          uncompletedShape;
	private       FreeDrawing    uncompletedFreeShape;

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
		dragShapeSize = 10;
		stroke = new BasicStroke(5);
		frameScene = false;
	}

	public ro.utcluj.composite.Shape createShape() {
		switch (currentShapeType) {
			case FILLED_OVAL:
				return new ro.utcluj.composite.FilledOval(x1, y1, x2, y2, color);
			case FILLED_RECTANGLE:
				return new ro.utcluj.composite.FilledRectangle(x1, y1, x2, y2, color);
			case FILLED_ROUND_RECTANGLE:
				return new ro.utcluj.composite.FilledRoundRectangle(x1, y1, x2, y2, color);
			case OVAL:
				return new ro.utcluj.composite.Oval(x1, y1, x2, y2, color);
			case RECTANGLE:
				return new ro.utcluj.composite.Rectangle(x1, y1, x2, y2, color);
			case ROUND_RECTANGLE:
				return new ro.utcluj.composite.RoundRectangle(x1, y1, x2, y2, color);
			case LINE:
				return new ro.utcluj.composite.StraightLine(x1, y1, x2, y2, color);

			default:
				return null;
		}
	}

	public void setCurrentShapeType(BasicShapeType currentShapeType) {
		this.currentShapeType = currentShapeType;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(stroke);
		if (!frameScene) {
			scene.drawShape(g2d);
		} else {
			FramedShape framedScene = new FramedShape(scene);
			framedScene.drawShape(g2d);
		}

		if (uncompletedShape != null) {
			uncompletedShape.drawShape(g);
		}
		if (uncompletedFreeShape != null) {
			uncompletedFreeShape.drawShape(g);
		}
	}

	public void addDropShape(String label) {
		switch (label) {
			case "Circle":
				scene.add(new Oval(x2 - dragShapeSize / 2, y2 - dragShapeSize / 2, x2 + dragShapeSize / 2, y2 + dragShapeSize / 2, color));
				break;
			case "Filled Circle":
				scene.add(new FilledOval(x2 - dragShapeSize / 2, y2 - dragShapeSize / 2, x2 + dragShapeSize / 2, y2 + dragShapeSize / 2, color));
				break;
			case "Square":
				scene.add(new Rectangle(x2 - dragShapeSize / 2, y2 - dragShapeSize / 2, x2 + dragShapeSize / 2, y2 + dragShapeSize / 2, color));
				break;
			case "Filled Square":
				scene.add(new FilledRectangle(x2 - dragShapeSize / 2, y2 - dragShapeSize / 2, x2 + dragShapeSize / 2, y2 + dragShapeSize / 2, color));
				break;
		}
		repaint();
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
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

	public void setBrushSize(int size) {
		stroke = new BasicStroke(size);
	}

	public void setFrameScene(boolean frameScene) {
		this.frameScene = frameScene;
		repaint();
	}

	public enum BasicShapeType {
		FILLED_OVAL, FILLED_RECTANGLE, FILLED_ROUND_RECTANGLE, FREE_DRAWING, OVAL, RECTANGLE, ROUND_RECTANGLE, LINE
	}

	private class MyMouseListener implements MouseListener, MouseMotionListener {

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

		@Override
		public void mousePressed(MouseEvent arg0) {
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
		public void mouseEntered(MouseEvent arg0) {
			setCursor(cursor);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}
	}
}
