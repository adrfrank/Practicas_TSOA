/********************************************
 * Adrian Francisco Gonzalez Gutierrez
 * Tarea A
 ********************************************/


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat extends Frame implements WindowListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Panel pnlSuperior;
	Label lblIp,lblMensaje;
	Button btnEnviar;
	TextField txtIp,txtMensaje;
	TextArea txtArea;
	Receptor r;
	Emisor emisor;
	int puerto = 7000;
	public Chat(){
		pnlSuperior = new Panel();
		lblIp = new Label("IP: ");
		lblMensaje = new Label("Mensaje: ");
		btnEnviar = new Button("Enviar");
		txtIp = new TextField(15);
		txtMensaje = new TextField(20);
		txtArea = new TextArea();
		txtMensaje.setSize(20,100);
		setLayout(new BorderLayout());
		add("North",pnlSuperior);
		add("Center",txtArea);
		pnlSuperior.add(lblIp);
		pnlSuperior.add(txtIp);
		pnlSuperior.add(lblMensaje);
		pnlSuperior.add(txtMensaje);
		pnlSuperior.add(btnEnviar);
		setTitle("Chat");
		setSize(500,500);
		txtArea.setEditable(false);
		addWindowListener(this);
		btnEnviar.addActionListener(this);
		r = new Receptor();
		r.start();
		emisor = new Emisor();
	}
	
	class Receptor extends Thread{
		boolean correr;
		@Override
		public void run(){
			DatagramSocket socketRecepcion;
			DatagramPacket dp;
			correr = true;
			int puertoEntrada=puerto;
			byte[] buffer=new byte[1024];
			System.out.print("Iniciando socket recepcion en puerto "+puertoEntrada+"...");
			try{
				socketRecepcion=new DatagramSocket(puertoEntrada);
				System.out.println("hecho");
				dp=new DatagramPacket(buffer,buffer.length);
				while(correr){//deseable validar con bandera
					socketRecepcion.receive(dp);
					String msg = "IP emisora: "+dp.getAddress().getHostName()+": "+
							new String(buffer,0,dp.getLength());
					txtArea.append(msg+"\n");
					System.out.println(msg);
				}
				socketRecepcion.close();//codigo inalcanzable sin bandera
			}catch(SocketException e){
				System.out.println("Error iniciando socket: "+e.getMessage());
			}catch(IOException e){
				System.out.println("IOException: "+e.getMessage());
			}
			System.out.print("Cerrando socket recepcion...");
			System.out.println("hecho");
		}
		public void detener(){
			System.out.println("Deteniendo");
			correr = false;
		}
	}
	
	class Emisor{
		public void enviar(String mensaje, String ip){
			DatagramSocket socketEmision;
			DatagramPacket dp;
			//int puertoSalida=2002;
			byte[] buffer;
			//System.out.print("Iniciando socket emision en puerto "+puertoSalida+"...");
			try{
				socketEmision=new DatagramSocket();  //constructor sobrecargado para recibir o no un #puerto
				System.out.println("hecho");
				buffer=mensaje.getBytes();
				dp=new DatagramPacket(buffer,buffer.length,InetAddress.getByName(ip),puerto);
				socketEmision.send(dp);
				System.out.print("Cerrando socket emision...");
				socketEmision.close();
				System.out.println("hecho");
			}catch(SocketException e){
				System.out.println("Error iniciando socket: "+e.getMessage());
			}catch(UnknownHostException e){
				System.out.println("UnknownHostException: "+e.getMessage());
			}catch(IOException e){
				System.out.println("IOException: "+e.getMessage());
			}
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chat c = new Chat();
		c.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Enviar")){
			emisor.enviar(txtMensaje.getText(), txtIp.getText());
		}
		
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
		// TODO Auto-generated method stub
		r.detener();
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

}
