package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.ParMaquinaProceso;
import sistemaDistribuido.visual.rpc.DespleganteConexiones;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;

public class ProgramaConector{
	private DespleganteConexiones desplegante;
	private Hashtable<Integer,DatosServidor> conexiones;   //las llaves que provee DespleganteConexiones

	/**
	 * 
	 */
	public ProgramaConector(DespleganteConexiones desplegante){
		this.desplegante=desplegante;
	}

	/**
	 * Inicializar tablas en programa conector
	 */
	public void inicializar(){
		conexiones=new Hashtable<Integer,DatosServidor>();
	}

	/**
	 * Remueve tuplas visualizadas en la interfaz gr�fica registradas en tabla conexiones
	 */
	private void removerConexiones(){
		Set<Integer> s=conexiones.keySet();
		Iterator<Integer> i=s.iterator();
		while(i.hasNext()){
			desplegante.removerServidor(((Integer)i.next()).intValue());
			i.remove();
		}
	}

	/**
	 * Al solicitar que se termine el proceso, por si se implementa como tal
	 */
	public void terminar() {
		removerConexiones();
		desplegante.finalizar();
	}

	public int registro(String nombreServidor, String version,
			ParMaquinaProceso asa) {
		
		int id = desplegante.agregarServidor(nombreServidor, version, asa.dameIP(), String.valueOf(asa.dameID()));
		DatosServidor ds = new DatosServidor(nombreServidor, version,asa);
		conexiones.put(id, ds);

		return id;
	}

	public boolean desregistro(String nombreServidor, String version,
			int identificacionUnica) {
		
		DatosServidor ds = conexiones.get(identificacionUnica);
		if(ds != null && ds.getNombre().equalsIgnoreCase(nombreServidor)
				&& ds.getVersion().equalsIgnoreCase(version)){
			desplegante.removerServidor(identificacionUnica);
			return true;
		}	
		return false;
	}
	class DatosServidor {
		String nombre;
		String version;
		ParMaquinaProceso asa;
		
		public DatosServidor(String nombre, String version,
				ParMaquinaProceso asa) {
			this.nombre = nombre;
			this.version = version;
			this.asa = asa;
		}
		
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public ParMaquinaProceso getAsa() {
			return asa;
		}
		public void setAsa(ParMaquinaProceso asa) {
			this.asa = asa;
		}

	}
	public ParMaquinaProceso busqueda(String nombreServidor, String version) {
		Iterator<Entry<Integer, DatosServidor>> it = conexiones.entrySet().iterator();
		List<DatosServidor> servidores = new ArrayList<DatosServidor>();
		while(it.hasNext()){
			Entry<Integer, DatosServidor> ds = it.next();
			if(ds.getValue().getNombre().equalsIgnoreCase(nombreServidor) 
					&& ds.getValue().getVersion().equalsIgnoreCase(version))
			{
				servidores.add(ds.getValue());
			}
		}
		if(servidores.size() > 0){
			int n =(int)( Math.random() * servidores.size());
			return servidores.get(n).getAsa();
		}
		return null;
	}
}
