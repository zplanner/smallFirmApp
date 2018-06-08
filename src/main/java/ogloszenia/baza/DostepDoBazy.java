package ogloszenia.baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ogloszenia.exn.BladBazyDanych;

public class DostepDoBazy implements AutoCloseable {
	private static final String PASSWD = "abc123";
	private static final String USER = "planner";
	private static final String URL_BAZY = "jdbc:postgresql://localhost/userApp";
	private Connection c;
	
	public DostepDoBazy() throws BladBazyDanych {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(URL_BAZY, USER, PASSWD);
		} catch (ClassNotFoundException | SQLException e) {
			throw new BladBazyDanych("Błąd podczas łączenia z bazą", e);
		}
	}

	public void close() {
		try {
			if(c != null)
				c.close();
		} catch (Exception e) {
			System.err.println("WARNING: Błąd poczas rozłączania");
			e.printStackTrace();
		}
	}
	
	Connection getC() {
		return c;
	}

	public void beginTransaction() throws SQLException {
		c.setAutoCommit(false);
		c.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
	}

	public void endTransaction(boolean commit) throws SQLException {
		if(commit) {
			c.commit();
		} else {
			c.rollback();
		}
		c.setAutoCommit(true);
	}

	public OgloszeniaDAO ogloszeniaDAO() {
		return new OgloszeniaDAO(this);
	}

	public SprzedawcyDAO sprzedawcyDAO() {
		return new SprzedawcyDAO(this);
	}
}
