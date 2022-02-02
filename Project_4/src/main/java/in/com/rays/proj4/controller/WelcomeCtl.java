package in.com.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.com.rays.proj4.util.ServletUtility;

/**
 * Servlet implementation class WelcomeCtl
 */

/**
 * @author Rinku
 *
 */
@WebServlet(name = "WelcomeCtl", urlPatterns = { "/WelcomeCtl" })
public class WelcomeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(WelcomeCtl.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("WelcomeCtl Method doGet Started");

		ServletUtility.forward(getView(), request, response);

		log.debug("Welcome Method doget Ended");
	}
	// non-javadoc

	@Override
	protected String getView() {

		return ORSView.WELCOME_VIEW;
	}

}