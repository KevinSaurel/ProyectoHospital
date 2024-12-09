package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.Cita;
import domain.Context;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;

public class MenuPaciente extends JFrame {

    private JButton btnVolver;
    private JButton btnCitas;
    private JButton btnHospitales;
    private JButton btnMedicos;
    private JButton btnHistorial;
    private JButton btnContacto;
    private JButton btnParametros;
    private JButton btnUsuario;
    private ArrayList<Doctor> medicos;
    private List<Cita> citas;
    private Paciente paciente;

    private JPanel panelHilo;
    private JLabel labelMedicos;

    public MenuPaciente(Persona usuario) {
        paciente = (Paciente) usuario;
        medicos = new ArrayList<>();
        Context context = Context.getInstance();
        this.medicos = context.getMedicos();
        context.setPaciente(paciente);

        ImageIcon i = new ImageIcon("resources/images/hospital.png");
        setIconImage(i.getImage());

        btnVolver = new JButton("VOLVER");
        btnCitas = new JButton("CITAS");
        btnHospitales = new JButton("HOSPITALES");
        btnMedicos = new JButton("MEDICOS");
        btnHistorial = new JButton("MI HISTORIAL");
        btnContacto = new JButton("Contacto");
        btnParametros = new JButton("Parametros");
        btnUsuario = new JButton("Usuario");
        Color color = new Color(6, 99, 133);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(1, 5, 10, 50));

        ImageIcon iconoHospital = new ImageIcon(getClass().getResource("resources/images/hospital.png"));
        Image imagenEscalada = iconoHospital.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        iconoHospital = new ImageIcon(imagenEscalada);
        JLabel labelConIcono = new JLabel(iconoHospital);

        grid.add(labelConIcono);
        grid.add(btnContacto);
        grid.add(btnParametros);
        grid.add(btnUsuario);

        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        grid.setBackground(Color.white);
        panel.add(grid, BorderLayout.NORTH);

        JPanel pngHistorial = new JPanel();
        pngHistorial.setLayout(new GridLayout(1, 2, 10, 10));

        panelHilo = new JPanel(new BorderLayout());
        HiloImagen hilo = new HiloImagen();
        hilo.start();

        pngHistorial.add(panelHilo);
        pngHistorial.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel cita = new JPanel();
        cita.setLayout(new GridLayout(2, 1, 10, 10));
        cita.add(btnHistorial);
        anadirColores(cita.getComponents(), color);
        cita.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel ubicacionHospital = new JPanel();
        ubicacionHospital.setLayout(new GridLayout(1, 2, 10, 10));
        ubicacionHospital.add(btnCitas);
        ubicacionHospital.add(btnMedicos);
        ubicacionHospital.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        anadirColores(ubicacionHospital.getComponents(), color);
        cita.add(ubicacionHospital);
        pngHistorial.add(cita);
        panel.add(pngHistorial, BorderLayout.CENTER);

        anadirColores(panel.getComponents(), color);

        btnHistorial.addActionListener(e -> {
            Persona p = new Persona("title", "title", "title", 1, "");
            if (usuario instanceof Paciente) {
                VentanaHistorial ventana = new VentanaHistorial(paciente, p);
                ventana.setVisible(true);
            }
        });

        btnMedicos.addActionListener(e -> {
            if (paciente instanceof Paciente) {
                VentanaMedicos ventana = new VentanaMedicos(medicos, paciente);
                ventana.setVisible(true);
            }
        });

        btnCitas.addActionListener(e -> {
            List<Cita> l = new ArrayList<>();
            for (Cita c : citas) {
                if ((c.getPaciente().getCodigoPaciente()) == (paciente.getCodigoPaciente())) {
                    l.add(c);
                }
            }
            VentanaCitas ventana = new VentanaCitas(l, usuario);
            ventana.setVisible(true);
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void anadirColores(Component[] components, Color color) {
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setBackground(color);
                component.setForeground(Color.white);
                component.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
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
            } else if (component instanceof JPanel) {
                JPanel componentN = (JPanel) component;
                anadirColores(componentN.getComponents(), color);
            }
        }
    }

    private class HiloImagen extends Thread {
        private int currentIndex = 0;

        public void run() {
            ImageIcon imagenMenu = new ImageIcon(getClass().getResource("resources/images/Menuimage.png"));
            Image imagenMenuEscalada = imagenMenu.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            ImageIcon imagenMenuIcon = new ImageIcon(imagenMenuEscalada);

            ImageIcon imagenMedicos = new ImageIcon(getClass().getResource("resources/images/MedicosFelices.png"));
            Image imagenMedicosEscalada = imagenMedicos.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            ImageIcon imagenMedicosIcon = new ImageIcon(imagenMedicosEscalada);

            ImageIcon imagenSala = new ImageIcon(getClass().getResource("resources/images/imagenSala.png"));
            Image imagenSalaEscalada = imagenSala.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
            ImageIcon imagenSalaIcon = new ImageIcon(imagenSalaEscalada);

            List<ImageIcon> list = new ArrayList<>();
            list.add(imagenMenuIcon);
            list.add(imagenMedicosIcon);
            list.add(imagenSalaIcon);

            try {
                while (true) {
                    SwingUtilities.invokeLater(() -> {
                        panelHilo.removeAll();
                        labelMedicos = new JLabel(list.get(currentIndex));
                        panelHilo.add(labelMedicos, BorderLayout.CENTER);
                        panelHilo.revalidate();
                        panelHilo.repaint();
                        currentIndex = (currentIndex + 1) % list.size();
                    });
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }
        }
    }
}

//FUENTE-EXTERNA
//URL: (url de la fuente externa)
//SIN-CAMBIOS ó ADAPTADO (explicar modificación realizada)
//IAG (herramienta: ChatGPT)
//SIN CAMBIOS ó ADAPTADO (He usado chatgpt para soluciona dudas y saber porque me salen errores  )
