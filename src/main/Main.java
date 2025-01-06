package main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.SwingUtilities;

import domain.Administrador;
import domain.Cama;
import domain.Cita;
import domain.Doctor;
import domain.Paciente;
import domain.Persona;
import gui.VentanaSeleccion;
import persistente.GestorBD;

public class Main {
    public static void main(String[] args) {
//       try {
		//System.out.println(Arrays.toString(getResourceListing(Main.class,"resources")));
//	} catch (URISyntaxException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
       System.out.println();
       
       GestorBD gestorBD = new GestorBD();
      
       
       
       gestorBD.crearBBDD();
//    	   List<Persona> personas = gestorBD.cargarPersonasCsv();
//    	   for(Persona p : personas) {
//
//               gestorBD.insertarPersona(p);
//    	   }
//           List<Doctor> doctores = gestorBD.cargarMedicosCsv();
//           gestorBD.insertarDoctores(doctores);
//           
//           List<Paciente> pacientes = gestorBD.cargarPacientes();
//           gestorBD.insertarPacientes(pacientes);
//           
////           List<Administrador> administradores = gestorBD.cargarAdmins();
////           gestorBD.insertarAdministradores(administradores);
////           
//           List<Cita> citas = gestorBD.cargarCitas();
//           gestorBD.insertarCita();
//           
//           List<Cama> camas = gestorBD.cargarCamas();
//           gestorBD.insertarCamas();
	   
	   // Retrieve citas to verify
	   List<Cita> citasRecuperadas = gestorBD.getCitas();
	   System.out.println("Número de citas recuperadas: " + citasRecuperadas.size());
    	
    	
    	
        SwingUtilities.invokeLater(() -> {
            VentanaSeleccion ventanaHistorial = new VentanaSeleccion( gestorBD);
            ventanaHistorial.setVisible(true);
          
        });
    }
    static String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
          /* A file path: easy enough */
          return new File(dirURL.toURI()).list();
        } 

        if (dirURL == null) {
          /* 
           * In case of a jar file, we can't actually find a directory.
           * Have to assume the same jar as clazz.
           */
          String me = clazz.getName().replace(".", "/")+".class";
          dirURL = clazz.getClassLoader().getResource(me);
        }
        
        if (dirURL.getProtocol().equals("jar")) {
          /* A JAR path */
          String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
          JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
          Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
          Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
          while(entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if (name.startsWith(path)) { //filter according to the path
              String entry = name.substring(path.length());
              int checkSubdir = entry.indexOf("/");
              if (checkSubdir >= 0) {
                // if it is a subdirectory, we just return the directory name
                entry = entry.substring(0, checkSubdir);
              }
              result.add(entry);
            }
          }
          return result.toArray(new String[result.size()]);
        } 
          
        throw new UnsupportedOperationException("Cannot list files for URL "+dirURL);
    }
}
    