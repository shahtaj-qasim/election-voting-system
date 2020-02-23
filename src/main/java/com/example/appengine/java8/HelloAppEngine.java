/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.java8;

// [START example]
import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Datastore APIs
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "HelloAppEngine", value = "/hello")
public class HelloAppEngine extends HttpServlet {

	private static final Logger logger = Logger.getLogger(HelloAppEngine.class.getName());

	
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
  
    
	try {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		  Query query = new Query("Candidates");
		  List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());	
	        request.setAttribute("candidates", results);
		request.getRequestDispatcher("/voting.jsp").forward(request, response);
		
		  Properties properties = System.getProperties();

		    response.setContentType("text/plain");
		    response.getWriter().println("Hello App Engine - Standard using "
		            + SystemProperty.version.get() + " Java "
		            + properties.get("java.specification.version"));
		    
		
		  
	} catch (ServletException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			  Query query = new Query("Candidates");
			  List<Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());	
		        request.setAttribute("candidates", results);
//use identifier to make sure same data is not added multiple times when app is reloaded/invoked each time
	    //creating new entity
		
		  
    	response.sendRedirect("/index.jsp");

		  
		}
		catch(Exception e) {
			logger.info(e.getMessage());
		}

	}

  public static String getInfo() {
    return "Version: " + System.getProperty("java.version")
          + " OS: " + System.getProperty("os.name")
          + " User: " + System.getProperty("user.name");
  }
  
  

}
// [END example]
