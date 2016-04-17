package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.rpc.modoMonitor.RPC;  //para pr�ctica 4
import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.ConvertidorPaquetes;
import sistemaDistribuido.util.Escribano;

public class LibreriaCliente extends Libreria{

	/**
	 * 
	 */
	public LibreriaCliente(Escribano esc){
		super(esc);
	}

	/**
	 * Ejemplo de resguardo del cliente suma
	 */
	@Override
	protected void suma(){
		int asaDest=248;
		//...
		imprimeln("Prepara el buffer de mensajes");
		byte[] message = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
		solicitud.setOptCode(SUMA);
		imprimeln("Ordena parametros dentro del buffer");
		solicitud.setOperationParameters((short)2, stack);
		asaDest=RPC.importarInterfaz(ProcesoServidor.ServerName, ProcesoServidor.ServerVersion);  //para pr�ctica 4
		if(asaDest == RPC.ServidorNoEncontrado) {
			stack.push(RPC.ServidorNoEncontrado);
			return;
		}
		imprimeln("Llena los campos de encabezado");
		Nucleo.send(asaDest,message);
		//...
		byte[] respMessage=new byte[ConvertidorPaquetes.SOL_LENGTH];
		
		Nucleo.receive(Nucleo.dameIdProceso(), respMessage);
		ConvertidorPaquetes respuesta = new ConvertidorPaquetes(respMessage);
		respuesta.extractOperationParameters(stack);
	}

	@Override
	protected void resta() {
		int asaDest=248;
		//...
		imprimeln("Prepara el buffer de mensajes");
		byte[] message = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
		solicitud.setOptCode(RESTA);
		imprimeln("Ordena parametros dentro del buffer");
		solicitud.setOperationParameters((short)2, stack);
		asaDest=RPC.importarInterfaz(ProcesoServidor.ServerName, ProcesoServidor.ServerVersion);  //para pr�ctica 4
		if(asaDest == RPC.ServidorNoEncontrado) {
			stack.push(RPC.ServidorNoEncontrado);
			return;
		}
		Nucleo.send(asaDest,message);
		//...
		byte[] respMessage=new byte[ConvertidorPaquetes.SOL_LENGTH];
		imprimeln("Llena los campos de encabezado");
		Nucleo.receive(Nucleo.dameIdProceso(), respMessage);
		ConvertidorPaquetes respuesta = new ConvertidorPaquetes(respMessage);
		respuesta.extractOperationParameters(stack);
	}

	@Override
	protected void multiplicacion() {
		int asaDest=248;
		//...
		imprimeln("Prepara el buffer de mensajes");
		byte[] message = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
		solicitud.setOptCode(MULTIPLICACION);
		imprimeln("Ordena parametros dentro del buffer");
		solicitud.setOperationParameters((short)2, stack);
		asaDest=RPC.importarInterfaz(ProcesoServidor.ServerName, ProcesoServidor.ServerVersion);  //para pr�ctica 4
		if(asaDest == RPC.ServidorNoEncontrado) {
			stack.push(RPC.ServidorNoEncontrado);
			return;
		}
		imprimeln("Llena los campos de encabezado");
		Nucleo.send(asaDest,message);
		//...
		byte[] respMessage=new byte[ConvertidorPaquetes.SOL_LENGTH];
		
		Nucleo.receive(Nucleo.dameIdProceso(), respMessage);
		ConvertidorPaquetes respuesta = new ConvertidorPaquetes(respMessage);
		respuesta.extractOperationParameters(stack);
	}

	@Override
	protected void division() {
		int asaDest=248;
		//...
		imprimeln("Prepara el buffer de mensajes");
		byte[] message = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
		solicitud.setOptCode(DIVISION);
		imprimeln("Ordena parametros dentro del buffer");
		solicitud.setOperationParameters((short)2, stack);
		asaDest=RPC.importarInterfaz(ProcesoServidor.ServerName, ProcesoServidor.ServerVersion);  //para pr�ctica 4
		if(asaDest == RPC.ServidorNoEncontrado) {
			stack.push(RPC.ServidorNoEncontrado);
			return;
		}
		imprimeln("Llena los campos de encabezado");
		Nucleo.send(asaDest,message);
		//...
		byte[] respMessage=new byte[ConvertidorPaquetes.SOL_LENGTH];
		Nucleo.receive(Nucleo.dameIdProceso(), respMessage);
		ConvertidorPaquetes respuesta = new ConvertidorPaquetes(respMessage);
		respuesta.extractOperationParameters(stack);
	}

	@Override
	protected void sumatoria() {
		int asaDest=248;
		//...
		byte[] message = new byte[ConvertidorPaquetes.SOL_LENGTH];
		ConvertidorPaquetes solicitud = new ConvertidorPaquetes(message);
		solicitud.setOptCode(SUMATORIA);
		solicitud.setOperationParameters((short)stack.size(), stack);
		asaDest=RPC.importarInterfaz(ProcesoServidor.ServerName, ProcesoServidor.ServerVersion);  //para pr�ctica 4
		if(asaDest == RPC.ServidorNoEncontrado) {
			stack.push(RPC.ServidorNoEncontrado);
			return;
		}
		imprimeln("Llena los campos de encabezado");
		Nucleo.send(asaDest,message);
		//...
		byte[] respMessage=new byte[ConvertidorPaquetes.SOL_LENGTH];
		Nucleo.receive(Nucleo.dameIdProceso(), respMessage);
		ConvertidorPaquetes respuesta = new ConvertidorPaquetes(respMessage);
		respuesta.extractOperationParameters(stack);
	}

}