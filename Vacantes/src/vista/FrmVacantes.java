package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.VacanteController;
import dao.DbConnection;
import excepciones.CamposVaciosException;
import excepciones.IsbnException;
import modelo.Vacante;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class FrmVacantes extends JFrame {
	private JPanel panel;
	private JTextField textId, textFechaPublicacion, textNombreVacante, textDescripcion,textSalario;
	JCheckBox chckbxActiva;
	private JButton btnPrimero, btnAtras, btnAdelante, btnUltimo;
	private JButton btnNuevo, btnEditar, btnGuardar, btnDeshacer, btnBorrar;
	private JPanel panelMantenimiento;
	private JPanel panelGrid;
	private JScrollPane scrollPane;
	private JTable tblVacantes;	
	DefaultTableModel dtm;
	private JTextField textDetalle;
	private JFrame frame;
	JComboBox comboCampo;
	private JTextField textFiltrar;
	JButton btnFiltrar;
	JLabel lblConsulta;	
	private Connection cn;
	private DbConnection dbc;
	private VacanteController vc;
	private List<Vacante> listaVacantes;
	private int indice = 0;
	
	
	
	public FrmVacantes() {
		setTitle("F O R M U L A R I O   D E   M A N T E N I M I E N T O");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1182, 476);
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		definirVentana();
		definirEventos();
		resultset(indice=0);
		controladorNavegacion(false, false, true, true);
		controladorCajasTexto(false, false, false, false, false, false, false);
		frame=this;
		this.setVisible(true);
	}
	
	//E V E N T O S
	private void definirEventos(){
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultset(indice=0);
				controladorNavegacion(false, false, true, true);
	
			}
		});
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultset(indice = indice-1);
				if(indice == 0) {
					controladorNavegacion(false, false, true, true);
				}else {
					controladorNavegacion(true, true, true, true);
				}
			}
		});
		btnAdelante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultset(indice = indice+1);
				if(indice == listaVacantes.size()-1) {
					controladorNavegacion(true, true, false, false);
				}else {
					controladorNavegacion(true, true, true, true);
				}
				
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultset(indice = listaVacantes.size()-1);
				controladorNavegacion(true, true, false, false);
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controladorNavegacion(false, false, false, false);
				controladorCajasTexto(true, false, true, true, true, true, false);
				cotroladorOperacion(false,false,false,true,true);
				
				textId.setText("");
				textFechaPublicacion.setText(""+LocalDate.now());;
				textNombreVacante.setText("");
				textDescripcion.setText("");
				textDetalle.setText("");
				textSalario.setText("");
				
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controladorNavegacion(false, false, false, false);
				controladorCajasTexto(false, true, true, true, true, true, true);
				cotroladorOperacion(false,false,false,true,true);
			}
		});
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(frame, "DESEA GUARDAR LOS CAMBIOS?");
				if(result == 0) {
					dbc = new DbConnection();
					cn = dbc.getConnection();
					vc = new VacanteController(cn);
					
					try {
						vc.eliminarVacante("DELETE FROM vacantes WHERE id ='" +textId.getText() +"'");
						dbc.disconnect();
						dbc = null;
						vc = null;
						JOptionPane.showMessageDialog(frame, "Se ha eliminado el libro con exito", "E X I T O", JOptionPane.INFORMATION_MESSAGE);
						resultset(indice=0);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "NO se ha eliminado el libro con exito", "E R R O R", JOptionPane.INFORMATION_MESSAGE);
						dbc.disconnect();
						dbc = null;
						vc = null;
					}
				}else {}

			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dbc = new DbConnection();
				cn = dbc.getConnection();
				vc = new VacanteController(cn);
				
				try {
					Vacante v = new Vacante(textId.getText(), LocalDate.now().toString(), textNombreVacante.getText(), textDescripcion.getText(),
							textDetalle.getText(), textSalario.getText(), "true");
					if(listaVacantes.contains(v)) {
						int rows = vc.updateVacante(textId.getText(), textFechaPublicacion.getText(), textNombreVacante.getText(), textDescripcion.getText(),
								textDetalle.getText(), textSalario.getText(), ""+chckbxActiva.isSelected());
						JOptionPane.showMessageDialog(frame, "Se ha actualizado la vacante con exito", "E X I T O", JOptionPane.INFORMATION_MESSAGE);
					}else {
						int rows =vc.agregarVacante(textId.getText(), textNombreVacante.getText(), textDescripcion.getText(),
								textDetalle.getText(), textSalario.getText());
						JOptionPane.showMessageDialog(frame, "Se ha agregado la vacante con exito", "E X I T O", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (DateTimeParseException | CamposVaciosException | IsbnException | SQLException | NumberFormatException e1) {
					JOptionPane.showMessageDialog(frame, "No se ha agregado la vacante con exito", "E R R O R", JOptionPane.INFORMATION_MESSAGE);
				}
				
				resultset(indice = 0);
				cotroladorOperacion(true,true,true,false,false);
				controladorNavegacion(false, false, true, true);
				controladorCajasTexto(false, false, false, false, false, false, false);
			}
		});
		btnDeshacer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultset(indice = 0);
				cotroladorOperacion(true,true,true,false,false);
				controladorNavegacion(false, false, true, true);
				controladorCajasTexto(false, false, false, false, false, false, false);
			}
			
		});
		
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbc = new DbConnection();
				cn = dbc.getConnection();
				vc = new VacanteController(cn);
				List <Vacante> listaFiltrada = null;
				String busqueda = "'%" +textFiltrar.getText() +"%'";
				try {
					listaFiltrada = vc.getVacantes("SELECT * FROM vacantes WHERE " +comboCampo.getSelectedItem().toString()+" LIKE " +busqueda);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String identificadores[] = {"id", "Fecha Publicacion", "Nombre vacante", "Descripcion", "Detalle", "Salario", "Activa"};
				dtm.setRowCount(0);
				dtm.setColumnCount(0);
				dtm.setColumnIdentifiers(identificadores);
				
				for(int i = 0; i < listaFiltrada.size(); i++) {
					dtm.addRow(listaFiltrada.get(i).toString().split(","));
				}
				dbc.disconnect();
				dbc = null;
				vc = null;
			}
		});
		
	}

	// D E F I N I R   V E N T A N A
private void definirVentana() {
	// TODO Ap�ndice de m�todo generado autom�ticamente
	JPanel panelLibros = new JPanel();
	panelLibros.setLayout(null);
	panelLibros.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Vacantes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
	panelLibros.setBounds(28, 118, 298, 227);
	panel.add(panelLibros);

	textId = new JTextField();
	textId.setEditable(false);
	textId.setColumns(10);
	textId.setBounds(82, 29, 128, 20);
	panelLibros.add(textId);

	JLabel lblId = new JLabel("Id");
	lblId.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblId.setBounds(26, 32, 46, 14);
	panelLibros.add(lblId);

	JLabel lblFechaPublicacin = new JLabel("Fecha Publicaci\u00F3n");
	lblFechaPublicacin.setHorizontalAlignment(SwingConstants.CENTER);
	lblFechaPublicacin.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblFechaPublicacin.setBounds(22, 51, 105, 32);
	panelLibros.add(lblFechaPublicacin);

	textFechaPublicacion = new JTextField();
	textFechaPublicacion.setEditable(false);
	textFechaPublicacion.setColumns(10);
	textFechaPublicacion.setBounds(151, 57, 117, 20);
	panelLibros.add(textFechaPublicacion);

	JLabel lblVacante = new JLabel("Vacante");
	lblVacante.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblVacante.setBounds(26, 91, 46, 14);
	panelLibros.add(lblVacante);

	textNombreVacante = new JTextField();
	textNombreVacante.setEditable(false);
	textNombreVacante.setColumns(10);
	textNombreVacante.setBounds(82, 88, 186, 20);
	panelLibros.add(textNombreVacante);

	JLabel lblDescripcion = new JLabel("Descripcion");
	lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblDescripcion.setBounds(26, 121, 65, 14);
	panelLibros.add(lblDescripcion);

	textDescripcion = new JTextField();
	textDescripcion.setEditable(false);
	textDescripcion.setColumns(10);
	textDescripcion.setBounds(101, 119, 167, 20);
	panelLibros.add(textDescripcion);
	
	JLabel lblSalario = new JLabel("Salario");
	lblSalario.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblSalario.setBounds(26, 173, 46, 14);
	panelLibros.add(lblSalario);
	
	textSalario = new JTextField();
	textSalario.setBounds(82, 170, 105, 20);
	textSalario.setEditable(false);
	panelLibros.add(textSalario);
	textSalario.setColumns(10);
	
	chckbxActiva = new JCheckBox("Activa");
	chckbxActiva.setFont(new Font("Tahoma", Font.BOLD, 11));
	chckbxActiva.setBounds(25, 197, 97, 23);
	panelLibros.add(chckbxActiva);
	
	JLabel lblDetalle = new JLabel("Detalle");
	lblDetalle.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblDetalle.setBounds(26, 146, 46, 14);
	panelLibros.add(lblDetalle);
	
	textDetalle = new JTextField();
	textDetalle.setEditable(false);
	textDetalle.setBounds(82, 143, 186, 20);
	panelLibros.add(textDetalle);
	textDetalle.setColumns(10);
	

	JPanel panelNavegador = new JPanel();
	panelNavegador.setLayout(null);
	panelNavegador.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Navegador", TitledBorder.LEADING,
			TitledBorder.TOP, null, Color.BLUE));
	panelNavegador.setBounds(28, 356, 218, 74);
	panel.add(panelNavegador);

	// NAVEGADOR
	ImageIcon imaNav = new ImageIcon("imagenes/navPri.jpg");
	btnPrimero = new JButton("", imaNav);
	btnPrimero.setBounds(15, 15, 40, 40);
	panelNavegador.add(btnPrimero);
	imaNav = new ImageIcon("imagenes/navIzq.jpg");
	btnAtras = new JButton("", imaNav);
	btnAtras.setBounds(65, 15, 40, 40);
	panelNavegador.add(btnAtras);
	imaNav = new ImageIcon("imagenes/navDer.jpg");
	btnAdelante = new JButton("", imaNav);
	btnAdelante.setBounds(115, 15, 40, 40);
	panelNavegador.add(btnAdelante);
	imaNav = new ImageIcon("imagenes/navUlt.jpg");
	btnUltimo = new JButton("", imaNav);
	btnUltimo.setBounds(165, 15, 40, 40);
	panelNavegador.add(btnUltimo);
	
	panelMantenimiento = new JPanel();
	panelMantenimiento.setLayout(null);
	panelMantenimiento.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), "Mantenimiento Vacantes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
	panelMantenimiento.setBounds(28, 21, 266, 74);
	panel.add(panelMantenimiento);
	
	imaNav = new ImageIcon("imagenes/botonAgregar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnNuevo = new JButton("", imaNav);
	btnNuevo.setToolTipText("Insertar Nuevo Libro");
	btnNuevo.setBounds(15, 15, 40, 40);
	panelMantenimiento.add(btnNuevo);
	
	imaNav = new ImageIcon("imagenes/botonEditar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnEditar = new JButton("", imaNav);
	btnEditar.setToolTipText("Editar");
	btnEditar.setBounds(65, 15, 40, 40);
	panelMantenimiento.add(btnEditar);
	
	imaNav = new ImageIcon("imagenes/botonGuardar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnGuardar = new JButton("", imaNav);
	


	btnGuardar.setEnabled(false);
	btnGuardar.setToolTipText("Guardar");
	btnGuardar.setBounds(166, 15, 40, 40);
	panelMantenimiento.add(btnGuardar);
	
	imaNav = new ImageIcon("imagenes/botonDeshacer.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnDeshacer = new JButton("", imaNav);


	btnDeshacer.setEnabled(false);
	btnDeshacer.setToolTipText("Deshacer");
	btnDeshacer.setBounds(216, 15, 40, 40);
	panelMantenimiento.add(btnDeshacer);
	
	imaNav = new ImageIcon("imagenes/borrar.jpg");
	imaNav = new ImageIcon(imaNav.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_DEFAULT));
	btnBorrar = new JButton("", imaNav);
	btnBorrar.setToolTipText("Borrar Registro");
	btnBorrar.setBounds(116, 15, 40, 40);
	panelMantenimiento.add(btnBorrar);
	
	panelGrid = new JPanel();
	panelGrid.setBounds(363, 98, 766, 305);
	panel.add(panelGrid);
	panelGrid.setLayout(new BorderLayout(0, 0));
	
	scrollPane = new JScrollPane();
	panelGrid.add(scrollPane, BorderLayout.CENTER);
	dtm=new DefaultTableModel();
	tblVacantes = new JTable(dtm);

	
	scrollPane.setViewportView(tblVacantes);
	
	comboCampo = new JComboBox();
	comboCampo.setModel(new DefaultComboBoxModel(new String[] {"nombreVacante", "id", "detalle"}));
	comboCampo.setBounds(363, 52, 101, 20);
	panel.add(comboCampo);
	
	textFiltrar = new JTextField();
	textFiltrar.setBounds(472, 52, 177, 20);
	panel.add(textFiltrar);
	textFiltrar.setColumns(10);
	
	btnFiltrar = new JButton("FILTRAR");
	btnFiltrar.setBounds(659, 51, 89, 23);
	panel.add(btnFiltrar);
	
	lblConsulta = new JLabel("Consulta");
	lblConsulta.setBounds(392, 21, 257, 14);
	panel.add(lblConsulta);

}

//Movernos por el resulset
	private void resultset(int indice) {
		dbc = new DbConnection();
		cn = dbc.getConnection();
		vc = new VacanteController(cn);
		try {
			listaVacantes = vc.getVacantes("SELECT * from vacantes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		textId.setText(listaVacantes.get(indice).getId());
		textFechaPublicacion.setText(listaVacantes.get(indice).getFechaPublicacion().toString());
		textNombreVacante.setText(listaVacantes.get(indice).getNombreVacante());
		textDescripcion.setText(listaVacantes.get(indice).getDescripccion());
		textDetalle.setText(listaVacantes.get(indice).getDetalle());
		textSalario.setText(""+listaVacantes.get(indice).getSalario());
		chckbxActiva.setSelected(listaVacantes.get(indice).isActiva());	
		
		dbc.disconnect();
		dbc = null;
		vc = null;
	}
	
	private void navegacion(int indice) {
		System.out.println("b");
	}

//HABILITAR DESABILITAR PANELES
	private void controladorNavegacion(Boolean a, Boolean b, Boolean c, Boolean d) {
		btnPrimero.setEnabled(a);
		btnAtras.setEnabled(b);
		btnAdelante.setEnabled(c);
		btnUltimo.setEnabled(d);
	}
	
	private void cotroladorOperacion(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e) {
		btnNuevo.setEnabled(a);
		btnEditar.setEnabled(b);
		btnBorrar.setEnabled(c);	
		btnGuardar.setEnabled(d);
		btnDeshacer.setEnabled(e);
	}
	
	private void controladorCajasTexto(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f, Boolean g) {
		textId.setEditable(a);
		textFechaPublicacion.setEditable(b);
		textNombreVacante.setEditable(c);
		textDescripcion.setEditable(d);
		textDetalle.setEditable(e);
		textSalario.setEditable(f);
		chckbxActiva.setEnabled(g);
	}
	

	
	
	
	
}