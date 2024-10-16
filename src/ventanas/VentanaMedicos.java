package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import clases.Doctor;

public class VentanaMedicos extends JFrame {

    private List<Doctor> medicos;
    private JTable tablaMedicos;
    private JScrollPane scrollPaneMedicos;
    private JTextField txtFiltro;
    private DefaultTableModel modeloDatosMedicos;
    private JButton btnAnadirM;

    public VentanaMedicos(List<Doctor> medicos) {
        this.medicos = medicos;

        // aqui pongo las llamadas para que filtra por nombre 
        txtFiltro = new JTextField(20);
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent arg0) {
                filtrarMedicos();
            }

            public void insertUpdate(DocumentEvent arg0) {
                filtrarMedicos();
            }

            public void removeUpdate(DocumentEvent arg0) {
                filtrarMedicos();
            }
        });

        // https://stackoverflow.com/questions/38150025/adding-jtable-into-jframe
        // me he basado un poco con eso 
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        

        panelPrincipal.setBorder(BorderFactory.createTitledBorder("Tabla Médicos"));
        panelPrincipal.setBackground(Color.red);
        String[] nombreColumnas = {"Nombre", "Apellido", "Especialidad", "Horario"};
        modeloDatosMedicos = new DefaultTableModel(nombreColumnas, 0);
        tablaMedicos = new JTable(modeloDatosMedicos);
        tablaMedicos.setGridColor(Color.black);
        initTables(); // Call the method to initialize the table

        for (Doctor medico : medicos) {
            Object[] fila = {medico.getNombre(), medico.getApellido(), medico.getEspecialidad(), medico.getHorario()};
            modeloDatosMedicos.addRow(fila);
        }
        
       

        // voy a usar icono en recursos para icono8 atras 
        ImageIcon iconBack = new ImageIcon(getClass().getResource("/recursos/icons8-back-25.png"));

        // Create a button with the custom icon
        JButton btnBack = new JButton(iconBack);
        btnBack.setBackground(Color.RED);
        btnBack.setPreferredSize(new Dimension(80, 25));
        btnBack.addActionListener(e -> {
            // Assuming you have a MenuPaciente window to go back to
            MenuPaciente ventana = new MenuPaciente();
            ventana.setVisible(true);
            this.dispose(); // Close the current window
        });
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnBack.setBackground(new Color(248,160,148)); // Change to hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnBack.setBackground(Color.RED); // Revert to original color
            }
        });
        
        
        btnAnadirM = new JButton("Añadir Médico");
        btnAnadirM.setBackground(Color.RED);
        btnAnadirM.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAnadirM.setBackground(new Color(248,160,148)); // Change to hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAnadirM.setBackground(Color.RED); // Revert to original color
            }
        });
        // aqui creo el boton para anadir el medico
        btnAnadirM.addActionListener(e -> {
            JFrame frameNuevoMedico = new JFrame("Añadir Nuevo Médico");
            frameNuevoMedico.setSize(400, 300);
            frameNuevoMedico.setLayout(new GridLayout(8, 2)); // 7 labels + text fields, and 1 button

            JLabel lblContrasena = new JLabel("Contraseña:");
            JTextField txtContrasena = new JTextField();
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblApellido = new JLabel("Apellido:");
            JTextField txtApellido = new JTextField();
            JLabel lblEdad = new JLabel("Edad:");
            JTextField txtEdad = new JTextField();
            JLabel lblUbicacion = new JLabel("Ubicación:");
            JTextField txtUbicacion = new JTextField();
            JLabel lblEspecialidad = new JLabel("Especialidad:");
            JTextField txtEspecialidad = new JTextField();
            JLabel lblHorario = new JLabel("Horario:");
            JTextField txtHorario = new JTextField();

            
            JButton btnAnadir = new JButton("Añadir Médico");

           
            frameNuevoMedico.add(lblContrasena);
            frameNuevoMedico.add(txtContrasena);
            frameNuevoMedico.add(lblNombre);
            frameNuevoMedico.add(txtNombre);
            frameNuevoMedico.add(lblApellido);
            frameNuevoMedico.add(txtApellido);
            frameNuevoMedico.add(lblEdad);
            frameNuevoMedico.add(txtEdad);
            frameNuevoMedico.add(lblUbicacion);
            frameNuevoMedico.add(txtUbicacion);
            frameNuevoMedico.add(lblEspecialidad);
            frameNuevoMedico.add(txtEspecialidad);
            frameNuevoMedico.add(lblHorario);
            frameNuevoMedico.add(txtHorario);
            frameNuevoMedico.add(new JLabel()); // Label vacio para llenar ultimo grid
            frameNuevoMedico.add(btnAnadir);

            btnAnadir.addActionListener(e2 -> {
                String contrasena = txtContrasena.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String ubicacion = txtUbicacion.getText();
                String especialidad = txtEspecialidad.getText();
                String horario = txtHorario.getText();
                int edad;

                try {
                    edad = Integer.parseInt(txtEdad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameNuevoMedico, "Edad debe ser un número válido.");
                    return;
                }

                // por si hay campos vacios 
                if (nombre.isEmpty() || apellido.isEmpty() || especialidad.isEmpty() || horario.isEmpty() || edad < 0 || ubicacion.isEmpty()) {
                    JOptionPane.showMessageDialog(frameNuevoMedico, "Por favor, rellena todos los campos.");
                    return;
                }
                Doctor nuevoMedico = new Doctor(contrasena, nombre, apellido, edad, ubicacion, especialidad, horario);
                medicos.add(nuevoMedico);

                modeloDatosMedicos.addRow(new Object[]{nombre, apellido, especialidad, horario});

                frameNuevoMedico.dispose(); 
            });

            frameNuevoMedico.setLocationRelativeTo(null);
            frameNuevoMedico.setVisible(true);
        });

        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.add(txtFiltro, BorderLayout.CENTER);
        panelArriba.add(btnAnadirM, BorderLayout.EAST);
        panelArriba.add(btnBack, BorderLayout.WEST);

        // scroll pane crear y meter al panel 
        scrollPaneMedicos = new JScrollPane(tablaMedicos);
        panelPrincipal.add(scrollPaneMedicos, BorderLayout.CENTER);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

        setTitle("Lista de Médicos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        add(panelPrincipal);
        setVisible(true);
    }

    private void initTables() {
        TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value != null ? value.toString() : "");
            result.setHorizontalAlignment(JLabel.CENTER);
            return result;
        };

        tablaMedicos.setDefaultRenderer(Object.class, cellRenderer);
        tablaMedicos.setRowHeight(40);
        tablaMedicos.getTableHeader().setReorderingAllowed(false);
        tablaMedicos.getTableHeader().setResizingAllowed(false);
        tablaMedicos.setAutoCreateRowSorter(true);
        tablaMedicos.getColumnModel().getColumn(2).setPreferredWidth(400);
        //intento poner color 
        tablaMedicos.getTableHeader().setBackground(Color.red);
        //bold para la cabecerA
        tablaMedicos.getTableHeader().setFont(tablaMedicos.getFont().deriveFont(java.awt.Font.BOLD));
        
        tablaMedicos.setForeground(Color.RED);
        tablaMedicos.setBackground(Color.WHITE);
    }

    private void filtrarMedicos() {
        // Se borran los datos 
        modeloDatosMedicos.setRowCount(0);

        String filtro = txtFiltro.getText().toLowerCase();
        for (Doctor medico : medicos) {
            if (medico.getNombre().toLowerCase().contains(filtro) ||
                medico.getApellido().toLowerCase().contains(filtro) ||
                medico.getEspecialidad().toLowerCase().contains(filtro) ||
                medico.getHorario().toLowerCase().contains(filtro)) {
                modeloDatosMedicos.addRow(new Object[]{medico.getNombre(), medico.getApellido(), medico.getEspecialidad(), medico.getHorario()});
            }
        }
    }
}
// he usado chatgpt y stack overflow  para resolver errores y dudas . 
//https://stackoverflow.com/questions/14563799/jtable-cellrenderer-changes-backgroundcolor-of-cells-while-running

