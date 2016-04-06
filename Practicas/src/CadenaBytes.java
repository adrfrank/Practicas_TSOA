/*  Adrian Francisco Gonzalez Gutierrez
 *  Seccion D05
 *  Tarea 3 
 * */

public class CadenaBytes {
	public byte[] toBytes(String str){		
		byte[] tmp = str.getBytes();
		byte[] ret = new byte[tmp.length+1];
		ret[0] = (byte)tmp.length;
		System.arraycopy( tmp, 0, ret, 1, tmp.length );
		for(int i=0;i <ret.length;++i){
			System.out.print(ret[i]+" ");
		}
		System.out.println();
		return ret;
	}
	public String toString(byte[] bstr){
		String str = new String(bstr,1,bstr[0]);
		System.out.println(str);
		return str;
	}
	public static void main(String[] args){
		CadenaBytes c = new CadenaBytes();
		byte[] bstr = c.toBytes("aa AA bb cc");
		c.toString(bstr);
		
	}
}
