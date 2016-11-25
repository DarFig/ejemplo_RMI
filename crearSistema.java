package ejemplo_RMI;


/**
 * 
 * @author Dariel
 * @param args[0] -c|-a|-u ejecutar un servidor de c�lculo, el servidor de asignaci�n o el cliente respectivamente
 * @param args[1] en caso de -c y -a [ip del registro]; en caso de -u valor m�nimo del rango
 * @param args[2] en caso de -u valor m�ximo del rango
 * @param args[3] en caso de -u n�mero de servidores de c�lculo a utilizar 
 * @param args[4] en caso de -u [ip del registro]
 */
public class crearSistema {
	public static void main(String[] args){
		String ip;
		
		try {
			if(args.length != 0){
				if(args[0].equals("-c")){//
					if(args.length < 2){//no hay ip
						ip = "localhost";
					}
					else{
						ip = args[1];
					}
					Thread calculo = new Thread(new WorkerServer(ip));
					calculo.start();
					calculo.join();
				}else if(args[0].equals("-a")){
					if(args.length < 2)//no hay ip
						ip = "127.0.0.1";
					else
						ip = args[1];
					Thread server = new Thread(new WorkerFactoryServer(ip));
					server.start();
					server.join();
				}else if((args[0].equals("-u")) && (args.length > 3)){
					if(args.length < 5)//no hay ip
						ip = "127.0.0.1";
					else
						ip = args[4];
					Thread cliente = new Thread(new Cliente(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), ip));
					cliente.start();
				}else{
					if(args[0].equals("-u"))
						System.err.println("Error, n�mero de par�metros incorrectos.");
					else
						System.err.println("Error, opci�n: " + args[0] + "no reconocida.");
				}
			}else {
				System.err.println("Error, n�mero de par�metros incorrectos.");
			}
			
		} catch(Exception e){
			
			e.printStackTrace();
		}
	}
}
