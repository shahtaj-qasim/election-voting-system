package com.example.appengine.java8;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "Results", value = "/results")
public class ResultsServlet extends HttpServlet {
	
	private final static Logger LOGGER = Logger.getLogger(BallotServlet.class.getName());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		    Query q = new Query("Candidates");

			//Use PreparedQuery interface to retrieve results
		    PreparedQuery pq = ds.prepare(q);
	        
		    //get number of votes and max vote
		    float sum = 0;
		    int maxVotes = 0;
		    int currVote = 0;
		    int currCandidate = 0;
			int winningCandidate = 0;
		    
			for (Entity candidate : pq.asIterable()) {
				
				currVote = Integer.parseInt((String) candidate.getProperty("votes"));
				
				if(currVote >= maxVotes) {
					
					maxVotes = currVote;
					winningCandidate = currCandidate;
					
				}
				
				sum += currVote;
				currCandidate++;
			}
			
		     //get number of students and calc percentage
			 Key keyEmails = KeyFactory.createKey("Emails", "emailsList");
 
    	     Entity emailList = null;
			try {
				emailList = ds.get(keyEmails);
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			 List<String> emails = (List<String>) emailList.getProperty("emails");
			 
			 int numStudents = emails.size();
			 
			 float percentage = (sum/numStudents * 100);
			 
		     request.setAttribute("candidates", pq);
		     request.setAttribute("winningCandidate", winningCandidate);
		     request.setAttribute("percentage", percentage);
		
			 response.setContentType("text/plain");
			  
			 request.getRequestDispatcher("/result.jsp").forward(request, response);
		 

	}

	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		}
}