package sistemaDistribuido.sistema.exclusion.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.PaqueteRecurso;

enum Operacion{
	Solicitar,
	Liberar, 
	Nada
}

/**
 * 
 */
public class ProcesoCliente extends Proceso{
	String msg;
	short code;
	
	Operacion operacion;
	int recurso;
	RecursosClienteListener listener;
	
	public RecursosClienteListener getListener() {
		return listener;
	}

	public void setListener(RecursosClienteListener listener) {
		this.listener = listener;
	}

	public String[] getRecursos() {
		return ProcesoServidor.recursos;
	}

	

	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);
		start();
	}

	public void sendMessage(Short code, String msg){
		this.msg = msg;
		this.code = code;
	}
	
	public void solicitarRecurso(int recurso){
		operacion = Operacion.Solicitar;
		this.recurso = recurso;
	}
	
	public void liberarRecurso(int recurso){
		operacion = Operacion.Liberar;
		this.recurso = recurso;
	}
	
	void enviarSolicitudRecurso(){
		byte[] solCliente=new byte[PaqueteRecurso.SOL_LENGTH];
		byte[] respCliente=new byte[PaqueteRecurso.SOL_LENGTH];
		PaqueteRecurso sol = new PaqueteRecurso(solCliente);
		
		imprimeln("Generando paquete de solicitud de recursos");
		sol.setSolicitud();
		sol.setRecurso((byte) recurso);
		
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(248,solCliente);
		
		imprimeln("Invocando a receive()");
		Nucleo.receive(dameID(),respCliente);
		
		imprimeln("Procesando respuesta recibida del servidor");
		PaqueteRecurso resp = new PaqueteRecurso(respCliente);
		println("Codigo: "+resp.getOptCode());
		println("Origen: "+resp.getEmisor());
		println("Destino: "+resp.getReceptor());
		
		if(resp.getOptCode() == -1){
			println("Servidor envio TA");
			if(listener != null)
				listener.recursoLiberado(recurso);
		}if(resp.getOptCode() == -2){
			println("Servidor envio AU");
			if(listener != null)
				listener.recursoLiberado(recurso);
			
		}else if(resp.esOK()){
			println("Recurso conseguido: "+ProcesoServidor.recursos[recurso]);
			if(listener != null)
			listener.recursoConseguido(recurso);
		}
	}
	
	void enviarLiberacionRecurso(){
		byte[] solCliente=new byte[PaqueteRecurso.SOL_LENGTH];
		PaqueteRecurso sol = new PaqueteRecurso(solCliente);
		
		imprimeln("Generando paquete de liberacion de recursos");
		sol.setLiberacion();
		sol.setRecurso((byte) recurso);
		
		imprimeln("Señalamiento al núcleo para envío de mensaje");
		Nucleo.send(248,solCliente);
		if(listener != null)
			listener.recursoLiberado(recurso);
	}
	

	
	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicio de proceso");
		imprimeln("Proceso cliente en ejecucion. (Exclusion)");
		imprimeln("Esperando datos para continuar.");
		while(continuar()){
			Nucleo.suspenderProceso();
			switch(operacion){
			case Liberar:
				operacion = Operacion.Nada;
				enviarLiberacionRecurso();
				break;
			case Solicitar:
				operacion = Operacion.Nada;
				enviarSolicitudRecurso();
				break;
			case Nada:
			default:
				break;
			
			}			
		}
		enviarLiberacionRecurso();
		
	}
}
