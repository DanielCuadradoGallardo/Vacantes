package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import excepciones.CamposVaciosException;
import excepciones.IsbnException;

public class Vacante {
	
	private String id;
	private LocalDate fechaPublicacion;
	private String nombreVacante;
	private String descripccion;
	private String detalle;
	private int salario;
	private boolean activa;
	
	
	public Vacante() {}
	
	public Vacante(String id, String fechaPublicacion, String nombreVacante, String descripccion, String detalle, String salario, String activa) throws DateTimeParseException, CamposVaciosException, IsbnException {
		if((id.length()  == 0 || fechaPublicacion.length()  == 0 || nombreVacante.length() == 0
			|| descripccion.length() == 0 || detalle.length() == 0 || salario.length() == 0 || activa.length() == 0)) {
			
			throw new CamposVaciosException("Has introducido un campo vacio");
		}
		
		setId(id);
		setFechaPublicacion(fechaPublicacion);
		setNombreVacante(nombreVacante);
		setDescripccion(descripccion);
		setDetalle(detalle);
		setSalario(salario);
		setActiva(activa);
	}
	
	public Vacante(String id, LocalDate fechaPublicacion, String nombreVacante, String descripccion, String detalle, int salario, boolean activa) {
		this.id = id;
		this.fechaPublicacion = fechaPublicacion;
		this.nombreVacante = nombreVacante;
		this.descripccion = descripccion;
		this.detalle = detalle;
		this.salario = salario;
		this.activa = activa;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) throws IsbnException {
		String idOriginal = id;
		id = id.replace("-", "");
		
		try {
			Double test =Double.parseDouble(id);
		}catch(NumberFormatException e) {
			throw new IsbnException();
		}
		
		if(id.length() != 13) {
			throw new IsbnException();
		}
		int digitoDeControl = Character.getNumericValue(id.charAt(12));
		
		int suma = 0;
		
		
		for(int i = 0; i < 12; i++) {
			if(i %2 == 0) {
				suma = suma +Character.getNumericValue(id.charAt(i)) * 1;
			}else {
				suma = suma +Character.getNumericValue(id.charAt(i)) * 3;
			}
		}
		int decenaSuperior = suma;
		while(decenaSuperior % 10 != 0) {
			decenaSuperior++;
		}
		
		int digitoDeControlFinal = decenaSuperior - suma;
		
		if(digitoDeControlFinal != digitoDeControl) {
			throw new IsbnException();
		}else {
			this.id = idOriginal;
		}
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) throws DateTimeParseException {
		this.fechaPublicacion = LocalDate.parse(fechaPublicacion);
	}

	public String getNombreVacante() {
		return nombreVacante;
	}

	public void setNombreVacante(String nombreVacante) {
		this.nombreVacante = nombreVacante;
	}

	public String getDescripccion() {
		return descripccion;
	}

	public void setDescripccion(String descripccion) {
		this.descripccion = descripccion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public int getSalario() {
		return salario;
	}

	public void setSalario(String salario) throws NumberFormatException  {
		this.salario = Integer.parseInt(salario);
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = Boolean.parseBoolean(activa);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacante other = (Vacante) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return id + ", " +fechaPublicacion +", " +nombreVacante +", "  +descripccion
				+ ", "+ detalle + ", " + salario + ", "+ activa;
	}
}
