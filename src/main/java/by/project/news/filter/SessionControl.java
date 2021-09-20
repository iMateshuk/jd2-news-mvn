package by.project.news.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.project.news.controller.CommandName;
import by.project.news.controller.impl.GoToUserDeletePage;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "/SessionControl", urlPatterns = "/Controller")
public class SessionControl implements Filter {

	private final static Logger log = LogManager.getLogger(GoToUserDeletePage.class);

	private final static String commandAnswer = CommandName.USER_ANSWER.toString().toLowerCase();

	private final static String COMMAND = "Controller?command=";

	private final static String MESSAGE = "&message=";

	private final static String MESSAGE_INFO = "swvs";

	private final static String REDIRECT = COMMAND + commandAnswer + MESSAGE + MESSAGE_INFO;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest req = (HttpServletRequest) request;

		final HttpServletResponse res = (HttpServletResponse) response;

		final HttpSession session = req.getSession(false);

		if (session == null) {

			log.error("Session is timeout");
			res.sendRedirect(REDIRECT);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
