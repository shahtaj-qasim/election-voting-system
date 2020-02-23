package com.example.appengine.java8;

import java.io.IOException;
import java.util.logging.Logger;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * Servlet implementation class CandidateServlet
 */
@WebServlet(name = "CandidateServlet", value = "/candidates")
public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CandidateServlet.class.getName());


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			logger.info("inside do get cndidate");
		  response.setContentType("text/plain");

		  //response.getWriter().append("Served at: ").append(request.getContextPath());
		  request.getRequestDispatcher("/addcandidate.jsp").forward(request, response);

		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}
		//response.sendRedirect("/candidates/addcandidate");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	    //use identifier to make sure same data is not added multiple times when app is reloaded/invoked each time
	    //creating new entity
		  Entity e = new Entity("Candidates");
		  e.setProperty("firstname", request.getParameter("firstname").trim());
		  e.setProperty("surname", request.getParameter("surname").trim());
		  e.setProperty("faculty", request.getParameter("faculty").trim());
		  e.setProperty("votes", "0");

		  ds.put(e);
		  
      	response.sendRedirect("/admin/manage");

		  
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}

	}

}
