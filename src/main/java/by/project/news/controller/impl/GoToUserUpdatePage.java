package by.project.news.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.bean.User;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.service.UserService;
import by.project.news.util.SessionWork;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GoToUserUpdatePage implements Command {

	private final static Logger log = LogManager.getLogger(GoToUserUpdatePage.class);

	private final static String PATH = "/WEB-INF/jsp/" + CommandName.USER_TOOLS_UPDATE.toString().toLowerCase()
			+ ".jsp";

	private final static UserService userService = ServiceProvider.getInstance().getUserService();

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();
	private final static String commandUserUpdate = CommandName.USER_UPDATE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String ROLE_ADMIN = "admin";
	private final static String USER = "user";
	private final static String ATTRIBUTE_USER_DATA = "userData";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT_SE = COMMAND + commandAnswer + ACTION + commandUserUpdate + MESSAGE;
	private final static String REDIRECT_UE = COMMAND + commandAuth + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSessionUser(session);

			User user = (User) session.getAttribute(USER);

			if (!user.getRole().equals(ROLE_ADMIN)) {

				request.setAttribute(ATTRIBUTE_USER_DATA, userService.loadUserData(user));
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_SE + Parser.excRemovePath(e.getMessage()));

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_UE + Parser.excRemovePath(e.getMessage()));
		}

	}

}
