package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");
		
		try {
			HashMap<String,String> datosConexion;
			
			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();		
			
			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;
			
			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			} 

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			//e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		String query = "SELECT * FROM `depositos`";	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		HashMap<Integer, Deposito> hs = new HashMap<Integer, Deposito>();
		try {
			pstmt = conn1.prepareStatement(query);
			rset = pstmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rset.next()) {
				Deposito ds = new Deposito(rset.getString(2), rset.getInt(3), rset.getInt(4));
				hs.put(ds.getId(), ds);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return hs;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		
		String query = "SELECT * FROM `dispensadores`";	
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		HashMap<String, Dispensador> hs = new HashMap<String, Dispensador>();
		try {
			pstmt = conn1.prepareStatement(query);
			rset = pstmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rset.next()) {
				Dispensador ds = new Dispensador(rset.getString(2),rset.getString(3),rset.getInt(4),rset.getInt(5));
				hs.put(ds.getClave(), ds);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return hs;	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = true;
		for (Entry<Integer, Deposito> entry : depositos.entrySet()) {
			String sql = "UPDATE `depositos` SET `nombre`= '"+entry.getValue().getNombreMoneda() +"',`valor`='"+entry.getValue().getValor()+"', `cantidad`= '"+entry.getValue().getCantidad()+"' WHERE nombre = '"+entry.getValue().getNombreMoneda()+"'";
			PreparedStatement pstmt = null;
			HashMap<Integer, Deposito> hs = new HashMap<Integer, Deposito>();
			try {
				pstmt = conn1.prepareStatement(sql);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(
			HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = true;
		for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			String sql = "UPDATE `dispensadores` SET `clave`= '"+entry.getValue().getClave() +"', `nombre`='"+entry.getValue().getNombreProducto() +"',`precio`='"+entry.getValue().getPrecio()+"', `cantidad`= '"+entry.getValue().getCantidad()+"' WHERE nombre = '"+entry.getValue().getNombreProducto()+"'";
			System.out.println(sql);
			PreparedStatement pstmt = null;
			HashMap<String, Dispensador> hs = new HashMap<String, Dispensador>();
			try {
				pstmt = conn1.prepareStatement(sql);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return todoOK;
	}

} // Fin de la clase