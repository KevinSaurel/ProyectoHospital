package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import domain.Persona;
import persistente.GestorBD;

public class VentanaVisualizarUsuario extends JFrame {
    
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
    private static final int SPACING = 20;
    protected GestorBD gestorBD;
    private final JButton btnVolver;
    private final Persona usuario;
    
    public VentanaVisualizarUsuario(Persona usuario , GestorBD gestorBD) {
    	this.gestorBD = gestorBD;
        this.usuario = usuario;
        this.btnVolver = new JButton();
        
        setupMainFrame();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        
        setVisible(true);
    }
    
    private void setupMainFrame() {
        setTitle("Mi Perfil de Usuario");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(SPACING, SPACING));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING));
        
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createUserInfoPanel(), BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(SPACING, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        // Logo and back button section
        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, SPACING, 0));
        leftSection.setBackground(BACKGROUND_COLOR);
        
        setupBackButton();
        leftSection.add(btnVolver);
        
        // Add hospital logo
        ImageIcon hospitalIcon = new ImageIcon("resources/images/hospital.png");
        Image scaledImage = hospitalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        leftSection.add(logoLabel);
        
        // Navigation buttons
        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, SPACING, 0));
        rightSection.setBackground(BACKGROUND_COLOR);
        
        JButton btnParametros = createStyledButton("Parámetros");
        JButton btnUsuario = createStyledButton("Usuario");
        
        rightSection.add(btnParametros);
        rightSection.add(btnUsuario);
        
        headerPanel.add(leftSection, BorderLayout.WEST);
        headerPanel.add(rightSection, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createUserInfoPanel() {
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(BACKGROUND_COLOR);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(SPACING * 2, SPACING * 4, SPACING * 2, SPACING * 4));
        
        String[][] userFields = {
            {"Nombre", usuario.getNombre()},
            {"Apellido", usuario.getApellido()},
            {"Edad", String.valueOf(usuario.getEdad())},
            {"Contraseña", usuario.getContrasena()},
            {"Ubicación", usuario.getUbicacion()}
        };
        
        for (String[] field : userFields) {
            userInfoPanel.add(createFieldPanel(field[0], field[1]));
            userInfoPanel.add(Box.createVerticalStrut(SPACING));
        }
        
        return userInfoPanel;
    }
    
    private JPanel createFieldPanel(String labelText, String value) {
        JPanel fieldPanel = new JPanel(new BorderLayout(SPACING, 0));
        fieldPanel.setBackground(BACKGROUND_COLOR);
        
        // Create card-like panel with shadow effect
        fieldPanel.setBorder(BorderFactory.createCompoundBorder(
            new SoftBevelBorder(BevelBorder.RAISED),
            BorderFactory.createEmptyBorder(SPACING, SPACING, SPACING, SPACING)
        ));
        
        // Label and value
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel label = new JLabel(labelText + ":");
        label.setFont(LABEL_FONT);
        label.setForeground(PRIMARY_COLOR);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(BUTTON_FONT);
        
        textPanel.add(label);
        textPanel.add(valueLabel);
        
        // Edit button
        JButton editButton = createStyledButton("Editar");
        editButton.setPreferredSize(new Dimension(100, 35));
        
        fieldPanel.add(textPanel, BorderLayout.CENTER);
        fieldPanel.add(editButton, BorderLayout.EAST);
        
        return fieldPanel;
    }
    
    private void setupBackButton() {
        ImageIcon iconBack = new ImageIcon("resources/images/icons8-back-25.png");
        btnVolver.setIcon(iconBack);
        styleButton(btnVolver);
        btnVolver.setPreferredSize(new Dimension(80, 35));
        
        btnVolver.addActionListener(e -> {
            MenuTrabajador ventana = new MenuTrabajador(usuario, gestorBD);
            ventana.setVisible(true);
            dispose();
        });
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
}