//import static org.junit.Assert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.*;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.junit.jupiter.api.Test;
//import org.apache.http.HttpResponse;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//
//public class TodoTest {
//
//    public static final String baseUrl = "http://localhost:4567/";
//    public static final String toDoEndPoint = "todos";
//    public static final String toDoEndIDPoint = "todos/";
//    public static final String categoriesEndPoint = "/categories";
//    public static final String tasksOfEndPoint = "/tasksof";
//    public JSONParser jsonParser = new JSONParser();
//    public HttpClient httpClient = HttpClientBuilder.create().build();
//
//    final String id = "id";
//    final String description = "description";
//    final String title = "title";
//    final String status = "doneStatus";
//    final String todos = "todos";
//    final String categories = "categories";
//    final String project ="projects";
//
//    @Test
//    public void to_dos_get_test()
//            throws ClientProtocolException, IOException {
//
//        //Set request
//        HttpUriRequest request = new HttpGet(  baseUrl+ toDoEndPoint );
//        HttpResponse httpResponse = httpClient.execute(request);
//
//        //Check response status
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//        //Check code
//        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//        try{
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            JSONArray todos_list = (JSONArray) response_jason.get(todos);
//
//            int todos_list_size = todos_list.size();
//            //check size
//            assertEquals(2, todos_list_size);
//
//        }
//        catch(Exception PasrException){
//            System.out.println("Failure");
//        }
//
//    }
//
//    @Test
//    public void to_dos_head_test()
//            throws ClientProtocolException, IOException {
//        HttpUriRequest request = new HttpHead(  baseUrl+ toDoEndPoint );
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void to_dos_post_empty_test()
//            throws ClientProtocolException, IOException {
//
//        HttpUriRequest request = new HttpPost(  baseUrl+ toDoEndPoint );
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
//
//    }
//
//    @Test
//    public void to_dos_post_title_param_test()
//            throws ClientProtocolException, IOException {
//        HttpPost request = new HttpPost(  baseUrl+ toDoEndPoint );
//        String title_value = "Test001";
//        String desc_value = "OK";
//        JSONObject json = new JSONObject();
//        json.put(title, title_value);
//        json.put(description, desc_value);
//
//        StringEntity userEntity = new StringEntity(json.toString());
//        request.addHeader("content-type", "application/json");
//        request.setEntity(userEntity);
//        HttpResponse httpResponse = httpClient.execute( request );
//
//        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
//        try{
//            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            assertEquals(title_value, (String) (response_jason.get(title)));
//            assertEquals(desc_value, (String) (response_jason.get(description)));
//            String id = (String)response_jason.get("id");
//            HttpUriRequest request_delete = new HttpDelete(  baseUrl+ toDoEndPoint+"/"+id );
//            HttpResponse httpResponse_delete = httpClient.execute( request_delete );
//            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
//
//        }
//        catch(Exception PasrException){
//            System.out.println("Failure at to_dos_post_title_param_test");
//        }
//
//    }
//
//    @Test
//    public void to_dos_id_get_test () throws ClientProtocolException, IOException{
//        String expected_id = "1";
//        String expected_title = "scan paperwork";
//        String expected_status = "false";
//        String expected_description = "";
//        //Set request
//        HttpUriRequest request = new HttpGet(  baseUrl+ toDoEndIDPoint+ expected_id);
//        HttpResponse httpResponse = httpClient.execute(request);
//
//        //Check response status
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//        //Check code
//        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//        try{
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            JSONArray todos_list = (JSONArray) response_jason.get(todos);
//            JSONObject todo_object = (JSONObject) todos_list.get(0);
//            assertEquals(expected_id, (String) (todo_object.get(id)));
//            assertEquals(expected_title, (String) (todo_object.get(title)));
//            assertEquals(expected_status, (String) (todo_object.get(status)));
//            assertEquals(expected_description, (String) (todo_object.get(description)));
//
//        }
//        catch(Exception PasrException){
//            System.out.println("Failure");
//        }
//
//    }
//
//    @Test
//    public void to_dos_id_head_test()
//            throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        HttpUriRequest request = new HttpGet(  baseUrl+ toDoEndIDPoint+ expected_id);
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void to_dos_id_post_test()
//            throws ClientProtocolException, IOException{
//
//
//
//    }
//
//    @Test
//    public void to_dos_id_put_test()
//            throws ClientProtocolException, IOException{
//
//
//
//    }
//
//    @Test
//    public void to_dos_id_cat_get_test()
//            throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        String expected_title = "Office";
//        String expected_description = "";
//        //Set request
//        HttpUriRequest request = new HttpGet(  baseUrl+ toDoEndIDPoint+ expected_id+categoriesEndPoint);
//        HttpResponse httpResponse = httpClient.execute(request);
//
//        //Check response status
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//        //Check code
//        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//        try{
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            JSONArray todos_list = (JSONArray) response_jason.get(categories);
//            JSONObject todo_object = (JSONObject) todos_list.get(0);
//            assertEquals(expected_id, (String) (todo_object.get(id)));
//            assertEquals(expected_title, (String) (todo_object.get(title)));
//            assertEquals(expected_description, (String) (todo_object.get(description)));
//
//        }
//        catch(Exception PasrException){
//            System.out.println("Failure");
//        }
//
//    }
//
//    @Test
//    public void to_dos_id_cat_head_test()
//            throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        System.out.println(baseUrl+ toDoEndPoint+expected_id+categoriesEndPoint);
//        HttpUriRequest request = new HttpHead(  baseUrl+ toDoEndIDPoint+expected_id+categoriesEndPoint);
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void to_dos_id_cat_post_empty_test()
//            throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        HttpUriRequest request = new HttpPost(  baseUrl+ toDoEndIDPoint +expected_id+categoriesEndPoint);
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void to_dos_id_cat_post_param_test()
//            throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        HttpPost request = new HttpPost(baseUrl + toDoEndIDPoint + expected_id + categoriesEndPoint);
//        String title_value = "Test_cat_001";
//        JSONObject json = new JSONObject();
//        json.put(title, title_value);
//        StringEntity userEntity = new StringEntity(json.toString());
//        request.addHeader("content-type", "application/json");
//        request.setEntity(userEntity);
//        HttpResponse httpResponse = httpClient.execute(request);
//
//        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
//        try {
//            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            assertEquals(title_value, (String) (response_jason.get(title)));
//            String id = (String) response_jason.get("id");
//            HttpUriRequest request_delete = new HttpDelete(baseUrl + toDoEndIDPoint + expected_id + categoriesEndPoint + "/" + id);
//            HttpResponse httpResponse_delete = httpClient.execute(request_delete);
//            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
//
//        } catch (Exception PasrException) {
//            System.out.println("Failure at to_dos_post_title_param_test");
//        }
//    }
//
//    @Test
//    public void to_dos_id_task_get_test()
//        throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        String expected_title = "Office Work";
//        String expected_description = "";
//        //Set request
//        HttpUriRequest request = new HttpGet(  baseUrl+ toDoEndIDPoint+ expected_id+tasksOfEndPoint);
//        HttpResponse httpResponse = httpClient.execute(request);
//
//        //Check response status
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//        //Check code
//        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//        try{
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            JSONArray todos_list = (JSONArray) response_jason.get(categories);
//            JSONObject todo_object = (JSONObject) todos_list.get(0);
//            assertEquals(expected_id, (String) (todo_object.get(id)));
//            assertEquals(expected_title, (String) (todo_object.get(title)));
//            assertEquals(expected_description, (String) (todo_object.get(description)));
//
//        }
//        catch(Exception PasrException){
//            System.out.println("Failure");
//        }
//
//    }
//
//    @Test
//    public void to_dos_id_task_head_test()
//        throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        System.out.println(baseUrl+ toDoEndPoint+expected_id+categoriesEndPoint);
//        HttpUriRequest request = new HttpHead(  baseUrl+ toDoEndIDPoint+expected_id+tasksOfEndPoint);
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void to_dos_id_task_post_empty_test()
//        throws ClientProtocolException, IOException {
//        //This test failed the unit test, buggy
//        String expected_id = "1";
//        HttpUriRequest request = new HttpPost(  baseUrl+ toDoEndIDPoint +expected_id+tasksOfEndPoint);
//        HttpResponse httpResponse = httpClient.execute( request );
//        assertEquals(400, httpResponse.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void to_dos_id_task_post_param_test()
//        throws ClientProtocolException, IOException {
//        String expected_id = "1";
//        HttpPost request = new HttpPost(baseUrl + toDoEndIDPoint + expected_id + tasksOfEndPoint);
//        String title_value = "Test_task_001";
//        JSONObject json = new JSONObject();
//        json.put(title, title_value);
//        StringEntity userEntity = new StringEntity(json.toString());
//        request.addHeader("content-type", "application/json");
//        request.setEntity(userEntity);
//        HttpResponse httpResponse = httpClient.execute(request);
//
//        assertEquals(201, httpResponse.getStatusLine().getStatusCode());
//        try {
//            String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//            JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//            assertEquals(title_value, (String) (response_jason.get(title)));
//            String id = (String) response_jason.get("id");
//            HttpUriRequest request_delete = new HttpDelete(baseUrl + toDoEndIDPoint + expected_id + tasksOfEndPoint + "/" + id);
//            HttpResponse httpResponse_delete = httpClient.execute(request_delete);
//            assertEquals(200, httpResponse_delete.getStatusLine().getStatusCode());
//
//        } catch (Exception PasrException) {
//            System.out.println("Failure at to_dos_post_title_param_test");
//        }
//    }
//}
