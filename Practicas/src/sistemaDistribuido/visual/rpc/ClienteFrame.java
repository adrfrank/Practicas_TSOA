package sistemaDistribuido.visual.rpc;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.rpc.modoUsuario.ProcesoCliente;
import sistemaDistribuido.util.OperatorHelper;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Label;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteFrame extends ProcesoFrame{
	private static final long serialVersionUID=1;
	private ProcesoCliente proc;
	private TextField campo1,campo2,campo3,campo4;
	private Button botonSolicitud;

	public ClienteFrame(RPCFrame frameNucleo){
		super(frameNucleo,"Cliente de Archivos");
		add("South",construirPanelSolicitud());
		validate();
		proc=new ProcesoCliente(this);
		fijarProceso(proc);
	}

	public Panel construirPanelSolicitud(){
		Panel pSolicitud,pcodop1,pcodop2,pcodop3,pcodop4,pboton;
		pSolicitud=new Panel();
		pcodop1=new Panel();
		pcodop2=new Panel();
		pcodop3=new Panel();
		pcodop4=new Panel();
		pboton=new Panel();
		campo1=new TextField(10);
		campo2=new TextField(10);
		campo3=new TextField(10);
		campo4=new TextField(10);
		pSolicitud.setLayout(new GridLayout(5,1));

		pcodop1.add(new Label("Suma >> "));
		pcodop1.add(new Label("x y"));
		pcodop1.add(campo1);

		pcodop2.add(new Label("Resta >> "));
		pcodop2.add(new Label("x y"));
		pcodop2.add(campo2);

		pcodop3.add(new Label("multiplicacion >> "));
		pcodop3.add(new Label("x y"));
		pcodop3.add(campo3);

		pcodop4.add(new Label("sumatoria >> "));
		pcodop4.add(new Label("x y ..."));
		pcodop4.add(campo4);

		botonSolicitud=new Button("Solicitar");
		pboton.add(botonSolicitud);
		botonSolicitud.addActionListener(new ManejadorSolicitud());

		pSolicitud.add(pcodop1);
		pSolicitud.add(pcodop2);
		pSolicitud.add(pcodop3);
		pSolicitud.add(pcodop4);
		pSolicitud.add(pboton);

		return pSolicitud;
	}

	class ManejadorSolicitud implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String com=e.getActionCommand();
			if (com.equals("Solicitar")){
				botonSolicitud.setEnabled(false);
				int[] ops;
				ops = OperatorHelper.GetOperators(campo1.getText());
				if(ops.length >= 2 ){
					proc.setSum1(ops[0]);
					proc.setSum2(ops[1]);					
				}
				
				ops = OperatorHelper.GetOperators(campo2.getText());
				if(ops.length >= 2 ){
					proc.setMinuendo(ops[0]);
					proc.setSustraendo(ops[1]);					
				}
				
				ops = OperatorHelper.GetOperators(campo3.getText());
				if(ops.length >= 2 ){
					proc.setFactor1(ops[0]);
					proc.setFactor2(ops[1]);					
				}
//				ops = OperatorHelper.GetOperators(campo4.getText());
//				if(ops.length >= 2 ){
//					proc.setDividendo(ops[0]);	
//					proc.setDivisor(ops[1]);
//									
//				}
				ops = OperatorHelper.GetOperators(campo4.getText());
				if(ops.length >= 1 ){
					proc.setSumandos(ops);
				}
				
				//...
				Nucleo.reanudarProceso(proc);
				botonSolicitud.setEnabled(true);
			}
		}
	}
}
