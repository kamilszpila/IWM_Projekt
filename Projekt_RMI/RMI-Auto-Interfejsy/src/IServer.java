import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface IServer extends Remote {

	void checkFiles() throws RemoteException;
	void addCar(Car car) throws IOException;//to file
	void removeCar(int ID) throws RemoteException, IOException;//to file
	void rentCar(int userID, int carID) throws IOException;
	void returnCar(int userID, int carID, double distance, double fuel) throws IOException;
	void checkCar(int ID) throws IOException;
	void refuelCar(int ID) throws IOException;
//	void showListOfCars(String filtr) throws RemoteException;
	List<String> getListOfCars(String filtr) throws RemoteException;//friends.forEach(System.out::println);
	void refreshList() throws IOException;
	void addUser(User user) throws IOException;
	List<String> getListOfUsers(String filtr) throws RemoteException;//friends.forEach(System.out::println);
	List<Integer> getUsersID() throws RemoteException;
//	Car findCar() throws RemoteException;//kazdy plik do samochodu, linie w jakich jest napisany kierowca i przebieg i zuzyte paliwo
	
}
