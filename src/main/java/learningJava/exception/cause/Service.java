package learningJava.exception.cause;

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
			throw new ServiceException("매핑하는 데이터가 존재하지 않습니다",e);
		}
	}
}
