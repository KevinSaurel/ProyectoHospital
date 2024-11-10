package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import domain.Cama;
import domain.Historial;
import domain.Paciente;


public class VentanaCamas extends JFrame {
    private List<Cama> camas;
    private List<Paciente> pacientes;
    private JPanel camasPanel;
    private JPanel pacientePanel;
    private int selectedCamaId = -1;
    private int selectedPacienteId = -1;
    private JTable pacienteTable;
    private DefaultTableModel tableModel;
    private final Color BUTTON_BLUE = new Color(6, 99, 133);
    private static final Color PRIMARY_COLOR = new Color(6, 99, 133);
    private static final Color SECONDARY_COLOR = new Color(7, 120, 163);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    
    public VentanaCamas() {
        setTitle("Gestión de Camas del Hospiatal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/db/hospital.png").getImage());
        
        initializeData();
        createCamasPanel();
        createPacientePanel();

        // Layout principal usando JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(new JScrollPane(camasPanel));
        splitPane.setBottomComponent(pacientePanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.3);

        // Barra de herramientas modificada (solo con el botón de actualizar y los atajos)
        JToolBar toolBar = createToolBar();
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Agregar un KeyListener global para todos los componentes
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown()) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_K:  // Ctrl + K para nueva cama
                                showNuevaCamaDialog();
                                return true;
                            case KeyEvent.VK_J:  // Ctrl + J para nuevo paciente
                                showNuevoPacienteDialog();
                                return true;
                        }
                    }
                    return false;
                }
            });
    }

    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton refreshButton = createStyledButton("Actualizar");
        refreshButton.addActionListener(e -> updateUI());
        
        JButton btnBack = createBackButton();
        
        // Crear panel para los atajos de teclado
        JPanel shortcutsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        shortcutsPanel.setOpaque(false);
        
        // Agregar labels para mostrar los atajos de teclado
        JLabel shortcutCama = new JLabel("Añadir Cama (Ctrl + K)");
        JLabel shortcutPaciente = new JLabel("Nuevo Paciente (Ctrl + J)");
        shortcutCama.setForeground(Color.BLACK);
        shortcutPaciente.setForeground(Color.BLACK);
        shortcutCama.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        shortcutPaciente.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        toolBar.add(btnBack);
        toolBar.addSeparator(new Dimension(20, 0)); // Añade 20 pixeles de espacio
        toolBar.add(refreshButton);
        toolBar.addSeparator(new Dimension(20, 0)); // Añade 20 pixeles de espacio
        toolBar.add(shortcutCama);
        toolBar.addSeparator(new Dimension(20, 0)); // Añade 20 pixeles de espacio
        toolBar.add(shortcutPaciente);

        return toolBar;
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
    
    private JButton createBackButton() {
        ImageIcon backIcon = new ImageIcon(getClass().getResource("src/db/icons8-back-25.png"));
        JButton btnBack = new JButton(backIcon);
        styleButton(btnBack);
        btnBack.setPreferredSize(new Dimension(80, 35));
//        btnBack.addActionListener(e -> {
//            MenuTrabajador window = new MenuTrabajador();
//            window.setVisible(true);
//            dispose();
//        });
        return btnBack;
    }

    private void showNuevaCamaDialog() {
        JDialog dialog = new JDialog(this, "Añadir Nueva Cama", true);
        dialog.setLayout(new BorderLayout());
        
        // Panel para los campos usando GridLayout
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField numCamaField = new JTextField();
        String[] tiposCama = {"Normal", "UCI"};
        javax.swing.JComboBox<String> tipoCamaCombo = new javax.swing.JComboBox<>(tiposCama);
        
        fieldsPanel.add(new JLabel("Número de Cama:"));
        fieldsPanel.add(numCamaField);
        fieldsPanel.add(new JLabel("Tipo de Cama:"));
        fieldsPanel.add(tipoCamaCombo);
        
        // Panel para las imágenes
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Crear labels para las imágenes
        JLabel normalLabel = new JLabel();
        JLabel uciLabel = new JLabel();
        
        // Cargar y redimensionar las imágenes
        //IAG modificado
        try {
            ImageIcon normalIcon = new ImageIcon(getClass().getResource("src/db/health-check_5809279.png"));
            ImageIcon uciIcon = new ImageIcon(getClass().getResource("src/db/emergency_9587034.png"));
            
            // Redimensionar las imágenes
            Image normalImg = normalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            Image uciImg = uciIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            
            normalLabel.setIcon(new ImageIcon(normalImg));
            uciLabel.setIcon(new ImageIcon(uciImg));
            
            // Añadir textos debajo de las imágenes
            normalLabel.setText("Normal");
            uciLabel.setText("UCI");
            normalLabel.setHorizontalTextPosition(JLabel.CENTER);
            uciLabel.setHorizontalTextPosition(JLabel.CENTER);
            normalLabel.setVerticalTextPosition(JLabel.BOTTOM);
            uciLabel.setVerticalTextPosition(JLabel.BOTTOM);
            
            // Añadir bordes para resaltar la selección
            normalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            uciLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
        } catch (Exception e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
        }
        
        imagePanel.add(normalLabel);
        imagePanel.add(uciLabel);
        
        // Listener para el comboBox
        tipoCamaCombo.addActionListener(e -> {
            String selectedType = (String) tipoCamaCombo.getSelectedItem();
            // Resaltar la imagen seleccionada
            if ("Normal".equals(selectedType)) {
                normalLabel.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
                uciLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            } else {
                uciLabel.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
                normalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });
        
        // Click listeners para las imágenes
        MouseAdapter imageClickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == normalLabel) {
                    tipoCamaCombo.setSelectedItem("Normal");
                } else if (e.getSource() == uciLabel) {
                    tipoCamaCombo.setSelectedItem("UCI");
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                ((JLabel)e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        };
        
        normalLabel.addMouseListener(imageClickListener);
        uciLabel.addMouseListener(imageClickListener);
        
        // Panel para el botón
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton guardarButton = createStyledButton("Guardar");
        guardarButton.addActionListener(e -> {
            try {
                int numCama = Integer.parseInt(numCamaField.getText());
                
                boolean camaExiste = camas.stream()
                    .anyMatch(c -> c.getNumCama() == numCama);
                
                if (camaExiste) {
                    JOptionPane.showMessageDialog(dialog,
                        "El número de cama ya existe",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String tipoCama = (String) tipoCamaCombo.getSelectedItem();
                Cama nuevaCama = new Cama(numCama, false, tipoCama, null);
                camas.add(nuevaCama);
                updateUI();
                dialog.dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Por favor ingrese un número de cama válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(guardarButton);
        
        // Panel principal para organizar campos e imágenes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(fieldsPanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        
        // Añadir los paneles al diálogo
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Seleccionar "Normal" por defecto
        tipoCamaCombo.setSelectedItem("Normal");
        
        // Configurar el diálogo
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        numCamaField.requestFocusInWindow();
        
        dialog.setVisible(true);
    }

    private void initializeData() {
        camas = new ArrayList<>();
        pacientes = new ArrayList<>();

        // Crear pacientes de ejemplo con historial
        List<Historial> historialExample1 = new ArrayList<>();
        List<Historial> historialExample2 = new ArrayList<>();
        
        // Agregar algunos pacientes de ejemplo
        pacientes.add(new Paciente("pass123", "Juan", "Pérez", 45, "Lima", 0001, historialExample1));
        pacientes.add(new Paciente("pass456", "María", "García", 32, "Arequipa", 0002, historialExample2));
        pacientes.add(new Paciente("pass123", "John", "Iribar", 28, "Basauri", 0003, historialExample1));
        pacientes.add(new Paciente("pass456", "James", "Lopez", 54, "Madrid", 0004, historialExample2));
        pacientes.add(new Paciente("pass123", "Fran", "Espin", 40, "Lima", 0005, historialExample1));
        pacientes.add(new Paciente("pass456", "Carla", "García", 22, "Arequipa", 0006, historialExample2));
        pacientes.add(new Paciente("pass123", "Josu", "Sainz", 38, "Basauri", 0007, historialExample1));
        pacientes.add(new Paciente("pass456", "Hugo", "Serrano", 62, "Madrid", 0010, historialExample2));
        
        
        // Crear camas con diferentes tipos
        for (int i = 1; i <= 8; i++) {
            String tipoCama = (i % 2 == 0) ? "UCI" : "Normal";
            camas.add(new Cama(i, false, tipoCama, null));
        }
    }

    private void createCamasPanel() {
        camasPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        camasPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Cama cama : camas) {
            JPanel camaPanel = createCamaPanel(cama);
            camasPanel.add(camaPanel);
        }
    }

    private JPanel createCamaPanel(Cama cama) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Establecer el borde según el tipo de cama
        if (cama.getTipoCama().equals("UCI")) {
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 0, 0), 3), // Borde rojo oscuro más grueso para UCI
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        } else {
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
        }
        
        // Crear el JLabel para la imagen
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Cargar la imagen según el estado de la cama
        try {
            ImageIcon icon;
            if (cama.isOcupada()) {
                icon = new ImageIcon(getClass().getResource("src/db/CamaRoja.png"));
            } else {
                icon = new ImageIcon(getClass().getResource("src/db/CamaVerde.png"));
            }
            
            // Redimensionar la imagen
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
        
        // Fondo blanco para el panel
        panel.setBackground(Color.WHITE);
        
        // Solo mostrar el número de cama si no está ocupada
        JLabel camaLabel = new JLabel("Cama " + cama.getNumCama());
        configurarLabel(camaLabel);
        
        // Añadir componentes al panel
        panel.add(imageLabel);
        panel.add(camaLabel);
        
        // Si hay paciente, mostrar su información
        if (cama.isOcupada() && cama.getPaciente() != null) {
            JLabel pacienteLabel = new JLabel(cama.getPaciente().getNombre() + " " + 
                                            cama.getPaciente().getApellido());
            configurarLabel(pacienteLabel);
            panel.add(pacienteLabel);
        }

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedCamaId = cama.getNumCama();
                showPacienteTable();
                resaltarSeleccion(panel);
            }
        });

        return panel;
    }

    private void configurarLabel(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
    }

    private void createPacientePanel() {
        pacientePanel = new JPanel(new BorderLayout());
        pacientePanel.setBorder(BorderFactory.createTitledBorder("Lista de Pacientes"));

        // Panel superior para las camas con altura fija
        camasPanel.setPreferredSize(new Dimension(getWidth(), 250));

        String[] columnNames = {"Código", "Nombre", "Apellido", "Edad", "Ubicación"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        pacienteTable = new JTable(tableModel);
        pacienteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Establecer altura preferida para la tabla
        JScrollPane scrollPane = new JScrollPane(pacienteTable);
        scrollPane.setPreferredSize(new Dimension(getWidth(), 300));
        
        pacienteTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = pacienteTable.getSelectedRow();
                if (row != -1) {
                    selectedPacienteId = row;
                }
            }
        });

        // Panel de botones con FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setPreferredSize(new Dimension(getWidth(), 50));
        
        JButton asignarButton = createStyledButton("Asignar a Cama");
        JButton liberarButton = createStyledButton("Liberar Cama");
        JButton historialButton = createStyledButton("Ver Historial");

        asignarButton.addActionListener(e -> asignarPaciente());
        liberarButton.addActionListener(e -> liberarCama());
        historialButton.addActionListener(e -> showHistorialDialog());

        buttonPanel.add(asignarButton);
        buttonPanel.add(liberarButton);
        buttonPanel.add(historialButton);

        // Usar un panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        pacientePanel.add(mainPanel, BorderLayout.CENTER);
    }

    private void showNuevoPacienteDialog() {
        JDialog dialog = new JDialog(this, "Nuevo Paciente", true);
        dialog.setLayout(new GridLayout(0, 2, 5, 5));
        
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField edadField = new JTextField();
        JTextField ubicacionField = new JTextField();
        JPasswordField contrasenaField = new JPasswordField();
        
        // Panel para los campos
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        fieldsPanel.add(new JLabel("Nombre:"));
        fieldsPanel.add(nombreField);
        fieldsPanel.add(new JLabel("Apellido:"));
        fieldsPanel.add(apellidoField);
        fieldsPanel.add(new JLabel("Edad:"));
        fieldsPanel.add(edadField);
        fieldsPanel.add(new JLabel("Ubicación:"));
        fieldsPanel.add(ubicacionField);
        fieldsPanel.add(new JLabel("Contraseña:"));
        fieldsPanel.add(contrasenaField);
        
        // Panel para el botón
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(e -> {
            try {
                int edad = Integer.parseInt(edadField.getText());
                int codigoPaciente = pacientes.size() + 0001;
                
                if (nombreField.getText().trim().isEmpty() || 
                    apellidoField.getText().trim().isEmpty() || 
                    ubicacionField.getText().trim().isEmpty() || 
                    contrasenaField.getPassword().length == 0) {
                    
                    JOptionPane.showMessageDialog(dialog,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Paciente nuevoPaciente = new Paciente(
                    new String(contrasenaField.getPassword()),
                    nombreField.getText().trim(),
                    apellidoField.getText().trim(),
                    edad,
                    ubicacionField.getText().trim(),
                    codigoPaciente,
                    new ArrayList<>()
                );
                
                pacientes.add(nuevoPaciente);
                updateUI();
                dialog.dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog,
                    "Por favor ingrese una edad válida",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(guardarButton);
        
        // Añadir los paneles al diálogo
        dialog.setLayout(new BorderLayout());
        dialog.add(fieldsPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Configurar el tamaño y posición del diálogo
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        
        // Hacer que el campo de nombre tenga el foco inicial
        nombreField.requestFocusInWindow();
        
        dialog.setVisible(true);
    }

    private void showHistorialDialog() {
        if (selectedPacienteId != -1) {
            Paciente paciente = pacientes.get(selectedPacienteId);
            JDialog dialog = new JDialog(this, "Historial Médico - " + 
                paciente.getNombre() + " " + paciente.getApellido(), true);
            
            DefaultTableModel historialModel = new DefaultTableModel(
                new String[]{"Fecha", "Descripción"}, 0);
            
            JTable historialTable = new JTable(historialModel);
            
            // Agregar entradas del historial a la tabla
            for (Historial entrada : paciente.getHistorialPaciente()) {
                historialModel.addRow(new Object[]{
                    entrada.toString(), // Ajusta esto según tu clase Historial
                    entrada.toString()
                });
            }
            
            dialog.add(new JScrollPane(historialTable));
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    private void showPacienteTable() {
        tableModel.setRowCount(0);
        for (Paciente paciente : pacientes) {
            Object[] row = {
                paciente.getCodigoPaciente(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getEdad(),
                paciente.getUbicacion()
            };
            tableModel.addRow(row);
        }
    }

    private void asignarPaciente() {
        if (selectedCamaId != -1 && selectedPacienteId != -1) {
            Cama camaSeleccionada = camas.stream()
                .filter(c -> c.getNumCama() == selectedCamaId)
                .findFirst()
                .orElse(null);

            if (camaSeleccionada != null && !camaSeleccionada.isOcupada()) {
                Paciente pacienteSeleccionado = pacientes.get(selectedPacienteId);
                
                // Verificar si el paciente ya está asignado a alguna cama
                boolean pacienteYaAsignado = camas.stream()
                    .anyMatch(c -> c.isOcupada() && 
                        c.getPaciente() != null && 
                        c.getPaciente().getCodigoPaciente() == pacienteSeleccionado.getCodigoPaciente());

                if (pacienteYaAsignado) {
                    JOptionPane.showMessageDialog(this,
                        "El paciente ya está asignado a otra cama",
                        "Error de Asignación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                camaSeleccionada.setPaciente(pacienteSeleccionado);
                camaSeleccionada.setOcupada(true);
                updateUI();
                JOptionPane.showMessageDialog(this,
                    "Paciente asignado exitosamente a la cama " + selectedCamaId);
            } else {
                JOptionPane.showMessageDialog(this,
                    "La cama seleccionada no está disponible",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una cama y un paciente",
                "Error de Selección",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void liberarCama() {
        if (selectedCamaId != -1) {
            Cama cama = camas.stream()
                .filter(c -> c.getNumCama() == selectedCamaId)
                .findFirst()
                .orElse(null);

            if (cama != null && cama.isOcupada()) {
                cama.setPaciente(null);
                cama.setOcupada(false);
                updateUI();
                JOptionPane.showMessageDialog(this, 
                    "Cama " + selectedCamaId + " liberada exitosamente");
            }
        }
    }

    private void resaltarSeleccion(JPanel panel) {
        // Resetear bordes de todos los paneles
        for (Component comp : camasPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel camaPanel = (JPanel) comp;
                // Buscar la cama correspondiente
                int numCama = Integer.parseInt(((JLabel)camaPanel.getComponent(1)).getText().split(" ")[1]);
                Cama cama = camas.stream()
                    .filter(c -> c.getNumCama() == numCama)
                    .findFirst()
                    .orElse(null);
                    
                if (cama != null && cama.getTipoCama().equals("UCI")) {
                    camaPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(139, 0, 0), 3),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                } else {
                    camaPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                }
            }
        }
        // Resaltar el panel seleccionado manteniendo el grosor del borde si es UCI
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLUE, 3),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void updateUI() {
        camasPanel.removeAll();
        for (Cama cama : camas) {
            camasPanel.add(createCamaPanel(cama));
        }
        
        showPacienteTable();
        camasPanel.revalidate();
        camasPanel.repaint();
    }

}