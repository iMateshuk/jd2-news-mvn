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
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserOperRegistration implements Command {

	private final static Logger log = LogManager.getLogger(UserOperRegistration.class);

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandAutho = CommandName.USER_AUTHORIZATION.toString().toLowerCase();
	private final static String commandReg = CommandName.REGISTRATION.toString().toLowerCase();

	private final static String ATTRIBUTE_USER = "user";
	private final static String ROLE_ADMIN = "admin";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";
	private final static String NEW_USER = "&newuser=";

	private final static String REDIRECT_SESSION = COMMAND + commandAnswer + ACTION + commandReg + MESSAGE;
	private final static String REDIRECT_USER = COMMAND + commandAnswer + ACTION + commandReg + MESSAGE;
	private final static String REDIRECT_USER_NEW = COMMAND + commandAutho + MESSAGE;
	private final static String REDIRECT = COMMAND + CommandName.INDEX.toString().toLowerCase();
	private final static String REDIRECT_EX = COMMAND + commandAnswer + ACTION + commandReg + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.sendRedirect(REDIRECT_SESSION + "usersessiontimeout");
			return;
		}

		String redirect = REDIRECT;

		try {

			UserData userData = BeanCreator.createUserData(request);

			userService.registration(userData);

			User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

			if (user != null && user.getRole().equals(ROLE_ADMIN)) {

				redirect = REDIRECT_USER + "registrationsuccess";
			} else {

				redirect = REDIRECT_USER_NEW + "registrationsuccess" + NEW_USER + userData.getLogin();
			}

		} catch (ServiceException e) {

			log.error(e);
			redirect = REDIRECT_EX + Parser.excRemovePath(e.getMessage());
		} catch (UtilException e) {

			log.error(e);
			redirect = REDIRECT_EX + Parser.excRemovePath(e.getMessage());
		}

		response.sendRedirect(redirect);

	}

}
