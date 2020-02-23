package com.example.appengine.java8;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import io.jsonwebtoken.Jwts;



//import com.google.appengine.api.mail.MailService.Message;

/**
 * Servlet implementation class MailServlet
 */
@WebServlet(name="MailServlet", value="/mailapi")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MailServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		 Key keyEmails = KeyFactory.createKey("Emails", "emailsList");

		   Entity emailList;
		try {
			emailList = ds.get(keyEmails);
		   List<String> emails = (List<String>) emailList.getProperty("emails");

			if (emails != null ){
			String tokenInEmail="";
			
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			for (String s : emails) {
	        try {
	        	tokenInEmail= generateJWTToken(s);

		   		  
	        	logger.info("Hi "+tokenInEmail);
	        	Message msg = new MimeMessage(session);
					  msg.setFrom(new InternetAddress("shahtaj.qasim7@gmail.com", "Election admin"));
					  msg.addRecipient(Message.RecipientType.TO,
					                   new InternetAddress(s, "Uni Student"));
					  msg.setSubject("Elections are coming. Vote for good!");
					  msg.setText("This email is to inform you about the fact that because of your laziness, we created"
					  		+ " this voting system. So please go and vote: "
					  		+ " Your token link is: "+ "https://assignment02.appspot.com/ballot?token="+tokenInEmail);
					  Transport.send(msg);

			  
	        }
	        catch(Exception e) {
	        	logger.info(e.getMessage());
	        }
			}
			}
		} catch (EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		response.sendRedirect("/admin/manage");

	}
	
	public String generateJWTToken(String stuEmail) {
		  String token = Jwts.builder()
		     // .setSubject(user)
		      .claim("groups", new String[] { "admin", "student" })
		      .claim("mail", stuEmail)
		      //.signWith(SignatureAlgorithm.HS512, System.getProperty("JWT-KEY"))
		      .compact();

		  DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		  Entity t = new Entity("Token");
		  t.setProperty("name", token);
		  t.setProperty("valid", "true");

		  ds.put(t);
		  return token;
		}
}
