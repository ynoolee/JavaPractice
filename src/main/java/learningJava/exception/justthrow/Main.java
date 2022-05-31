package learningJava.exception.justthrow;

public class Main {
	public static void main(String[] args) {
		Dao dao = new Dao();
		Service service = new Service(dao);
		Controller controller = new Controller(service);

		controller.process();
	}
}
