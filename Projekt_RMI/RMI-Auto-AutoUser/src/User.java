import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userID;
    private String name;
	private String surname;
	private String PESEL;
	private static int userCounter = 0;
    
	
	public User(String name, String surname, String PESEL) {
    
		userCounter++;
    	this.setID(userCounter);
    	this.setName(name);
    	this.setSurname(surname);
        this.setPESEL(PESEL);
}

	//set methods
	public void setID(int ID) {
        this.userID = ID;}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setPESEL(String PESEL) {
		this.PESEL = PESEL;
	}	

	public static void setCounter(int counter) {
        User.userCounter = counter;}


	//get methods
    public int getID() {
    	return this.userID;}

    public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getPESEL() {
		return this.PESEL;
	}
	
    public static int getCounter() {
    	return userCounter;}

	public String toString() {
		return "ID: " + this.userID 
				+ ", Name: " + this.name 
				+ ", Surname: " + this.surname 
				+ ", Pesel: " + this.PESEL;
	}
	
}
	