package by.project.news;

import org.junit.Assert;
import org.junit.Test;

import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.dao.DAOException;
import by.project.news.dao.UserDAO;
import by.project.news.dao.impl.UserDB;
import by.project.news.util.Generator;

public class DAOUserDBTest {

	UserData userData = new UserData.UserDataBuilder().setLogin("test").setPassword(Generator.genStringHash("test"))
			.setAge("45").setRole("user").build();

	User user = new User.UserBuilder().setLogin("test").setRole("user").setAge("45").build();

	UserDAO userDAO = new UserDB();

	@Test
	public void daoRegTestNewUser() throws DAOException {

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

}
