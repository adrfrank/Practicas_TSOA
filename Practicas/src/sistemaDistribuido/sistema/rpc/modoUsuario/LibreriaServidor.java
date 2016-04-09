package sistemaDistribuido.sistema.rpc.modoUsuario;

import sistemaDistribuido.sistema.rpc.modoUsuario.Libreria;
import sistemaDistribuido.util.Escribano;

public class LibreriaServidor extends Libreria{

	/**
	 * 
	 */
	public LibreriaServidor(Escribano esc){
		super(esc);
	}

	/**
	 * Ejemplo de servidor suma verdadera
	 */
	protected void suma(){
		//saca parametros de pila
		 stack.push(stack.pop()+stack.pop());
		//devuelve valor izquierdo
	}

	@Override
	protected void resta() {
		// TODO Auto-generated method stub
		stack.push(stack.pop()-stack.pop());
	}

	@Override
	protected void multiplicacion() {
		stack.push(stack.pop()*stack.pop());
		
	}

	@Override
	protected void division() {
		stack.push(stack.pop()/stack.pop());
		
	}

	@Override
	protected void sumatoria() {
		int sum = 0;
		while(stack.size() > 0){
			sum += stack.pop();
		}
		stack.push(sum);
		
	}

}