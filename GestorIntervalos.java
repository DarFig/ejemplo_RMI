package ejemplo_RMI;

import java.util.ArrayList;

/**
 * @author Dariel 
 */
public class GestorIntervalos {
	private int min;
	private int max;
	private int subSize;
	
	
	public GestorIntervalos(int n, int min, int max){
		this.min = min;
		this.max = max;
		//si el intervalo total es mayor que n*100 se usa como factor de división
		if((max-min)+1 >= n*100){
			this.subSize = ((max-min)+1)/n*100;
		}else{
			this.subSize = ((max-min)+1)/n*2;
		}
	}
	
	public synchronized ArrayList<Integer> entregarIntervalo(){
		ArrayList<Integer> subIntervalo = new ArrayList<Integer>();
		
		if(min > max){
			return null;
		}else{
			if((min + subSize) > max){//si subintervalo > que intervalo
				subIntervalo.add(min);
				subIntervalo.add(max);//se envía lo que queda
				min = max + 1;
				return subIntervalo;
			}else{
				subIntervalo.add(min);
				min += subSize;
				subIntervalo.add(min);
				min++;
				return subIntervalo;
			}	
		}
	}
}
