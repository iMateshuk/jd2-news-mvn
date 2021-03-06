package by.project.news.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.util.BeanCreator;
import by.project.news.util.SessionWork;
import by.project.news.util.UserRole;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NewsOperDelete implements Command {

	private final static Logger log = LogManager.getLogger(NewsOperDelete.class);

	private final static NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandDelete = CommandName.NEWS_DELETE.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String REDIRECT = COMMAND + commandAnswer + ACTION + commandDelete;
	private final static String REDIRECT_UE = COMMAND + commandAuth + MESSAGE;
	private final static String REDIRECT_EX = COMMAND + commandAnswer + ACTION + commandDelete + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			HttpSession session = request.getSession(false);

			SessionWork.validateSessionUser(session);

			SessionWork.validateRoleUser(session, UserRole.ADMIN);

			newsServices.delete(BeanCreator.createNews(request));

			response.sendRedirect(REDIRECT);

		} catch (ServiceException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_EX + Parser.excRemovePath(e.getMessage()));

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_UE + Parser.excRemovePath(e.getMessage()));
		}

	}

}
