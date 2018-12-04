package accesoDatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

public class out_data {

	private String file;

	out_data(String file) {
		this.setFile(file);
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return this.file;
	}

	public void escribir(String datos, String simbolo) throws FileNotFoundException, IOException {

		File config = new File(file);
		if (config.exists()) {
				in_data in = new in_data(file);
				int tamanoPrevio = in.leer().get("Datos del archivo").length();
				if (tamanoPrevio != 0) {
					datos = in.leer().get("Datos del archivo") + datos+ simbolo;
				}
				OutputStream output = new FileOutputStream(config);
				for (int i = 0; i < datos.length(); i++) {
					output.write((byte) datos.charAt(i));
				}

		}

	}

	public void overwrite(String datos) {
		File config = new File(file);
		try {
			OutputStream output = new FileOutputStream(config);
			for (int i = 0; i < datos.length(); i++) {
				output.write((byte) datos.charAt(i));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public OutputStream createStream() throws FileNotFoundException {
		File config = new File(file);
		OutputStream output = null;
		if (config.exists()) {
			output = new FileOutputStream(config);
		}
		return output;

	}
}
