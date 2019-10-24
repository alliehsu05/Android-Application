package com.example.calorietrackerr;


import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodSearchAPI {
    private static final String API_KEY = "WmriwhHrrXDLfsUPnnldcEWRoJ18jU0KbTiUKGEe";




    public static List<String> searchFood(String foodNum) {
        foodNum = foodNum.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL(" https://api.nal.usda.gov/ndb/V2/reports?ndbno="+ foodNum + "&format=JSON" + "&api_key=" + API_KEY);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }

        List<String> list = new ArrayList<>();
        try{

            String calorie = "";
            String fat = "";
            String serving = "";
            String servingUnit = "";
            JSONObject jsonObject2 = new JSONObject(textResult);
            JSONArray foodDetail = jsonObject2.getJSONArray("foods").getJSONObject(0).getJSONObject("food").getJSONArray("nutrients");

            for(int index = 0; index < foodDetail.length(); index++){

                if(foodDetail.getJSONObject(index).getString("name").equals("Energy")){

                    JSONObject j =foodDetail.getJSONObject(index).getJSONArray("measures").getJSONObject(0);
                    calorie = j.getString("value");

                    list.add(calorie);
                }
                if(foodDetail.getJSONObject(index).getString("name").equals("Total lipid (fat)")){
                    JSONObject j =foodDetail.getJSONObject(index).getJSONArray("measures").getJSONObject(0);
                    fat =  j.getString("value");
                    list.add(fat);
                }}
            serving = foodDetail.getJSONObject(0).getJSONArray("measures").getJSONObject(0).getString("label");
            list.add(serving);
            servingUnit = foodDetail.getJSONObject(0).getJSONArray("measures").getJSONObject(0).getString("qty");
            list.add(servingUnit);


        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public static String searchFoodName(String foodName) {
        foodName = foodName.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String result = "";

        try {
            url = new URL(" https://api.nal.usda.gov/ndb/search/?q="+ foodName + "&format=JSON" + "&api_key=" + API_KEY );
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                result += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally{
            connection.disconnect();
        }
        String no = "";
        try{
            JSONObject jsonObject = new JSONObject(result);
            no = jsonObject.getJSONObject("list").getJSONArray("item").getJSONObject(0).getString("ndbno");
        }catch (Exception e){
            e.printStackTrace();
            no = "NO INFO FOUND";
        }
        return no;
    }
}


