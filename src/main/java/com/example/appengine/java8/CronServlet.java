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
import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.apphosting.api.ApiProxy.LogRecord.Level;

import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "CronServlet", value = "/cron/cronjob")
public class CronServlet extends HttpServlet {
	
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
	try {
	_logger.info("Cron Job has been executed");

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key keyStart = KeyFactory.createKey("Date", "start");
	Entity startElection = datastore.get(keyStart);
	DateTime startDate = new DateTime( startElection.getProperty("date"));
	DateTime yet = new DateTime();
	DateTime sendReminder = startDate.minusDays(1);
	
	if (yet.getDayOfYear()==sendReminder.getDayOfYear()) {
		_logger.info("Cron Job : one day before the election day : send emails reminder");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		 Key keyEmails = KeyFactory.createKey("Emails", "emailsList");

		   Entity emailList;
		try {
			emailList = ds.get(keyEmails);
		   List<String> emails = (List<String>) emailList.getProperty("emails");

			if (emails != null ){
			
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			for (String s : emails) {
	        try {
	        	
	        	Message msg = new MimeMessage(session);
					  msg.setFrom(new InternetAddress("cocopiriou@gmail.com", "Election admin"));
					  msg.addRecipient(Message.RecipientType.TO,
					                   new InternetAddress(s, "Uni Student"));
					  msg.setSubject("Election Reminder");
					  msg.setText("Dear students, "
						  		+ "\n" + "As a reminder, the election starts tomorrow."
						  				+ " You can vote by clicking on the link sent to you in the previous email. ");
						  Transport.send(msg);
	        	
			  
	        }
	        catch(Exception e) {
	        	_logger.info(e.getMessage());
	        }
			}
			}
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}        
	}
	
	
	}
	catch (Exception ex) {
	}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	doGet(req, resp);
	}
}