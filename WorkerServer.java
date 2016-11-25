package ejemplo_RMI;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * @author Dariel
 */
@SuppressWarnings("serial")
public class WorkerServer extends UnicastRemoteObject implements Worker, Runnable {
	
	private String ip;
	

	public WorkerServer(String ip) throws RemoteException{
		super();
		this.ip = ip;
	}
	/**
	 * Devuelve los primos dentro del intervalo [min,max]
	 */
	@Override
	public ArrayList<Integer> encuentraPrimos(int min, int max) throws RemoteException {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for(int i = min;  i<=max; i++) {
			if(primo(i))
				lista.add(i);
		}
		return lista;
	}
	
	private static boolean primo(int candidato){
		if(candidato == 0 || candidato == 1)
			return false;
		int div = 2;
		boolean continuar = true;
		while((div*div <= candidato) && continuar) {
			if(candidato % div == 0)
				continuar = false;
			div++;
		}
		return continuar;
	}

	@Override
	public void run() {
		//Se encarga de autoregistrar al trabajador
		try {
			Registry registro = LocateRegistry.getRegistry(ip);
			String[] enRegistro = registro.list();
			int numWorkers = 0;
			for(String worker: enRegistro){
				if(worker != null){
					if(worker.startsWith("Worker")){
						numWorkers ++;
					}
				}
			}
			registro.rebind("Worker" + numWorkers, this);
			System.out.println("Registrado Worker: "+ numWorkers);
		} catch(Exception e){
			System.err.println("Error, registro RMI no encontrado");
		}
	
	}
}
