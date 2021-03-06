package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

/**
 * 
 */
public class ProcesoCliente extends Proceso{
	private Libreria lib;
	int sum1,sum2,minuendo,sustraendo,factor1,factor2,dividendo,divisor;
	int[] sumandos;
	
	public int[] getSumandos() {
		return sumandos;
	}

	public void setSumandos(int[] sumandos) {
		this.sumandos = sumandos;
	}

	public int getSum1() {
		return sum1;
	}

	public void setSum1(int sum1) {
		this.sum1 = sum1;
	}

	public int getSum2() {
		return sum2;
	}

	public void setSum2(int sum2) {
		this.sum2 = sum2;
	}

	public int getMinuendo() {
		return minuendo;
	}

	public void setMinuendo(int minuendo) {
		this.minuendo = minuendo;
	}

	public int getSustraendo() {
		return sustraendo;
	}

	public void setSustraendo(int sustraendo) {
		this.sustraendo = sustraendo;
	}

	public int getFactor1() {
		return factor1;
	}

	public void setFactor1(int factor1) {
		this.factor1 = factor1;
	}

	public int getFactor2() {
		return factor2;
	}

	public void setFactor2(int factor2) {
		this.factor2 = factor2;
	}

	public int getDividendo() {
		return dividendo;
	}

	public void setDividendo(int dividendo) {
		this.dividendo = dividendo;
	}

	public int getDivisor() {
		return divisor;
	}

	public void setDivisor(int divisor) {
		this.divisor = divisor;
	}

	/**
	 * 
	 */
	public ProcesoCliente(Escribano esc){
		super(esc);

		lib=new LibreriaCliente(esc);  //luego con esta comentando la anterior, para subrutina servidor remota
		start();
	}


	/**
	 * Programa Cliente
	 */
	public void run(){
		imprimeln("Proceso cliente en ejecucion.");
		while(this.continuar()){
			imprimeln("Esperando datos para continuar.");
			Nucleo.suspenderProceso();
			imprimeln("Salio de suspenderProceso");

			int resultado;
			try {
				resultado=lib.suma(sum1,sum2);
				imprimeln("suma="+resultado);
				resultado=lib.resta(minuendo,sustraendo);
				imprimeln("diferencia="+resultado);
				resultado=lib.multiplicacion(factor1,factor2);
				imprimeln("multiplicacion="+resultado);
//				resultado=lib.division(dividendo,divisor);
//				imprimeln("cociente="+resultado);
				resultado=lib.sumatoria(sumandos);
				imprimeln("Sumatoria="+resultado);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				imprimeln("Error de comunicacion");
			}
			
		}
		imprimeln("Fin del cliente.");
	}
}
