/*  Adrian Francisco Gonzalez Gutierrez
 *  Seccion D05
 *  Tarea 6
 * */
public class Proceso {

	public void porValor(int a, int b){
		System.out.println("porValor[Antes]: param1: "+a+" param2: "+b);
		a = b * 2;
		b =  a * 3;
		System.out.println("porValor[Despues]: param1: "+a+" param2: "+b);
	}
	public void porReferencia(int[] a, int[] b){
		System.out.println("porReferencia[Antes]: param1: "+a[0]+" param2: "+b[0]);
		a[0] = b[0] * 2;
		b[0] = a[0]*3;
		System.out.println("porReferencia[Despues]: param1: "+a[0]+" param2: "+b[0]);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a=4;
		int[] arr = new int[1];
		Proceso p = new Proceso();
		System.out.println("Main: "+a);
		p.porValor(a, a);
		System.out.println("Main: "+a);
		arr[0] = a;
		p.porReferencia(arr, arr);
		System.out.println("Main: "+arr[0]);
		
		
	}

}
