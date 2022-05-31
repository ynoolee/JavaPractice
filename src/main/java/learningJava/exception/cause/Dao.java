package learningJava.exception.cause;

public class Dao {
	public void find(){
		throw new DaoException("SQL Exception");
	}
}
