package ejemplo_RMI;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 * @author Dariel
 */
public class Cliente implements Runnable{
	private int min;
	private int max;
	private int n;
	private String ip;
	private GestorIntervalos gestor;
	
	public Cliente(int min, int max, int n, String ip){
		this.min = min;
		this.max = max;
		this.n = n;
		this.ip = ip;
		this.gestor = new GestorIntervalos(this.n, this.min, this.max);
	}
	
	@Override
	public void run(){
		try {
			//Localizar el registro
			Registry registro = LocateRegistry.getRegistry(ip);
			
			//Localizar el servidor de asignacion
			WorkerFactory servidorAsig; 
			servidorAsig = (WorkerFactory) registro.lookup("ServidorAsignacion");
			
			//pedimos n workers
			ArrayList<Worker> trabajadores = servidorAsig.dameWorkers(n);
			
			//lista para almacenar los primos
			ArrayList<Integer> primos = new ArrayList<Integer>();
			TrataWorker[] tratadores = new TrataWorker[n];
			Thread[] hilosTratadores = new Thread[n];
			
			for(int i = 0; i < n; i++){
				tratadores[i] = new TrataWorker(trabajadores.get(i), gestor);
				hilosTratadores[i] = new Thread(tratadores[i]);
				hilosTratadores[i].start();
			}
			for(int i = 0; i < n; i++){
				hilosTratadores[i].join();//esperando al final de los tratadores
			}
			for(int i = 0; i < n; i++){
				primos.addAll(tratadores[i].getPrimos());//recopilando soluciones
			}
			System.out.println("Lista de primos: \r\n" + primos.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
