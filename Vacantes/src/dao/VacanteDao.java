package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Vacante;

public class VacanteDao {
	
	Connection cn;
	
	public VacanteDao(Connection cn) {
		this.cn = cn;
	}

	public List<Vacante> getVacantes(String sql) throws SQLException {
		List<Vacante> listaVacantes = new ArrayList<Vacante>();
		PreparedStatement pst = cn.prepareStatement(sql);
		
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			Vacante vc = new Vacante(rs.getString("id"), rs.getDate("fechaPublicacion").toLocalDate(), 
					rs.getString("nombreVacante"), rs.getString("descripccion"), rs.getString("detalle"),
					rs.getInt("salario"), rs.getBoolean("activa"));
			
			listaVacantes.add(vc);
			vc = null;
		}
		
		return listaVacantes;
		
	}

	public int agregarVacante(Vacante vc) throws SQLException {
		
		String sql = "INSERT INTO vacantes VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pst = cn.prepareStatement(sql);
		
		pst.setString(1, vc.getId());
		pst.setDate(2, Date.valueOf(LocalDate.now()));
		pst.setString(3, vc.getNombreVacante());
		pst.setString(4, vc.getDescripccion());
		pst.setString(5, vc.getDetalle());
		pst.setInt(6, vc.getSalario());
		pst.setBoolean(7, true);
		
		int rows = pst.executeUpdate();
		pst = null;
		
		return rows;
	}

	public int eliminarVacante(String sql) throws SQLException {
		PreparedStatement pst = cn.prepareStatement(sql);
		
		int rows = pst.executeUpdate();
		return rows;
	}

	public int updateVacante(Vacante vc) throws SQLException {
		String sql = "UPDATE vacantes SET fechaPublicacion=?, nombreVacante=?, descripccion=?, detalle=?, salario=?, activa=? WHERE id=?";
		PreparedStatement pst = cn.prepareStatement(sql);
		
		pst.setDate(1, Date.valueOf(vc.getFechaPublicacion()));
		pst.setString(2, vc.getNombreVacante());
		pst.setString(3, vc.getDescripccion());
		pst.setString(4, vc.getDetalle());
		pst.setInt(5, vc.getSalario());
		pst.setBoolean(6, vc.isActiva());
		pst.setString(7, vc.getId());
		
		int rows = pst.executeUpdate();
		return rows;
	}
	
}
