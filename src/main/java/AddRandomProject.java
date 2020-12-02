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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

@TestMethodOrder(Alphanumeric.class)
public class AddRandomProject {

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
	
	
	@BeforeAll
	public static void generateProject() throws Exception{
//		try{
//			//allStart = System.currentTimeMillis();
//			ArrayList<String> command = new ArrayList<String>();
//			command.add("java"); // quick and dirty for unix
//			command.add("-jar");
//			command.add("/Users/hezirui/Downloads/runTodoManagerRestAPI-1.5.5.jar");
//
//			ProcessBuilder builder = new ProcessBuilder(command);
//			builder.redirectErrorStream(true);
//			process = builder.inheritIO().start();
//			Thread.sleep(1000);
//			URL url = new URL(baseUrl);
//			connection= (HttpURLConnection) url.openConnection();
//			connection.connect();
//			assertEquals(HttpURLConnection.HTTP_OK, connection.getResponseCode());
//			//partStart = System.currentTimeMillis();
//		}
//		catch(Exception e){
//			System.out.println("Error in connection");
//			throw new Exception();
//		}
		
		for (int i = 0; i < 1000; i++) {
			new AddRandomProject().createRandomProjects();
		}
	}

	@BeforeEach
	public void setup() throws Exception{
		try{
			//allStart = System.currentTimeMillis();
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
//		process.destroy();
//		Thread.sleep(500);
//		System.out.println("The execution time include setup, teardown, and check correctness is " + (System.currentTimeMillis()-allStart)/1000.0 + "s");
	}
	    
	    
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
			//print_time_so_far(partStart);
	        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
	        try{
	            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
	            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
	            assertEquals(title_value, (String) (response_jason.get(title)));
	            assertEquals(desc_value, (String) (response_jason.get(description)));
	            assertEquals("false", (response_jason.get(completed)));
	            assertEquals("true", (response_jason.get(active)));
	            String id = (String)response_jason.get("id");
	
	        }
	        catch(Exception ParseException){
	            System.out.println("Failure at createProjectWithoutId");
	        }
	
	    }


	    
	    @Test
	    public void updateProjectById()
	            throws ClientProtocolException, IOException{
	    	 //this.createProjectWithoutId();
	    	
	    	 String expected_id = "500";
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
				//print_time_so_far(partStart);
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
		        catch(Exception ParseException){
		            System.out.println("Failure at updateProjectById");
		        }

	    }
	    
	    @Test
	    public void deleteProjectById()
	    		throws ClientProtocolException, IOException {
	    	this.createProjectWithoutId();
	    	String delete_id = "600";
	    	HttpUriRequest request_delete = new HttpDelete(  baseUrl+ projectEndPoint+"/"+ delete_id );
            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
	    }
	    
	    
	    private void createRandomProjects()
	            throws ClientProtocolException, IOException {
	        HttpPost request = new HttpPost(  baseUrl+ projectEndPoint );
	        
	        byte[] array = new byte[7]; // length is bounded by 7
	        new Random().nextBytes(array);
	        String title_value = randomString();
	        String desc_value = randomString();
	        
	        //String title_value = "Random Test Project";
	        //String desc_value = "random process";
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
	
	    }
	    
	    private String randomString() {
	        int leftLimit = 97; // letter 'a'
	        int rightLimit = 122; // letter 'z'
	        int targetStringLength = 10;
	        Random random = new Random();
	        StringBuilder buffer = new StringBuilder(targetStringLength);
	        for (int i = 0; i < targetStringLength; i++) {
	            int randomLimitedInt = leftLimit + (int) 
	              (random.nextFloat() * (rightLimit - leftLimit + 1));
	            buffer.append((char) randomLimitedInt);
	        }
	        String generatedString = buffer.toString();
	        return generatedString;
	    }

		private void print_time_so_far(long start_time){
			System.out.println("The execute time without setup, teardown, and check correctness is " + (System.currentTimeMillis()-start_time)/1000.0 + "s");
		}
}
