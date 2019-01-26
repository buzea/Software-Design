package ro.utcluj.frames;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import ro.utcluj.composite.ComplexShape;
import ro.utcluj.frames.Canvas.BasicShapeType;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

@SuppressWarnings("SpellCheckingInspection")
public class ApplicationWindow {

	private JFrame                    frame;
	private JComboBox<BasicShapeType> shapeBox;
	private Color                     selectedColor;
	private JPanel                    showColorPanel;
	private Canvas                    canvas;
	private JToggleButton             tglbtnFrameToggle;

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 618, 462);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Canvas();
		new MyDropTargetListener(canvas);

		JLabel lblShape = new JLabel("Shape:");

		shapeBox = new JComboBox<>();
		shapeBox = new JComboBox<>(Canvas.BasicShapeType.values());

		shapeBox.addActionListener(arg0 -> canvas.setCurrentShapeType((BasicShapeType) shapeBox.getSelectedItem()));

		selectedColor = new Color(100, 100, 100);

		JPanel colorPanel = new JPanel();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(canvas);

		JButton btnChoose = new JButton("Choose Color");
		btnChoose.addActionListener(e -> {
			selectedColor = JColorChooser.showDialog(null, "Choose a Color", selectedColor);
			showColorPanel.setBackground(selectedColor);
			canvas.setColor(selectedColor);
		});

		JPanel dragShapesPanel = new JPanel();
		dragShapesPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));

		JPanel panel = new JPanel();

		tglbtnFrameToggle = new JToggleButton("Frame Toggle");
		tglbtnFrameToggle.addActionListener(e -> canvas.setFrameScene(tglbtnFrameToggle.isSelected()));

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addComponent(panel, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addComponent(shapeBox, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE).addComponent(lblShape, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addComponent(dragShapesPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE).addComponent(btnChoose, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)).addComponent(tglbtnFrameToggle, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)).addGap(6).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE).addGap(8)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addComponent(lblShape).addPreferredGap(ComponentPlacement.RELATED).addComponent(shapeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10).addComponent(colorPanel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnChoose).addPreferredGap(ComponentPlacement.RELATED).addComponent(dragShapesPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(7).addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(tglbtnFrameToggle))).addContainerGap()));
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblBrushSize = new JLabel("Brush Size:");
		lblBrushSize.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblBrushSize);

		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.addChangeListener(arg0 -> canvas.setBrushSize(slider.getValue()));
		slider.setValue(1);
		slider.setMaximum(10);
		panel.add(slider);
		dragShapesPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblDragdropShapes = new JLabel("Drag&Drop Shapes:");
		lblDragdropShapes.setFont(new Font("Tahoma", Font.BOLD, 11));
		dragShapesPanel.add(lblDragdropShapes, "2, 2");

		JLabel lblCircle = new JLabel("Circle");
		lblCircle.setTransferHandler(new TransferHandler("text"));
		lblCircle.addMouseListener(new DragMouseAdapter());

		JSeparator separator_1 = new JSeparator();
		dragShapesPanel.add(separator_1, "2, 4");

		dragShapesPanel.add(lblCircle, "2, 6, center, default");

		JLabel lblSquare = new JLabel("Square");
		lblSquare.setTransferHandler(new TransferHandler("text"));
		lblSquare.addMouseListener(new DragMouseAdapter());
		dragShapesPanel.add(lblSquare, "2, 8, center, default");

		JLabel lblFilleCircle = new JLabel("Filled Circle");
		lblFilleCircle.setTransferHandler(new TransferHandler("text"));
		lblFilleCircle.addMouseListener(new DragMouseAdapter());
		dragShapesPanel.add(lblFilleCircle, "2, 10, center, default");

		JLabel lblFilledRectangle = new JLabel("Filled Square");
		lblFilledRectangle.setTransferHandler(new TransferHandler("text"));
		lblFilledRectangle.addMouseListener(new DragMouseAdapter());
		dragShapesPanel.add(lblFilledRectangle, "2, 12, center, default");

		JSeparator separator = new JSeparator();
		dragShapesPanel.add(separator, "2, 14");

		JLabel lblSize = new JLabel("Shape Size:");
		lblSize.setFont(new Font("Tahoma", Font.BOLD, 11));
		dragShapesPanel.add(lblSize, "2, 16, center, default");

		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(arg0 -> canvas.setDragShapeSize((int) spinner.getValue()));
		spinner.setModel(new SpinnerNumberModel(1, 0, null, 10));
		dragShapesPanel.add(spinner, "2, 18");
		colorPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),}));

		JLabel lblColor = new JLabel("Color:");
		colorPanel.add(lblColor, "2, 2");

		showColorPanel = new JPanel();
		showColorPanel.setBackground(selectedColor);
		colorPanel.add(showColorPanel, "4, 2, fill, fill");
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(arg0 -> System.exit(0));

		JMenuItem mntmSaveScene = new JMenuItem("Save Scene");
		mntmSaveScene.addActionListener(arg0 -> {

			try {
				FileOutputStream fout = new FileOutputStream("scene.bin");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(canvas.getScene());
				oos.close();
				fout.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		});

		JMenuItem mntmClearScene = new JMenuItem("Clear Scene");
		mntmClearScene.addActionListener(e -> canvas.clearScene());
		mnFile.add(mntmClearScene);
		mnFile.add(mntmSaveScene);

		JMenuItem mntmLoadScene = new JMenuItem("Load Scene");
		mntmLoadScene.addActionListener(e -> {
			try {
				FileInputStream fin = new FileInputStream("scene.bin");
				ObjectInputStream ois = new ObjectInputStream(fin);
				ComplexShape scene = (ComplexShape) ois.readObject();
				canvas.setScene(scene);
				ois.close();
				fin.close();
			} catch (IOException | ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(frame, "No Scene Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		});
		mnFile.add(mntmLoadScene);
		mnFile.add(mntmExit);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	@SuppressWarnings({"unused", "SpellCheckingInspection"})
	private class MyDropTargetListener extends DropTargetAdapter {

		private final DropTarget dropTarget;

		private final Canvas canvas;

		public MyDropTargetListener(Canvas panel) {
			canvas = panel;

			dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
		}

		public void drop(DropTargetDropEvent event) {
			try {
				DataFlavor dflv = new DataFlavor("application/x-java-jvm-local-objectref; class=java.lang.String");
				Transferable tr = event.getTransferable();
				String label = (String) tr.getTransferData(dflv);

				if (event.isDataFlavorSupported(dflv)) {
					Point p = event.getLocation();
					canvas.setX2(p.x);
					canvas.setY2(p.y);
					event.acceptDrop(DnDConstants.ACTION_COPY);
					canvas.addDropShape(label);
					event.dropComplete(true);
					return;
				}
				event.rejectDrop();
			} catch (Exception e) {
				e.printStackTrace();
				event.rejectDrop();
			}
		}
	}

	private class DragMouseAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, e, TransferHandler.COPY);
		}
	}
}
