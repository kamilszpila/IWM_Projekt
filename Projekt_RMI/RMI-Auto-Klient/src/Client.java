import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;


public class Client {
	
	private Scanner userInput = new Scanner(System.in);
	IServer remoteObject; // referencja do zdalnego obiektu
	List<String> list;
	String line, name, surname, PESEL, filter;
	int uID, cID;
	double distance, fuel;
	
	
    public static void main(String[] args) throws IOException {
    	new Client();
    }

	public Client() {
		Registry reg;    // rejestr nazw obiektow
		try {
			// pobranie referencji do rejestru nazw obiektow
			reg = LocateRegistry.getRegistry();
			// odszukanie zdalnego obiektu po jego nazwie
			remoteObject = (IServer) reg.lookup("CarServer");
			
			remoteObject.addCar(new Car(2018, "Ford",	"Mondeo",	"MZA12345678901234", 0.0,	  60));
			remoteObject.addCar(new Car(2017, "Skoda",	"Octavia",	"MZA12345678901235", 10000.0, 60));
			remoteObject.addCar(new Car(2016, "Toyota",	"Auris",	"MZA12345678901236", 30000.0, 40));
			remoteObject.addCar(new Car(2015, "Kia",	"Ceed",		"MZA12345678901237", 60000.0, 50));
			remoteObject.addCar(new Car(2014, "Opel",	"Corsa",	"MZA12345678901238", 75000.0, 40));

			loop();

		} catch (RemoteException e) 	{ e.printStackTrace();
		} catch (NotBoundException e) 	{ e.printStackTrace();
		} catch (IOException e) 		{ e.printStackTrace(); }
	}

	void newClient() {
		System.out.println("Enter user name:");
		name = userInput.nextLine();
		System.out.println("Enter user surname:");
		surname = userInput.nextLine();
		System.out.println("Enter user PESEL:");
		PESEL = userInput.nextLine();
		
		try {
			remoteObject.addUser(new User(name, surname, PESEL));
			System.out.println("Your ID: " + User.getCounter());
		} catch (IOException e) { e.printStackTrace(); }		

	}

	
	void rentCar() {
		System.out.println("Enter user ID:");
		uID = userInput.nextInt();
		userInput.nextLine();
		List<Integer> usersID;
		try {
			usersID = remoteObject.getUsersID();
			if(usersID.contains(uID)) {						
				list = remoteObject.getListOfCars("Driver: 0");
				System.out.println("Enter text to filter results (or press enter for all results):");
				filter = userInput.nextLine();
				System.out.println("Available cars:");
				list.stream()
					.filter(c -> c.contains(filter))
					.forEach(System.out::println);
				
				System.out.println("Choose car:");
				cID = userInput.nextInt();
				userInput.nextLine();
				remoteObject.rentCar(uID, cID);
			}
		} catch (RemoteException e) { e.printStackTrace();
		} catch (IOException e) 	{ e.printStackTrace(); }						

	}
	
	
	void returnCar() {
		System.out.println("Enter user ID:");
		uID = userInput.nextInt();
		userInput.nextLine();
		List<Integer> usersID2;
		try {
			usersID2 = remoteObject.getUsersID();
			if(usersID2.contains(uID)) {
				System.out.println("Enter car ID:");
				cID = userInput.nextInt();
				userInput.nextLine();
				System.out.println("Distance:");
				distance = userInput.nextDouble();
				//userInput.nextLine();
				System.out.println("Fuel used:");
				fuel = userInput.nextDouble();
				userInput.nextLine();
				remoteObject.returnCar(uID, cID, distance, fuel);
			}
		} catch (RemoteException e) { e.printStackTrace();
		} catch (IOException e) 	{ e.printStackTrace(); }
	}

	
	void showListOfCars() {
		try {
			list = remoteObject.getListOfCars("");
			System.out.println("Cars in system:");
			list.stream()
				.map(c -> c.toString())
				.forEach(System.out::println);
		} catch (RemoteException e) { e.printStackTrace(); }
	}

	
	void addCar() {
		System.out.println("Enter year of production:");
		int year = userInput.nextInt();
		userInput.nextLine();
		System.out.println("Enter brand:");
		String brand = userInput.nextLine();
		System.out.println("Enter model:");
		String model = userInput.nextLine();
		System.out.println("Enter VIN:");
		String VIN = userInput.nextLine();
		System.out.println("Enter mileage:");
		double mileage = userInput.nextDouble();
		userInput.nextLine();
		System.out.println("Enter max fuel level:");
		int maxFuelLevel = userInput.nextInt();
		userInput.nextLine();

		try {
			remoteObject.addCar(new Car(year, brand, model, VIN, mileage, maxFuelLevel));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
	void loop() {
		while(true) {
			System.out.println("[n]ew client, [r]ent car, re[t]urn car, [s]how list of cars, [a]dd car, [e]nd: ");
			if(userInput.hasNextLine()) {
				line = userInput.nextLine();
				if(!line.matches("[nrtsae]")) {
					System.out.println("You entered invalid command !");
					continue;
				}
				switch (line) {
					case "n":
						newClient();						
						break;
						
					case "r":
						rentCar();
						break;
					
					case "t":
						returnCar();
						break;
					
					case "s":
						showListOfCars();
						break;

					case "a":
						addCar();
						break;
					
					case "e":
						return;
				}
			}
		}
	}
}
