package sistemaDistribuido.util;

public class OperatorHelper {

	public static int[] GetOperators(String cad){
		String[] strops = cad.split(" ");
		int[] ops = new int[strops.length];
		int tmp;
		for(int i=0; i < strops.length ; ++i){
			tmp=0;
			try {
				tmp=Integer.parseInt(strops[i]);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			ops[i] = tmp;
		}
		return ops;
	}
}
