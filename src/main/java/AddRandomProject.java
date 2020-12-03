import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddRandomCategory {

    public static final String baseUrl = "http://localhost:4567/";
    public static final String toDoEndPoint = "todos";
    public static final String toDoEndIDPoint = "todos/";
    public static final String tasksOfEndPoint = "/tasksof";
    public static final String projectEndPoint = "projects";
    public static final String tasksEndIDPoint = "/tasks";
    public static final String categoriesEndPoint = "/categories";
    static public JSONParser jsonParser = new JSONParser();
    static public HttpClient httpClient = HttpClientBuilder.create().build();

    static final String id = "id";
    static final String description = "description";
    static final String title = "title";
    static final String status = "doneStatus";
    static final String todos = "todos";
    static final String categories = "categories";
    static final String project ="projects";
    static final String completed = "completed";
    static final String active = "active";
    static HttpURLConnection connection;
    private static Process process;
    static ArrayList<String> generatedString = new ArrayList<String>();
    static ArrayList<Integer> generatedID = new ArrayList<Integer>();
    static Random random = new Random(2020);


    public static void main(String[] args) {
        long start_time = 0;
        try{
            startServer();
            start_time = System.currentTimeMillis();
            //------Here you can add any test you like------
            //Notice that Add-> Modify-> Delete in order
            //EndPoint should include /
            //The number for one group of testing should be the same
            //It totally depends on you to disable the verbose
            //------Here you can add any test you like------
            int delete_freq = 10;
            for(int i=0; i<10000; i++) {
            	sendAddRequests(projectEndPoint,1,true);
                sendModifyRequests(projectEndPoint+'/', 1, true);
                if(i%delete_freq==0) {
                	sendDeleteRequests(projectEndPoint+'/', 1, true);
                }
                System.out.println("progress: "+i+"/"+10000);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println("Finish the testing " + (System.currentTimeMillis()-start_time));
            stopServer();
        }


    }

    public static void startServer(){
        try{
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
        }
        catch(Exception e){
            System.out.println("Error in connection");
        }
    }

    public static void stopServer(){
        try{
            process.destroy();
        }
        catch(Exception e){
            System.out.println("Error in connection");
        }
    }

    public static String randomTitleGenerator(boolean verbose){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        if(verbose)
            System.out.println("New generated string: "+ generatedString);
        return generatedString;
    }

    /**
     *
     * @param range The range of random number
     * @param verbose If print out this random int
     * @return
     */
    public static int randomOtherIdGenerator(int range, boolean verbose ){
        int randomInteger = random.nextInt(range);
        if(verbose)
            System.out.println("pseudo random int in a range : " + randomInteger);
        return randomInteger;

    }

    /**
     * This funciton is for adding new todo, category, projects
     * TODOENDPOINT, CATEGORYENDPOINT
     * PROJECTENDPOINT
     * @param endPoint
     * @param num The number of sample
     */
    public static void sendAddRequests(String endPoint, int num, boolean verbose){
        try {
            for(int i =0; i <num ;i++){
                JSONObject json = new JSONObject();
                //TITLE
                String random_title_value = randomTitleGenerator(verbose);
                json.put(title, random_title_value);
                generatedString.add(random_title_value);
                StringEntity userEntity = new StringEntity(json.toString());
                
                JSONObject response_jason = send_post_request(endPoint, 201, userEntity);
                
                String result_id_string = (String) response_jason.get("id");
                int result_id = Integer.parseInt(result_id_string);
                if(verbose)
                    System.out.println("New created : "+ result_id);
                generatedID.add(result_id);
            }
            System.out.println("-------------");
        } catch (Exception ParseException) {
            System.out.println("Failure at AddTestS1");
        }
    }
    /*
        Notice this num should be consistent with the previous one
     */
    public static void sendModifyRequests(String endPoint, int num, boolean verbose){
        try {
            for(int i =0; i <num ;i++){
                JSONObject json = new JSONObject();
                //TITLE
                json.put(title, generatedString.get(0));
                //Description
                json.put(description, randomTitleGenerator(verbose));
                StringEntity userEntity = new StringEntity(json.toString());
                send_post_request(endPoint+generatedID.get(0), 200, userEntity);
            }
            System.out.println("-------------");
        } catch (Exception ParseException) {
            System.out.println("Failure at AddTestS1");
        }
    }

    /*
    Notice this num should be consistent with the previous one
    */
    public static void sendDeleteRequests(String endPoint, int num, boolean verbose){
        try {
            for(int i =0; i <num ;i++){
                if(verbose)
                    System.out.println("Delete ID: "+ generatedID.get(0));
                send_delete_request(endPoint+generatedID.get(0), 200);
                generatedID.remove(0);
                generatedString.remove(0);
            }
            System.out.println("-------------");
        } catch (Exception ParseException) {
            System.out.println("Failure at AddTestS1");
        }
    }

    /**
     * If there is a need you can uncommented those
     * @param toDoEndPoint
     * @param status
     * @throws IOException
     * @throws ParseException
     */
    static void send_request(String toDoEndPoint, int status) throws IOException, ParseException {
        HttpUriRequest request = new HttpGet(  baseUrl+ toDoEndPoint);
        httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(status, httpResponse.getStatusLine().getStatusCode());
//        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//        JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//        return response_jason;
    }

    static JSONObject send_post_request(String toDoEndPoint,int status, StringEntity userEntity) throws IOException, ParseException {
        HttpPost request = new HttpPost(  baseUrl+ toDoEndPoint);
        request.addHeader("content-type", "application/json");
        request.setEntity(userEntity);
        httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(status, httpResponse.getStatusLine().getStatusCode());
        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
        JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
        return response_jason;
    }

    static void send_delete_request(String toDoEndPoint,int status) throws IOException, ParseException {
        HttpUriRequest request = new HttpDelete(  baseUrl+ toDoEndPoint);
        httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(request);
        if(status!=0) {
            assertEquals(status, httpResponse.getStatusLine().getStatusCode());
        }

//        String responseBody = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//        JSONObject response_jason = (JSONObject) jsonParser.parse(responseBody);
//        return response_jason;
    }

}
