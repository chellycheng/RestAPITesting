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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

@TestMethodOrder(Alphanumeric.class)
public class CategoriesTest {

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
	private static Process process;
	long allStart;
	long partStart;


	@BeforeEach
	public void setup() throws Exception{
		try{
			allStart = System.currentTimeMillis();
			ArrayList<String> command = new ArrayList<String>();
			command.add("java"); // quick and dirty for unix
			command.add("-jar");
			if(System.getProperties().getProperty("user.name").equals("Pengnan Fan")) {
            	command.add("D:\\McGill\\20Fall\\ECSE 429\\runTodoManagerRestAPI-1.5.5.jar");
            } else {
            	command.add("/Users/hehuimincheng/ECSE429/runTodoManagerRestAPI-1.5.5.jar");
            }
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.redirectErrorStream(true);
			process = builder.inheritIO().start();
			Thread.sleep(500);
			URL url = new URL(baseUrl);
			connection= (HttpURLConnection) url.openConnection();
			connection.connect();
			assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
			partStart = System.currentTimeMillis();
		}
		catch(Exception e){
			System.out.println("Error in connection");
			throw new Exception();
		}
	}

	@AfterEach
	public void afterClass() throws Exception{
		System.out.println("The execute time without setup, teardown is  " + (System.currentTimeMillis()-partStart)/1000.0 +"s");
		process.destroy();
		Thread.sleep(500);
		System.out.println("The execution time include setup, teardown, and check correctness is " + (System.currentTimeMillis()-allStart)/1000.0 + "s");
	}
	    @Test 
	    public void getCategories() throws ClientProtocolException, IOException {
	        
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint);
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    	String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
	    	try {
	    		JSONObject response_json = (JSONObject) jsonParser.parse(responseBody);
	    		JSONArray categories_list = (JSONArray) response_json.get(categories);
	    		int list_size = categories_list.size();
	    		assertTrue(list_size >= 1);
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    	
	    }
	    
	    @Test
	    public void categoriesHead() throws ClientProtocolException, IOException {
	        HttpHead request = new HttpHead(  baseUrl+categoriesEndPoint );
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void createCategoryWithoutId() throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ categoriesEndPoint );
	        String title_value = "429 Test Category 2";
	        String desc_value = "working in process 2";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void createCategoryWithoutId2() throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ categoriesEndPoint );
	        String title_value = "429 Test Category 3";
	        String desc_value = "working in process 3";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
//	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
//	            assertEquals(desc_value, (String) (response_jason.get(description)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void createCategoryWithoutId3() throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ categoriesEndPoint );
	        String title_value = "429 Test Category 4";
	        String desc_value = "working in process 4";
	        JSONObject json = new JSONObject();
//	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void createCategoryWithoutId4() throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ categoriesEndPoint );
	        String title_value = "429 Test Category 5";
	        String desc_value = "working in process 5";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	        json.put(id, 1);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(400, httpResponse.getStatusLine().getStatusCode());

	    }
	    
	    @Test
	    public void createCategoryWithoutId5() throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ categoriesEndPoint );
	        String title_value = "429 Test Category 5";
	        String desc_value = "working in process 5";
	        JSONObject json = new JSONObject();
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
	    }
	    
	    @Test 
	    public void getCategoriesById() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint+"/1");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    	String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
	    	try {
	    		JSONObject response_json = (JSONObject) jsonParser.parse(responseBody);
	    		JSONArray categories_list = (JSONArray) response_json.get(categories);
	    		int list_size = categories_list.size();
	    		
	    		assertTrue(list_size >= 1);
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    }
	    
	    @Test 
	    public void getCategoriesById2() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint+"/1000");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(404, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void CategoryHeadById() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpHead(baseUrl+categoriesEndPoint+"/1");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void CategoryHeadById2() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpHead(baseUrl+categoriesEndPoint+"/100000000");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(404, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void postCategoryById() throws ClientProtocolException, IOException {
	    	HttpPut request = new HttpPut(baseUrl+categoriesEndPoint+"/1");
	    	String title_value = "429 Test Category 1";
	        String desc_value = "working in process 1";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void postCategoryById1() throws ClientProtocolException, IOException {
	    	HttpPut request = new HttpPut(baseUrl+categoriesEndPoint+"/10000");
	    	String title_value = "429 Test Category 2";
	        String desc_value = "working in process 2";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(404, httpResponse.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void postCategoryById2() throws ClientProtocolException, IOException {
	    	HttpPut request = new HttpPut(baseUrl+categoriesEndPoint+"/1");
	    	String title_value = "429 Test Category 3";
	        String desc_value = "working in process 3";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
//	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void postCategoryById3() throws ClientProtocolException, IOException {
	    	HttpPut request = new HttpPut(baseUrl+categoriesEndPoint+"/1");
	    	String title_value = "429 Test Category 4";
	        String desc_value = "working in process 4";
	        JSONObject json = new JSONObject();
//	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
	    }

	    @Test
	    public void putCategoryById() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/1");
	    	String title_value = "429 Test Category";
	        String desc_value = "working in process";
	        JSONObject json = new JSONObject();
	        json.put(id, 1);
	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void putCategoryById1() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/10000");
	    	String title_value = "429 Test Category 2";
	        String desc_value = "working in process 2";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(404, httpResponse.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void putCategoryById2() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/1");
	    	String title_value = "429 Test Category 3";
	        String desc_value = "working in process 3";
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
//	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
//	            assertEquals(desc_value, (String) (response_jason.get(description)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void putCategoryById3() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/1");
	    	String title_value = "429 Test Category 4";
	        String desc_value = "working in process 4";
	        JSONObject json = new JSONObject();
	        json.put(description, desc_value);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	        }
	        catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void deleteCategoryById() throws ClientProtocolException, IOException {
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ categoriesEndPoint+"/2");
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
			print_time_so_far(partStart);
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void deleteCategoryById2() throws ClientProtocolException, IOException {
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ categoriesEndPoint+"/200");
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
			print_time_so_far(partStart);
            assertEquals(404, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void createProjectsToSpecificCategoryById() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/1/projects");
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
			print_time_so_far(partStart);
	        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
	        try{
	        	String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	            assertEquals("false", (response_jason.get(completed)));
	            assertEquals("true", (response_jason.get(active)));
	        } catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void createProjectsToSpecificCategoryById2() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/1114514/projects");
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
			print_time_so_far(partStart);
	        assertEquals(404, httpResponse.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void getProjectsByCategoryId() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint+"/1/projects");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    	String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
	    	try {
	    		JSONObject response_json = (JSONObject) jsonParser.parse(responseBody);
	    		JSONArray categories_list = (JSONArray) response_json.get(categories);
	    		int list_size = categories_list.size();
	    		assertTrue(list_size >= 1);
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    }
	    
	    @Test
	    public void getProjectsByCategoryId1() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint+"/114514/projects");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(404, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void ProjectsHeadByCategoryId() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpHead(baseUrl+categoriesEndPoint+"/1/projects");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void ProjectsHeadByCategoryId1() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpHead(baseUrl+categoriesEndPoint+"/114514/projects");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(404, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void deleteProjectsByCategoryId() throws ClientProtocolException, IOException {
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ categoriesEndPoint+"/1/projects/1");
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
			print_time_so_far(partStart);
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void getTodosByCategoryID() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint+"/1/todos");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    	String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
	    	try {
	    		JSONObject response_json = (JSONObject) jsonParser.parse(responseBody);
	    		JSONArray categories_list = (JSONArray) response_json.get(categories);
	    		int list_size = categories_list.size();
	    		assertTrue(list_size >= 1);
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	    }
	    
	    @Test
	    public void getTodosByCategoryID1() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpGet(baseUrl+categoriesEndPoint+"/114514/todos");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(404, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void TodosHeadByCategoryID() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpHead(baseUrl+categoriesEndPoint+"/1/todos");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(200, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void TodosHeadByCategoryID2() throws ClientProtocolException, IOException {
	    	HttpUriRequest request = new HttpHead(baseUrl+categoriesEndPoint+"/114514/todos");
	    	HttpResponse response = httpClient.execute(request);
			print_time_so_far(partStart);
	    	assertEquals(404, response.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void createTodosByCategoryID() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/1/todos");
	    	String title_value = "429 Test todo 123";
	        String desc_value = "4567";
	        Boolean completed_status = false;
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	        json.put("doneStatus", completed_status);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
	        try{
	        	String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	            assertEquals("false", (response_jason.get("doneStatus")));
	        } catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	    }
	    
	    @Test
	    public void createTodosByCategoryID1() throws ClientProtocolException, IOException {
	    	HttpPost request = new HttpPost(baseUrl+categoriesEndPoint+"/114514/todos");
	    	String title_value = "429 Test todo 123";
	        String desc_value = "4567";
	        Boolean completed_status = false;
	        JSONObject json = new JSONObject();
	        json.put(title, title_value);
	        json.put(description, desc_value);
	        json.put("doneStatus", completed_status);
	
	        StringEntity userEntity = new StringEntity(json.toString());
	        request.addHeader("content-type", "application/json");
	        request.setEntity(userEntity);
	        HttpResponse httpResponse = httpClient.execute( request );
			print_time_so_far(partStart);
	    }
	    
	    public void deleteTodosById() throws ClientProtocolException, IOException {
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ categoriesEndPoint+"/1/todos/1");
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
			print_time_so_far(partStart);
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void deleteTodosById1() throws ClientProtocolException, IOException {
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ categoriesEndPoint+"/114514/todos/1");
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
			print_time_so_far(partStart);
            assertEquals(404, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    
	    @Test
	    public void deleteTodosById2() throws ClientProtocolException, IOException {
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ categoriesEndPoint+"/1/todos/114514");
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
			print_time_so_far(partStart);
            assertEquals(404, httpResponse_delete.getStatusLine().getStatusCode());
	    }

		private void print_time_so_far(long start_time){
			System.out.println("The execute time without setup, teardown, and check correctness is " + (System.currentTimeMillis()-start_time)/1000.0 + "s");
		}
	    
}
