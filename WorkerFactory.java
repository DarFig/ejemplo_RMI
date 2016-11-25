package ejemplo_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface WorkerFactory extends Remote{
	//Devuelve un vector de hasta n referencias a objeto worker
	ArrayList<Worker> dameWorkers(int n) throws RemoteException;
}
