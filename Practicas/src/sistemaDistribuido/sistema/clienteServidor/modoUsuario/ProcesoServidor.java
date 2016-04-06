package sistemaDistribuido.sistema.clienteServidor.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.ConvertidorPaquetes;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.util.ManejadorArchivos;
import sistemaDistribuido.util.Pausador;

/**
 * 
 */
public class ProcesoServidor extends Proceso{

	/**
	 * 
	 */
	public ProcesoServidor(Escribano esc){
		super(esc);
		start();
	}

	/**
	 * 
	 */
	public void run(){
		imprimeln("Inicio de proceso");
		imprimeln("Proceso servidor en ejecucion.");
		byte[] solServidor=new byte[ConvertidorPaquetes.SOL_LENGTH];
		byte[] respServidor;
		
		
		while(continuar()){
			imprimeln("Invocando a receive()");
			Nucleo.receive(dameID(),solServidor);
			ConvertidorPaquetes cp = new ConvertidorPaquetes(solServidor);
			imprimeln("Procesando petición recibida del cliente");
			short codop=cp.getOptCode();
			String cdato = cp.getStringData();
			String respuesta="";
			ManejadorArchivos manArchivos = new ManejadorArchivos();
			switch(codop){
			case 0: //Crear
				imprimeln("Operacion: crear");
				imprimeln("Datos: "+cdato);
				manArchivos.crear(cdato);
				respuesta = "creado: "+cdato;
				break;
			case 1: //Eliminar
				imprimeln("Operacion: eliminar");
				imprimeln("Datos: "+cdato);
				manArchivos.eliminar(cdato);
				respuesta = "eliminado: "+cdato;
				break;
			case 2: //Lee
				imprimeln("Operacion: leer");
				imprimeln("Datos: "+cdato);
				String l = manArchivos.leer(cdato);
				respuesta = "leido "+cdato+": "+l;
				break;
			case 3: //Escribir
				imprimeln("Operacion: escribir");
				imprimeln("Datos: "+cdato);
				if(cdato.split(",").length > 0){
					manArchivos.escribir(cdato.split(",")[0], cdato.split(",")[1]);
					respuesta = "escrito: "+cdato;
				}else{
					respuesta = "error ";
				}
				
				break;
				
			}			
			imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
			respServidor=new byte[ConvertidorPaquetes.SOL_LENGTH];
			ConvertidorPaquetes cpResp = new ConvertidorPaquetes(respServidor);
			cpResp.setData(respuesta);
			Pausador.pausa(1000);  //sin esta línea es posible que Servidor solicite send antes que Cliente solicite receive
			imprimeln("Señalamiento al núcleo para envío de mensaje");
			imprimeln("enviando respuesta");
			Nucleo.send(0,respServidor);
		}
	}
}
