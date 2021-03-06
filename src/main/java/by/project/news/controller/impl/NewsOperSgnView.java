package by.project.news.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.bean.NewsData;
import by.project.news.bean.User;
import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.service.NewsService;
import by.project.news.service.ServiceException;
import by.project.news.service.ServiceProvider;
import by.project.news.util.SessionWork;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NewsOperSgnView implements Command {

	private final static Logger log = LogManager.getLogger(NewsOperSgnView.class);

	private final static NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandSgn = CommandName.NEWS_TOOLS_SGNVIEW.toString().toLowerCase();
	private final static String commandMain = CommandName.MAIN.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String PATH = "/WEB-INF/jsp/" + commandMain + ".jsp";

	private final static String CLEAN = "clean";
	private final static String USER = "user";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String COMMAND_SAVE = "cmdSave";

	private final static String REDIRECT_UE = COMMAND + commandAuth + MESSAGE;
	private final static String REDIRECT_EX = COMMAND + commandAnswer + ACTION + commandSgn + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSessionUser(session);

			if (request.getParameter(CLEAN) != null) {

				SessionWork.cleanAttributes(session);
			}

			NewsData newsData = newsServices.sgnAuthorView((User) session.getAttribute(USER),
					new NewsData.NewsDataBuilder().setPage(SessionWork.takePage(request, session)).build());

			session.setAttribute(COMMAND_SAVE, commandSgn);

			SessionWork.setAttributePagination(newsData, request, session);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			log.error(e);
			SessionWork.cleanAttributes(session);
			response.sendRedirect(REDIRECT_EX + Parser.excRemovePath(e.getMessage()));

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_UE + Parser.excRemovePath(e.getMessage()));
			return;
		}

	}

}
