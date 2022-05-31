package learningJava.exception.justthrow;

public class Service {
	private final Dao dao;

	public Service(Dao dao) {
		this.dao = dao;
	}

	public void get() {
		dao.find();
	}
}
