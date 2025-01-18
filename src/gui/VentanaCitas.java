package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import domain.Cita;
import domain.Context;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;
import persistente.GestorBD;

public class VentanaCitas extends JFrame {

	// datos para que las ventanas casen con coherencia
	
		// Colores
    private static final Color PRIMARY_COLOR = new Color(6, 99, 133);
    private static final Color SECONDARY_COLOR = new Color(7, 120, 163);
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    	// Fuentes
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    	// Entidades necesarias
    private List<Cita> citas; // Lista de citas
    private JTable tablaCitas; // Tabla principal
    private JScrollPane scrollPaneCitas; // ScrollPane donde irá la tabla
    private JTextField txtFiltro; // Caja de texto donde se mostrará el filtro
    private DefaultTableModel modeloDatosCitas; // Modelo de datos para la tabla principal
    
    	// Botones necesarios en la ventana
    private JButton btnAddCita;
    private JButton btnModCita;
    private JButton btnBorrCita;
    private JButton btnBack;
    	// Unión a otras clases necesarias
    private Persona usuario;
    private GestorBD gestorBD;

    public VentanaCitas(List<Cita> citas, Persona usuario,  GestorBD gestorBD) {
        // Inicializar lista de citas
        this.citas = (citas != null) ? citas : new ArrayList<>();
        this.usuario = usuario;
        this.gestorBD = gestorBD;

        // Configurar ventana
        setTitle("Gestión de Citas");
        ImageIcon i = new ImageIcon("resources/images/hospital.png");
        setIconImage(i.getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        initComponents(); // Inicializar componentes
        llenarTablaCitas(); // Llenar la tabla con los datos de citas
        agregarDatos();
		
        setVisible(true);
    }

    private void initComponents() {
        // Panel base
        JPanel panelBase = new JPanel(new BorderLayout());
        panelBase.setBackground(PRIMARY_COLOR);

        // Panel superior (norte)
        JPanel panelNorte = new JPanel(new GridLayout(1, 2));
        panelNorte.setBackground(PRIMARY_COLOR);

        // Botón "Atrás"
        btnBack = new JButton(new ImageIcon("resources/images/icons8-back-25.png"));
        btnBack.setBackground(PRIMARY_COLOR);
        btnBack.setPreferredSize(new Dimension(80, 25));
        btnBack.addActionListener(e -> {
            MenuTrabajador ventana = new MenuTrabajador(usuario, gestorBD);
            ventana.setVisible(true);
            this.dispose();
        });
        panelNorte.add(btnBack);

        // Cuadro de texto para filtro
        txtFiltro = new JTextField(20);
        panelNorte.add(txtFiltro);
        
        txtFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterCitas();
            }
        });

        // Panel inferior (sur)
        JPanel panelSur = new JPanel(new GridLayout(1, 3));

        // Botón "Añadir Cita"
        btnAddCita = createStyledButton("Añadir Cita");
        panelSur.add(btnAddCita);

        // Botón "Modificar Cita"
        btnModCita = createStyledButton("Modificar Cita");
        panelSur.add(btnModCita);

        // Botón "Eliminar Cita"
        btnBorrCita = createStyledButton("Eliminar Cita");
        panelSur.add(btnBorrCita);
        	// se añade la funcionalidad de eliminar las citas
        btnBorrCita.addActionListener(e -> borrarCita());
        
        // Panel central (tabla)
        scrollPaneCitas = new JScrollPane();
        panelBase.add(scrollPaneCitas, BorderLayout.CENTER);

        // Configuración de la tabla de citas
        String[] columnas = {"Código Paciente", "Nombre Paciente", "Apellido Paciente", "Doctor", "Fecha"};
        modeloDatosCitas = new DefaultTableModel(columnas, 0) {
        	 @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Deshabilitar edición de celdas
        }
        };
       
        tablaCitas = new JTable(modeloDatosCitas);
        tablaCitas.setGridColor(Color.BLACK);
        scrollPaneCitas.setViewportView(tablaCitas);

        // Añadir paneles al panel base
        panelBase.add(panelNorte, BorderLayout.NORTH);
        panelBase.add(panelSur, BorderLayout.SOUTH);

        // Añadir panel base a la ventana
        this.add(panelBase);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(BACKGROUND_COLOR);
        button.setFont(BUTTON_FONT);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BACKGROUND_COLOR);
                button.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
                button.setForeground(BACKGROUND_COLOR);
            }
        });
        return button;
    }
    	// Llamada a la función que plasma los datos de las citas en la tabla
    private void llenarTablaCitas() {
        modeloDatosCitas.setRowCount(0); // Limpiar la tabla

        for (Cita cita : citas) {
        	
        	// Formatear la fecha a String para evitar problemas de tipo
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String formattedDate = dateFormat.format(cita.getFechaHora());
        	
//            modeloDatosCitas.addRow(new Object[]{
//                cita.getPaciente().getCodigoPaciente(),             // Código del paciente
//                cita.getPaciente().getNombre(),                     // Nombre del paciente
//                cita.getPaciente().getApellido(),                   // Apellido del paciente
//                cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(), // Nombre del doctor
//                cita.getFechaHora()                                 // Fecha y hora de la cita
//            });
            
            Object[] row = {
            		cita.getPaciente().getCodigoPaciente(),             // Código del paciente
                    cita.getPaciente().getNombre(),                     // Nombre del paciente
                    cita.getPaciente().getApellido(),                   // Apellido del paciente
                    cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(), // Nombre del doctor
                    cita.getFechaHora()                                 // Fecha y hora de la cita
            };
            
            modeloDatosCitas.addRow(row);
        }

        	// Se define el modelo de datos sobre como se mostrarán las citas
        tablaCitas.setDefaultRenderer(Object.class, (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value != null ? value.toString() : "");
            result.setHorizontalAlignment(JLabel.CENTER);
            return result;
        });

        tablaCitas.setRowHeight(40);
        tablaCitas.getTableHeader().setReorderingAllowed(false);
        tablaCitas.getTableHeader().setResizingAllowed(false);
        tablaCitas.setAutoCreateRowSorter(true);

        // Configurar ancho preferido de las columnas
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(100); // Código del paciente
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre del paciente
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(150); // Apellido del paciente
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(200); // Nombre del doctor
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(150); // Fecha

        // Alineación personalizada para ciertas columnas
        DefaultTableCellRenderer leftAlignRenderer = new DefaultTableCellRenderer();
        leftAlignRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        leftAlignRenderer.setFont(new Font("Arial", Font.BOLD, 12));

        // Aplicar alineación a las columnas "Nombre" y "Apellido"
        tablaCitas.getColumnModel().getColumn(1).setCellRenderer(leftAlignRenderer); 
        tablaCitas.getColumnModel().getColumn(2).setCellRenderer(leftAlignRenderer); 

        // Estilo del encabezado
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(PRIMARY_COLOR);
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(tablaCitas.getFont().deriveFont(Font.BOLD));

        // Aplicar estilo a los encabezados de todas las columnas
        for (int i = 0; i < tablaCitas.getColumnModel().getColumnCount(); i++) {
            tablaCitas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        // Forzar la actualización visual de la tabla
        tablaCitas.revalidate();
        tablaCitas.repaint();
    }
    
    	// función que trae los datos ed la base de datos.
    private void agregarDatos() {
        try {
            gestorBD = new GestorBD();
            gestorBD.crearBBDD();
            gestorBD.insertarCita();
            this.citas = gestorBD.getCitas();
            llenarTablaCitas();
        } catch (SQLException e) {
            System.err.println("Error al agregar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    private void filterCitas() {
        modeloDatosCitas.setRowCount(0); // Limpiar la tabla antes de aplicar el filtro

        String filter = txtFiltro.getText().toLowerCase(); // Obtener el texto del filtro
        if (filter.isEmpty()) {
            // Si el filtro está vacío, mostrar todas las citas
            llenarTablaCitas();
            return;
        } 

        for (Cita cita : citas) {
            String codigoPaciente = String.valueOf(cita.getPaciente().getCodigoPaciente()); // Convertir a String
            String nombrePaciente = cita.getPaciente().getNombre();
            String apellidoPaciente = cita.getPaciente().getApellido();
            String nombreDoctor = cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido();
            String fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(cita.getFechaHora());

            boolean igual = codigoPaciente.contains(filter) ||
                              nombrePaciente.toLowerCase().contains(filter) ||
                              apellidoPaciente.toLowerCase().contains(filter) ||
                              nombreDoctor.toLowerCase().contains(filter) ||
                              fechaHora.toLowerCase().contains(filter);

            if (igual) {
                modeloDatosCitas.addRow(new Object[]{
                    codigoPaciente,
                    nombrePaciente,
                    apellidoPaciente,
                    nombreDoctor,
                    fechaHora
                });
            }
        }
    }

    // Añadir funcionalidad al btnAddCita;
    
    
    // Añadir funcionalidad al btnModCita;
    
    
    // Añadir funcionalidad al btnBorrCita;
    
    	//Función para eliminar una fila de la tabla citas
    private void borrarCita() {
   	 int selectedRow = tablaCitas.getSelectedRow();
   	 
   	 if (selectedRow != -1) {
   	 int confirm = JOptionPane.showConfirmDialog(this, 
   	            "¿Estás seguro de que quieres borrar este paciente?", 
   	            "Confirmar eliminación", 
   	            JOptionPane.YES_NO_OPTION);

   	        if (confirm == JOptionPane.YES_OPTION) {
   	            // Get the patient's code from the selected row
   	            int patientCode = (int) modeloDatosCitas.getValueAt(selectedRow, 0);

   	            // Remove from the pacientes list
   	            citas.removeIf(cita -> cita.getPaciente().getCodigoPaciente() == patientCode);

   	            // Remove from the table model
   	            modeloDatosCitas.removeRow(selectedRow);

   	            JOptionPane.showMessageDialog(this, "Paciente eliminado con éxito.", 
   	                "Eliminación completa", JOptionPane.INFORMATION_MESSAGE);
   	        }
   	    } else {
   	        JOptionPane.showMessageDialog(this, "Selecciona un paciente para borrar.", 
   	            "Error", JOptionPane.ERROR_MESSAGE);
   	    }
   }

    
}
    
//FUENTE-EXTERNA
//URL: (https://chatgpt.com/c/674f39f7-75f0-8001-aa1b-d846d3f769cc)
//ADAPTADO (He tenido que ir modificando porque habia errores con el seguimiento de los datos)
//IAG (herramienta:chatgpt )
//
