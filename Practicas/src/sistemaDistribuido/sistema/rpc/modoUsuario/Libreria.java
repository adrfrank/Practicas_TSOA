package sistemaDistribuido.sistema.rpc.modoUsuario;

import java.util.Stack;

import sistemaDistribuido.util.Escribano;

public abstract class Libreria{
	private Escribano esc;
	protected Stack<Integer> stack;
	public static final short SUMA=0,RESTA=1,MULTIPLICACION=2,DIVISION=3,SUMATORIA=4;
	
	/**
	 * 
	 */
	public Libreria(Escribano esc){
		this.esc=esc;
		stack = new Stack<Integer>();
	}

	/**
	 * 
	 */
	protected void imprime(String s){
		esc.imprime(s);
	}

	/**
	 * 
	 */
	protected void imprimeln(String s){
		esc.imprimeln(s);
	}

	/**
	 * Ejemplo para el paso intermedio de parametros en pila.
	 * Esto es lo que esta disponible como interfaz al usuario programador
	 */
	/*public int suma(int sum1,int sum2){
    //...
    suma();
    //...
    return 0;
  }*/

	public int suma(int sum1,int sum2){
		imprimeln("Llamando a procedimiento de resguardo");
		stack.push(sum2);
		stack.push(sum1);
		
		suma();
		return stack.pop();
	}

	public int resta(int minuendo,int sustraendo){
		imprimeln("Llamando a procedimiento de resguardo");
		stack.push(sustraendo);
		stack.push(minuendo);
		
		resta();
		return (Integer)stack.pop();
	}

	public int multiplicacion(int multiplicando,int multiplicador){
		imprimeln("Llamando a procedimiento de resguardo");
		stack.push(multiplicador);
		stack.push(multiplicando);
		
		multiplicacion();
		return stack.pop();
	}

	public int division(int dividendo,int divisor){
		imprimeln("Llamando a procedimiento de resguardo");
		stack.push(divisor);
		stack.push(dividendo);
		
		
		
		division();
		return stack.pop();
	}

	public int sumatoria(int[] nums){
		imprimeln("Llamando a procedimiento de resguardo");
		for(int i=0; i < nums.length; ++i){
			stack.push(nums[i]);
		}
		sumatoria();
		return stack.pop();
	}
	
	/**
	 * Servidor suma verdadera generable por un compilador estandar
	 * o resguardo de la misma por un compilador de resguardos.
	 */
	protected abstract void suma();
	protected abstract void resta();
	protected abstract void multiplicacion();
	protected abstract void division();
	protected abstract void sumatoria();

}