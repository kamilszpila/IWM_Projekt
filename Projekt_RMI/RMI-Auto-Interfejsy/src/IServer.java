import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Remote {

	void checkFiles() throws RemoteException;
	void addCar(Car car) throws IOException;
	void rentCar(int userID, int carID) throws IOException;
	void returnCar(int userID, int carID, double distance, double fuel) throws IOException;
	void checkCar(int ID) throws IOException;
	void refuelCar(int ID) throws IOException;
//	void showListOfCars(String filtr) throws RemoteException;
	List<String> getListOfCars(String filtr) throws RemoteException;
	void refreshList() throws IOException;
	void addUser(User user) throws IOException;
	List<String> getListOfUsers(String filtr) throws RemoteException;
	List<Integer> getUsersID() throws RemoteException;
	
}
