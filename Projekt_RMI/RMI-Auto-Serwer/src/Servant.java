import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Servant 
		extends UnicastRemoteObject 
		implements IServer, Serializable {

	private Map<Integer, Car> cars = new HashMap<Integer, Car>();
	private Map<Integer, User> users = new HashMap<Integer, User>();
		
		
	File fileCars;
	File fileUsers;
	
	public Servant() throws IOException, ClassNotFoundException {
		checkFiles();
	}

	@Override
	public void checkFiles() throws RemoteException {
		fileCars = new File("Cars.txt");
		if(!fileCars.exists()) {
            try {
				fileCars.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
		fileUsers = new File("Users.txt");
		if(!fileUsers.exists()) {
            try {
				fileUsers.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	@Override
	public void addCar(Car car) throws IOException {
		if (!cars.containsValue(car)) {
            cars.put(car.getID(), car);
            Files.write(fileCars.toPath(), ((car.toString()) + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
            File fileC = new File("Car " + car.getID() + ".txt");
            LocalTime localTime1 = LocalTime.now();
            Files.write(fileC.toPath(), (car.toString() + System.lineSeparator() + localTime1 + " - Added" + System.lineSeparator()).getBytes());
		}	
	}

	@Override
	public void removeCar(int ID) throws IOException {
/*		Car car = cars.values().stream()
						.filter(c -> (c.getID()==(ID)))
						.findFirst().get();
		
		cars.remove(car);
//						.map(c -> c.getID());
		
		Files.write(file.toPath(), (cars.values().stream()
										.map(c -> c.toString())
										.collect(Collectors.joining(System.lineSeparator()))).getBytes());
		/*List<String> list =
            		Files.readAllLines(file.toPath());
		list.forEach(System.out::println);
		list.remove()*/
	}

	@Override
	public void rentCar(int userID, int carID) throws IOException {
		if(users.containsKey(userID)) {

			if((cars.values().stream()
						.filter(c -> c.getDriver()==users.get(userID).getID())
						.count())==0) 
			{
				cars.values().stream()
							.filter(c -> c.getID()==carID)
							.filter(c -> c.getDriver()==0)
							.forEach(c -> { 
							c.setDriver(users.get(userID).getID());
						File fileC = new File("Car " + carID + ".txt");
					    LocalTime localTime1 = LocalTime.now();
		
					    try {
							Files.write(fileC.toPath(), (localTime1 + " - Rented by: " + users.get(userID).getID()
									+ System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
							File fileU = new File("User " + userID + ".txt");
				            Files.write(fileU.toPath(), (localTime1 + " - Rented car: " + carID + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
				            
						    refreshList();
					    } catch (IOException e) { e.printStackTrace(); }
					    });
			}
		}
	}

	@Override
	public void returnCar(int userID, int carID, double distance, double fuel) throws IOException {
		if(cars.values().stream()
						.filter(c -> c.getID()==carID)
						.filter(c -> c.getDriver()==users.get(userID).getID())
						.count()==1) 
		{
			
			cars.values().stream()
						.filter(c -> c.getID()==carID)
						.filter(c -> c.getDriver()==users.get(userID).getID())
						.forEach(c -> c.sumJourney(distance, fuel));
			
			File fileC = new File("Car " + carID + ".txt");
            LocalTime localTime1 = LocalTime.now();
  
            Files.write(fileC.toPath(), (localTime1 + " - Returned  by: " + users.get(userID).getID()
            		+ " - Distance: " + distance + " km - Fuel: " + fuel + " L" + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);

		    File fileU = new File("User " + userID + ".txt");
            Files.write(fileU.toPath(), (localTime1 + " - Returned car: " + carID 
            		+ " - Distance: " + distance + " km - Fuel: " + fuel + " L" + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);

            checkCar(carID);
            
            refreshList();
		}
    }

	@Override
	public void checkCar(int ID) throws IOException {
		if(cars.get(ID).getFuelLevel() < (cars.get(ID).getMaxFuelLevel()*0.8)) {
			refuelCar(ID);
		}		
	}

	@Override
	public void refuelCar(int ID) throws IOException {
		File fileC = new File("Car " + ID + ".txt");
        LocalTime localTime1 = LocalTime.now();
        Files.write(fileC.toPath(), (localTime1 + " - Refueled with: " + (cars.get(ID).getMaxFuelLevel() - cars.get(ID).getFuelLevel()) + " L" + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
        cars.get(ID).setFuelLevel(cars.get(ID).getMaxFuelLevel());
	}

/*	@Override
	public void showListOfCars(String filter) throws RemoteException {
		if(filter == null) {
			cars.toString();
		}else {
			cars.values().stream()
					.filter(c -> c.toString().contains(filter))
					.forEach(System.out::println);
		}
	}
*/
	@Override
	public List<String> getListOfCars(String filter) throws RemoteException {
		if(filter == "") {
			List<String> list =
					cars.values().stream()
					.map(c -> c.toString())
					.collect(Collectors.toList());
			return list;
		}else {
			List<String> list = 
					cars.values().stream()
					.map(c -> c.toString())
					.filter(c -> c.contains(filter))
					.collect(Collectors.toList());
			return list;
		}
	}

	@Override
	public void refreshList() throws IOException {
		Files.write(fileCars.toPath(), ("").getBytes());
		cars.values().stream()
						.map(c -> c.toString())
						.forEach(c -> {
							try {
								Files.write(fileCars.toPath(), ((c) + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
		
	}

	@Override
	public void addUser(User user) throws IOException {
		if (!users.containsValue(user)) {
            users.put(user.getID(), user);
            Files.write(fileUsers.toPath(), ((user.toString()) + System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
            File fileU = new File("User " + user.getID() + ".txt");
            LocalTime localTime1 = LocalTime.now();
            Files.write(fileU.toPath(), (user.toString() + System.lineSeparator() + localTime1 + " - Added" + System.lineSeparator()).getBytes());
		}
    }

	@Override
	public List<String> getListOfUsers(String filter) throws RemoteException {
		if(filter == "") {
			List<String> list =
					users.values().stream()
					.map(c -> c.toString())
					.collect(Collectors.toList());
			return list;
		}else {
			List<String> list = 
					users.values().stream()
					.map(c -> c.toString())
					.filter(c -> c.contains(filter))
					.collect(Collectors.toList());
			return list;
		}
	}

	@Override
	public List<Integer> getUsersID() throws RemoteException {
		List<Integer> list =
				users.keySet().stream()
				.collect(Collectors.toList());
			return list;
	}
		
}
