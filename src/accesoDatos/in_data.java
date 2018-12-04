package accesoDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/*
 *Creado por Elias Peria�ez
 *17 sept. 2018
 *Como parte del proyecto Jaime_Leer_Archivos
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (M�s informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Peria�ez
 *17 sept. 2018
 *As part of the project Jaime_Leer_Archivos
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */

public class in_data {

	private File file;
	private InputStream stream;

	public in_data(String file) throws FileNotFoundException {
		this.file = new File(file);
		this.stream = new FileInputStream(this.file);
	
	}

	public HashMap<String, String> leer() {
		HashMap<String, String> result = new HashMap<String, String>();

		result.put("Ruta del archivo", file.getAbsolutePath());
		result.put("Peso del archivo", Long.toString(file.length()) + " bytes");
		result.put("Datos del archivo", "");
		try {
			int i = stream.read();

			while (i != -1) {
				result.put("Datos del archivo", result.get("Datos del archivo") + (char) i);
				i = stream.read();
				System.out.println(result);
			}
		} catch (IOException e ) {
			e.printStackTrace();
		}

		return result;

	}

	public Properties startupConfig() {

		Properties prop = new Properties();

		try {
			prop.load(stream);

		} catch (FileNotFoundException e) {
			System.err.println("Error archivo de configuracion no encontrado");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Error el archivo de configuracion esta corrupto o no se pudieron leer los datos");
			System.exit(-1);
		}

		return prop;

	}

	public void destroyStream() {
		try {
			this.stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFile(String file) {
		File config = new File(file);
		this.file = config;
	}

	public InputStream getStream() {
		return this.stream;
	}
}
