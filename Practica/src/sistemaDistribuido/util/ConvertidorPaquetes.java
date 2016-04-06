package sistemaDistribuido.util;

public class ConvertidorPaquetes {
	
	/*	Estructura de mensaje
	 *  Id del emisor: 4 bytes
	 *  Id del receptor: 4 bytes
	 *  Cod de operación: 2 bytes
	 *  Datos: 1014 bytes
	 * 
	 * */
	
	byte[] bytes;
	static final int EMISOR_INDEX=0;
	static final int EMISOR_LENGTH = 4;
	static final int RECEPTOR_INDEX=4;
	static final int RECEPTOR_LENGTH=4;
	static final int OPCODE_INDEX=8;
	static final int OPCODE_LENGTH=2;
	static final int DATA_INDEX=10;
	static final int DATA_LENGTH=1014;
	public static final int SOL_LENGTH=1024;
	public ConvertidorPaquetes(byte[] bytes){
		this.bytes = bytes;
	}
	public int getEmisor(){
		byte[] btmp = new byte[EMISOR_LENGTH];
		System.arraycopy(bytes, EMISOR_INDEX, btmp, 0, EMISOR_LENGTH);
		int tmp = Parametros.desempaquetarInt(btmp);
		return tmp;
	}
	public void setEmisor(int id){
		
	}
	public int getReceptor(){
		byte[] btmp = new byte[RECEPTOR_LENGTH];
		System.arraycopy(bytes, RECEPTOR_INDEX, btmp, 0, RECEPTOR_LENGTH);
		int tmp = Parametros.desempaquetarInt(btmp);
		return tmp;
		
	}
	public void setReceptor(int id){
		byte[] btmp = Parametros.empaquetar(id);
		System.arraycopy(btmp, 0, bytes, RECEPTOR_INDEX, RECEPTOR_LENGTH);
	}
	
	public void setOptCode(short code){
		byte[] btmp = Parametros.empaquetar(code);
		System.arraycopy(btmp, 0, bytes, OPCODE_INDEX, OPCODE_LENGTH);
	}
	
	public short getOptCode(){
		byte[] btmp = new byte[OPCODE_LENGTH];
		System.arraycopy(bytes, OPCODE_INDEX, btmp, 0, OPCODE_LENGTH);
		short tmp = Parametros.desempaquetarShort(btmp);
		return tmp;
	}
	
	public byte[] getData(){
		byte[] btmp = new byte[Math.min(DATA_LENGTH, bytes.length-10)];
		System.arraycopy(bytes, DATA_INDEX, btmp, 0, Math.min(DATA_LENGTH, bytes.length-10) );
		return btmp;
	}
	public void setData(byte[] data){
		System.arraycopy(data, 0, bytes, DATA_INDEX, data.length);
	}
	
	
	public void setData(String data){
		byte[] msgData = new byte[data.length()+1];
		msgData[0] = (byte) data.length();
		byte[] cadbytes = data.getBytes();
		System.arraycopy(cadbytes, 0, msgData, 1, cadbytes.length);
		setData(msgData);
	}
	public String getStringData(){
		byte[] btmp = getData();
		String cad = new String(btmp,1,btmp[0]);
		return cad;
	}
}
