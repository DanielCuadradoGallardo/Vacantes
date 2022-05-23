package vista;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import controller.VacanteController;
import dao.DbConnection;
import dao.VacanteDao;
import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Vacante;

public class Main {

	public static void main(String[] args) {
		FrmVacantes frmVacantes = new FrmVacantes();
		
		/*DbConnection dbc = new DbConnection();
		Connection cn = dbc.getConnection();
		VacanteController vc = new VacanteController(cn);	
		
		
		try {
			List <Vacante> listaVacante = vc.getVacantes("SELECT * from vacantes");
			for(Vacante elem: listaVacante) {
				System.out.println(elem.toString());
			}
			vc = null;
			dbc.disconnect();
			dbc = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		dbc = new DbConnection();
		cn = dbc.getConnection();
		vc = new VacanteController(cn);
		
		try {
			int rows = vc.agregarVacante("978-84-92-49370-8", "Programador", "Necesitamos un programador", "DAW", "5000");
			if(rows > 0) {
				System.out.println("AGREGADO CORRECTAMENTE");
			}else{
				System.err.println("ERROR AL AGREGAR");
			}
			vc = null;
			dbc.disconnect();
			dbc = null;
		} catch (CamposVaciosException | IsbnException | DateTimeParseException | NumberFormatException | SQLException e) {
			System.out.println(e.getMessage());
		}
		
		/*
		dbc = new DbConnection();
		cn = dbc.getConnection();
		vc = new VacanteController(cn);
		
		try {
			String id = "978-84-92-49370-8";
			String sql = "DELETE FROM vacantes WHERE id = '" +id+"'";
			int rows = vc.eliminarVacante(sql);
			if(rows > 0) {
				System.out.println("ELIMINADO CORRECTAMENTE");
			}else{
				System.err.println("ERROR AL ELIMINAR");
			}
			vc = null;
			dbc.disconnect();
			dbc = null;
		} catch (DateTimeParseException | NumberFormatException | SQLException e) {
			e.getMessage();
		}
		
		dbc = new DbConnection();
		cn = dbc.getConnection();
		vc = new VacanteController(cn);
		
		try {
			String id = "978-84-92-49370-8";
			String fechaPublicacion = "2022-01-01";
			String nombreVacante = "Pescadero";
			String descripccion = "Necesitamos un pescadero profesional";
			String detalle ="Ciencias marinas";
			String salario ="2000";
			String activa = "false";
			
			int rows = vc.updateVacante(id,fechaPublicacion, nombreVacante, descripccion, detalle, salario, activa);
			if(rows > 0) {
				System.out.println("ACTUALIZADO CORRECTAMENTE");
			}else{
				System.err.println("ERROR AL ACTUALIZAR");
			}
			vc = null;
			dbc.disconnect();
			dbc = null;
		} catch (DateTimeParseException | NumberFormatException | SQLException | CamposVaciosException | IsbnException e) {
			System.out.println(e.getMessage());
		}
		
		dbc = new DbConnection();
		cn = dbc.getConnection();
		vc = new VacanteController(cn);
		
		try {
			List <Vacante> listaVacante = vc.getVacantes("SELECT * from vacantes ORDER BY fechaPublicacion DESC");
			for(int i = 0; i < 3; i++) {
				System.out.println(listaVacante.get(i));
			}
			vc = null;
			dbc.disconnect();
			dbc = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
}
