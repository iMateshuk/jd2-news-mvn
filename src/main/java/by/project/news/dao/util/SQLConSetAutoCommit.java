package by.project.news.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLConSetAutoCommit implements AutoCloseable {

	private Connection con;
	private boolean originalAutoCommit;

	public SQLConSetAutoCommit(Connection con, boolean autoCommit) throws SQLException {

		this.con = con;
		originalAutoCommit = con.getAutoCommit();
		con.setAutoCommit(autoCommit);
	}

	@Override
	public void close() throws SQLException {

		con.setAutoCommit(originalAutoCommit);
	}

}
