import java.awt.*;
import java.awt.event.*;

public class Examen extends Frame implements WindowListener, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button boton1;
	private TextField campo;
	private TextArea area;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Examen e = new Examen();
		e.setVisible(true);
		
	}
	public Examen(){
		boton1 = new Button("Enviar");
		area = new TextArea();
		campo = new TextField();
		setLayout(new BorderLayout());
		add("North",campo);
		add("Center",area);
		add("South",boton1);
		area.setEnabled(false);
		addWindowListener(this);
		boton1.addActionListener(this);
		setSize(500, 500);
		this.setTitle("Examen");
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Enviar")){
			area.append(campo.getText()+"\n");			
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
