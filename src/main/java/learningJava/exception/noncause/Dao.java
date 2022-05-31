package learningJava.exception.noncause;

public class Dao {
	public void find(){
		throw new DaoException("SQL Exception");
	}
}
