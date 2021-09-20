package by.project.news.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.bean.User;
import by.project.news.bean.UserData;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.service.UserService;
import by.project.news.util.BeanCreator;
import by.project.news.util.SessionWork;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserOperPassword implements Command {

	private final static Logger log = LogManager.getLogger(UserOperPassword.class);

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserPass = CommandName.USER_PASSWORD.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String ATTRIBUTE_USER = "user";
	private final static String ROLE_ADMIN = "admin";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_USER = COMMAND + commandAnswer + ACTION + commandUserPass + MESSAGE;
	private final static String REDIRECT = COMMAND + commandAnswer + ACTION + commandUserPass + MESSAGE;
	private final static String REDIRECT_SE = COMMAND + commandAnswer + ACTION + commandUserPass + MESSAGE;
	private final static String REDIRECT_UE = COMMAND + commandAuth + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSessionUser(session);

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_UE + Parser.excRemovePath(e.getMessage()));
			return;
		}

		try {

			User user = (User) session.getAttribute(ATTRIBUTE_USER);

			UserData userData = BeanCreator.createUserData(request);

			if (!(user.getLogin().equals(userData.getLogin()) || user.getRole().equals(ROLE_ADMIN))) {

				response.sendRedirect(REDIRECT_USER + "wronguserlogin");
				return;
			}

			userService.password(userData);

			response.sendRedirect(REDIRECT + "userpasswordsuccess");

		} catch (ServiceException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_SE + Parser.excRemovePath(e.getMessage()));

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_UE + Parser.excRemovePath(e.getMessage()));
		}

	}

}
