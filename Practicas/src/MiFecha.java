
public class MiFecha {
	private int dia;
	private int mes;
	private int anio;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private boolean verifica(int dia,int mes,int anio){
		
		return true;
	}
		
	public int dameDia(){ return dia;}
	public int dameMes(){ return mes;}
	public int dameAnio(){ return anio;}

	public void fijaDia(int dia){
		if(verifica(dia,this.mes,this.anio)){
			this.dia = dia;
		}
	}
	public void fijaMes(int mes){
		if(verifica(this.dia,mes,this.anio)){
			this.mes = mes;
		}
	}
	public void fijaAnio(int anio){
		if(verifica(this.dia,this.mes,anio)){
			this.anio = anio;
		}
	}
}
