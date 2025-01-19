package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.util.Date;

import domain.*;
import persistente.GestorBD;

public class VentanaHistorial extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color PRIMARY_COLOR = new Color(6, 99, 133);
    private static final Color SECONDARY_COLOR = new Color(7, 120, 163);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private final JButton btnAnadir;
    private final JTable tableHistorial;
    private final HistorialTableModel tableModel;
    private final Paciente paciente;
    private final List<Paciente> listaPacientes;
    private final Persona usuario;
    private final GestorBD gestorBD;

    public VentanaHistorial(Paciente paciente, Persona usuario, GestorBD gestorBD) {
        this.paciente = paciente;
        this.usuario = usuario;
        this.gestorBD = gestorBD;
        this.listaPacientes = Context.getInstance().getPacientes();

        // Initialize components
        btnAnadir = createStyledButton("Añadir");
        tableModel = new HistorialTableModel(paciente.getHistorialPaciente());
        tableHistorial = new JTable(tableModel);

        // Setup UI
        setupFrame();
        setupMainPanel();
        setupListeners();
    }

    private void setupFrame() {
        setTitle("Historial del Paciente");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("resources/images/hospital.png").getImage());
    }

    private void setupMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(createUpperPanel());
        mainPanel.add(createLowerPanel());
        setContentPane(mainPanel);
    }

    private JPanel createUpperPanel() {
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        upperPanel.add(createPatientInfoPanel(), BorderLayout.CENTER);
        return upperPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JButton btnVolver = createBackButton();
        JLabel logoLabel = createLogoLabel();

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.add(btnVolver);
        leftPanel.add(logoLabel);

        headerPanel.add(leftPanel, BorderLayout.WEST);
        return headerPanel;
    }

    private JButton createBackButton() {
        JButton btnVolver = createStyledButton("");
        btnVolver.setIcon(new ImageIcon("resources/images/icons8-back-25.png"));
        btnVolver.setPreferredSize(new Dimension(80, 35));
        btnVolver.addActionListener(e -> handleBack());
        return btnVolver;
    }

    private JLabel createLogoLabel() {
        ImageIcon iconoHospital = new ImageIcon("resources/images/hospital.png");
        Image scaledImage = iconoHospital.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaledImage));
    }

    private JPanel createPatientInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        infoPanel.setBackground(BACKGROUND_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addInfoLabel(infoPanel, "Nombre: " + paciente.getNombre());
        addInfoLabel(infoPanel, "Apellido: " + paciente.getApellido());
        addInfoLabel(infoPanel, "Edad: " + paciente.getEdad());
        addInfoLabel(infoPanel, "Ubicación: " + paciente.getUbicacion());
        addInfoLabel(infoPanel, "Código Paciente: " + paciente.getCodigoPaciente());
        
        if (usuario instanceof Doctor) {
            infoPanel.add(btnAnadir);
        } else {
            infoPanel.add(new JLabel());
        }

        return infoPanel;
    }

    private void addInfoLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(LABEL_FONT);
        label.setForeground(PRIMARY_COLOR);
        panel.add(label);
    }

    private JPanel createLowerPanel() {
        JPanel lowerPanel = new JPanel(new BorderLayout());
        initializeTable();
        lowerPanel.add(new JScrollPane(tableHistorial), BorderLayout.CENTER);
        return lowerPanel;
    }

    private void initializeTable() {
        tableHistorial.setRowHeight(40);
        tableHistorial.getTableHeader().setReorderingAllowed(false);
        tableHistorial.getTableHeader().setResizingAllowed(false);
        tableHistorial.setAutoCreateRowSorter(true);
        tableHistorial.getColumnModel().getColumn(2).setPreferredWidth(400);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < tableHistorial.getColumnCount(); i++) {
            TableColumn column = tableHistorial.getColumnModel().getColumn(i);
            column.setCellRenderer(centerRenderer);
            
            JTableHeader header = tableHistorial.getTableHeader();
            header.setBackground(PRIMARY_COLOR);
            header.setForeground(Color.WHITE);
            header.setFont(BUTTON_FONT);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void setupListeners() {
        btnAnadir.addActionListener(e -> {
            if (usuario instanceof Doctor) {
                anadirHistorial();
            }
        });
    }

    private void anadirHistorial() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 100));
        
        JButton btnSubmit = createStyledButton("Guardar");

        panel.add(new JLabel("Ingrese el historial:"), BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(btnSubmit, BorderLayout.SOUTH);

        btnSubmit.addActionListener(e -> {
            String newEntry = textField.getText().trim();
            if (!newEntry.isEmpty()) {
                Historial nuevoHistorial = new Historial();
                nuevoHistorial.setCausa(newEntry);
                nuevoHistorial.setFecha(new Date());
                
                if (usuario instanceof Doctor) {
                    nuevoHistorial.setMedico((Doctor) usuario);
                }
                
                paciente.getHistorialPaciente().add(0, nuevoHistorial);
                tableModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(this, "Historial añadido exitosamente.");
                
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) {
                    window.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Por favor, ingrese un texto.");
            }
        });

        JOptionPane.showMessageDialog(this, panel, "Añadir Historial", JOptionPane.PLAIN_MESSAGE);
    }

    private void handleBack() {
        if (paciente instanceof Paciente) {
            new MenuPaciente(paciente, gestorBD).setVisible(true);
        } else {
            new VentanaPacientes(listaPacientes, usuario, gestorBD).setVisible(true);
        }
        dispose();
    }
}