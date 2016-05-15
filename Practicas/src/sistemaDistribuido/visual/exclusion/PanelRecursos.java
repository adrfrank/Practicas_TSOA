package sistemaDistribuido.visual.exclusion;

import java.awt.*;

import sistemaDistribuido.sistema.exclusion.modoUsuario.RecursosServidorListener;
import sistemaDistribuido.util.Escribano;

public class PanelRecursos extends Panel implements RecursosServidorListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List[] lstRecs;
	Label[] lblRecs;
	String[] recursos;
	Escribano escribano;
	public PanelRecursos(String[] recursos, Escribano escribano){
		this.escribano = escribano;
		this.recursos = recursos;
		setLayout(new GridLayout(2,2));
		lstRecs = new List[recursos.length];
		lblRecs = new Label[recursos.length];
		for(int i=0; i < recursos.length; ++i){
			lstRecs[i] = new List();
			lblRecs[i] = new Label(recursos[i]);
			Panel p = new Panel();
			p.setLayout(new GridLayout(2,1));
			p.add(lblRecs[i]);
			p.add(lstRecs[i]);
			add(p);
			actualizarEstado(i,null,null);
		}
		
	}
	@Override
	public void actualizarEstado(int recurso, Integer propietario, java.util.List<Integer> cola) {
		escribano.println("Recurso actualizado: "+recurso);
		escribano.println("Propietario: "+(propietario==null?"Ninguno":propietario.toString()));
		lblRecs[recurso].setText(recursos[recurso]+" (Propietario: "+(propietario==null?"Ninguno":propietario.toString())+")");

		lstRecs[recurso].removeAll();
		if(cola != null)
			for(Integer procs : cola){
				lstRecs[recurso].add(procs.toString());
			}

	}
}
