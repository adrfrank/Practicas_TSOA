package sistemaDistribuido.visual.exclusion;

import sistemaDistribuido.sistema.exclusion.modoUsuario.ProcesoServidor;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;

public class ServidorFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoServidor proc;
	PanelRecursos panelRecursos;
	public ServidorFrame(MicroNucleoFrame frameNucleo){
		super(frameNucleo,"Servidor de Archivos (exclusion)");
		proc=new ProcesoServidor(this);
		fijarProceso(proc);
		panelRecursos = new PanelRecursos(ProcesoServidor.recursos,this);
		proc.setListener(panelRecursos);
		add(panelRecursos, "South");
	}
}