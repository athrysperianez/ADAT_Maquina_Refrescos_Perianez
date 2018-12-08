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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class JSON implements I_Acceso_Datos {
	private ApiRequests encargadoPeticiones = new ApiRequests();

	private final String SERVER_PATH = "http://localhost/JSONrefrescos/";
	private final String GET_DEPOSITOS = "leeDepositos.php";
	private final String GET_DISPENSADOR = "leeDispensadores.php";
	private final String SET_DISPENSADOR = "escribirDispensadores.php";
	private final String SET_DEPOSITOS = "escribirDepositos.php";
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> auxhm = new HashMap<Integer, Deposito>();

		try {

			String url = SERVER_PATH + GET_DEPOSITOS; // Sacadas de configuracion

			String response = encargadoPeticiones.getRequest(url);


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
					JSONArray array = (JSONArray) respuesta.get("depositos");
					if (array.size() > 0) {
						String nombre;
						int precio;
						int cantidad;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							nombre = row.get("nombre").toString();
							precio = Integer.parseInt(row.get("valor").toString());
							cantidad = Integer.parseInt(row.get("cantidad").toString());
							auxhm.put(precio, new Deposito(nombre, precio, cantidad));
						}

					} else {
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Ha ocurrido un error en el estado");
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
		HashMap<String, Dispensador> auxhm = new HashMap<String, Dispensador>();

		try {

			String url = SERVER_PATH + GET_DISPENSADOR; // Sacadas de configuracion

			String response = encargadoPeticiones.getRequest(url);


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
						Dispensador ds;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);

							// public Dispensador(String clave, String nombre, int p, int inicial){
							ds = new Dispensador(row.get("clave").toString(), row.get("nombre").toString(),
									Integer.parseInt(row.get("precio").toString()),
									Integer.parseInt(row.get("cantidad").toString()));

							auxhm.put(ds.getClave(), ds);
						}


					} else {
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
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		Boolean result = true;
		JSONObject objDepositos = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		objPeticion.put("peticion", "add");
		Iterator<Entry<Integer, Deposito>> it = depositos.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Deposito> dp = it.next();
			objDepositos.put("nombre", dp.getValue().getNombreMoneda());
			objDepositos.put("valor", dp.getValue().getValor());
			objDepositos.put("cantidad", dp.getValue().getCantidad());
			objPeticion.put("depositoAnnadir", objDepositos);
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, objPeticion.toJSONString());
			Request request = new Request.Builder().url(SERVER_PATH + SET_DEPOSITOS).post(body).build();

			try {
				Response response = client.newCall(request).execute();
			} catch (Exception e) {
				result = false;
			}
		}

		return result;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		Boolean result = true;
		JSONObject objDepositos = new JSONObject();
		JSONObject objPeticion = new JSONObject();
		objPeticion.put("peticion", "add");
		Iterator<Entry<String, Dispensador>> it = dispensadores.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Dispensador> dp = it.next();
			objDepositos.put("nombre", dp.getValue().getNombreProducto());
			objDepositos.put("clave", dp.getValue().getClave());
			objDepositos.put("precio", dp.getValue().getPrecio());
			objDepositos.put("cantidad", dp.getValue().getCantidad());
			objPeticion.put("dispensadorAnnadir", objDepositos);
			OkHttpClient client = new OkHttpClient();
			RequestBody body = RequestBody.create(JSON, objPeticion.toJSONString());
			Request request = new Request.Builder().url(SERVER_PATH + SET_DISPENSADOR).post(body).build();
			Response response;
			try {
				response = client.newCall(request).execute();
			} catch (Exception e) {
				result = false;
			}
		}

		return result;
	}
}
