/*  Adrian Francisco Gonzalez Gutierrez
 *  Seccion D05
 *  Tarea 2 
 * */
public class Convertidor {
	boolean messages = false;
	static String sep = " | ";
	public String convertir(byte n){
		String s="";
		int t=0x80;
		if(messages) System.out.print(n+" ");
		for(int i=0; i < 8; ++i){
			if(i % 8 == 0)
				s +=sep;
			s += (n&t>>>i)!= 0?"1":"0";
		}
		s += sep;
		System.out.println(s);
		return s;
	}
	
	public String convertir(short n){
		String s="";
		int t=0x8000;
		if(messages) System.out.print(n+" ");
		for(int i=0; i < 16; ++i){
			if(i % 8 == 0)
				s += sep;
			s += (n&t>>>i)!= 0?"1":"0";
		}
		s += sep;
		System.out.println(s);
		return s;
	}
	
	public String convertir(int n){
		String s="";
		int t=0x80000000;
		if(messages) System.out.print(n+" ");
		for(int i=0; i < 32; ++i){
			if(i % 8 == 0)
				s += sep;
			s += (n&t>>>i)!= 0?"1":"0";
		}
		s += sep;
		System.out.println(s);
		return s;
	}
	
	public String convertir(long n){
		String s="";
		long t=0x8000000000000000l;
		if(messages) System.out.print(n+" ");
		for(int i=0; i < 64; ++i){
			if(i % 8 == 0)
				s += sep;
			s += (n&t>>>i)!= 0?"1":"0";
		}
		s += sep;
		System.out.println(s);
		return s;
	}
	
	public static void main(String[] args){
		Convertidor c= new  Convertidor();
		byte b = (byte)0x88;
		short s = (short)0xFFcF;
		int i= 0x80000000;
		long l = 0x0000000000000000l;
		c.convertir(b);
		c.convertir(s);
		c.convertir(i);
		c.convertir(l);
		c.convertir((int) 0x01);
		c.convertir((int) 0x02);
		c.convertir((int) 0x01); 
		c.convertir((int) 0x80);
		c.convertir((int) 0x100);
		c.convertir((int) 0x400);
		c.convertir((int) 0x7fffffff);
		c.convertir((int) 0xffffffff);
		c.convertir((int) 0xfffffffe);
		c.convertir((int) 0xffffff81);
		c.convertir((int) 0xffffff80);
		c.convertir((int) 0x80000000);
		test();
	}
	
	private static void test(){
		Convertidor c = new Convertidor();	
		assert c.convertir((long)0x01).equals(c.convertir((long)1));
		assert c.convertir(0xffffffffffffffffl).equals(c.convertir((long)-1));
		assert c.convertir((int) 0x01).equals(c.convertir(1));
		assert c.convertir((int) 0x02).equals(c.convertir(2)); 
		assert c.convertir((int) 0x80).equals(c.convertir(128));
		assert c.convertir((int) 0x100).equals(c.convertir(256));
		assert c.convertir((int) 0x400).equals(c.convertir(1024));
		assert c.convertir((int) 0x7fffffff).equals(c.convertir(2147483647));
		assert c.convertir((int) 0xffffffff).equals(c.convertir(-1));
		assert c.convertir((int) 0xfffffffe).equals(c.convertir(-2));
		assert c.convertir((int) 0xffffff81).equals(c.convertir(-127));
		assert c.convertir((int) 0xffffff80).equals(c.convertir(-128));
		assert c.convertir((int) 0x80000000).equals(c.convertir(-2147483648));
		assert c.convertir((int) 1).equals(" | 00000000 | 00000000 | 00000000 | 00000001 | ");
		assert c.convertir((int) 2).equals(" | 00000000 | 00000000 | 00000000 | 00000010 | ");
		assert c.convertir((int) 128).equals(" | 00000000 | 00000000 | 00000000 | 10000000 | "); 
		assert c.convertir((int) 256).equals(" | 00000000 | 00000000 | 00000001 | 00000000 | ");
		assert c.convertir((int) 1024).equals(" | 00000000 | 00000000 | 00000100 | 00000000 | ");
		assert c.convertir((int) 2147483647).equals(" | 01111111 | 11111111 | 11111111 | 11111111 | ");
		assert c.convertir((int) -1).equals(" | 11111111 | 11111111 | 11111111 | 11111111 | ");
		assert c.convertir((int) -2).equals(" | 11111111 | 11111111 | 11111111 | 11111110 | ");
		assert c.convertir((int) -127).equals(" | 11111111 | 11111111 | 11111111 | 10000001 | ");
		assert c.convertir((int) -128).equals(" | 11111111 | 11111111 | 11111111 | 10000000 | ");
		assert c.convertir((int) -2147483648).equals(" | 10000000 | 00000000 | 00000000 | 00000000 | ");
		assert c.convertir((short) 1).equals(" | 00000000 | 00000001 | ");
		assert c.convertir((short) 2).equals(" | 00000000 | 00000010 | ");
		assert c.convertir((short) 128).equals(" | 00000000 | 10000000 | "); 
		assert c.convertir((short) 256).equals(" | 00000001 | 00000000 | ");
		assert c.convertir((short) 1024).equals(" | 00000100 | 00000000 | ");
		assert c.convertir((short) -1).equals(" | 11111111 | 11111111 | ");
		assert c.convertir((short) -2).equals(" | 11111111 | 11111110 | ");
		assert c.convertir((short) -127).equals(" | 11111111 | 10000001 | ");
		
	}
	
	
}
