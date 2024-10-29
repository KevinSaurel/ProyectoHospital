package clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Context {
    private static Context instance;  // Singleton instance
    
    private ArrayList<Doctor> medicos = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Enfermero> enfermeros = new ArrayList<>();

    // Private constructor for Singleton pattern
    private Context() {
        loadDoctors();
        loadPatients();
        loadNurses();
    }
    
    // Public method to get the singleton instance
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    // Method to load patients from file
    private void loadPatients() {
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
                List<Historial> historialEntries = parseHistorialEntries(historialEntriesSerialized, dateFormat);

                Paciente nuevoPaciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoPaciente, historialEntries);
                pacientes.add(nuevoPaciente);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }
    }

    // Parse historial entries for each patient
    private List<Historial> parseHistorialEntries(String serialized, SimpleDateFormat dateFormat) {
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
                    System.out.println("Fecha inválida en historial: " + parts[1]);
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

    // Method to load doctors from file
    private void loadDoctors() {
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

    // Load nurses (placeholder, to be implemented if needed)
    private void loadNurses() {
        // Implementation for loading nurses from file if required
    }

    // Getter methods for accessing data
    public ArrayList<Doctor> getMedicos() {
        return medicos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Enfermero> getEnfermeros() {
        return enfermeros;
    }
}
