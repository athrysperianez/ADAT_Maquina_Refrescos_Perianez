/*
 *Creado por Elias Periañez
 *23 oct. 2018
 *Como parte del proyecto ADAT_Maquina_Refrescos_Periañez
 *Este archivo esta bajo la licencia de Creative Commons Reconocimiento 4.0 Internacional (Más informacion https://creativecommons.org/licenses/by/4.0/)
________________________________________________________________________________________________________________________________________________________
 *Created by Elias Periañez
 *23 oct. 2018
 *As part of the project ADAT_Maquina_Refrescos_Periañez
 *This file is under the Creative Commons Attribution 4.0 International (More info here https://creativecommons.org/licenses/by/4.0/)
 */
package accesoDatos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import logicaRefrescos.Dato;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class Hibernate implements I_Acceso_Datos {
	private Session s;

	public Hibernate() {
		s = new Configuration().configure().buildSessionFactory().openSession();
	}

	private boolean checkSuccess(Transaction tr) {
		boolean returner = false;
		boolean seguir = true;
		while (seguir) {
			switch (tr.getStatus()) {
			case COMMITTED:
				seguir = false;
				returner = true;
				break;

			case FAILED_COMMIT:
				seguir = false;
				break;

			default:
				break;
			}
		}
		return returner;
	}

	private List recuperarDatos(Dato e) {
		Query q = s.createQuery("Select e from " + e.getClass().toString().substring(e.getClass().toString().lastIndexOf(".") + 1) + " e");
		return q.list();
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> hs = new HashMap<Integer, Deposito>();
		Iterator data = this.recuperarDatos(new Deposito()).iterator();
		while (data.hasNext()) {
			Deposito ds = (Deposito) data.next();
			hs.put(ds.getValor(), ds);
		}

		return hs;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> hs = new HashMap<String, Dispensador>();
		Iterator data = this.recuperarDatos(new Dispensador()).iterator();
		while (data.hasNext()) {
			Dispensador ds = (Dispensador) data.next();
			hs.put(ds.getClave(), ds);
		}

		return hs;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		s.beginTransaction();
		for (Entry<Integer, Deposito> entry : depositos.entrySet()) {
			s.saveOrUpdate(entry.getValue());
		}
		s.getTransaction().commit();
		return this.checkSuccess(s.getTransaction());
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		s.beginTransaction();
		for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			s.saveOrUpdate(entry.getValue());
		}
		s.getTransaction().commit();
		return this.checkSuccess(s.getTransaction());
	}

}
