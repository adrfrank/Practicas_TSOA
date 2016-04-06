/**************************************	
 *  Adrian Francisco Gonzalez Gutierrez
 *  212354584
 *  Tarea 8 
 ****************************************/

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class ClaseAdministradora {
	Hashtable<Integer,Materia> materias;
	int id_secuencial;
	public ClaseAdministradora(){
		materias = new Hashtable<Integer,Materia>();
		id_secuencial = 0;
	}
	
	public int agregarMateria(String clave, String materia){
		id_secuencial ++;
		Materia m = new Materia();
		m.setClave(clave);
		m.setMateria(materia);
		materias.put(new Integer(id_secuencial), m);
		imprimirMaterias();
		return id_secuencial;
	}
	
	public Materia obtenerMateria(int id){
		Materia m=null;
		m = materias.get(id);
		return m;
	}
	
	public boolean eliminarMateria(int id){
		imprimirMaterias();
		return materias.remove(id)==null;		
	}
	
	public void imprimirMaterias(){
		Enumeration<Integer> keys = materias.keys();
		Integer i;
		while(keys.hasMoreElements()){
			i=keys.nextElement();
			Materia m = materias.get(i);
			System.out.println(i+"\t| "+m.getClave()+"\t| "+m.getMateria());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String op,tmp;
		Scanner s = new Scanner(System.in);
		ClaseAdministradora am = new ClaseAdministradora();
		do{
			System.out.println("1. Agregar");
			System.out.println("2. Eliminar");
			System.out.println("3. Mostrar");
			System.out.println("4. Mostrar todos");
			System.out.println("0. Salir");
			System.out.println();
			System.out.println("Opción: ");
			op = s.nextLine().trim();
			switch(op){
			case "1"://Agregar
				String nombre,clave;
				System.out.println("Nombre de la materia: ");
				nombre = s.nextLine();
				System.out.println("Clave de materia: ");
				clave = s.nextLine();
				am.agregarMateria(clave, nombre);
				break;
			case "2"://Eliminar
				System.out.println("Id de matera a eliminar: ");
				tmp = s.nextLine();
				try{
					Integer id = Integer.parseInt(tmp);
					am.eliminarMateria(id);
				}catch(Exception e){
					System.out.println("Id no válido");
				}
				break;
			case "3"://Mostrar materia
				System.out.println("Ingrese el id de la materia");
				try{
					tmp = s.nextLine();
					Integer id = Integer.parseInt(tmp);
					Materia m = am.obtenerMateria(id);
					if(m== null)
						System.out.println("La materia no existe");
					else
						System.out.println(m);
				}catch(Exception e){
					System.out.println("Id no válido");
				}				
				break;
			case "4"://Imprimir materias
				am.imprimirMaterias();
				break;
			case "0":
				System.out.println("Saliendo");
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}
			
		}while (!op.equals("0"));
		s.close();
	}
	

}

class Materia{
	String clave;
	String materia;
	public String getClave(){
		return clave;
	}
	public void setClave(String c){
		clave = c;
	}
	public String getMateria(){
		return materia;
	}
	public void setMateria(String m){
		materia = m;
	}

	public String toString(){
		return "----------\nClave: "+clave+"\nNombre: "+materia+"\n----------\n";
		
	}
}
