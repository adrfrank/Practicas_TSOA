package sistemaDistribuido.sistema.exclusion.modoUsuario;

import java.util.Hashtable;
import java.util.LinkedList;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.PaqueteRecurso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.Pausador;

/**
 * 
 */
public class ProcesoServidor extends Proceso{
	public static final String[] recursos= new String[]{"Rec 1","Rec 2","Rec 3","Rec 4",};
	Hashtable<Byte,ManejadorRecurso> manejadoresRecursos;
	RecursosServidorListener listener;
	
	public RecursosServidorListener getListener() {
		return listener;
	}

	public void setListener(RecursosServidorListener listener) {
		this.listener = listener;
	}

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
		manejadoresRecursos = new Hashtable<Byte,ManejadorRecurso>();
		for(int i=0; i < recursos.length; ++i){
			ManejadorRecurso m = new ManejadorRecurso((byte) i);
			m.start();
			manejadoresRecursos.put((byte) i, m);
		}
		start();
		
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicio de proceso");
		imprimeln("Proceso servidor en ejecucion. (Exclusión)");
		byte[] solServidor=new byte[PaqueteRecurso.SOL_LENGTH];		
		
		while(continuar()){
			imprimeln("Invocando a receive() desde servidor: "+dameID());
			Nucleo.receive(dameID(),solServidor);
			PaqueteRecurso cp = new PaqueteRecurso(solServidor);
			imprimeln("Procesando petición recibida del cliente");
			
			if(cp.esSolicitud()){
				byte rec = cp.getRecurso();
				ManejadorRecurso mr = manejadoresRecursos.get(rec);
				if(mr != null){
					mr.concederRecurso(cp.getEmisor());
				}
			}else if(cp.esLiberacion()){
				byte rec = cp.getRecurso();
				ManejadorRecurso mr = manejadoresRecursos.get(rec);
				if(mr != null){
					mr.liberarRecurso(cp.getEmisor());
				}
			}
			
		}
	}
	
	class ManejadorRecurso extends Thread{
		byte recurso;
		boolean recursoLibre;
		LinkedList<Integer> colaEspera;
		Integer propietario;
		boolean continuar;
		byte[] respServidor;
		
		public ManejadorRecurso(byte recurso){
			
			colaEspera = new LinkedList<Integer>();
			this.recurso = recurso;
			recursoLibre = true;
		}
		
		
		void concederRecurso(int solicitante){
			if(recursoLibre){
				recursoLibre = false;
				propietario = solicitante;
				enviarOK(solicitante);
			}else{
				colaEspera.offer(solicitante);				
			}
			if(listener != null)
				listener.actualizarEstado(recurso, propietario, colaEspera);
		}
		
		void enviarOK(int solicite){
			imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
			respServidor=new byte[PaqueteRecurso.SOL_LENGTH];
			PaqueteRecurso cpResp = new PaqueteRecurso(respServidor);
			cpResp.setRecurso(recurso);
			cpResp.setOK();			
			cpResp.setEmisor( ProcesoServidor.this.dameID() );
			cpResp.setReceptor(solicite);
			Pausador.pausa(200);  //sin esta lÃ­nea es posible que Servidor solicite send antes que Cliente solicite receive
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			imprimeln("enviando respuesta al proceso: "+cpResp.getReceptor());
			Nucleo.send(cpResp.getReceptor(),respServidor);
		}
		
		
		void liberarRecurso(int solicitante){
			if(!recursoLibre && solicitante == propietario){
				if(colaEspera.peek() == null){
					recursoLibre = true;
					propietario = null;
				}else{
					Integer siguiente = colaEspera.poll();
					propietario = siguiente.intValue();
					enviarOK(propietario);
				}
			}
			if(listener != null)
				listener.actualizarEstado(recurso, propietario, colaEspera);
		
		}
		
		
		public void run(){
			while(continuar);
		}
	}
}
