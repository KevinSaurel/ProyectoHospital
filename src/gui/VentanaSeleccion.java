package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import domain.Administrador;
import domain.Context;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;

public class VentanaSeleccion extends JFrame {
    private static final long serialVersionUID = 1L;
    protected JButton botonCerrar, botonPaciente, botonTrabajador;
    protected JPanel pAbajo, pCentro;
    protected JTextField textoIdentificacion;
    protected JLabel lblIdentificacion, lblImagenTrabajador, lblImagenCliente;
    protected Icon ImageIcon;

    private ArrayList<Doctor> listaMedicos;
    private ArrayList<Administrador> listaAdmin;
    private ArrayList<Paciente>listaPacientes;
    private Persona usuario;

    public VentanaSeleccion() {
    	ImageIcon i = new ImageIcon("/images/hospital.png");
    	ImageIcon icon = new ImageIcon("/images/hospital.png");
    	Image scaledImage = i.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
    	this.ImageIcon  = new ImageIcon(scaledImage);
        setIconImage(i.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 200, 600, 400);
        Color color = new Color(6, 99, 133);

        botonCerrar = new JButton("CERRAR");
        botonPaciente = new JButton("SOY PACIENTE");
        botonTrabajador = new JButton("SOY TRABAJADOR");
        lblIdentificacion = new JLabel("Introduce que eres: ");
        pAbajo = new JPanel();
        pCentro = new JPanel();
        pCentro.setBackground(color.white);
        pAbajo.setBackground(color.white);
        textoIdentificacion = new JTextField(20);
        pCentro.add(lblIdentificacion);
        pCentro.add(botonTrabajador);
        pCentro.add(botonPaciente);
        pAbajo.add(botonCerrar);

        anadirColores(pCentro.getComponents(), color);
        anadirColores(pAbajo.getComponents(), color);
        getContentPane().add(pAbajo, BorderLayout.SOUTH);
        getContentPane().add(pCentro, BorderLayout.CENTER);

        botonCerrar.addActionListener((e) -> {
            System.exit(0);
        });

        botonTrabajador.addActionListener((e) -> {
            // Inicio de sesión del trabajador
        	String usuarioN =(String)JOptionPane.showInputDialog(this, "Usuario:", "Input Dialog", JOptionPane.PLAIN_MESSAGE, ImageIcon, null, null);
            String contrasena = (String)JOptionPane.showInputDialog(this, "Contraseña:", "Input Dialog", JOptionPane.PLAIN_MESSAGE, ImageIcon, null, null);

            if (listaGetNombre(usuarioN, contrasena)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso!", "Bienvenido: " + usuarioN, JOptionPane.INFORMATION_MESSAGE);
//                MenuTrabajador ventana = new MenuTrabajador(usuario);
//                ventana.setVisible(true);
                SwingUtilities.invokeLater(() -> {
                    ProgressBar progressBar = new ProgressBar(usuario , false);
                    progressBar.setVisible(true);
                });              
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonPaciente.addActionListener(e -> {
        	

        	// Show the input dialog with the custom icon
        	String usuarioN = (String)JOptionPane.showInputDialog(this, "Usuario:", "Input Dialog", JOptionPane.PLAIN_MESSAGE, ImageIcon, null, null);
        	String contrasena = (String)JOptionPane.showInputDialog(this, "Contraseña:", "Input Dialog", JOptionPane.PLAIN_MESSAGE, ImageIcon, null, null);

            if (listaGetNombre(usuarioN, contrasena)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso!", "Bienvenido: " + usuarioN, JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> {
                    ProgressBar progressBar = new ProgressBar(usuario , true);
                    progressBar.setVisible(true);
                });         
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean listaGetNombre(String nombre, String contrasena) {
        Context context = Context.getInstance();
        listaMedicos = context.getMedicos();
        listaAdmin = context.getAdministrador();
        listaPacientes = context.getPacientes();

        for (Doctor medico : listaMedicos) {
            if (medico.getNombre().equals(nombre) && medico.getContrasena().equals(contrasena)) {
                usuario = medico;
                //System.out.println(usuario);
                return true;
            }
        }
        for (Administrador admin : listaAdmin) {
            if (admin.getNombre().equals(nombre) && admin.getContrasena().equals(contrasena)) {
                usuario = admin;
                //System.out.println(usuario);
                return true;
            }
        }
        for (Paciente p : listaPacientes) {
            if (p.getNombre().equals(nombre) && p.getContrasena().equals(contrasena)) {
                usuario = p;
               // System.out.println(usuario);
                return true;
            }
        }
        return false;
    }

    private void anadirColores(Component[] components, Color color) {
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setBackground(color);
                component.setForeground(Color.white);
                component.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        component.setBackground(Color.white);
                        component.setForeground(color);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        component.setBackground(color);
                        component.setForeground(Color.white);
                    }
                });
            }
        }
    }
}
