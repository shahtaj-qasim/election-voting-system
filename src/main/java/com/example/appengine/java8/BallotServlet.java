package com.example.appengine.java8;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.utils.SystemProperty;
import com.google.apphosting.api.ApiProxy.LogRecord.Level;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "Ballot", value = "/ballot")
public class BallotServlet extends HttpServlet {
	
	private final static Logger LOGGER = Logger.getLogger(BallotServlet.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		    String token = request.getQueryString().split("=")[1];
		    LOGGER.info( token );

		    
		    Query tokenq = new Query("Token").setFilter(new FilterPredicate ("name", FilterOperator.EQUAL, token));
	
		    PreparedQuery tpq = ds.prepare(tokenq);
		    Entity result  = tpq.asSingleEntity();
		    String isValid = (String) result.getProperty("valid"); 

		    if(isValid.equals("false"))
		    {
		    	LOGGER.info("is null");
		    	request.getRequestDispatcher("/expired.jsp").forward(request, response);
		    }
		    	
	
		    else {
				Query q = new Query("Candidates");
				LOGGER.info("result is not  null " );
		
				 //Use PreparedQuery interface to retrieve results
			     PreparedQuery pq = ds.prepare(q);
		
			     request.setAttribute("candidates", pq);
			     request.setAttribute("token", token);
			
				 response.setContentType("text/plain");
				  
				 request.getRequestDispatcher("/ballot.jsp").forward(request, response);
			 }
			
		}
		catch(Exception e) {
		}
	}

	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
		  
		  //increment the votes of the selected candidate
		
		  DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

			
		  int selectedCandidateNumber = Integer.parseInt(request.getParameter("vote").trim());
		    
		  Query q = new Query("Candidates");
		  int counter = 0;

		  // Use PreparedQuery interface to retrieve results
		  PreparedQuery pq = ds.prepare(q);
		  
		  for(Entity result   : pq.asIterable())
		  {
			  if(counter == selectedCandidateNumber)
			  {
				    int votes = Integer.parseInt((String) result.getProperty("votes")) + 1;
				    result.setProperty("votes", Integer.toString(votes));
				    
				    ds.put(result);
				    break;
			  }
			  
			  else 
				  counter++;
		  }
		  
		
		  //invalidate the token
		  String token =  request.getParameter("token").trim();
		  Query tokenq = new Query("Token").setFilter(new FilterPredicate ("name", FilterOperator.EQUAL, token));
			
		  PreparedQuery tpq = ds.prepare(tokenq);
		  Entity result  = tpq.asSingleEntity();
		  result.setProperty("valid", "false"); 
		  ds.put(result);
      	  response.sendRedirect("/submitted.jsp");



		}
		catch(Exception e) {
		}

	}
	}