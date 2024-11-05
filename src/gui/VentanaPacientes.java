package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import domain.*;

public class VentanaPacientes extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(6, 99, 133);
    private static final Color SECONDARY_COLOR = new Color(7, 120, 163);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final int SPACING = 20;

    private  List<Paciente> pacientes;
    private  JTable tablaPacientes;
    private  JScrollPane scrollPanePacientes;
    private  JTextField txtFiltro;
    private  DefaultTableModel modeloDatosPacientes;
    private  JButton btnAnadirP;
    private  JButton btnBorrarP;
    private  Persona usuario;

    public VentanaPacientes(List<Paciente> pacientes, Persona usuarioP) {
        this.pacientes = pacientes;
        this.usuario = usuarioP;

        setTitle("Lista de Pacientes");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/db/hospital.png").getImage());

        JPanel mainPanel = createMainPanel();
        add(mainPanel);

        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(SPACING, SPACING));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(SPACING, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);

        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, SPACING, 0));
        leftSection.setBackground(BACKGROUND_COLOR);
        leftSection.add(createBackButton());

        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, SPACING, 0));
        rightSection.setBackground(BACKGROUND_COLOR);
        rightSection.add(createFilterField());
        rightSection.add(createAddButton());
        rightSection.add(createDeleteButton());

        headerPanel.add(leftSection, BorderLayout.WEST);
        headerPanel.add(rightSection, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);

        String[] columnNames = {"Nombre", "Apellido", "Edad", "Código Paciente", "Historial"};
        modeloDatosPacientes = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        tablaPacientes = new JTable(modeloDatosPacientes);
        tablaPacientes.setGridColor(Color.BLACK);
        tablaPacientes.getColumn("Historial").setCellRenderer(new ButtonRenderer());
        tablaPacientes.getColumn("Historial").setCellEditor(new ButtonEditor(new JButton("Historial")));
        initTableStyle();
        addPatientData();

        scrollPanePacientes = new JScrollPane(tablaPacientes);
        tablePanel.add(scrollPanePacientes, BorderLayout.CENTER);

        return tablePanel;
    }

    private void initTableStyle() {
        tablaPacientes.setDefaultRenderer(Object.class, (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value != null ? value.toString() : "");
            result.setHorizontalAlignment(JLabel.CENTER);
            return result;
        });
        tablaPacientes.setRowHeight(40);
        tablaPacientes.getTableHeader().setReorderingAllowed(false);
        tablaPacientes.getTableHeader().setResizingAllowed(false);
        tablaPacientes.setAutoCreateRowSorter(true);
        tablaPacientes.getColumnModel().getColumn(2).setPreferredWidth(400);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(PRIMARY_COLOR);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(tablaPacientes.getFont().deriveFont(Font.BOLD));

        for (int i = 0; i < tablaPacientes.getColumnModel().getColumnCount(); i++) {
            tablaPacientes.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    private void addPatientData() {
        for (Paciente paciente : pacientes) {
            Object[] row = {paciente.getNombre(), paciente.getApellido(), paciente.getEdad(), paciente.getCodigoPaciente(), "Ver Historial"};
            modeloDatosPacientes.addRow(row);
        }
    }

    private JTextField createFilterField() {
        txtFiltro = new JTextField(20);
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent arg0) {
                filterPatients();
            }

            public void insertUpdate(DocumentEvent arg0) {
                filterPatients();
            }

            public void removeUpdate(DocumentEvent arg0) {
                filterPatients();
            }
        });
        return txtFiltro;
    }

    private JButton createBackButton() {
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/db/icons8-back-25.png"));
        JButton btnBack = new JButton(backIcon);
        styleButton(btnBack);
        btnBack.setPreferredSize(new Dimension(80, 35));
        btnBack.addActionListener(e -> {
            MenuTrabajador window = new MenuTrabajador(usuario);
            window.setVisible(true);
            dispose();
        });
        return btnBack;
    }

    private JButton createAddButton() {
        btnAnadirP = createStyledButton("Añadir Paciente");
        btnAnadirP.addActionListener(e -> showAddPatientDialog());
        return btnAnadirP;
    }

    private JButton createDeleteButton() {
        btnBorrarP = createStyledButton("Borrar Paciente");
        // Add delete functionality here
        return btnBorrarP;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        styleButton(button);
        return button;
    }

    private void styleButton(JButton button) {
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
    }

    private void filterPatients() {
        modeloDatosPacientes.setRowCount(0);

        String filter = txtFiltro.getText().toLowerCase();
        for (Paciente paciente : pacientes) {
            if (paciente.getNombre().toLowerCase().contains(filter) ||
                paciente.getApellido().toLowerCase().contains(filter) ||
                String.valueOf(paciente.getEdad()).contains(filter) ||
                String.valueOf(paciente.getCodigoPaciente()).contains(filter)) {
                Object[] row = {paciente.getNombre(), paciente.getApellido(), paciente.getEdad(), paciente.getCodigoPaciente(), "Ver Historial"};
                modeloDatosPacientes.addRow(row);
            }
        }
    }

    private void showAddPatientDialog() {
        JDialog newPatientDialog = new JDialog(this, "Añadir Nuevo Paciente", true);
        newPatientDialog.setSize(400, 300);
        newPatientDialog.setLayout(new GridLayout(8, 2, SPACING, SPACING));
        newPatientDialog.getContentPane().setBackground(PRIMARY_COLOR);

        JLabel lblContrasena = createLabel("Contraseña:");
        JTextField txtContrasena = new JTextField();
        JLabel lblNombre = createLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblApellido = createLabel("Apellido:");
        JTextField txtApellido = new JTextField();
        JLabel lblEdad = createLabel("Edad:");
        JTextField txtEdad = new JTextField();
        JLabel lblUbicacion = createLabel("Ubicación:");
        JTextField txtUbicacion = new JTextField();
        JLabel lblCodigoPaciente = createLabel("Código Paciente:");
        JTextField txtCodigoPaciente = new JTextField();

        JButton btnAdd = createStyledButton("Añadir Paciente");
        btnAdd.addActionListener(e -> {
            if (validatePatientData(txtNombre, txtApellido, txtEdad, txtUbicacion, txtCodigoPaciente)) {
                String password = txtContrasena.getText();
                String name = txtNombre.getText();
                String surname = txtApellido.getText();
                String location = txtUbicacion.getText();
                int patientCode = Integer.parseInt(txtCodigoPaciente.getText());
                List<Historial> history = null;
                int age = Integer.parseInt(txtEdad.getText());

                Paciente newPatient = new Paciente(password, name, surname, age, location, patientCode, history);
                pacientes.add(newPatient);
                Context.getInstance().guardarPaciente(newPatient);
                modeloDatosPacientes.addRow(new Object[]{name, surname, age, patientCode, "Ver Historial"});
                newPatientDialog.dispose();
            }
        });

        newPatientDialog.add(lblContrasena);
        newPatientDialog.add(txtContrasena);
        newPatientDialog.add(lblNombre);
        newPatientDialog.add(txtNombre);
        newPatientDialog.add(lblApellido);
        newPatientDialog.add(txtApellido);
        newPatientDialog.add(lblEdad);
        newPatientDialog.add(txtEdad);
        newPatientDialog.add(lblUbicacion);
        newPatientDialog.add(txtUbicacion);
        newPatientDialog.add(lblCodigoPaciente);
        newPatientDialog.add(txtCodigoPaciente);
        newPatientDialog.add(new JLabel()); // Empty label to fill last grid cell
        newPatientDialog.add(btnAdd);

        newPatientDialog.setLocationRelativeTo(this);
        newPatientDialog.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private boolean validatePatientData(JTextField txtNombre, JTextField txtApellido, JTextField txtEdad, JTextField txtUbicacion, JTextField txtCodigoPaciente) {
        String name = txtNombre.getText();
        String surname = txtApellido.getText();
        String location = txtUbicacion.getText();

        try {
            int age = Integer.parseInt(txtEdad.getText());
            int patientCode = Integer.parseInt(txtCodigoPaciente.getText());

            if (name.isEmpty() || surname.isEmpty() || location.isEmpty() || age < 0) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return false;
            }

            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age and Patient Code must be valid numbers.");
            return false;
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setBackground(PRIMARY_COLOR);
            setForeground(Color.WHITE);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value != null) ? value.toString() : "Ver Historial");
            return this;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JButton button) {
            this.button = button;
            this.button.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value != null) ? value.toString() : "Ver Historial";
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = tablaPacientes.getSelectedRow();
                int patientCode = (int) modeloDatosPacientes.getValueAt(row, 3);
                for (Paciente p : pacientes) {
                    if (p.getCodigoPaciente() == patientCode) {
                        String password = p.getContrasena();
                        String name = p.getNombre();
                        String surname = p.getApellido();
                        int age = p.getEdad();
                        String location = p.getUbicacion();
                        patientCode = p.getCodigoPaciente();
                        List<Historial> history = p.getHistorialPaciente();
                        Paciente patient = new Paciente(password, name, surname, age, location, patientCode, history);
                        if (usuario instanceof Doctor) {
                            VentanaHistorial historyWindow = new VentanaHistorial(patient, usuario);
                            historyWindow.setVisible(true);
                        } else if (usuario instanceof Administrador) {
                            JOptionPane.showMessageDialog(this.button, "Solo un medico puede acceder al historial de un paciente", "Acceso rechazado", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
        }
    }
}
//FUENTE-EXTERNA
//URL: (https://alud.deusto.es/mod/resource/view.php?id=800467)
//SIN-CAMBIOS ó ADAPTADO (he usado el cell editor para meter la imagen de la practica swing para meter los botones del historial )
//IAG (herramienta:chatgpt )
//SIN CAMBIOS ó ADAPTADO (me he ayudado con chatgpt para solucionar errores y dudas)
//