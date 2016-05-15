package sistemaDistribuido.sistema.clienteServidor.modoMonitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.MicroNucleoBase;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.ConvertidorPaquetes;
import sistemaDistribuido.util.Pausador;

/**
 * 
 */
public final class MicroNucleo extends MicroNucleoBase{
	private static MicroNucleo nucleo=new MicroNucleo();
	private Hashtable<Integer,ParMaquinaProceso > tablaEmision;
	private Hashtable<Integer,byte[]> tablaRecepcion;
	/**
	 * 
	 */
	private MicroNucleo(){
		tablaEmision = new Hashtable<Integer,ParMaquinaProceso>();
		tablaRecepcion = new Hashtable<Integer,byte[]>();
	}

	/**
	 * 
	 */
	public final static MicroNucleo obtenerMicroNucleo(){
		return nucleo;
	}

	/*---Metodos para probar el paso de mensajes entre los procesos cliente y servidor en ausencia de datagramas.
    Esta es una forma incorrecta de programacion "por uso de variables globales" (en este caso atributos de clase)
    ya que, para empezar, no se usan ambos parametros en los metodos y fallaria si dos procesos invocaran
    simultaneamente a receiveFalso() al reescriir el atributo mensaje---*/
	byte[] mensajef;

	public void sendFalso(int dest,byte[] message){
		System.arraycopy(message,0,mensajef,0,message.length);
		notificarHilos();  //Reanuda la ejecucion del proceso que haya invocado a receiveFalso()
	}

	public void receiveFalso(int addr,byte[] message){
		mensajef=message;
		suspenderProceso();
	}
	/*---------------------------------------------------------*/

	/**
	 * 
	 */
	protected boolean iniciarModulos(){
		return true;
	}

	/**
	 * 
	 */
	protected void sendVerdadero(int dest,byte[] message){
		//sendFalso(dest,message);
		imprimeln("El proceso invocante es el "+super.dameIdProceso());
		println("Buscando en listas locales el par (m�quina, proceso) que corresponde al par�metro dest de la llamada a send");
		ParMaquinaProceso pmp = tablaEmision.get(dest);
		
		if(pmp == null){
			println("Enviando mensaje de b�squeda del servidor�");
			pmp = dameDestinatarioDesdeInterfaz();
			println("Recibido mensaje que contiene la ubicaci�n (m�quina, proceso) del servidor");
			tablaEmision.put(pmp.dameID(), pmp);
		}
			
		
		
		imprimeln("Completando campos de encabezado del mensaje a ser enviado");
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
		solicitud.setReceptor(pmp.dameID());
		solicitud.setEmisor(super.dameIdProceso());
		DatagramSocket socketEmision;
		DatagramPacket dp;
		println("Origen empaquetado: "+solicitud.getEmisor());
		println("Destino empaquetado: "+solicitud.getReceptor());
		try{
			socketEmision=dameSocketEmision();  
			println("Socket obtenido");
			dp=new DatagramPacket(message,message.length,InetAddress.getByName(pmp.dameIP()),damePuertoRecepcion());
			imprimeln("Enviando mensaje a IP="+pmp.dameIP()+" ID="+pmp.dameID());
			socketEmision.send(dp);			
			println("Enviado");
		}catch(SocketException e){
			println("Error iniciando socket: "+e.getMessage());
		}catch(UnknownHostException e){
			println("UnknownHostException: "+e.getMessage());
		}catch(IOException e){
			println("IOException: "+e.getMessage());
		}
		//no descomentar la sig. linea en la pŕactica 2
		//suspenderProceso();   //esta invocacion depende de si se requiere bloquear al hilo de control invocador
		
	}

	public void registrarAsa(ParMaquinaProceso asa){
		this.tablaEmision.put(asa.dameID(), asa);
		
	}
	
	/**
	 * 
	 */
	protected void receiveVerdadero(int addr,byte[] message){
		println("Receive invocado, addr: "+addr);
		
		tablaRecepcion.put(addr, message);
		//el siguiente aplica para la pr�ctica #2
		suspenderProceso();
	}

	/**
	 * Para el(la) encargad@ de direccionamiento por servidor de nombres en pr�ctica 5  
	 */
	protected void sendVerdadero(String dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en pr�ctica 5
	 */
	protected void sendNBVerdadero(int dest,byte[] message){
	}

	/**
	 * Para el(la) encargad@ de primitivas sin bloqueo en pr�ctica 5
	 */
	protected void receiveNBVerdadero(int addr,byte[] message){
	}

	protected void sendErr(int addr, String ip, short errType){
		byte[] errMessage = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(errMessage);
		solicitud.setEmisor(this.dameIdProceso());
		solicitud.setReceptor(addr);
		solicitud.setOptCode(errType);
		tablaEmision.put(addr, new MaquinaProceso(ip,addr));
		this.send(addr, errMessage);
	}
	
	protected void sendTA(int addr,String ip){
		sendErr(addr,ip,(short)-1);		
	}
	
	protected void sendAU(int addr, String ip){
		sendErr(addr,ip,(short)-2);				
	}
	
	/**
	 * 
	 */
	public void run(){
		DatagramSocket socket = dameSocketRecepcion();		
		DatagramPacket dp;
		byte[] buffer=new byte[1024];
		dp=new DatagramPacket(buffer,buffer.length);
		String ip;
		int origen,destino;
		while(seguirEsperandoDatagramas()){
			
			try {
				println("Esperando recepcion en socket: "+this.damePuertoRecepcion());
				socket.receive(dp);
				println("Recibido mensaje proveniente de la red");
				println("Copiando el mensaje hacia el espacio del proceso");
				ip = dp.getAddress().getHostName();	
				println("IP: "+ip);
				ConvertidorPaquetes solicitud = new ConvertidorPaquetes(buffer);
				origen = solicitud.getEmisor();
				destino = solicitud.getReceptor();
				println("Origen: "+origen+", Destino: "+destino);
				println("Buscando proceso correspondiente al campo dest del mensaje recibido");
				Proceso p= this.dameProcesoLocal(destino);
				if(p == null){
					//Address Unknown
					println("Proceso destinatario no encontrado seg�n campo dest del mensaje recibido");
					this.sendAU(origen, ip);
					Pausador.pausa(1000);
					
				}else{
					println("Proceso encontrado: "+destino);
					if(!tablaRecepcion.containsKey(destino)){
						//Try Again
						println("Try Again");
						this.sendTA(origen, ip);
						Pausador.pausa(1000);
						
					}else{
						byte[] bytes=tablaRecepcion.get(destino);
						tablaRecepcion.remove(destino);
						tablaEmision.put(origen, new MaquinaProceso(ip,origen));
						System.arraycopy(buffer, 0, bytes, 0, buffer.length);
						this.reanudarProceso(p);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}catch(Exception e){
				
			}
			
			/* Lo siguiente es reemplazable en la pr�ctica #2,
			 * sin esto, en pr�ctica #1, seg�n el JRE, puede incrementar el uso de CPU
			 */ 			
			/*try{
				sleep(60000);
			}catch(InterruptedException e){
				System.out.println("InterruptedException");
			}*/
		}
		
	}
}
