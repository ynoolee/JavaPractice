package learningJava.exception.noncause;

public class Service {
	private final Dao dao;

	public Service(Dao dao) {
		this.dao = dao;
	}

	public void get() {
		try{
			dao.find();
		}
		catch (Exception e) {
			throw new ServiceException();
		}
	}
}
