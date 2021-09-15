package by.project.news;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.dao.DAOException;
import by.project.news.dao.UserDAO;
import by.project.news.dao.impl.UserDB;
import by.project.news.util.Generator;

public class DAOUserDBTest {

	private final static String LOGIN = "test";
	private final static String PASS = "test";
	private final static String ROLE_USER = "user";
	private final static String AGE = "45";

	private final static String LOGIN_WRONG = "!test!";
	private final static String PASS_WRONG = "!test!";

	private String genPass = Generator.genStringHash(PASS);
	private String genWrongPass = Generator.genStringHash(PASS_WRONG);

	private UserData userData;
	private User user;
	private UserData wrongUser;
	private UserDAO userDAO;

	@Before
	public void setUp() {
		userData = new UserData.UserDataBuilder().setLogin(LOGIN).setPassword(genPass).setAge(AGE).setRole(ROLE_USER)
				.build();

		user = new User.UserBuilder().setLogin(LOGIN).setRole(ROLE_USER).setAge(AGE).build();

		wrongUser = new UserData.UserDataBuilder().setLogin(LOGIN_WRONG).setPassword(genWrongPass).build();

		userDAO = new UserDB();
	}

	@Test
	public void daoRegNewUserTest() throws DAOException {

		try {

			userDAO.delete(userData);

		} catch (DAOException ignore) {

		}

		userDAO.registration(userData);
	}

	@Test(expected = DAOException.class)
	public void daoRegTestUserExist() throws DAOException {

		userDAO.registration(userData);
	}

	@Test
	public void daoAuthUserTest() throws DAOException {

		Assert.assertEquals(user, userDAO.authorization(userData));
	}

	@Test(expected = DAOException.class)
	public void daoAuthWrongUserTest() throws DAOException {

		userDAO.authorization(wrongUser);
	}

}
