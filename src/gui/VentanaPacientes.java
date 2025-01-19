package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import domain.Administrador;
import domain.Context;
import domain.Doctor;
import domain.Historial;
import domain.Paciente;
import domain.Persona;
import persistente.GestorBD;

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
    private  JButton btnGruposp;
    private  JButton btnBorrarP;
    private  Persona usuario;
    private GestorBD gestorBD;
    public VentanaPacientes(List<Paciente> pacientes, Persona usuarioP , GestorBD gestorBD) {
    	this.gestorBD = gestorBD;
        this.pacientes = pacientes;
        this.usuario = usuarioP;

        setTitle("Lista de Pacientes");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        ImageIcon i = new ImageIcon("resources/images/hospital.png");
        setIconImage(i.getImage());
        addKeyboardShortcut();

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
        rightSection.add(createGrupoButton());
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
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> {
            boolean isRowSelected = tablaPacientes.getSelectedRow() != -1;
            btnBorrarP.setEnabled(isRowSelected); // Enable button if a row is selected
        });

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
        tablaPacientes.getColumnModel().getColumn(2).setPreferredWidth(50);
        tablaPacientes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaPacientes.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaPacientes.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();

        leftAlignRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        leftAlignRenderer.setFont(new Font("Arial", Font.BOLD, 12));
      
        // Set the left alignment renderer for "Nombre" and "Apellido" columns
        tablaPacientes.getColumnModel().getColumn(0).setCellRenderer(leftAlignRenderer); 
        tablaPacientes.getColumnModel().getColumn(1).setCellRenderer(leftAlignRenderer); 
        
        
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(PRIMARY_COLOR);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(tablaPacientes.getFont().deriveFont(Font.BOLD));
        //headerRenderer.setFont(new Font("Arial", Font.BOLD, 12));
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
    private void addKeyboardShortcut() {
        // Define an Action that opens the "Añadir Paciente" dialog
        Action addPatientAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPatientDialog(); // Call method to open dialog
            }
        };

        // Set up the key binding on the root pane of the frame
        String key = "addPatient";
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control P"), key);
        getRootPane().getActionMap().put(key, addPatientAction);
    }

    private JButton createBackButton() {
        ImageIcon backIcon = new ImageIcon("resources/images/icons8-back-25.png");
        JButton btnBack = new JButton(backIcon);
        styleButton(btnBack);
        btnBack.setPreferredSize(new Dimension(80, 35));
        btnBack.addActionListener(e -> {
            MenuTrabajador window = new MenuTrabajador(usuario, gestorBD);
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
    private JButton createGrupoButton() {
    	btnGruposp = createStyledButton("Crear Grupo");
        btnGruposp.addActionListener(e -> crearGrupo());
        return btnGruposp;
    }
    private JButton createDeleteButton() {
        btnBorrarP = createStyledButton("Borrar Paciente");
        // Add delete functionality here
        btnBorrarP.addActionListener(e -> borrarP());
        return btnBorrarP;
    }
    
    
 // Método principal para generar grupos de consulta
    public void generarGruposConsulta(List<Paciente> pacientes, int tiempoDisponible, TipoGrupo tipo, List<String> ubicaciones) {
        List<List<Paciente>> resultado = new ArrayList<>();
        
        // Filtramos los pacientes según criterios
        List<Paciente> pacientesFiltrados = new ArrayList<>();
        for (Paciente p : pacientes) {
            if (tipo.incluyeEdad(p.getEdad()) && ubicaciones.contains(p.getUbicacion())) {
                pacientesFiltrados.add(p);
            }
        }
        
        // Debug: Imprimir información sobre pacientes filtrados
        System.out.println("Pacientes que cumplen los criterios: " + pacientesFiltrados.size());
        for (Paciente p : pacientesFiltrados) {
            System.out.println("- " + p.getNombre() + " " + p.getApellido() + 
                             " (Edad: " + p.getEdad() + ", Ubicación: " + p.getUbicacion() + ")");
        }
        
        if (pacientesFiltrados.isEmpty()) {
            System.out.println("No se encontraron pacientes que cumplan los criterios de edad (" + 
                             tipo.edadMin + "-" + tipo.edadMax + " años) y ubicación " + ubicaciones);
            return;
        }
        
        // Generamos las combinaciones
        int tiempoTotal = tiempoDisponible;
        List<Paciente> grupoActual = new ArrayList<>();
        generarCombinacionesPacientes(resultado, pacientesFiltrados, tiempoTotal, grupoActual);
        
        // Debug: Imprimir información sobre grupos generados
        System.out.println("\nGrupos generados: " + resultado.size());
        for (int i = 0; i < resultado.size(); i++) {
            List<Paciente> grupo = resultado.get(i);
            System.out.println("\nGrupo " + (i + 1) + ":");
            for (Paciente p : grupo) {
                System.out.println("- " + p.getNombre() + " " + p.getApellido());
            }
        }
        
        // Mostramos los resultados en la tabla
        if (!resultado.isEmpty()) {
            mostrarGruposEnTabla(resultado);
        }
    }

    // Método recursivo mejorado para generar combinaciones
    private void generarCombinacionesPacientes(List<List<Paciente>> resultado, 
                                             List<Paciente> pacientes, 
                                             int tiempoDisponible, 
                                             List<Paciente> grupoActual) {
        // El tiempo mínimo por paciente es 15 minutos
        final int TIEMPO_POR_PACIENTE = 15;
        
        // Si el grupo actual es válido (tiene al menos un paciente), lo añadimos a los resultados
        if (!grupoActual.isEmpty() && tiempoDisponible >= 0) {
            resultado.add(new ArrayList<>(grupoActual));
        }
        
        // Caso base: si no hay tiempo suficiente para otro paciente
        if (tiempoDisponible < TIEMPO_POR_PACIENTE) {
            return;
        }
        
        // Caso recursivo: probamos añadir cada paciente posible
        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);
            
            // Si el paciente no está ya en el grupo y hay tiempo suficiente
            if (!grupoActual.contains(p)) {
                // Añadimos el paciente al grupo
                grupoActual.add(p);
                
                // Llamada recursiva con el tiempo restante y los pacientes restantes
                // (para evitar duplicados, solo consideramos pacientes después del actual)
                List<Paciente> pacientesRestantes = new ArrayList<>(pacientes.subList(i + 1, pacientes.size()));
                generarCombinacionesPacientes(resultado, pacientesRestantes, 
                                            tiempoDisponible - TIEMPO_POR_PACIENTE, 
                                            grupoActual);
                
                // Retrocedemos: quitamos el último paciente para probar otras combinaciones
                grupoActual.remove(grupoActual.size() - 1);
            }
        }
    }

    public enum TipoGrupo {
        PEDIATRIA(0, 14),
        ADULTOS(15, 64),
        GERIATRIA(65, 150);

        public final int edadMin;
        public final int edadMax;

        TipoGrupo(int edadMin, int edadMax) {
            this.edadMin = edadMin;
            this.edadMax = edadMax;
        }

        public boolean incluyeEdad(int edad) {
            return edad >= edadMin && edad <= edadMax;
        }
    }

    // Método para mostrar los grupos en la tabla
    private void mostrarGruposEnTabla(List<List<Paciente>> grupos) {
        modeloDatosPacientes.setRowCount(0);
        
        for (List<Paciente> grupo : grupos) {
            for (Paciente p : grupo) {
                Object[] row = {
                    p.getNombre(),
                    p.getApellido(),
                    p.getEdad(),
                    p.getCodigoPaciente(),
                    "Ver Historial"
                };
                modeloDatosPacientes.addRow(row);
            }
            // Añadimos una fila vacía entre grupos
            modeloDatosPacientes.addRow(new Object[]{"---", "---", "---", "---", "---"});
        }
    }

    
    private void crearGrupo(){
    	//Añadir el codigo recursivo
    	btnGruposp.addActionListener((e) -> {
    		List<String> ubicaciones = Arrays.asList("Madrid", "Barcelona", "Valencia");
            
    	    
    	    // Generar grupos de pediatría (60 minutos)
    	    System.out.format("%nGenerando grupos de %s con duración de %d minutos...%n", "PEDIATRÍA", 60);
    	    generarGruposConsulta(pacientes, 60, TipoGrupo.PEDIATRIA, ubicaciones);

    	    // Generar grupos de adultos (90 minutos)
    	    System.out.format("%nGenerando grupos de %s con duración de %d minutos...%n", "ADULTOS", 90);
    	    generarGruposConsulta(pacientes, 90, TipoGrupo.ADULTOS, ubicaciones);

    	    // Generar grupos de geriatría (45 minutos)
    	    System.out.format("%nGenerando grupos de %s con duración de %d minutos...%n", "GERIATRÍA", 45);
    	    generarGruposConsulta(pacientes, 45, TipoGrupo.GERIATRIA, ubicaciones);
            
    	});
    	
    	
    	
    	
    }
    private void borrarP() {
    	 int selectedRow = tablaPacientes.getSelectedRow();
    	 
    	 if (selectedRow != -1) {
    	 int confirm = JOptionPane.showConfirmDialog(this, 
    	            "¿Estás seguro de que quieres borrar este paciente?", 
    	            "Confirmar eliminación", 
    	            JOptionPane.YES_NO_OPTION);

    	        if (confirm == JOptionPane.YES_OPTION) {
    	            // Get the patient's code from the selected row
    	            int patientCode = (int) modeloDatosPacientes.getValueAt(selectedRow, 3);

    	            // Remove from the pacientes list
    	            pacientes.removeIf(p -> p.getCodigoPaciente() == patientCode);

    	            // Remove from the table model
    	            modeloDatosPacientes.removeRow(selectedRow);

    	            JOptionPane.showMessageDialog(this, "Paciente eliminado con éxito.", 
    	                "Eliminación completa", JOptionPane.INFORMATION_MESSAGE);
    	        }
    	    } else {
    	        JOptionPane.showMessageDialog(this, "Selecciona un paciente para borrar.", 
    	            "Error", JOptionPane.ERROR_MESSAGE);
    	    }
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
            if (filter.isEmpty()) {
                // If filter is empty, show all patients
                addPatientData();
                return;
            }

            for (Paciente paciente : pacientes) {
                String nombre = paciente.getNombre();
                String apellido = paciente.getApellido();
                String edad = String.valueOf(paciente.getEdad());
                String codigoPaciente = String.valueOf(paciente.getCodigoPaciente());

                boolean matches = nombre.toLowerCase().contains(filter) || 
                                  apellido.toLowerCase().contains(filter) || 
                                  edad.contains(filter) || 
                                  codigoPaciente.contains(filter);

                if (matches) {
                    String highlightedNombre = highlightText(nombre, filter);
                    String highlightedApellido = highlightText(apellido, filter);
                    String highlightedEdad = highlightText(edad, filter);
                    String highlightedCodigoPaciente = highlightText(codigoPaciente, filter);

                    // Add the row with the highlighted text to the table model
                    Object[] row = {
                        highlightedNombre, 
                        highlightedApellido, 
                        highlightedEdad, 
                        highlightedCodigoPaciente, 
                        "Ver Historial"
                    };
                    modeloDatosPacientes.addRow(row);
                }
            }
        }

        private String highlightText(String text, String filter) {
            if (text.toLowerCase().contains(filter)) {
                int startIndex = text.toLowerCase().indexOf(filter);
                int endIndex = startIndex + filter.length();
                
                String beforeMatch = text.substring(0, startIndex);
                String match = text.substring(startIndex, endIndex);
                String afterMatch = text.substring(endIndex);

                // Create the HTML formatted string to highlight the filter match
                return "<html>" + 
                       beforeMatch + 
                       "<strong style='background-color:yellow; color:blue;'>" + match + "</strong>" + 
                       afterMatch + 
                       "</html>";
            }
            return text; // If no match, return the text as is
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
                            VentanaHistorial historyWindow = new VentanaHistorial(patient, usuario, gestorBD);
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