package com.bookt.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import androidx.collection.ArraySet;

import com.bookt.bakingapp.Adapters.StepsAdapter;
import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.Classes.Step;
import com.bookt.bakingapp.JSONUtilities.JsonUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {




    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);

        SharedPreferences sharedPreferences = context.getSharedPreferences("BAKING_APP_PREFERENCES",MODE_PRIVATE);
        int size = sharedPreferences.getInt("size",0);
        int Id = sharedPreferences.getInt("ID",0);
        String recipe_name  = sharedPreferences.getString("recipe_name","");
        String ingerdient   = "";
        for(int i =0;i<size;i++){
           ingerdient =  ingerdient+" "+sharedPreferences.getString(""+i,"")+"\n\n";
        }




        views.setTextViewText(R.id.widget_recipe_name,recipe_name);
        views.setTextViewText(R.id.widget_tv,ingerdient);


        Intent intent = new Intent(context,RecipeDetailActivity.class);
        intent.putExtra("ID",Id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        views.setOnClickPendingIntent(R.id.widget_tv,pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

