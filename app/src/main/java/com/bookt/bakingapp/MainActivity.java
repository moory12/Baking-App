package com.bookt.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.bookt.bakingapp.Adapters.RecipeAdapter;
import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.JSONUtilities.JsonUtilities;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    RecyclerView recipeRecycler;
    RecipeAdapter recipeAdapter;
    ArrayList<Recipe> recipes;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecycler       = findViewById(R.id.recipe_recycler_view);
        swipeRefreshLayout   = findViewById(R.id.swipe_refresh);
        recipes              = new ArrayList<>();


        //play the refreshing sign to notify the user that the app working
        swipeRefreshLayout.setRefreshing(true);

        //start reading from the JSON url
        asyncCall();






        recipeAdapter = new RecipeAdapter(this,recipes);
        recipeRecycler.setAdapter(recipeAdapter);
        if(findViewById(R.id.tablet)!=null){
            recipeRecycler.setLayoutManager(new GridLayoutManager(this,3));
        }
        else {
            recipeRecycler.setLayoutManager(new LinearLayoutManager(this));
        }



        //on Refresh call asyncCall again to read from JSON
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                asyncCall();
            }
        });



    }


    public void asyncCall(){
        try {
            if(isOnline()) {
                Uri uri = Uri.parse(this.getString(R.string.APIKEY));
               new  JsonAsync().execute(new URL(uri.toString()));
            }
            else{
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }




    //This method were copied from  https://stackoverflow.com/a/4009133
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }





    // Getting JSON Recipe From URL
    public  class  JsonAsync extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String RecipeURL = null;

            try {
                RecipeURL = getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return RecipeURL;
        }


        @Override
        protected void onPostExecute(String results) {
            recipes.clear();
            recipes.addAll(JsonUtilities.JSONtoRecipe(results));
            recipeAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }



        public  String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }
    }



}
