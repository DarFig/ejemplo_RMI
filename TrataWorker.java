package ejemplo_RMI;

import java.util.ArrayList;

/**
 * @author Dariel
 */
public class TrataWorker implements Runnable{
	private Worker trabajador;
	private ArrayList<Integer> primos;
	private GestorIntervalos gestor;

	
	public TrataWorker(Worker worker, GestorIntervalos gestor){
		this.trabajador = worker;
		this.primos = new ArrayList<Integer>();
		this.gestor = gestor;
	}


	@Override
	public void run() {
		ArrayList<Integer> intervalo;
		try{
			while((intervalo = gestor.entregarIntervalo()) != null){
				primos.addAll(trabajador.encuentraPrimos(intervalo.get(0), intervalo.get(1)));
            }
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Integer> getPrimos(){
		return primos;
	}
}
