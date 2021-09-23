package by.project.news.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.bean.News;
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

public class GoToNewsViewPage implements Command {

	private final static Logger log = LogManager.getLogger(GoToNewsViewPage.class);

	private final static String PATH = "/WEB-INF/jsp/" + CommandName.NEWS_VIEW.toString().toLowerCase() + ".jsp";

	private final static NewsService newsServices = ServiceProvider.getInstance().getNewsService();

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";
	private final static String ACTION = "&action=";

	private final static String PARAM_NEWSID = "newsId";
	private final static String ATTRIBUTE_NEWS = "news";

	private final static String commandAnswer = CommandName.NEWS_ANSWER.toString().toLowerCase();
	private final static String commandView = CommandName.NEWS_VIEW.toString().toLowerCase();
	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String REDIRECT_ERROR = COMMAND + commandAnswer + MESSAGE;
	private final static String REDIRECT_SE = COMMAND + commandAnswer + ACTION + commandView + MESSAGE;

	private final static String REDIRECT_SESSION = COMMAND + commandAuth + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		try {

			SessionWork.validateSessionUser(session);

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_SESSION + Parser.excRemovePath(e.getMessage()));
			return;
		}

		String newsId = (String) request.getParameter(PARAM_NEWSID);

		if (newsId == null) {

			newsId = (String) session.getAttribute(PARAM_NEWSID);
		} else {

			session.setAttribute(PARAM_NEWSID, newsId);
		}

		if (newsId == null) {

			response.sendRedirect(REDIRECT_ERROR + "commonerror");
			return;
		}

		try {

			request.setAttribute(ATTRIBUTE_NEWS,
					newsServices.chooseNews(new News.NewsBuilder().setId(newsId).build()));

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
			requestDispatcher.forward(request, response);

		} catch (ServiceException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_SE + Parser.excRemovePath(e.getMessage()));
		}

	}

}
