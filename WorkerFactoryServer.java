package ejemplo_RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * @author Dariel
 */
@SuppressWarnings("serial")
public class WorkerFactoryServer extends UnicastRemoteObject implements WorkerFactory, Runnable {
	private String ip;
	private Registry registro;
	
	public WorkerFactoryServer(String ip) throws RemoteException {
		this.ip = ip;
		this.registro = LocateRegistry.getRegistry(this.ip);
	}
	/**
	 * Devuelve n workers
	 */
	@Override
	public ArrayList<Worker> dameWorkers(int n) throws RemoteException {
		Worker worker;
		ArrayList<Worker> trabajadores = new ArrayList<Worker>();
		String[] enRegistro = registro.list();		
		int asignados = 0;
		for(String objeto : enRegistro){
			if(asignados > n ) {
				break;
			}else {
				if(objeto.contains("Worker")){			
					try {			
						worker = (Worker) registro.lookup(objeto);
						trabajadores.add(worker);
						asignados++;
					} catch (NotBoundException e) {
						System.err.println("Error con "+ objeto +". Buscando otro Worker");
					}
					
					
				}
			}
		}
		
		return trabajadores;
	}

	@Override
	public void run() {
		//se encarga del auto registro
		try {
			
			registro.rebind("ServidorAsignacion", this);
		} catch(Exception e){
			System.err.println("Error, registro RMI no encontrado");
		}
		
	}

}
