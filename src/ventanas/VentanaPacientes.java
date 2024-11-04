package ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import clases.Administrador;
import clases.Context;
import clases.Doctor;
import clases.Historial;
import clases.Paciente;
import clases.Persona;

public class VentanaPacientes extends JFrame{

    private List<Paciente> pacientes;
    private JTable tablaPacientes;
    private JScrollPane scrollPanePacientes;
    private JTextField txtFiltro;
    private DefaultTableModel modeloDatosPacientes;
    private JButton btnAnadirP;
    private JButton btnBorrarP;
    private Persona usuario;

    public VentanaPacientes(List<Paciente> pacientes , Persona usuarioP) {
    	
    	Context context = Context.getInstance();  
    	this.pacientes = context.getPacientes(); 
        
        usuario = usuarioP;
        Color color = new Color(6,99,133);
        
        ImageIcon i = new ImageIcon("src/recursos/hospital.png");
		setIconImage(i.getImage());
        
        // llamada para que filtre por nombre
        txtFiltro = new JTextField(20);
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent arg0) {
                filtrarPacientes();
            }

            public void insertUpdate(DocumentEvent arg0) {
                filtrarPacientes();
            }

            public void removeUpdate(DocumentEvent arg0) {
                filtrarPacientes();
            }
        });

        // Panel principal y configuraci�n general
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(color);

        String[] nombreColumnas = { "Nombre", "Apellido", "Edad", "C�digo Paciente", "Historial" };
        modeloDatosPacientes = new DefaultTableModel(nombreColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
               
                return column == 4;
            }
        };
        tablaPacientes = new JTable(modeloDatosPacientes);
        tablaPacientes.setGridColor(Color.black);
        tablaPacientes.getColumn("Historial").setCellRenderer(new ButtonRenderer());
        tablaPacientes.getColumn("Historial").setCellEditor(new ButtonEditor(new JButton("Historial")));
        initTables();
        //JButton btnHistorial = new JButton();
        tablaPacientes.getColumnModel().getColumn(0).setPreferredWidth(150); 
        tablaPacientes.getColumnModel().getColumn(1).setPreferredWidth(150); 
        tablaPacientes.getColumnModel().getColumn(2).setPreferredWidth(100); 
        tablaPacientes.getColumnModel().getColumn(3).setPreferredWidth(150); 
        tablaPacientes.getColumnModel().getColumn(4).setPreferredWidth(100); 
        
        for (Paciente paciente : pacientes) {
            Object[] fila = {paciente.getNombre(), paciente.getApellido(), paciente.getEdad(),
                    paciente.getCodigoPaciente(), "Historial" };
            modeloDatosPacientes.addRow(fila);
        }

     
        ImageIcon iconBack = new ImageIcon(getClass().getResource("/recursos/icons8-back-25.png"));
        JButton btnBack = new JButton(iconBack);
        btnBack.setBackground(color);
        btnBack.setPreferredSize(new Dimension(80, 25));
        btnBack.addActionListener(e -> {
            MenuTrabajador ventana = new MenuTrabajador(usuario);
            ventana.setVisible(true);
            ((JFrame) btnBack.getTopLevelAncestor()).dispose(); 
        });
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnBack.setBackground(Color.white);
                btnBack.setForeground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnBack.setBackground(color);
                btnBack.setForeground(Color.white);
            }
        });

       
        btnAnadirP = new JButton("A�adir Paciente");
        btnAnadirP.setBackground(color);
        btnAnadirP.setForeground(Color.white);
        btnAnadirP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAnadirP.setBackground(Color.white);
                btnAnadirP.setForeground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAnadirP.setBackground(color);
                btnAnadirP.setForeground(Color.white);
            }
        });
        btnBorrarP = new JButton("Borrar Paciente");
        btnBorrarP.setBackground(color);
        btnBorrarP.setForeground(Color.white);
        btnBorrarP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnBorrarP.setBackground(Color.white);
            	btnBorrarP.setForeground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	btnBorrarP.setBackground(color);
            	btnBorrarP.setForeground(Color.white);
            }
        });

        
        btnAnadirP.addActionListener(e -> {
            JFrame frameNuevoPaciente = new JFrame("A�adir Nuevo Paciente");
            frameNuevoPaciente.setSize(400, 300);
            frameNuevoPaciente.setLayout(new GridLayout(8, 2)); 
            frameNuevoPaciente.getContentPane().setBackground(color);

            JLabel lblContrasena = new JLabel("Contrase�a:");
            JTextField txtContrasena = new JTextField();
            JLabel lblNombre = new JLabel("Nombre:");
            JTextField txtNombre = new JTextField();
            JLabel lblApellido = new JLabel("Apellido:");
            JTextField txtApellido = new JTextField();
            JLabel lblEdad = new JLabel("Edad:");
            JTextField txtEdad = new JTextField();
            JLabel lblUbicacion = new JLabel("Ubicaci�n:");
            JTextField txtUbicacion = new JTextField();
            JLabel lblCodigoPaciente = new JLabel("C�digo Paciente:");
            JTextField txtCodigoPaciente = new JTextField();

            JButton btnAnadir = new JButton("A�adir Paciente");
            btnAnadir.setBackground(color);
            btnAnadir.setForeground(Color.white);
            btnAnadir.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btnAnadir.setBackground(Color.white);
                    btnAnadir.setForeground(color);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btnAnadir.setBackground(color);
                    btnAnadir.setForeground(Color.white);
                }
            });

            frameNuevoPaciente.add(lblContrasena);
            lblContrasena.setForeground(Color.WHITE);
            frameNuevoPaciente.add(txtContrasena);
            frameNuevoPaciente.add(lblNombre);
            lblNombre.setForeground(Color.WHITE);
            frameNuevoPaciente.add(txtNombre);
            frameNuevoPaciente.add(lblApellido);
            lblApellido.setForeground(Color.WHITE);
            frameNuevoPaciente.add(txtApellido);
            frameNuevoPaciente.add(lblEdad);
            lblEdad.setForeground(Color.WHITE);
            frameNuevoPaciente.add(txtEdad);
            frameNuevoPaciente.add(lblUbicacion);
            lblUbicacion.setForeground(Color.WHITE);
            frameNuevoPaciente.add(txtUbicacion);
            frameNuevoPaciente.add(lblCodigoPaciente);
            lblCodigoPaciente.setForeground(Color.WHITE);
            frameNuevoPaciente.add(txtCodigoPaciente);
            frameNuevoPaciente.add(new JLabel()); // Label vac�o para llenar �ltimo grid
            frameNuevoPaciente.add(btnAnadir);

            btnAnadir.addActionListener(e2 -> {
                String contrasena = txtContrasena.getText();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String ubicacion = txtUbicacion.getText();
                int codigoP = Integer.parseInt(txtCodigoPaciente.getText());
                List<Historial> historial = new ArrayList<>();
                int edad;

                try {
                    edad = Integer.parseInt(txtEdad.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameNuevoPaciente, "Edad debe ser un n�mero v�lido.");
                    return;
                }

                
                if (nombre.isEmpty() || apellido.isEmpty() || ubicacion.isEmpty() || edad < 0) {
                    JOptionPane.showMessageDialog(frameNuevoPaciente, "Por favor, rellena todos los campos.");
                    return;
                }
                Paciente nuevoPaciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoP, historial);
                pacientes.add(nuevoPaciente);
                context.guardarPaciente(nuevoPaciente);

                modeloDatosPacientes.addRow(new Object[]{nombre, apellido, edad, codigoP});

                frameNuevoPaciente.dispose(); 
            });

            frameNuevoPaciente.setLocationRelativeTo(null);
            frameNuevoPaciente.setVisible(true);
        });
        JPanel panelbtn = new JPanel(new BorderLayout());
       
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelbtn.add(txtFiltro,BorderLayout.CENTER);
        panelbtn.add(btnBorrarP,BorderLayout.EAST);
        panelArriba.add(panelbtn, BorderLayout.CENTER);
        
        panelArriba.add(btnAnadirP, BorderLayout.EAST);
        
        panelArriba.add(btnBack, BorderLayout.WEST);

        
        scrollPanePacientes = new JScrollPane(tablaPacientes);
        panelPrincipal.add(scrollPanePacientes, BorderLayout.CENTER);
        panelPrincipal.add(panelArriba, BorderLayout.NORTH);

      
        	
        
        
        
        setTitle("Lista de Pacientes");
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

        tablaPacientes.setDefaultRenderer(Object.class, cellRenderer);
        tablaPacientes.setRowHeight(40);
        tablaPacientes.getTableHeader().setReorderingAllowed(false);
        tablaPacientes.getTableHeader().setResizingAllowed(false);
        tablaPacientes.setAutoCreateRowSorter(true);
        tablaPacientes.getColumnModel().getColumn(2).setPreferredWidth(400);

        
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(6, 99, 133));
        headerRenderer.setForeground(Color.WHITE); 
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(tablaPacientes.getFont().deriveFont(java.awt.Font.BOLD));

        for (int i = 0; i < tablaPacientes.getColumnModel().getColumnCount(); i++) {
            tablaPacientes.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    private void filtrarPacientes() {
        
        modeloDatosPacientes.setRowCount(0);

        String filtro = txtFiltro.getText().toLowerCase();
        for (Paciente paciente : pacientes) {
            if (paciente.getNombre().toLowerCase().contains(filtro) ||
                paciente.getApellido().toLowerCase().contains(filtro) ||
                String.valueOf(paciente.getEdad()).contains(filtro) ||
                String.valueOf(paciente.getCodigoPaciente()).contains(filtro)) {
                modeloDatosPacientes.addRow(new Object[]{paciente.getNombre(), paciente.getApellido(), paciente.getEdad(), paciente.getCodigoPaciente(), "Ver Historial"});
            }
        }
    }

     




class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
    	setBackground(new Color(6, 99, 133));
    	setForeground(Color.white);

        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setText((value != null) ? value.toString() : "Ver Historial");
        return this;
    }
}

class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JButton button) {
        this.button = button;
        this.button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
            int column) {
        label = (value != null) ? value.toString() : "Ver Historial";
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            
            //System.out.pintln("Historial button clicked on row " + tablaPacientes.getSelectedRow());
            int row = tablaPacientes.getSelectedRow();
           
            int codigoP = (int) modeloDatosPacientes.getValueAt(row, 3);
            for(Paciente p :pacientes) {
            	if(p.getCodigoPaciente()==codigoP) {
            	String	contrasena=p.getContrasena();
            	String nombre=p.getNombre();
            	String	apellido = p.getApellido();
            	int	edad=p.getEdad();
            	String	ubicacion=p.getUbicacion();
            		codigoP = p.getCodigoPaciente();
            		List<Historial> lista = p.getHistorialPaciente();
            		Paciente pac = new Paciente(contrasena,nombre,apellido,
            									edad,ubicacion,codigoP,lista);
            		//System.out.println(pac);
            		if(usuario instanceof Doctor) {
            		VentanaHistorial ventanaHistorial = new VentanaHistorial(pac , usuario);
            		ventanaHistorial.setVisible(true);
            		//this.dispose();
            		}else if (usuario instanceof Administrador){
            			JOptionPane.showMessageDialog(null, "Solo un medico puede acceder al historial de un paciente", "Acceso rechazado", JOptionPane.ERROR_MESSAGE);
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