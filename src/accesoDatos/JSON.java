/*
 *Creado por Elias Periañez
 *4 dic. 2018
 *Como parte del proyecto ADAT_Maquina_Refrescos_Periañez
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *4 dic. 2018
 *As part of the project ADAT_Maquina_Refrescos_Periañez
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */
package accesoDatos;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class JSON implements I_Acceso_Datos{
	private ApiRequests encargadoPeticiones;

	private String SERVER_PATH = "http://localhost/nacho/CochesJSONServer/";
	private String GET_DEPOSITOS = "leeDepositos.php";
	private String GET_MARCA = "leeMarcas.php";
	private String SET_COCHE = "escribirCoche.php";
	private String SET_MARCA = "escribirMarca.php";

	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> auxhm = new HashMap<Integer, Deposito>();

		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para coches");

			String url = SERVER_PATH + GET_DEPOSITOS; // Sacadas de configuracion

			// System.out.println("La url a la que lanzamos la petición es " +
			// url); 
			// Traza para pruebas

			String response = encargadoPeticiones.getRequest(url);

			 System.out.println(response); // Traza para pruebas

			// Parseamos la respuesta y la convertimos en un JSONObject
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				System.out.println("El json recibido no es correcto. Finaliza la ejecución");
				System.exit(-1);
			} else { // El JSON recibido es correcto
				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado"); 
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) { 
					JSONArray array = (JSONArray) respuesta.get("coches");

					if (array.size() > 0) {

						// Declaramos variables
						Deposito nuevoDeposito;
						
						int id;
						String clave;
						String nombre;
						int precio;
						int cantidad;
						


						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							nombre = row.get("nombre").toString();
							precio = Integer.parseInt(row.get("precio").toString());
							cantidad = Integer.parseInt(row.get("cantidad").toString());
							
							//	public Deposito(String n, int v, int inicial){
							nuevoDeposito = new Deposito(nombre, precio, cantidad);

							auxhm.put(i, nuevoDeposito);
						}

						System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
						System.out.println();

					} else { // El array de jugadores está vacío
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

					System.exit(-1);

				}
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

			System.exit(-1);
		}

		return auxhm;
	}	

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		return null;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		return false;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		return false;
	}

}
