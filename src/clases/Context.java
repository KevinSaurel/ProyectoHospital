package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Context {
    private static Context instance;  
    
    private ArrayList<Doctor> medicos = new ArrayList<>();
    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private List<Enfermero> enfermeros = new ArrayList<>();
    private ArrayList<Administrador> administradores = new ArrayList<>();

   
    private Context() {
        cargarMedicos();
        cargarPacientes();
        cargarAdmins();
       
    }
    
    // creo un unico instace de esta classe
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

   
    private void cargarPacientes() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader br = new BufferedReader(new FileReader("src/recursos/pacientes.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                
                String contrasena = parts[0];
                String nombre = parts[1];
                String apellido = parts[2];
                int edad = Integer.parseInt(parts[3]);
                String ubicacion = parts[4];
                int codigoPaciente = Integer.parseInt(parts[5]);

                String historialEntriesSerialized = parts[6];
                List<Historial> historialEntries = creaHistorial(historialEntriesSerialized, dateFormat);

                Paciente nuevoPaciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoPaciente, historialEntries);
                pacientes.add(nuevoPaciente);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }
    }
    private void cargarAdmins() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/recursos/Administradores.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] parts = linea.split(",");
                
                String contrasena = parts[0];
                String nombre = parts[1];
                String apellido = parts[2];
                int edad = Integer.parseInt(parts[3]);
                String ubicacion = parts[4];
                String turno = parts[5];

                
                

                Administrador nuevoAdmin = new Administrador(contrasena, nombre, apellido, edad, ubicacion, turno);
                administradores.add(nuevoAdmin);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los admins: " + e.getMessage());
        }
    }
    
    private List<Historial> creaHistorial(String serialized, SimpleDateFormat dateFormat) {
        List<Historial> historialEntries = new ArrayList<>();
        String[] entries = serialized.split(";");
        
        for (String entry : entries) {
            String[] parts = entry.split("\\|");
            if (parts.length == 2) {
                String causa = parts[0];
                java.util.Date fecha;
                try {
                    fecha = dateFormat.parse(parts[1]);
                } catch (ParseException e) {
                    System.out.println("Fecha inv�lida en historial: " + parts[1]);
                    continue;
                }
                Historial historial = new Historial();
                historial.setCausa(causa);
                historial.setFecha(fecha);
                historialEntries.add(historial);
            }
        }
        
        return historialEntries;
    }

    private void cargarMedicos() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/recursos/doctores.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, ",");
                String contrasena = st.nextToken();
                String nombre = st.nextToken();
                String apellido = st.nextToken();
                int edad = Integer.parseInt(st.nextToken());
                String ubicacion = st.nextToken();
                String especialidad = st.nextToken();
                String horario = st.nextToken();
                
                Doctor nuevoDoctor = new Doctor(contrasena, nombre, apellido, edad, ubicacion, especialidad, horario);
                medicos.add(nuevoDoctor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void guardarPaciente(Paciente paciente) {
    	try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/recursos/pacientes.csv"))){
    		StringBuilder linea = new StringBuilder();

    		linea.append(paciente.getContrasena()).append(",");
    		linea.append(paciente.getNombre()).append(",");
    		linea.append(paciente.getApellido()).append(",");
    		linea.append(paciente.getEdad()).append(",");
    		linea.append(paciente.getUbicacion()).append(",");
    		linea.append(paciente.getCodigoPaciente()).append(",");
    		
    		 StringBuilder historial = new StringBuilder();
    		 List<Historial> lista = new ArrayList<>();
    		    

    		 
    		 //linea.append(lista);
    		 bw.write(linea.toString());
    	        bw.newLine();  
    	}catch (IOException e) {
            System.out.println("Error al guardar paciente : " + e.getMessage());
        }
    			
    }
    public void guardarHistorial(Paciente paciente, Historial historial) {
        // Encontrar al paciente y agregarle el historial en memoria
        for (Paciente p : pacientes) {
            if (paciente.equals(p)) {
                p.getHistorialPaciente().add(historial);
                break;
            }
        }

        // Reescribir todos los datos en pacientes.csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/recursos/pacientes.csv", false))) { 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (Paciente p : pacientes) {
                StringBuilder linea = new StringBuilder();
                linea.append(p.getContrasena()).append(",");
                linea.append(p.getNombre()).append(",");
                linea.append(p.getApellido()).append(",");
                linea.append(p.getEdad()).append(",");
                linea.append(p.getUbicacion()).append(",");
                linea.append(p.getCodigoPaciente()).append(",");

                // Serializar historial del paciente
                StringBuilder historialSerializado = new StringBuilder();
                for (Historial h : p.getHistorialPaciente()) {
                    historialSerializado.append(h.getCausa()).append("|").append(dateFormat.format(h.getFecha())).append(";");
                }

                
                linea.append(historialSerializado);

                // Escribir la l�nea completa para este paciente
                bw.write(linea.toString());
                bw.newLine();  // Mover a la siguiente l�nea para el pr�ximo paciente
            }

            System.out.println("Archivo pacientes.csv reescrito correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el historial del paciente: " + e.getMessage());
        }
    }

    	
    		

   
    public ArrayList<Doctor> getMedicos() {
        return medicos;
    }
    public ArrayList<Administrador> getAdministrador() {
        return administradores;
    }

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Enfermero> getEnfermeros() {
        return enfermeros;
    }
}

	//FUENTE-EXTERNA
	//URL: (https://stackoverflow.com/questions/65033185/how-to-capture-the-field-values-in-the-csv-file-using-bufferedreader)
	//SIN-CAMBIOS ó ADAPTADO (he usado el buffered reader y parser del stack overflow  )
	//IAG (herramienta:chatgpt )
	//SIN CAMBIOS ó ADAPTADO (me he ayudado con chatgpt para solucionar errores y dudas)
	//