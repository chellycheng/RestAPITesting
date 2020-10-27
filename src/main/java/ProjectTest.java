import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class ProjectTest {

	 public static final String baseUrl = "http://localhost:4567/";
	    public static final String projectEndPoint = "projects";
	    public static final String tasksEndIDPoint = "/tasks";
	    public static final String categoriesEndPoint = "/categories";
	    public JSONParser jsonParser = new JSONParser();
	    public HttpClient httpClient = HttpClientBuilder.create().build();

	    final String id = "id";
	    final String description = "description";
	    final String title = "title";
	    final String status = "doneStatus";
	    final String todos = "todos";
	    final String categories = "categories";
	    final String project ="projects";
	    final String completed = "completed";
	    final String active = "active";
	    static HttpURLConnection connection;
	    
	    @BeforeEach
	    public void setup() throws Exception{
	        try{
	            URL url = new URL(baseUrl);
	            connection= (HttpURLConnection) url.openConnection();
	            connection.connect();
	            assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
	        }
	        catch(Exception e){
	            System.out.println("Error in conneciton");
	            throw new Exception();
	        }
	    }
	    
	    @AfterAll
	    public static void after() {
	        connection.disconnect();
	    }
	    
	    

	    @Test
	    public void getProjects()
	            throws ClientProtocolException, IOException {

	        //Set request
	        HttpUriRequest request = new HttpGet(  baseUrl+ projectEndPoint);
	        HttpResponse httpResponse = httpClient.execute(request);

	        //Check response status
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        //Check code
	        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	        try{
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            JSONArray projects_list = (JSONArray) response_jason.get(project);

	            int todos_list_size = projects_list.size();
	            //check size
	            assertTrue(todos_list_size >= 1);

	        }
	        catch(Exception PasrException){
	            System.out.println("Failure");
	        }

	    }

	    @Test
	    public void projectHeaders()
	            throws ClientProtocolException, IOException {
	        HttpHead request = new HttpHead(  baseUrl+ projectEndPoint );
	        HttpResponse httpResponse = httpClient.execute( request );
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	    }

//	    @Test
//	    public void to_dos_post_empty_test()
//	            throws ClientProtocolException, IOException {
//
//	        HttpUriRequest request = new HttpPost(  baseUrl+ projectEndPoint );
//	        HttpResponse httpResponse = httpClient.execute( request );
//	        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
//
//	    }
	    
	    @Test
	    public void createProjectWithoutId()
	            throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ projectEndPoint );
	        String title_value = "429 Test Project";
	        String desc_value = "working in process";
	        Boolean completed_status = false;
	        Boolean active_status = true;
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	        json.put(completed, completed_status);
	        json.put(active, active_status);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
	
	        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	            assertEquals("false", (response_jason.get(completed)));
	            assertEquals("true", (response_jason.get(active)));
	            String id = (String)response_jason.get("id");
//	            HttpUriRequest request_delete = new HttpDelete(  baseUrl+ projectEndPoint+"/"+id );
//	            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
//	            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	
	        }
	        catch(Exception PasrException){
	            System.out.println("Failure at createProjectWithoutId");
	        }
	
	    }


	    @Test
	    public void getProjectById () throws ClientProtocolException, IOException{
	        String expected_id = "1";
	        String expected_title = "Office Work";
	        String expected_completed = "false";
	        String expected_active = "false";
	        String expected_description = "";
	        //Set request
	        HttpUriRequest request = new HttpGet(  baseUrl+ projectEndPoint+ "?id="+ expected_id);
	        HttpResponse httpResponse = httpClient.execute(request);

	        //Check response status
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        //Check code
	        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	        try{
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            JSONArray project_list = (JSONArray) response_jason.get(todos);
	            JSONObject project_object = (JSONObject) project_list.get(0);
	            assertEquals(expected_id, (String) (project_object.get(id)));
	            assertEquals(expected_title, (String) (project_object.get(title)));
	            assertEquals(expected_completed, (String) (project_object.get(completed)));
	            assertEquals(expected_active, (String) (project_object.get(expected_active)));
	            assertEquals(expected_description, (String) (project_object.get(description)));

	        }
	        catch(Exception PasrException){
	            System.out.println("Failure");
	        }

	    }

	    @Test
	    public void to_dos_id_head_test()
	            throws ClientProtocolException, IOException {
	        String expected_id = "1";
	        HttpUriRequest request = new HttpGet(  baseUrl+ projectEndPoint+ "?id="+ expected_id);
	        HttpResponse httpResponse = httpClient.execute( request );
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	    }

	    @Test
	    public void postProjectWithId()
	            throws ClientProtocolException, IOException{
	    	 String expected_id = "1";
	    	 HttpPost request = new HttpPost(  baseUrl+ projectEndPoint+ "/"+ expected_id);
		        String title_value = "New Office Work";
		        String desc_value = "new process";
		        Boolean completed_status = true;
		        Boolean active_status = true;
		        JSONObject json = new JSONObject();
		        json.put(title, title_value);
		        json.put(description, desc_value);
		        json.put(completed, completed_status);
		        json.put(active, active_status);
		
		        StringEntity userEntity = new StringEntity(json.toString());
		        request.addHeader("content-type", "application/json");
		        request.setEntity(userEntity);
		        HttpResponse httpResponse = httpClient.execute( request );
		
		        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		        try{
		            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
		            assertEquals(title_value, (String) (response_jason.get(title)));
		            assertEquals(desc_value, (String) (response_jason.get(description)));
		            assertEquals("true", (response_jason.get(completed)));
		            assertEquals("true", (response_jason.get(active)));
		            String id = (String)response_jason.get("id");
		
		        }
		        catch(Exception PasrException){
		            System.out.println("Failure at postProjectWithId");
		        }
		

	    }
	    
	    @Test
	    public void updateProjectById()
	            throws ClientProtocolException, IOException{
	    	 this.createProjectWithoutId();
	    	
	    	 String expected_id = "2";
	    	 HttpPut request = new HttpPut(  baseUrl+ projectEndPoint+ "/"+ expected_id);
		        String title_value = "Office Work";
		        String desc_value = "";
		        Boolean completed_status = false;
		        Boolean active_status = false;
		        JSONObject json = new JSONObject();
		        json.put(title, title_value);
		        json.put(description, desc_value);
		        json.put(completed, completed_status);
		        json.put(active, active_status);
		
		        StringEntity userEntity = new StringEntity(json.toString());
		        request.addHeader("content-type", "application/json");
		        request.setEntity(userEntity);
		        HttpResponse httpResponse = httpClient.execute( request );
		
		        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
		        try{
		            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
		            assertEquals(title_value, (String) (response_jason.get(title)));
		            assertEquals(desc_value, (String) (response_jason.get(description)));
		            assertEquals("false", (response_jason.get(completed)));
		            assertEquals("false", (response_jason.get(active)));
		            String id = (String)response_jason.get("id");
		
		        }
		        catch(Exception PasrException){
		            System.out.println("Failure at updateProjectById");
		        }

	    }
	    
	    @Test
	    public void deleteProjectById()
	    		throws ClientProtocolException, IOException {
	    	this.createProjectWithoutId();
	    	String delete_id = "3";
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ projectEndPoint+"/"+ delete_id );
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    

	    @Test
	    public void getTodosByProjectId()
	            throws ClientProtocolException, IOException {
	        String expected_id = "1";
	        String todo_id = "2";
	        String expected_title = "scan paperwork";
	        String expected_status = "false";
	        String expected_desc = "";
	        //Set request
	        HttpUriRequest request = new HttpGet(  baseUrl+ projectEndPoint+ "/"+ expected_id+tasksEndIDPoint);
	        HttpResponse httpResponse = httpClient.execute(request);

	        //Check response status
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        //Check code
	        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	        try{
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            JSONArray todo_list = (JSONArray) response_jason.get(todos);
	            JSONObject todo_object = (JSONObject) todo_list.get(0);
	            assertEquals(todo_id, (String) (todo_object.get(id)));
	            assertEquals("false",  (todo_object.get(status)));
	            assertEquals(expected_desc, (String) (todo_object.get(description)));

	        }
	        catch(Exception PasrException){
	            System.out.println("Failure");
	        }

	    }

	    @Test
	    public void getTodoHeader()
	            throws ClientProtocolException, IOException {
	        String expected_id = "101";
	        HttpUriRequest request = new HttpHead(  baseUrl+ projectEndPoint+ "/"+ expected_id+tasksEndIDPoint);
	        HttpResponse httpResponse = httpClient.execute( request );
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	    }

	    @Test
	    public void createTasksByTodoId()
	            throws ClientProtocolException, IOException {
    	 	String expected_id = "1";
    	 	String expected_title = "scan paperwork";
	        String expected_status = "false";
	        String expected_desc = "";
	        HttpPost request = new HttpPost(  baseUrl+ projectEndPoint+ "/"+ expected_id+tasksEndIDPoint);
	        String todo_id = "1";
	        JSONObject json = new JSONObject();
	        json.put("id", todo_id);
	 
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
	
	        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);

	        // This API has bug in exploratory text
	        assertEquals(404, httpResponse.getStatusLine().getStatusCode());
	        try{
	        	JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            JSONArray todo_list = (JSONArray) response_jason.get(todos);
	            JSONObject todo_object = (JSONObject) todo_list.get(0);
	            assertEquals(expected_id, (String) (todo_object.get(id)));
	            assertEquals(expected_title, (String) (todo_object.get(title)));
	            assertEquals("false",  (todo_object.get(status)));
	            assertEquals(expected_desc, (String) (todo_object.get(description)));
	
	        }
	        catch(Exception PasrException){
	            System.out.println("Failure at createTasksByTodoId");
	        }
		

	    }

	    @Test
	    public void deleteTaskById()
	            throws ClientProtocolException, IOException {
	    	String project_id = "1";
	    	String task_id = "2";
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ projectEndPoint+"/"+ project_id+ tasksEndIDPoint+"/"+ task_id );
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }

	    @Test
	    public void getCategoryById()
	        throws ClientProtocolException, IOException {
	        String project_id = "131";
	        String expected_id = "1";
	        //Set request
	        HttpGet request = new HttpGet(  baseUrl+ projectEndPoint+ "/"+ project_id+categoriesEndPoint);
	        HttpResponse httpResponse = httpClient.execute(request);

	        //Check response status
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        //Check code
	        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	        
	        try{
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            JSONArray category_list = (JSONArray) response_jason.get(categories);
	            JSONObject category_object = (JSONObject) category_list.get(0);
	            assertEquals(expected_id, (String) (category_object.get(id)));

	        }
	        catch(Exception PasrException){
	            System.out.println("Failure");
	        }

	    }

	    @Test
	    public void getHeadersByCategory()
	        throws ClientProtocolException, IOException {
	    	  String expected_id = "99";
		        HttpUriRequest request = new HttpHead(  baseUrl+ projectEndPoint+ "/"+ expected_id+categoriesEndPoint);
		        HttpResponse httpResponse = httpClient.execute( request );
		        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	    }

	    @Test
	    public void postCategoryById()
	    		 throws ClientProtocolException, IOException {
	    	// This API has bug in exploratory test
    	 	String expected_id = "1";
	        HttpPost request = new HttpPost(  baseUrl+ projectEndPoint+ "/"+ expected_id+categoriesEndPoint);
	        String category_id = "1";
	        JSONObject json = new JSONObject();
	        json.put("id", category_id);
	 
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
	
	        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);

	        assertEquals(404, httpResponse.getStatusLine().getStatusCode());
	        try{
	        	JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            JSONArray category_list = (JSONArray) response_jason.get(todos);
	            JSONObject category_object = (JSONObject) category_list.get(0);
	            assertEquals(category_id, (String) (category_object.get(id)));
	
	        }
	        catch(Exception PasrException){
	            System.out.println("Failure at postCategoryById");
	        }

	    }

	    @Test
	    public void deleteCategoryById()
	        throws ClientProtocolException, IOException {
	    	// This API has bug in exploratory test
	    	String project_id = "1";
	    	String category_id = "1";
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ projectEndPoint+"/"+ project_id+ categoriesEndPoint+"/"+ category_id );
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }
}
