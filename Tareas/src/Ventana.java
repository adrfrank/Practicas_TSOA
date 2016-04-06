/*  Adrian Francisco Gonzalez Gutierrez
 *  Seccion D05
 *  Tarea 4 
 * */
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Ventana extends Frame implements WindowListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField campo;
	private TextArea area;
	private Button boton;
	private Panel panel;
	private Button boton2;
	private Button boton3;
	
	
	public Ventana(){
		campo = new TextField();
		area = new TextArea();
		boton = new Button("Enviar");
		boton2 = new Button("Limpiar");
		boton3 = new Button("Cerrar");
		panel = new Panel();
		boton.addActionListener(this);
		boton2.addActionListener(this);
		boton3.addActionListener(this);
		//boton.setActionCommand("Enviar");
		area.setEnabled(false);
		setSize(500,500);
		setLayout(new BorderLayout());
		
		panel.add(boton);
		panel.add(boton2);
		panel.add(boton3);
		
		add("North",campo);
		add("Center",area);
		//add("South",boton);
		add("South",panel);
		addWindowListener(this);
		this.setTitle("Mi venteana");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ventana v = new Ventana();
		v.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println("Terminando");
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String comando = arg0.getActionCommand();
		if(comando.equals("Enviar")){
			area.append(campo.getText()+"\n");
		} else if(comando.equals("Cerrar")){
			System.exit(0);
		} else if(comando.equals("Limpiar")){
			area.setText("");
			campo.setText("");
		}
		
	}

}
