package sistemaDistribuido.visual.exclusion;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.exclusion.modoUsuario.ProcesoCliente;
import sistemaDistribuido.sistema.exclusion.modoUsuario.RecursosClienteListener;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import java.awt.*;
import java.awt.event.*;


public class ClienteFrame extends ProcesoFrame implements RecursosClienteListener{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;

	PanelSolicitudRecursos recursosPanel;

	public ClienteFrame(MicroNucleoFrame frameNucleo){
		super(frameNucleo,"Cliente de Archivo (exclusion)");
		proc=new ProcesoCliente(this);
		fijarProceso(proc);
		proc.setListener(this);
		//add("South",construirPanelSolicitud());
		recursosPanel = new PanelSolicitudRecursos(proc.getRecursos());
		recursosPanel.addListener(new ManejadorSolicitud());
		add("South",recursosPanel);
		validate();
		
	}

	

	class ManejadorSolicitud implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String com=e.getActionCommand();
			if (com.equals("Solicitar")){
				proc.sendMessage((short)3,"");
				Nucleo.reanudarProceso(proc);
				
			} else if(com.startsWith("sol,")){
				int n = Integer.parseInt(com.split(",")[1]);
				recursosPanel.setEnableSols(false);
				recursosPanel.setEnableLibs(false);
				proc.solicitarRecurso(n);
				Nucleo.reanudarProceso(proc);
				
			}else if(com.startsWith("lib,")){
				int n = Integer.parseInt(com.split(",")[1]);
				proc.liberarRecurso(n);
				Nucleo.reanudarProceso(proc);
			}
		}
	}
	
	class PanelSolicitudRecursos extends Panel{
		private static final long serialVersionUID = 1L;
		String[] recursos;
		Button[] btnSols, btnLibs;
		Label[] lblRecs;
		
		public void setEnableSols(boolean state){
			for(Button btn : btnSols){
				btn.setEnabled(state);
			}
		}
		
		public void setEnableLibs(boolean state){
			for(Button btn : btnLibs){
				btn.setEnabled(state);
			}
		}
		
		public void setEnableSol(int n, boolean state){
			btnSols[n].setEnabled(state);
		}
		
		public void setEnableLib(int n,boolean state){
			btnLibs[n].setEnabled(state);
		}
		
		void addListener(ActionListener al){
			for(Button btn : btnSols){
				btn.addActionListener(al);
			}
			for(Button btn : btnLibs){
				btn.addActionListener(al);
			}
		}
		
		public PanelSolicitudRecursos(String[] recursos){
			this.recursos = recursos;
			btnSols = new Button[recursos.length];
			btnLibs = new Button[recursos.length];
			lblRecs = new Label[recursos.length];
			setLayout(new GridLayout(recursos.length,3));
			for(int i=0; i < recursos.length; ++i){
				btnSols[i] = new Button("Solicitar "+recursos[i]);
				btnSols[i].setActionCommand("sol,"+i);
				btnLibs[i] = new Button("Liberar "+recursos[i]);
				btnLibs[i].setEnabled(false);
				btnLibs[i].setActionCommand("lib,"+i);
				lblRecs[i] = new Label(recursos[i]);
				add(lblRecs[i]);
				add(btnSols[i]);
				add(btnLibs[i]);
			}
			
		}

		
		
	}

	@Override
	public void recursoConseguido(int recurso) {
		this.recursosPanel.setEnableLib(recurso, true);
		
	}

	@Override
	public void recursoLiberado(int recurso) {
		// TODO Auto-generated method stub
		this.recursosPanel.setEnableLibs(false);
		this.recursosPanel.setEnableSols(true);
		
	}
}
