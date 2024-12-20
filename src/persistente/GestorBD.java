package persistente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import domain.Administrador;
import domain.Cama;
import domain.Cita;
import domain.Doctor;
import domain.Historial;
import domain.Paciente;
import domain.Persona;


public class GestorBD {
	private final String PROPERTIES_FILE = "resources/config/app.properties";
	private final String CSV_DOCOTORES = "resources/data/doctores.csv";
	private final String CSV_PACIENTES = "resources/data/pacientes.csv";
	private final String CSV_CAMAS = "resources/data/camas.csv";
	private final String CSV_CITAS = "resources/data/citas.csv";
	private final String CSV_ADMINISTRADORES = "resources/data/Administradores.csv";

	private final String LOG_FOLDER = "resources/log";

	private Properties properties;
	private String driverName;
	private String databaseFile;
	private String connectionString;
	public ArrayList<Cita>citas;


	private static Logger logger = Logger.getLogger(GestorBD.class.getName());

	public GestorBD() {
		try (FileInputStream fis = new FileInputStream("resources/config/logger.properties")) {
			LogManager.getLogManager().readConfiguration(fis);

			properties = new Properties();
			properties.load(new FileReader(PROPERTIES_FILE));

			driverName = properties.getProperty("driver");
			databaseFile = properties.getProperty("file");
			connectionString = properties.getProperty("connection");

			File dir = new File(LOG_FOLDER);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			// Crear carpeta para la BBDD si no existe
			dir = new File(databaseFile.substring(0, databaseFile.lastIndexOf("/")));

			if (!dir.exists()) {
				dir.mkdirs();
			}

			// Cargar el diver SQLite
			Class.forName(driverName);
		} catch (Exception ex) {
			logger.warning(String.format("Error al cargar el driver de BBDD: %s", ex.getMessage()));
		}
	}

	public void crearBBDD() {
	    // Check if the database creation is enabled
	    if (properties.get("createBBDD").equals("true")) {

	        // SQL statements for creating tables
	        String sql1 = "CREATE TABLE IF NOT EXISTS persona (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " contrasena TEXT NOT NULL,\n"
	                + " nombre TEXT NOT NULL,\n"
	                + " apellido TEXT NOT NULL,\n"
	                + " edad INTEGER NOT NULL,\n"
	                + " ubicacion TEXT NOT NULL\n"
	                + ");";

	        String sql2 = "CREATE TABLE IF NOT EXISTS paciente (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " codigo_paciente INTEGER NOT NULL UNIQUE,\n"
	                + " persona_id INTEGER NOT NULL,\n"
	                + " FOREIGN KEY(persona_id) REFERENCES persona(id)\n"
	                + ");";

	        String sql3 = "CREATE TABLE IF NOT EXISTS doctor (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " especialidad TEXT NOT NULL,\n"
	                + " horario TEXT NOT NULL,\n"
	                + " persona_id INTEGER NOT NULL,\n"
	                + " FOREIGN KEY(persona_id) REFERENCES persona(id)\n"
	                + ");";

	        String sql4 = "CREATE TABLE IF NOT EXISTS historial (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " causa TEXT NOT NULL,\n"
	                + " fecha DATE NOT NULL,\n"
	                + " medico_id INTEGER NOT NULL,\n"
	                + " paciente_id INTEGER NOT NULL,\n"
	                + " FOREIGN KEY(medico_id) REFERENCES doctor(id),\n"
	                + " FOREIGN KEY(paciente_id) REFERENCES paciente(id)\n"
	                + ");";

	        String sql5 = "CREATE TABLE IF NOT EXISTS cita (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " codigo_paciente INTEGER NOT NULL,\n"
	                + " nombre_doctor TEXT NOT NULL,\n"
	                + " fecha_hora TEXT NOT NULL,\n"
	                + " FOREIGN KEY(codigo_paciente) REFERENCES paciente(codigo_paciente)\n"
	                + ");";

	        String sql6 = "CREATE TABLE IF NOT EXISTS cama (\n"
	                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
	                + " num_cama INTEGER NOT NULL,\n"
	                + " ocupada INTEGER NOT NULL,\n"
	                + " tipo_cama TEXT NOT NULL,\n"
	                + " paciente_id INTEGER,\n"
	                + " FOREIGN KEY(paciente_id) REFERENCES paciente(id)\n"
	                + ");";

	        // Execute table creation statements
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pStmt1 = con.prepareStatement(sql1);
	             PreparedStatement pStmt2 = con.prepareStatement(sql2);
	             PreparedStatement pStmt3 = con.prepareStatement(sql3);
	             PreparedStatement pStmt4 = con.prepareStatement(sql4);
	             PreparedStatement pStmt5 = con.prepareStatement(sql5);
	             PreparedStatement pStmt6 = con.prepareStatement(sql6)) {

	            pStmt1.executeUpdate();
	            pStmt2.executeUpdate();
	            pStmt3.executeUpdate();
	            pStmt4.executeUpdate();
	            pStmt5.executeUpdate();
	            pStmt6.executeUpdate();

	            logger.info("Base de datos y tablas creadas correctamente.");

	        } catch (SQLException ex) {
	            logger.warning("Error al crear las tablas: " + ex.getMessage());
	        }
	    }
	}

	public void borrarBBDD() {
		if (properties.get("deleteBBDD").equals("true")) {	
			String sql1 = "DROP TABLE IF EXISTS persona;";
			String sql2 = "DROP TABLE IF EXISTS paciente;";
			String sql3 = "DROP TABLE IF EXISTS doctor;";
			String sql4 = "DROP TABLE IF EXISTS historial";
			String sql5 = "DROP TABLE IF EXISTS cita;";
			String sql6 = "DROP TABLE IF EXISTS camas;";
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5);
				 PreparedStatement pStmt6 = con.prepareStatement(sql6)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute()&& !pStmt4.execute()&& !pStmt5.execute()&& !pStmt6.execute()) {
		        	logger.info("Se han borrado las tablas");
		        }
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar las tablas: %s", ex.getMessage()));
			}
			
			try {
				//Se borra físicamente el fichero de la BBDD
				Files.delete(Paths.get(databaseFile));
				logger.info("Se ha borrado el fichero de la BBDD");
			} catch (Exception ex) {
				logger.warning(String.format("Error al borrar el fichero de la BBDD: %s", ex.getMessage()));
			}
		}
	}
	 public void insertarDoctores(List<Doctor> doctores) {
	        String sql = "INSERT INTO doctor (especialidad, horario, persona_id) VALUES (?, ?, ?)";
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            for (Doctor doctor : doctores) {
	                int personaId = insertarPersona(doctor);
	                pstmt.setString(1, doctor.getEspecialidad());
	                pstmt.setString(2, doctor.getHorario());
	                pstmt.setInt(3, personaId);
	                pstmt.executeUpdate();
	            }

	            logger.info("Doctors inserted successfully");

	        } catch (SQLException e) {
	            logger.warning("Error inserting doctors: " + e.getMessage());
	        }
	    }
	 public List<Cita> getCitas() {
	        List<Cita> citas = new ArrayList<>();
	        String sql = "SELECT c.id, c.paciente_id, c.doctor_id, c.fecha_hora, " +
	                     "p.contrasena AS paciente_contrasena, p.nombre AS paciente_nombre, " +
	                     "p.apellido AS paciente_apellido, p.edad AS paciente_edad, p.ubicacion AS paciente_ubicacion, " +
	                     "d.contrasena AS doctor_contrasena, d.nombre AS doctor_nombre, " +
	                     "d.apellido AS doctor_apellido, d.edad AS doctor_edad, d.ubicacion AS doctor_ubicacion, " +
	                     "d.especialidad AS doctor_especialidad, d.horario AS doctor_horario " +
	                     "FROM cita c " +
	                     "JOIN paciente p ON c.paciente_id = p.id " +
	                     "JOIN doctor d ON c.doctor_id = d.id";

	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pStmt = con.prepareStatement(sql)) {

	            // Execute the query and get the result set
	            ResultSet rs = pStmt.executeQuery();
	            while (rs.next()) {
	                // Get the patient and doctor details
	                Paciente paciente = new Paciente(
	                        rs.getString("paciente_contrasena"),
	                        rs.getString("paciente_nombre"),
	                        rs.getString("paciente_apellido"),
	                        rs.getInt("paciente_edad"),
	                        rs.getString("paciente_ubicacion"),
	                        rs.getInt("paciente_id"),
	                        null // Historial entries can be fetched if needed
	                );

	                Doctor doctor = new Doctor(
	                        rs.getString("doctor_contrasena"),
	                        rs.getString("doctor_nombre"),
	                        rs.getString("doctor_apellido"),
	                        rs.getInt("doctor_edad"),
	                        rs.getString("doctor_ubicacion"),
	                        rs.getString("doctor_especialidad"),
	                        rs.getString("doctor_horario")
	                );

	                // Convert the appointment date from the result set
	                Date fecha = rs.getDate("fecha_hora");

	                // Create a Cita object and add it to the list
	                Cita cita = new Cita(paciente, doctor, fecha);
	                citas.add(cita);
	            }

	            logger.info(String.format("Se han recuperado %d citas", citas.size()));
	        } catch (SQLException e) {
	            logger.warning(String.format("Error al recuperar las citas: %s", e.getMessage()));
	        }

	        return citas;
	    }

	    public void insertarPacientes(List<Paciente> pacientes) {
	        String sql = "INSERT INTO paciente (codigo_paciente, persona_id) VALUES (?, ?)";
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            for (Paciente paciente : pacientes) {
	                int personaId = insertarPersona(paciente);
	                pstmt.setInt(1, paciente.getCodigoPaciente());
	                pstmt.setInt(2, personaId);
	                pstmt.executeUpdate();

	                insertarHistorial(paciente);
	            }

	            logger.info("Patients inserted successfully");

	        } catch (SQLException e) {
	            logger.warning("Error inserting patients: " + e.getMessage());
	        }
	    }

	    private int insertarPersona(Persona persona) throws SQLException {
	        String sql = "INSERT INTO persona (contrasena, nombre, apellido, edad, ubicacion) VALUES (?, ?, ?, ?, ?)";
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	            pstmt.setString(1, persona.getContrasena());
	            pstmt.setString(2, persona.getNombre());
	            pstmt.setString(3, persona.getApellido());
	            pstmt.setInt(4, persona.getEdad());
	            pstmt.setString(5, persona.getUbicacion());
	            pstmt.executeUpdate();

	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1);
	                }
	                throw new SQLException("Creating persona failed, no ID obtained.");
	            }
	        }
	    }

	    private void insertarHistorial(Paciente paciente) throws SQLException {
	        String sql = "INSERT INTO historial (causa, fecha, medico_id, paciente_id) VALUES (?, ?, ?, ?)";
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            for (Historial historial : paciente.getHistorialPaciente()) {
	                pstmt.setString(1, historial.getCausa());
	                pstmt.setDate(2, new java.sql.Date(historial.getFecha().getTime()));
	                pstmt.setString(3, historial.getMedico().getNombre());
	                pstmt.setInt(4, paciente.getCodigoPaciente());
	                pstmt.executeUpdate();
	            }

	            logger.info("Patient histories inserted successfully");
	        }
	    }
	    public void insertarCita() throws SQLException {
	        // Load existing citas
	        citas = (ArrayList<Cita>) cargarCitas();
	        
	        // SQL query for inserting data into the cita table
	        String sql = "INSERT INTO cita(codigo_paciente, nombre_doctor, fecha_hora) VALUES(?, ?, ?)";
	        
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {
	             
	            for (Cita cita : citas) {
	                // Set the prepared statement parameters
	                pstmt.setInt(1, cita.getPaciente().getCodigoPaciente()); // Use codigo_paciente
	                pstmt.setString(2, cita.getDoctor().getNombre()); // Use doctor's name
	                pstmt.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cita.getFechaHora())); // Format date
	                
	                // Execute the statement
	                pstmt.executeUpdate();
	            }
	            
	            logger.info("Citas inserted successfully.");
	        } catch (SQLException e) {
	            logger.severe("Error inserting citas: " + e.getMessage());
	            throw e;
	        }
	    }


	    public void insertarAdministradores(List<Administrador> administradores) {
	        String sql = "INSERT INTO administrador (persona_id, turno) VALUES (?, ?)";
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            for (Administrador admin : administradores) {
	                int personaId = insertarPersona(admin);
	                pstmt.setInt(1, personaId);
	                pstmt.setString(2, admin.getTurno());
	                pstmt.executeUpdate();
	            }

	            logger.info("Administrators inserted successfully");

	        } catch (SQLException e) {
	            logger.warning("Error inserting administrators: " + e.getMessage());
	        }
	    }

	   public List<Doctor> cargarMedicosCsv() {
		   List<Doctor>medicos = new ArrayList();
	        try (BufferedReader br = new BufferedReader(new FileReader("resources/data/doctores.csv"))) {
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
	        return medicos;
	    }
	   private List<Historial> cargarHistorialCsv(String serialized, SimpleDateFormat dateFormat) {
	        List<Historial> historiales = new ArrayList<>();
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
	                historiales.add(historial);
	            }
	        }
	        
	        return historiales;
	    }
	    public List<Administrador> cargarAdmins() {
	    	List<Administrador> admins = new ArrayList();
	        try (BufferedReader br = new BufferedReader(new FileReader("resources/data/Administradores.csv"))) {
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
	                admins.add(nuevoAdmin);
	            }
	        } catch (IOException e) {
	            System.out.println("Error al cargar los admins: " + e.getMessage());
	        }
	        return admins;
	    }
	    public List<Paciente> cargarPacientes() {
	    	List<Paciente> pacientes = new ArrayList();
	    	
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try (BufferedReader br = new BufferedReader(new FileReader("resources/data/pacientes.csv"))) {
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
	                List<Historial> historialEntries = cargarHistorialCsv(historialEntriesSerialized, dateFormat);

	                Paciente nuevoPaciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoPaciente, historialEntries);
	                pacientes.add(nuevoPaciente);
	            }
	        } catch (IOException e) {
	            System.out.println("Error al cargar los pacientes: " + e.getMessage());
	        }
	        return pacientes;
	    }
	    private static List<Historial> cargarHistorialCsv2(String serializedHistorial, SimpleDateFormat dateFormat) {
	        List<Historial> historial = new ArrayList<>();
	        if (serializedHistorial.endsWith(";")) {
	            serializedHistorial = serializedHistorial.substring(0, serializedHistorial.length() - 1);
	        }
	        String[] entries = serializedHistorial.split(":");
	        for (String entry : entries) {
	            String[] parts = entry.split("\\|");
	            if (parts.length == 2) {
	                try {
	                    String description = parts[0];
	                    java.util.Date utilDate = dateFormat.parse(parts[1]);
	                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	                    historial.add(new Historial(description, sqlDate));
	                } catch (ParseException e) {
	                    System.err.println("Error parsing date: " + parts[1]);
	                    e.printStackTrace();
	                }
	            }
	        }
	        return historial;
	    }
	    public List<Cita> cargarCitas() {
	         citas = new ArrayList<>();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        try (BufferedReader br = new BufferedReader(new FileReader("resources/data/citas.csv"))) {
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                String[] parts = linea.split(",");

	                // Validate that the parts array is correctly structured before parsing
	                if (parts.length < 14) {
	                    System.err.println("Invalid line format: " + linea);
	                    continue;  // Skip this line if it's invalid
	                }

	                // Parse the patient details
	                String contrasena = parts[0];
	                String nombre = parts[1];
	                String apellido = parts[2];
	                int edad = parseInt(parts[3], "Edad");
	                String ubicacion = parts[4];
	                int codigoPaciente = parseInt(parts[5], "Codigo Paciente");

	                // Handle medical history
	                String historialEntriesSerialized = parts[6];
	                List<Historial> historialEntries = cargarHistorialCsv2(historialEntriesSerialized, dateFormat);
	                Paciente nuevoPaciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoPaciente, historialEntries);

	                // Parse the doctor details
	                String contrasenaM = parts[7];
	                String nombreM = parts[8];
	                String apellidoM = parts[9];
	                int edadM = parseInt(parts[10], "Edad Medico");
	                String ubicacionM = parts[11];
	                String especialidad = parts[12];
	                String horario = parts[13];
	                Doctor nuevoMedico = new Doctor(contrasenaM, nombreM, apellidoM, edadM, ubicacionM, especialidad, horario);

	                // **Correctly parse the appointment date from the last column** (assuming it's parts[13] for the appointment date)
	                Date fecha = null;
	                try {
	                    // Parse the date as java.util.Date first
	                    java.util.Date utilDate = dateFormat.parse(parts[13]);
	                    
	                    // Convert to java.sql.Date
	                    fecha = new java.sql.Date(utilDate.getTime());
	                } catch (ParseException e) {
	                    System.out.println("Fecha invalida en historial: " + parts[13]);
	                    continue;  // Skip this line if the date is invalid
	                }

	                // Create the appointment
	                Cita cita = new Cita(nuevoPaciente, nuevoMedico, fecha);
	                citas.add(cita);
	            }
	        } catch (IOException e) {
	            System.out.println("Error al cargar los pacientes: " + e.getMessage());
	        }
	        return citas;
	    }
	    public void insertarCamas() throws SQLException {
	    	List<Cama> camas=new ArrayList<>();
	        camas = (ArrayList<Cama>) cargarCamas(); // Assuming you have a method to load beds
	        String sql = "INSERT INTO cama (num_cama, ocupada, tipo_cama, paciente_id) VALUES (?, ?, ?, ?)";
	        
	        try (Connection con = DriverManager.getConnection(connectionString);
	             PreparedStatement pstmt = con.prepareStatement(sql)) {
	            
	            for (Cama cama : camas) {
	                pstmt.setInt(1, cama.getNumCama());
	                pstmt.setBoolean(2, cama.isOcupada());
	                pstmt.setString(3, cama.getTipoCama());
	                
	                // Check if paciente is null before trying to get its ID
	                if (cama.getPaciente() != null) {
	                    pstmt.setInt(4, cama.getPaciente().getCodigoPaciente());
	                } else {
	                    pstmt.setNull(4, java.sql.Types.INTEGER);
	                }
	                
	                pstmt.executeUpdate();
	            }
	            
	            logger.info("Beds inserted successfully");
	        } catch (SQLException e) {
	            System.out.println("Error al cargar los camas: " + e.getMessage());
	            throw e;
	        }
	    }
	    public List<Cama> cargarCamas() {
	    	List<Cama> camas=new ArrayList<>();

	        try (BufferedReader br = new BufferedReader(new FileReader("resources/data/camas.csv"))) {
	            String linea;
	            while ((linea = br.readLine()) != null) {
	                String[] parts = linea.split(",");

	                // Validate that the parts array is correctly structured before parsing
	                if (parts.length < 5) {
	                    System.err.println("Invalid line format: " + linea);
	                    continue; // Skip this line if it's invalid
	                }

	                // Parse the bed details
	                int numCama = parseInt(parts[0], "Número de Cama");
	                boolean ocupada = Boolean.parseBoolean(parts[1]);
	                String tipoCama = parts[2];

	                // Parse patient details (if the bed is occupied)
	                Paciente paciente = null;
	                if (ocupada) {
	                    String contrasena = parts[3];
	                    String nombre = parts[4];
	                    String apellido = parts[5];
	                    int edad = parseInt(parts[6], "Edad Paciente");
	                    String ubicacion = parts[7];
	                    int codigoPaciente = parseInt(parts[8], "Codigo Paciente");

	                    // Create an empty historial for the patient
	                    List<Historial> historialEntries = new ArrayList<>();

	                    paciente = new Paciente(contrasena, nombre, apellido, edad, ubicacion, codigoPaciente, historialEntries);
	                }

	                // Create the bed
	                Cama cama = new Cama(numCama, ocupada, tipoCama, paciente);
	                camas.add(cama);
	            }
	        } catch (IOException e) {
	            System.out.println("Error al cargar las camas: " + e.getMessage());
	        }
	        return camas;
	    }
	 // Helper method to parse integer values with error handling
	    private int parseInt(String value, String fieldName) {
	        try {
	            return Integer.parseInt(value);
	        } catch (NumberFormatException e) {
	            System.err.println("Error al convertir el campo '" + fieldName + "' a entero: " + value);
	            return -1;  // Return a default value or handle the error
	        }
	    }

}