package accesoDatos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos
	in_data isDis;
	in_data isDep;
	out_data outDep;
	out_data outDis;

	public FicherosTexto() {
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
		fDis = new File("Ficheros/datos/dispensadores.txt");
		fDep = new File("Ficheros/datos/depositos.txt");
		try {
			this.createStreams();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void createStreams() throws FileNotFoundException {
		isDis = new in_data(fDis.getPath());
		isDep = new in_data(fDep.getPath());
		outDep = new out_data(fDep.getPath());
		outDis = new out_data(fDis.getPath());
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		String data = this.isDep.leer().get("Datos del archivo");
		System.out.println(data);
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		for (String x : data.split("\n")) {
			String[] processedData = x.split(";");
			Deposito dp = new Deposito(processedData[0], Integer.parseInt(processedData[1]), Integer.parseInt(processedData[2]));
			depositosCreados.put(dp.getId(), dp);
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		String data = this.isDis.leer().get("Datos del archivo");
		System.out.println(data);
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<String, Dispensador>();
		for (String x : data.split("\n")) {
			String[] processedData = x.split(";");
			Dispensador dp = new Dispensador(processedData[0], processedData[1], Integer.parseInt(processedData[2]),
					Integer.parseInt(processedData[3]));
			dispensadoresCreados.put(dp.getClave(), dp);
		}
		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		String dataOutput = "";
		for (HashMap.Entry<Integer, Deposito> entry : depositos.entrySet()) {
		
				dataOutput += entry.getValue().getNombreMoneda() + ";" + entry.getValue().getValor() + ";"
						+ entry.getValue().getCantidad() + "\n";
			
			}
		this.outDep.overwrite(dataOutput);
		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = true;
		String dataOutput = "";
		for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			
				
				dataOutput+=entry.getValue().getClave() + ";" + entry.getValue().getNombreProducto() + ";"
						+ entry.getValue().getPrecio() + ";" + entry.getValue().getCantidad() + "\n";
			}
		this.outDis.overwrite(dataOutput);
		
		return todoOK;
	}

} // Fin de la clase