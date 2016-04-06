/*  Adrian Francisco Gonzalez Gutierrez
 *  Seccion D05
 *  Tarea 7
 * */
public class Caracteres {
	
	private String cadena = "ADRIAN";
	
	public Caracteres(){
		
		
	}
	
	public void copiar(char[] arr){
		boolean[] cub = new boolean[arr.length];
		for(int i=0; i < arr.length;++i){
			cub[i] = false;
		}
		char c;
		for(int i=0; i+cadena.length() <= arr.length; ++i ){
			for(int j=0; j < cadena.length();++j,++i){
				c = cadena.charAt(j);
				if(arr[i] != c){
					cub[i] = true;
					arr[i] = c;
				}
			}
			i--;			
			
		}
		System.out.println(arr);
		for(int i=0; i < arr.length;++i){
			if(cub[i] == true)
				System.out.print(arr[i]);
		}
		System.out.println();
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] arr;
		String Letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int n =(int) (Math.random()*50);
		//n = 6;
		arr = new char[n];
		for(int i=0; i <n ; ++i){
			arr[i]=  Letras.charAt((int)(Math.random()*Letras.length()));
		}
		System.out.println(arr);
		Caracteres c = new Caracteres();
		c.copiar(arr);
		
	}

}
