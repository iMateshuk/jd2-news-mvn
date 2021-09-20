package by.project.news.controller.impl;

import java.io.IOException;

import by.project.news.controller.Command;
import by.project.news.controller.CommandName;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GoToRegistrationPage implements Command {

	private final static String PATH = "/WEB-INF/jsp/" + CommandName.USER_REGISTRATION.toString().toLowerCase()
			+ ".jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PATH);
		requestDispatcher.forward(request, response);

	}

}
