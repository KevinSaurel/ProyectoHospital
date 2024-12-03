package persistente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
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
import domain.Cita;
import domain.Doctor;
import domain.Historial;
import domain.Paciente;
import domain.Persona;

public class GestorBD {
	private final String PROPERTIES_FILE = "bd/app.properties";
	private final String CSV_DOCOTORES = "bd/doctores.csv";
	private final String CSV_PACIENTES = "bd/pacientes.csv";
	private final String CSV_CAMAS = "bd/camas.csv";
	private final String CSV_CITAS = "bd/citas.csv";
	private final String CSV_ADMINISTRADORES = "bd/Administradores.csv";

	private final String LOG_FOLDER = "resources/log";

	private Properties properties;
	private String driverName;
	private String databaseFile;
	private String connectionString;

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
	//Sólo se crea la BBDD si la propiedad initBBDD es true.
	if (properties.get("createBBDD").equals("true")) {
		//La base de datos tiene 5 tablas: Administrador, Camas, Citas, Pacientes, Doctores
		 // @todo crear las peticiones sql 
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
                + " paciente_id INTEGER NOT NULL,\n"
                + " doctor_id INTEGER NOT NULL,\n"
                + " fecha_hora DATETIME NOT NULL,\n"
                + " FOREIGN KEY(paciente_id) REFERENCES paciente(id),\n"
                + " FOREIGN KEY(doctor_id) REFERENCES doctor(id),\n"
                + " UNIQUE(paciente_id, doctor_id, fecha_hora)\n"
                + ");";
        //Se abre la conexión y se crea un PreparedStatement para crer cada tabla
		//Al abrir la conexión, si no existía el fichero por defecto, se crea.
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt1 = con.prepareStatement(sql1);
			 PreparedStatement pStmt2 = con.prepareStatement(sql2);
			 PreparedStatement pStmt3 = con.prepareStatement(sql3);
			 PreparedStatement pStmt4 = con.prepareStatement(sql4);
			 PreparedStatement pStmt5 = con.prepareStatement(sql5)){
			
			//Se ejecutan las sentencias de creación de las tablas
	        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute()) {
	        	logger.info("Se han creado las tablas");
	        }
		} catch (Exception ex) {
			logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
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
			
	        //Se abre la conexión y se crea un PreparedStatement para borrar cada tabla
			try (Connection con = DriverManager.getConnection(connectionString);
			     PreparedStatement pStmt1 = con.prepareStatement(sql1);
				 PreparedStatement pStmt2 = con.prepareStatement(sql2);
				 PreparedStatement pStmt3 = con.prepareStatement(sql3);
				 PreparedStatement pStmt4 = con.prepareStatement(sql4);
				 PreparedStatement pStmt5 = con.prepareStatement(sql5)) {
				
				//Se ejecutan las sentencias de borrado de las tablas
		        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute()&& !pStmt4.execute()&& !pStmt5.execute()) {
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
	    private void insertarCita(List<Cita> citas) throws  SQLException{
	    	String sql="INSERT INTO cita(id,paciente_id,doctor_id,fecha_hora) VALUES(?,?,?)";
	        try (Connection con = DriverManager.getConnection(connectionString);
		             PreparedStatement pstmt = con.prepareStatement(sql)) {
	        	 for (Cita cita : citas) {
		                pstmt.setInt(1, cita.getPaciente().getCodigoPaciente() );
		                pstmt.setInt(2, insertarPersona(cita.getDoctor()) );
		                pstmt.setDate(3, new java.sql.Date(cita.getFechaHora().getTime()));
		                pstmt.executeUpdate();
	        }
	        	 logger.info("Patient cita inserted successfully");
	   
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

	   private List<Doctor> cargarMedicosCsv() {
		   List<Doctor>medicos = new ArrayList();
	        try (BufferedReader br = new BufferedReader(new FileReader("src/db/doctores.csv"))) {
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
	    private List<Administrador> cargarAdmins() {
	    	List<Administrador> admins = new ArrayList();
	        try (BufferedReader br = new BufferedReader(new FileReader("src/db/Administradores.csv"))) {
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
	    private List<Paciente> cargarPacientes() {
	    	List<Paciente> pacientes = new ArrayList();
	    	
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        try (BufferedReader br = new BufferedReader(new FileReader("src/db/pacientes.csv"))) {
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
	    
}