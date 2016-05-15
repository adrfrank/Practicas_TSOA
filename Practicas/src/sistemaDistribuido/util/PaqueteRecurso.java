package sistemaDistribuido.util;

public class PaqueteRecurso extends ConvertidorPaquetes {
	static final int RECURSO_INDEX=10;
	static final short SOLICITUD_CODE=1;
	static final short LIBERACION_CODE=2;
	static final short OK_CODE=3;
	
	public PaqueteRecurso(byte[] bytes) {
		super(bytes);
		// TODO Auto-generated constructor stub
	}

	public void setLiberacion(){
		this.setOptCode(LIBERACION_CODE);
	}
	
	public void setSolicitud(){
		this.setOptCode(SOLICITUD_CODE);
	}
	
	public void setOK(){
		this.setOptCode(OK_CODE);
	}
	
	public boolean esLiberacion(){
		return this.getOptCode() == LIBERACION_CODE;
	}
	
	public boolean esSolicitud(){
		return this.getOptCode() == SOLICITUD_CODE;
	}
	
	public boolean esOK(){
		return this.getOptCode() == OK_CODE;
	}
	
	public void setRecurso(byte recurso){
		bytes[RECURSO_INDEX] = recurso;
	}
	
	public byte getRecurso(){
		return bytes[RECURSO_INDEX];
	}
}
