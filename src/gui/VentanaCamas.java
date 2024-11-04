package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import domain.Cama;
import domain.Context;
import domain.Historial;
import domain.Paciente;
import domain.Persona;

public class VentanaCamas extends JFrame{
	private JButton btnAsignarCama, btnVaciarCama, btnBack, btnAñadirCama;
	private List<Cama> camas;
	private JPanel pEste, pOeste, pCentro, pNorte, pSur;
	private JScrollPane scrollTabla; 
	private JTable tableCama;
	private CamaTableModel tableModel;
	private Persona usuario;
	private Paciente p;
	
	public VentanaCamas(List<Cama> camas, Persona usuarioP) {
		Context context = Context.getInstance();  
		this.p = context.getPaciente();
		this.camas = context.getCamas();
		this.usuario = usuarioP;
		Color color =  new Color(6,99,133);
		
		ImageIcon i = new ImageIcon("src/db/hospital.png");
		setIconImage(i.getImage());
		
		pEste = new JPanel();
		pOeste = new JPanel();
		pCentro = new JPanel(new GridLayout(2, 1));
		pNorte = new JPanel();
		pSur = new JPanel(new GridLayout(3, 1));
		
		getContentPane().add(pEste, BorderLayout.EAST);
		getContentPane().add(pOeste, BorderLayout.WEST);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		
        tableModel = new CamaTableModel(null);
        tableCama = new JTable(tableModel);
        scrollTabla = new JScrollPane(tableCama);
        
        //tableCama.setSelectionModel(ListSelectionModel.SINGLE_SELECTION);
        
        tableCama.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = tableCama.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String nCama = tableModel.getValueAt(filaSeleccionada, 0).toString();
                    String tipoCama = tableModel.getValueAt(filaSeleccionada, 2).toString();
				}
				
			}
		});
        
        btnAsignarCama = new JButton("Asignar");
		btnVaciarCama = new JButton("Vaciar");
		btnAñadirCama = new JButton("Añadir");
        pCentro.add(scrollTabla);
        pSur.add(btnAsignarCama);
        pSur.add(btnVaciarCama);
        pSur.add(btnAñadirCama);
        
        tableCama.getColumnModel().getColumn(0).setPreferredWidth(100); 
        tableCama.getColumnModel().getColumn(1).setPreferredWidth(100); 
        tableCama.getColumnModel().getColumn(2).setPreferredWidth(100); 
        
        
        
        for (Cama cama : camas) {
            Object[] fila = {cama.getNumCama(), cama.isOcupada(), cama.getTipoCama()};
            tableModel.addRow(fila);
        }
        
        
		ImageIcon iconBack = new ImageIcon(getClass().getResource("/recursos/icons8-back-25.png"));
        btnBack = new JButton(iconBack);
        btnBack.setBackground(color);
        btnBack.setPreferredSize(new Dimension(80, 25));
        
        pOeste.add(btnBack);
        
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
		
        btnAsignarCama.addActionListener((e) -> {
        	
        });

        
        
        btnAñadirCama.addActionListener((e) -> {
        	JFrame frameAñadirCama  = new JFrame("Añadir una nueva cama");
        	frameAñadirCama.setSize(400, 300);
        	frameAñadirCama.setLayout(new GridLayout(8, 2)); 
        	frameAñadirCama.getContentPane().setBackground(color);
            
            
            JLabel lblNumCama = new JLabel("Numero de Cama:");
            JTextField txtNumCama = new JTextField();
            JLabel lblOcupada = new JLabel("Esta Ocupada?:");
            JTextField txtOcupada = new JTextField();
            JLabel lblTipo = new JLabel("Tipo:");
            JTextField txtTipo = new JTextField();
            
            
            JButton btnAñadir = new JButton("Asignar Cama");
            btnAñadir.setBackground(color);
            btnAñadir.setForeground(Color.white);
            btnAñadir.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                	btnAñadir.setBackground(Color.white);
                	btnAñadir.setForeground(color);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	btnAñadir.setBackground(color);
                	btnAñadir.setForeground(Color.white);
                }
            });
            
            
            frameAñadirCama.add(lblNumCama);
            lblNumCama.setForeground(Color.WHITE);
            frameAñadirCama.add(txtNumCama);
            frameAñadirCama.add(lblOcupada);
            lblOcupada.setForeground(Color.WHITE);
            frameAñadirCama.add(txtOcupada);
            frameAñadirCama.add(lblTipo);
            lblTipo.setForeground(Color.WHITE);
            frameAñadirCama.add(txtTipo);
            
            frameAñadirCama.add(new JLabel()); // Label vac�o para llenar �ltimo grid
            frameAñadirCama.add(btnAñadir);
            
            
            btnAñadir.addActionListener(e2 -> {
                int numCama = Integer.parseInt(txtNumCama.getText());
                String respuesta = txtOcupada.getText().toLowerCase();
                
                //IAG (Claude) Inicio
                boolean ocupada;

                if (respuesta.equals("si")) {
                    ocupada = true;
                } else if (respuesta.equals("no")) {
                    ocupada = false;
                } else {
                    ocupada = false;  // Valor por defecto
                }
                
                // IAG (Claude) Fin
                
                String tipo = txtTipo.getText();
                
                if (numCama < 0 || respuesta.isEmpty() || tipo.isEmpty()) {
                    JOptionPane.showMessageDialog(frameAñadirCama, "Por favor, rellena todos los campos.");
                    return;
                }
                Cama nuevaCama = new Cama(numCama, ocupada, tipo);
                camas.add(nuevaCama);
//                context.guardarPaciente(nuevoPaciente);
//                tableCama.addRow(new Object[]{numCama, ocupada, tipo});

            	frameAñadirCama.dispose(); 
            });
            
            frameAñadirCama.setLocationRelativeTo(null);
            frameAñadirCama.setVisible(true);
        });
        
        
        
        btnVaciarCama.addActionListener((e) -> {
        	JLabel lblNumCama2 = new JLabel("Numero de Cama:");
            JTextField txtNumCama2 = new JTextField();
            JLabel lblOcupada2 = new JLabel("Esta Ocupada?:");
            JTextField txtOcupada2 = new JTextField();
            JLabel lblTipo2 = new JLabel("Tipo:");
            JTextField txtTipo2 = new JTextField();
            
            JFrame frameQuitarCama  = new JFrame("Vaciar cama ya no ocupada");
        	frameQuitarCama.setSize(400, 300);
        	frameQuitarCama.setLayout(new GridLayout(8, 2)); 
        	frameQuitarCama.getContentPane().setBackground(color);
            
        	int numCama2 = Integer.parseInt(txtNumCama2.getText());
            String respuesta2 = txtOcupada2.getText().toLowerCase();
            
            //IAG (Claude) Inicio
            boolean ocupada2;

            if (respuesta2.equals("si")) {
                ocupada2 = true;
            } else if (respuesta2.equals("no")) {
                ocupada2 = false;
            } else {
                ocupada2 = false;  // Valor por defecto
            }
            
            // IAG (Claude) Fin
            
            String tipo2 = txtTipo2.getText();
        	
        	if(ocupada2 == true) {
        		JOptionPane.showMessageDialog(frameQuitarCama, "No se puede vaciar la cama porque ya está vacia!");
        	} else {
        		ocupada2 = false;
        		JOptionPane.showMessageDialog(frameQuitarCama, "Has vaciado la cama con exito!");
        	}
        	
        
        	
        });
        
		setTitle("Camas");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	private void initTables() {
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
            JLabel result = new JLabel(value != null ? value.toString() : "");
            result.setHorizontalAlignment(JLabel.CENTER);
            return result;
        };
        
        tableCama.setDefaultRenderer(Object.class, cellRenderer);
        tableCama.setRowHeight(40);
        tableCama.getTableHeader().setReorderingAllowed(false);
        tableCama.getTableHeader().setResizingAllowed(false);
        tableCama.setAutoCreateRowSorter(true);
        tableCama.getColumnModel().getColumn(2).setPreferredWidth(400);
        
        
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(6, 99, 133));
        headerRenderer.setForeground(Color.WHITE); 
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(tableCama.getFont().deriveFont(java.awt.Font.BOLD));
        
        for (int i = 0; i < tableCama.getColumnModel().getColumnCount(); i++) {
            tableCama.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
	}
	
	
}
