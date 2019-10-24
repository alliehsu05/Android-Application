package com.example.calorietrackerr;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {

    private static final String BASE_URL =
            "http://118.139.3.183:8080/CalTracker/webresources/";

    public static String findByUsername(String username){
        final String methodPath = "restwsct.users/findByUsername/" + username;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByUserCredential(String username, String passwordhash){
        final String methodPath = "restwsct.credential/findByUsernameANDPasswordhash/" + username +"/" + passwordhash;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createUsers(Users users){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restwsct.users/";
        try {
            Gson gson =new Gson();
           // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
          //  String jsonUser = gson.toJson(users);
            String stringCourseJson=gson.toJson(users);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createCredential(Credential credential){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restwsct.credential/";
        try {
            Gson gson =new Gson();
            // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            //  String jsonUser = gson.toJson(users);
            String stringCourseJson=gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String countUsers(){
        final String methodPath = "restwsct.users/count";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void addFood(Food food) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "restwsct.food/";
        try {
            Gson gson = new Gson();
            // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            //  String jsonUser = gson.toJson(users);
            String stringCourseJson = gson.toJson(food);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String countFood(){
        final String methodPath = "restwsct.food/count";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findAllFood(){
        final String methodPath = "restwsct.food";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByCategory(String category){
        final String methodPath = "restwsct.food/findByCategory/" + category;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getTotalconsumed(Integer userid, String date){
        final String methodPath = "restwsct.consumption/getTotalCalconsumed/" + userid +"/" + date;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getTotalburned(Integer userid){
        final String methodPath = "restwsct.users/getTotalCal/" + userid;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getPeriodresult(Integer userid, String date1, String date2){
        final String methodPath = "restwsct.report/getPeriodresult/"+userid+"/" + date1 +"/" + date2;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createReport(Report report){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="restwsct.report/";
        try {
            Gson gson =new Gson();
            // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            //  String jsonUser = gson.toJson(users);
            String stringCourseJson=gson.toJson(report);
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String countReport(){
        final String methodPath = "restwsct.report/count";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the conneciton method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type ot json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            conn.disconnect();
        }
        return textResult;
    }


}
