package by.project.news.controller.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import by.project.news.util.SessionWork;
import by.project.news.util.Parser;
import by.project.news.util.UtilException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToNewsAddPage implements Command {

	private final static Logger log = LogManager.getLogger(GoToNewsAddPage.class);

	private final static String PATH = "/WEB-INF/jsp/" + CommandName.NEWS_TOOLS_ADD.toString().toLowerCase() + ".jsp";

	private final static String COMMAND = "Controller?command=";
	private final static String MESSAGE = "&message=";

	private final static String commandAuth = CommandName.USER_AUTHORIZATION.toString().toLowerCase();

	private final static String REDIRECT_UE = COMMAND + commandAuth + MESSAGE;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			SessionWork.validateSessionUser(request.getSession(false));

		} catch (UtilException e) {

			log.error(e);
			response.sendRedirect(REDIRECT_UE + Parser.excRemovePath(e.getMessage()));
			return;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
		requestDispatcher.forward(request, response);

	}

}
