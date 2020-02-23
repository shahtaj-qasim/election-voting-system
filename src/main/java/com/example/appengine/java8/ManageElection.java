package com.example.appengine.java8;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.repackaged.org.joda.time.DateTime;

@WebServlet(name = "ManageElection", value = "/admin/manage")
public class ManageElection extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(CandidateServlet.class.getName());


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException , ServletException {
	 
	  List<String> emails = new ArrayList<String>();
		  
		try {
			  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			  Key keyEmails = KeyFactory.createKey("Emails", "emailsList");
			   Entity emailList;
			emailList = datastore.get(keyEmails);
		    emails = (List<String>) emailList.getProperty("emails");
		    
		    Key keyStart = KeyFactory.createKey("Date", "start");
			Entity startElection = datastore.get(keyStart);
			Date startDate = (Date) startElection.getProperty("date");
			
			Key keyEnd = KeyFactory.createKey("Date", "end");
			Entity endElection = datastore.get(keyEnd);
			Date endDate = (Date) endElection.getProperty("date");
			
		    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	        request.setAttribute("startElection", formatter.format(startDate));
	        request.setAttribute("endElection", formatter.format(endDate));


		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        request.setAttribute("emails", emails);
	        
	    		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    		Query query = new Query("Candidates");
	    		  List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());	
	    	        request.setAttribute("candidates", results);
	    	
		 
	    request.getRequestDispatcher("/manage_election.jsp").forward(request, response);

	    response.setContentType("text/plain");
	    response.getWriter().println("Hello App Engine - Welcome to the admin part of the application ");

  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    
        Date parsedStart = format.parse(request.getParameter("start_date").trim());
        Date parsedEnd = format.parse(request.getParameter("end_date").trim());


        Entity startA = new Entity("Date" , "start");
		  startA.setProperty("date", parsedStart);
		  ds.put(startA);
		  
		  Entity endA = new Entity("Date" , "end");
		  endA.setProperty("date", parsedEnd);
		  ds.put(endA);
       
		//use identifier to make sure same data is not added multiple times when app is reloaded/invoked each time
	   /*
		//creating new entity
		  Entity start = new Entity("Start");
		  start.setProperty("date", request.getParameter("start_date").trim());
		  start.setProperty("time", request.getParameter("start_time").trim());
		  ds.put(start);
		  
		  Entity end = new Entity("End");
		  end.setProperty("date", request.getParameter("end_date").trim());
		  end.setProperty("time", request.getParameter("end_time").trim());
		  ds.put(end);
		  */
    	response.sendRedirect("/admin/manage");

		  
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}

	}

 

}
