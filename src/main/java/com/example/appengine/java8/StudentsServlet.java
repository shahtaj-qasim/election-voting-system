package com.example.appengine.java8;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;




@WebServlet(name = "StudentsServlet", value = "/admin/students")
@MultipartConfig
public class StudentsServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(StudentsServlet.class.getName());


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException , ServletException {
	  try {
	  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	   Key keyEmails = KeyFactory.createKey("Emails", "emailsList");

	   Entity emailList = datastore.get(keyEmails);
	   List<String> emails = (List<String>) emailList.getProperty("emails");
        request.setAttribute("emails", emails);
	  }catch(Exception e) {
			logger.info(e.getMessage());
		} 
	  
	    request.getRequestDispatcher("/manage_election.jsp").forward(request, response);
	    
        response.setContentType("text/plain");

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	  try{
          BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

          Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);

          List<BlobKey> blobKeys = blobs.get("file");

          // keyFileUploaded = blobKeys.get(0).getKeyString();
          BlobKey keyFileUploaded = blobKeys.get(0);
		   // Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		    //String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		    //InputStream fileContent = filePart.getInputStream();
          	BlobstoreInputStream fileContent = new BlobstoreInputStream(keyFileUploaded);
		    List<String> emails = new ArrayList<>();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));
			  	    
		    String line;
		    while ((line = reader.readLine()) != null) {
				emails.add(line);
		    }
		    
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		   
			  Entity emailList = new Entity("Emails", "emailsList");
			  emailList.setProperty("emails", emails);

			  ds.put(emailList);
	      request.setAttribute("emails", emails);
	        response.sendRedirect("/admin/manage");

		  
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		} 

	}

}
