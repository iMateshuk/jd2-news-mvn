package by.project.news.util;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLConAutoRollback implements AutoCloseable {

	private Connection con;
	private boolean committed;

	public SQLConAutoRollback(Connection con) throws SQLException {
		
		this.con = con;
	}

	public void commit() throws SQLException {
		
		con.commit();
		committed = true;
	}

	@Override
	public void close() throws SQLException {

		if (!committed) {

			con.rollback();
		}
	}

}
