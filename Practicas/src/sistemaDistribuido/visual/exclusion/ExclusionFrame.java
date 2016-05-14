package sistemaDistribuido.visual.exclusion;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.PanelClienteServidor;
import sistemaDistribuido.visual.exclusion.ClienteFrame;
import sistemaDistribuido.visual.exclusion.ServidorFrame;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExclusionFrame extends MicroNucleoFrame{
	private static final long serialVersionUID=1;
	private PanelClienteServidor panelBotones;

	public ExclusionFrame(){
		setTitle("Practica 7 - Exclusión");
	}

	protected Panel construirPanelSur(){
		panelBotones=new PanelClienteServidor();
		panelBotones.agregarActionListener(new ManejadorBotones());
		return panelBotones;
	}

	class ManejadorBotones implements ActionListener{

		public void actionPerformed(ActionEvent e){
			String com=e.getActionCommand();
			if (com.equals("Cliente")){
				levantarProcesoFrame(new ClienteFrame(ExclusionFrame.this));
			}
			else if (com.equals("Servidor")){
				levantarProcesoFrame(new ServidorFrame(ExclusionFrame.this));
			}
			
		}
	}



	public static void main(String args[]){
		ExclusionFrame frame=new ExclusionFrame();
		frame.setVisible(true);
		frame.imprimeln("Ventana del micronucleo iniciada.");
		Nucleo.iniciarSistema(frame,2001,2002,frame);
	}
}
