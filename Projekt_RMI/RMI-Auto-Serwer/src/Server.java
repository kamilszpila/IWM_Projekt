import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
	Registry reg; // rejestr nazw obiektow
	Servant servant; // klasa uslugowa
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			new Server();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
	protected Server() throws IOException, ClassNotFoundException {
		try {
			reg = LocateRegistry.createRegistry(1099); // Utworzenie rejestru nazw
			servant = new Servant(); // utworzenie zdalnego obiektu
			reg.rebind("CarServer", servant); // zwiazanie nazwy z obiektem
			System.out.println("Server ready.");
		} catch(RemoteException e){
			e.printStackTrace();
			throw e;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
