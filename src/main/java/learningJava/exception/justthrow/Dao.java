package learningJava.exception.justthrow;

public class Dao {
	public void find(){
		throw new DaoException("SQL Exception");
	}
}
