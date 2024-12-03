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
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import domain.Cita;
import domain.Context;
import domain.Persona;

public class VentanaCitas extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(6, 99, 133);
    private static final Color SECONDARY_COLOR = new Color(7, 120, 163);
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private List<Cita> citas; // Lista de citas
    private JTable tablaCitas;
    private JScrollPane scrollPaneCitas;
    private JTextField txtFiltro;
    private DefaultTableModel modeloDatosCitas;
    private JButton btnAddCita;
    private JButton btnModCita;
    private JButton btnBorrCita;
    private JButton btnBack;
    private Persona usuario;

    public VentanaCitas(List<Cita> citas, Persona usuario) {
        // Inicializar lista de citas
        this.citas = (citas != null) ? citas : new ArrayList<>();
        this.usuario = usuario;

        // Configurar ventana
        setTitle("Gestión de Citas");
        setIconImage(new ImageIcon("src/db/hospital.png").getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        initComponents(); // Inicializar componentes
        llenarTablaCitas(); // Llenar la tabla con los datos de citas
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
        btnBack = new JButton(new ImageIcon(getClass().getResource("/db/icons8-back-25.png")));
        btnBack.setBackground(PRIMARY_COLOR);
        btnBack.setPreferredSize(new Dimension(80, 25));
        btnBack.addActionListener(e -> {
            MenuTrabajador ventana = new MenuTrabajador(usuario);
            ventana.setVisible(true);
            this.dispose();
        });
        panelNorte.add(btnBack);

        // Cuadro de texto para filtro
        txtFiltro = new JTextField(20);
        panelNorte.add(txtFiltro);

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

        // Panel central (tabla)
        scrollPaneCitas = new JScrollPane();
        panelBase.add(scrollPaneCitas, BorderLayout.CENTER);

        // Configuración de la tabla de citas
        String[] columnas = {"Código Paciente", "Nombre Paciente", "Apellido Paciente", "Doctor", "Fecha"};
        modeloDatosCitas = new DefaultTableModel(columnas, 0);
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

    private void llenarTablaCitas() {
        modeloDatosCitas.setRowCount(0); // Limpiar la tabla

        if (citas == null || citas.isEmpty()) {
            return; // Evitar errores si la lista está vacía o nula
        }

        for (Cita cita : citas) {
            modeloDatosCitas.addRow(new Object[]{
                cita.getPaciente().getCodigoPaciente(),             // Código del paciente
                cita.getPaciente().getNombre(),                     // Nombre del paciente
                cita.getPaciente().getApellido(),                   // Apellido del paciente
                cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellido(), // Nombre del doctor
                cita.getFechaHora()                                 // Fecha y hora de la cita
            });
        }

        // Configurar el sorter para la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloDatosCitas);
        tablaCitas.setRowSorter(sorter);
    }
}
//FUENTE-EXTERNA
//URL: ()
//ADAPTADO (He tenido que ir modificando porque habia errores con el seguimiento de los datos)
//IAG (herramienta:chatgpt )
//
