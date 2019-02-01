package ro.utcluj.view;

import ro.utcluj.model.library.Library;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class EmployeeWindow implements Observer {

    public static final  String[]   COLUMN_NAMES = {"Title", "Author", "Genre", "Year", "Price", "Stock"};
    private static final boolean    isDebug      = false;
    private              JFrame     frmLibraryEmployee;
    private              JTable     table;
    private              JTextField textField;
    private              JButton    btnListAllBooks;
    private              JButton    btnSearchByTitle;
    private              JButton    btnSearchByAuthor;
    private              JButton    btnSearchByYear;
    private              JButton    btnSell;
    private              JSpinner   spinner;
    private final        Library    library;

    public EmployeeWindow(Library library) {
        this.library = library;
        this.library.addObserver(this);
        initialize();
        frmLibraryEmployee.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmLibraryEmployee = new JFrame();
        frmLibraryEmployee.setIconImage(Toolkit.getDefaultToolkit().getImage(EmployeeWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
        frmLibraryEmployee.setTitle("Library Employee");
        frmLibraryEmployee.setBounds(100, 100, 600, 350);
        frmLibraryEmployee.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane();

        JPanel commandPanel = new JPanel();
        GroupLayout groupLayout = new GroupLayout(frmLibraryEmployee.getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(commandPanel, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE).addGap(11)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(commandPanel, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)).addContainerGap()));
        GridBagLayout gbl_commandPanel = new GridBagLayout();
        gbl_commandPanel.columnWidths = new int[]{0, 0, 0};
        gbl_commandPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_commandPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_commandPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        commandPanel.setLayout(gbl_commandPanel);

        btnListAllBooks = new JButton("List All Books");
        GridBagConstraints gbc_btnListAllBooks = createGridConstraints();
        gbc_btnListAllBooks.gridy = 0;
        commandPanel.add(btnListAllBooks, gbc_btnListAllBooks);

        JLabel lblInput = new JLabel("Input:");
        lblInput.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblInput = new GridBagConstraints();
        gbc_lblInput.insets = new Insets(0, 0, 5, 5);
        gbc_lblInput.anchor = GridBagConstraints.EAST;
        gbc_lblInput.gridx = 0;
        gbc_lblInput.gridy = 1;
        commandPanel.add(lblInput, gbc_lblInput);

        textField = new JTextField();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        commandPanel.add(textField, gbc);
        textField.setColumns(10);

        btnSearchByTitle = new JButton("Search By Title");
        GridBagConstraints gbc_btnSearchByTitle = createGridConstraints();
        gbc_btnSearchByTitle.gridy = 2;
        commandPanel.add(btnSearchByTitle, gbc_btnSearchByTitle);

        btnSearchByAuthor = new JButton("Search By Author");
        GridBagConstraints gbc_btnSearchByAuthor = createGridConstraints();
        gbc_btnSearchByAuthor.gridy = 3;
        commandPanel.add(btnSearchByAuthor, gbc_btnSearchByAuthor);

        btnSearchByYear = new JButton("Search By Year");
        GridBagConstraints gbc_btnSearchByYear = createGridConstraints();
        gbc_btnSearchByYear.gridy = 4;
        commandPanel.add(btnSearchByYear, gbc_btnSearchByYear);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
        gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
        gbc_lblQuantity.gridx = 0;
        gbc_lblQuantity.gridy = 5;
        commandPanel.add(lblQuantity, gbc_lblQuantity);

        spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
        GridBagConstraints gbc_spinner = new GridBagConstraints();
        gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinner.insets = new Insets(0, 0, 5, 0);
        gbc_spinner.gridx = 1;
        gbc_spinner.gridy = 5;
        commandPanel.add(spinner, gbc_spinner);

        btnSell = new JButton("Sell");
        GridBagConstraints gbc_btnSell = new GridBagConstraints();
        gbc_btnSell.gridx = 1;
        gbc_btnSell.gridy = 6;
        commandPanel.add(btnSell, gbc_btnSell);

        table = new JTable();
        if (isDebug) {
            table.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null}, {null, null, null, null, null, null},}, COLUMN_NAMES));
        } else {
            table.setModel(createTabularData(library));
        }

        setColumnWidth();
        scrollPane.setViewportView(table);
        frmLibraryEmployee.getContentPane().setLayout(groupLayout);

        JMenuBar menuBar = new JMenuBar();
        frmLibraryEmployee.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        mnFile.setMnemonic('F');
        menuBar.add(mnFile);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(e -> System.exit(0));
        mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
        mnFile.add(mntmExit);
    }

    private DefaultTableModel createTabularData(Library library) {
        //NOTE: creating a new DefaultTableModel instance every time we receive new data will have a great performance penalty for a real life app
        return new DefaultTableModel(library.toObjectMatrix(), COLUMN_NAMES) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
    }

    private GridBagConstraints createGridConstraints() {
        GridBagConstraints gbc_btnSearchByYear = new GridBagConstraints();
        gbc_btnSearchByYear.insets = new Insets(0, 0, 5, 0);
        gbc_btnSearchByYear.gridx = 1;
        return gbc_btnSearchByYear;
    }

    @Override
    public void update(Observable o, Object arg) {
        table.setModel(createTabularData((Library) o));
        setColumnWidth();
    }

    private void setColumnWidth() {
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(3).setPreferredWidth(45);
        table.getColumnModel().getColumn(4).setPreferredWidth(45);
        table.getColumnModel().getColumn(5).setPreferredWidth(45);
    }

    public JFrame getFrmLibraryEmployee() {
        return frmLibraryEmployee;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable mTable) {
        this.table = mTable;
        setColumnWidth();
    }

    public JTextField getTextField() {
        return textField;
    }

    public JButton getBtnListAllBooks() {
        return btnListAllBooks;
    }

    public JButton getBtnSearchByTitle() {
        return btnSearchByTitle;
    }

    public JButton getBtnSearchByAuthor() {
        return btnSearchByAuthor;
    }

    public JButton getBtnSearchByYear() {
        return btnSearchByYear;
    }

    public JButton getBtnSell() {
        return btnSell;
    }

    public JSpinner getSpinner() {
        return spinner;
    }
}
