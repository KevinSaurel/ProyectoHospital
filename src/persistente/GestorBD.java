package persistente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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

	/*public void crearBBDD() {
	//Sólo se crea la BBDD si la propiedad initBBDD es true.
	if (properties.get("createBBDD").equals("true")) {
		//La base de datos tiene 5 tablas: Administrador, Camas, Citas, Pacientes, Doctores
		 * @todo crear las peticiones sql 
		String sql1 = "CREATE TABLE IF NOT EXISTS Administrador (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " editorial TEXT NOT NULL,\n"
                + " nombre TEXT NOT NULL,\n"
                + " email TEXT NOT NULL,\n"
                + " UNIQUE(nombre, email));";

		String sql2 = "CREATE TABLE IF NOT EXISTS Camas (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " editorial TEXT NOT NULL,\n"
                + " titulo TEXT UNIQUE NOT NULL\n"
                + ");";

		String sql3 = "CREATE TABLE IF NOT EXISTS Citas (\n"
                + " id_comic INTEGER,\n"
                + " id_personaje INTEGER,\n"
                + " PRIMARY KEY(id_comic, id_personaje)\n"
                + " FOREIGN KEY(id_comic) REFERENCES Comic(id) ON DELETE CASCADE\n"
                + " FOREIGN KEY(id_personaje) REFERENCES Personaje(id) ON DELETE CASCADE\n"
                + ");";
		String sql4="CREATE TABLE IF NOT EXISTS Pacientes";
		String sql5="CREATE TABLE IF NOT EXISTS Doctores";
		
        //Se abre la conexión y se crea un PreparedStatement para crer cada tabla
		//Al abrir la conexión, si no existía el fichero por defecto, se crea.
		try (Connection con = DriverManager.getConnection(connectionString);
		     PreparedStatement pStmt1 = con.prepareStatement(sql1);
			 PreparedStatement pStmt2 = con.prepareStatement(sql2);
			 PreparedStatement pStmt3 = con.prepareStatement(sql3)) {
			
			//Se ejecutan las sentencias de creación de las tablas
	        if (!pStmt1.execute() && !pStmt2.execute() && !pStmt3.execute()) {
	        	logger.info("Se han creado las tablas");
	        }
		} catch (Exception ex) {
			logger.warning(String.format("Error al crear las tablas: %s", ex.getMessage()));
			}
		}
	}*/
}