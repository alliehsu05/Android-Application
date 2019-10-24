package com.example.calorietrackerr;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class MydailydietFragment extends Fragment {
    View vMydailydiet;
    private EditText etAddfooditem;
    private Button bSearchfooditem;
    private TextView tvIteminfo;
    private Button bAddfooditem;
    private ImageView ivFood;
    private TextView mTest;
    Integer foodid;
    String foodidStr = "";
    String fooditem = "";
    String mCategory = "";
    List<String> FoodList = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vMydailydiet = inflater.inflate(R.layout.fragment_mydailydiet, container, false);
        etAddfooditem = (EditText) vMydailydiet.findViewById(R.id.addfooditem);
        bSearchfooditem = (Button) vMydailydiet.findViewById(R.id.searchfooditemBtn);
        tvIteminfo = (TextView) vMydailydiet.findViewById(R.id.iteminfo);
        bAddfooditem = (Button) vMydailydiet.findViewById(R.id.addfooditemBtn);
        ivFood = (ImageView) vMydailydiet.findViewById(R.id.foodimg);
        mTest = (TextView) vMydailydiet.findViewById(R.id.test);


        final Spinner spinner = (Spinner) vMydailydiet.findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, getCategory());
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = spinner.getSelectedItem().toString();
                FindFoodAsyncTask findFoodAsyncTask = new FindFoodAsyncTask();
                findFoodAsyncTask.execute();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Spinner spinner2 = (Spinner) vMydailydiet.findViewById(R.id.fooditem_spinner);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item, FoodList);
        spinner2.setAdapter(adapter2);

        CountFoodAsyncTask countFoodAsyncTask = new CountFoodAsyncTask();
        countFoodAsyncTask.execute();

        bSearchfooditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = etAddfooditem.getText().toString();
                SearchAsyncTask getSearchresult = new SearchAsyncTask();
                getSearchresult.execute(foodName);
                LoadImage searchImage = new LoadImage();
                searchImage.execute(foodName);
            }
        });

        bAddfooditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodInfo = tvIteminfo.getText().toString();
                String[] parts = foodInfo.split("  ");

                AddFoodAsyncTask addFoodAsyncTask = new AddFoodAsyncTask();
                addFoodAsyncTask.execute(foodidStr,etAddfooditem.getText().toString(),"Other", parts[1], parts[6], parts[5], parts[3]);

            }
        });

        return vMydailydiet;

    }

    private List<String> getCategory(){
        List<String> getCategory = new ArrayList<String>();
        getCategory.add("drink");
        getCategory.add("meal");
        getCategory.add("side");
        getCategory.add("bread");
        getCategory.add("dessert");
        getCategory.add("vegetable");
        getCategory.add("cheese");
        getCategory.add("other");
        return getCategory;
    }

    private class SearchAsyncTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            String no = FoodSearchAPI.searchFoodName(params[0]);
            List<String> foodInfo = FoodSearchAPI.searchFood(no);
            return foodInfo;
        }
        protected void onPostExecute(List<String> list) {
            String foodInfo = "Calorie:  "+list.get(0) + "  Fat:  " +list.get(1) + "  Serving:  " + list.get(3) +"  "+ list.get(2);
            tvIteminfo.setText(foodInfo);
        }
    }
    private Drawable loadImageFromNetwork(String imageLink) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(new URL(imageLink).openStream(), "imageFood.jpg");
        } catch (IOException e) {
            e.getMessage();
        }
        return drawable;
    }

    private class LoadImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return GoogleSearchAPI.searchImage(params[0], new String[]{"sum", "searchType"}, new
                    String[]{"1", "image"});
        }
        @Override
        protected void onPostExecute(String result) {
            final String imageLink = GoogleSearchAPI.getImage(result);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Drawable drawable = loadImageFromNetwork(imageLink);
                    ivFood.post(new Runnable(){
                        @Override
                        public void run(){
                            ivFood.setImageDrawable(drawable);
                        }
                    });
                }
            }).start();
        }
    }

    private class FindFoodAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.findByCategory(mCategory); }
        @Override
        protected void onPostExecute (String category){
            if(category.equals("[]")==false){
                FoodList.clear();
                String[] parts = category.split("\\{");
                for(int i=1;i<parts.length;i++) {
                    String[] a = parts[i].split("\\:");
                    String[] b = a[5].split("\\,");
                    fooditem = b[0].substring(1, b[0].length() - 1);
                    FoodList.add(fooditem);
                }
            }
            else{
                FoodList.clear();
            }
        }

    }
    private class AddFoodAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            Food food=new Food(Integer.parseInt(params[0]),params[1],params[2],Double.parseDouble(params[3]), params[4],Double.parseDouble(params[5]),Double.parseDouble(params[6]));
            RestClient.addFood(food);
            return "Food was added";
        }
        @Override
        protected void onPostExecute(String response) {
            mTest.setText(response);
        }
    }

    private class CountFoodAsyncTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground (Void...params){
            return RestClient.countFood();
        }
        @Override
        protected void onPostExecute (String num){
            foodid = Integer.parseInt(num);
            foodid = foodid+1;
            foodidStr = foodid.toString();
        }
    }

}


//WmriwhHrrXDLfsUPnnldcEWRoJ18jU0KbTiUKGEe
//https://developer.nrel.gov/api/alt-fuel-stations/v1/nearest.json?api_key=WmriwhHrrXDLfsUPnnldcEWRoJ18jU0KbTiUKGEe&location=Denver+CO