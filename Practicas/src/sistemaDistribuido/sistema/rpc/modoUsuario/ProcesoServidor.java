package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;   //para práctica 4
import java.util.Stack;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.ConvertidorPaquetes;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoServidor extends Proceso{
	private LibreriaServidor ls;   //para pr�ctica 3
	private int idUnico;
	public static String ServerName="MathServer";
	public static String ServerVersion= "1.0";

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
		ls=new LibreriaServidor(esc);   //para pr�ctica 3
		start();
	}

	/**
	 * Resguardo del servidor
	 */
	public void run(){
		imprimeln("Proceso servidor en ejecucion.");
		idUnico=RPC.exportarInterfaz(ServerName, ServerVersion, this.dameMaquinaProceso());  //para pr�ctica 4
		byte[] message = new byte[ConvertidorPaquetes.SOL_LENGTH];
		while(continuar()){
			Nucleo.receive(dameID(),message);
			try {
				imprimeln("Paquete recibido");
				ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
				byte[] respMessage = ProcesarRespuesta(solicitud);
				imprimeln("Enviando respuesta");
				Nucleo.send(solicitud.getEmisor(), respMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				imprimeln("Algo malo pasó xD");
			}
			
		}
		
		boolean result=RPC.deregistrarInterfaz(ServerName, ServerVersion, idUnico) ; //para pr�ctica 4
		if(result)
			imprimeln("Desregistrado");
		else
			imprimeln("Problema desregistrando");
	}
	
	byte[] ProcesarRespuesta(ConvertidorPaquetes solicitud){
		byte[] respMessage = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes respuesta = new ConvertidorPaquetes(respMessage);
		short operacion = solicitud.getOptCode();
		int[] parameters = solicitud.getOperationParameters();
		int res;
		switch(operacion){
		case Libreria.SUMA:
			res=ls.suma(parameters[0], parameters[1]);
			break;
		case Libreria.RESTA:
			res=ls.resta(parameters[0], parameters[1]);
			break;
		case Libreria.MULTIPLICACION:
			res=ls.multiplicacion(parameters[0], parameters[1]);
			break;
		case Libreria.DIVISION:
			res=ls.division(parameters[0], parameters[1]);
			break;
		case Libreria.SUMATORIA:
			res=ls.sumatoria(parameters);
			break;
		default:res=0;
			break;
		}
		Stack<Integer> stackResp = new Stack<Integer>();
		stackResp.push(res);
		respuesta.setOperationParameters((short)1, stackResp);
		return respMessage;
	}
}
