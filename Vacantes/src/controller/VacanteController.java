package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import dao.VacanteDao;
import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Vacante;

public class VacanteController {
	Connection cn;
	public VacanteController(Connection cn) {
		this.cn = cn;
	}
	
	public List<Vacante> getVacantes(String sql) throws SQLException{
			
		VacanteDao vd = new VacanteDao(cn);
			
		 return vd.getVacantes(sql);

	}
	
	public int agregarVacante(String id, String nombreVacante, String descripcion, String detalle, String salario) throws DateTimeParseException, CamposVaciosException, IsbnException, SQLException {
		int rows;
		Vacante vc = new Vacante(id, LocalDate.now().toString(), nombreVacante, descripcion, detalle, salario, "true");
		VacanteDao vd = new VacanteDao(cn);
		
		rows = vd.agregarVacante(vc);
		
		return rows;
	}

	public int eliminarVacante(String sql) throws SQLException {
		int rows;
		VacanteDao vd = new VacanteDao(cn);
		rows = vd.eliminarVacante(sql);
		return rows;
	}

	public int updateVacante(String id, String fechaPublicacion, String nombreVacante, String descripccion, String detalle,
			String salario, String activa) throws DateTimeParseException, CamposVaciosException, IsbnException, SQLException {
		int rows;
		VacanteDao vd = new VacanteDao(cn);
		Vacante vc = new Vacante(id, fechaPublicacion, nombreVacante, descripccion, detalle, salario, activa);
		rows = vd.updateVacante(vc);
		return rows;
	}
}
