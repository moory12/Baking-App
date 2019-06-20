package com.bookt.bakingapp.JSONUtilities;




import com.bookt.bakingapp.Classes.Ingredient;
import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.Classes.Step;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtilities {





    /*
    In this method you getting a String from AsyncTask from the MainActivity
    and filling an Array of Recipe and return it to the MainActivity

     */
    public static ArrayList<Recipe> JSONtoRecipe(String Jrecipe){

        //ArrayList that we going to fill and return it
        ArrayList<Recipe> recipe = new ArrayList<>();

        try {

            //Converting the String to JSON Array
            JSONArray jsonArray = new JSONArray(Jrecipe);

            //First loop that iterate on JSONArray
            for(int i = 0 ;i<jsonArray.length();i++){

                Recipe temp = new Recipe();
                JSONObject jsonRecipe = jsonArray.getJSONObject(i);
                JSONArray  jsonIngredients = jsonRecipe.getJSONArray("ingredients");
                JSONArray  jsonSteps       = jsonRecipe.getJSONArray("steps");


                //getting ingredients from the JSON
                ArrayList<Ingredient> ingredients = new ArrayList<>();

                for(int j=0;j<jsonIngredients.length();j++){

                    Ingredient ingredient = new Ingredient();
                    ingredient.setQuantity(jsonIngredients.getJSONObject(j).optDouble("quantity"));
                    ingredient.setIngredient(jsonIngredients.getJSONObject(j).optString("ingredient"));
                    ingredient.setMeasure(jsonIngredients.getJSONObject(j).optString("measure"));


                    ingredients.add(ingredient);
                }

                //getting steps from the JSON
                ArrayList<Step> steps = new ArrayList<>();
                for(int j=0;j<jsonSteps.length();j++){

                    Step step = new Step();
                    step.setId(jsonSteps.getJSONObject(j).optInt("id"));
                    step.setShortDescription(jsonSteps.getJSONObject(j).optString("shortDescription"));
                    step.setDescription(jsonSteps.getJSONObject(j).optString("description"));
                    step.setVideoURL(jsonSteps.getJSONObject(j).optString("videoURL"));
                    step.setThumbnailURL(jsonSteps.getJSONObject(j).optString("thumbnailURL"));

                    steps.add(step);
                }


                //filling temp an Object of Recipe
                temp.setId(jsonRecipe.optInt("id"));
                temp.setName(jsonRecipe.optString("name"));
                temp.setIngredients(ingredients);
                temp.setSteps(steps);
                temp.setServings(jsonRecipe.optInt("servings"));
                temp.setImage(jsonRecipe.optString("image"));
                temp.setJson(Jrecipe);

                // adding temp to the array
                recipe.add(temp);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return recipe;
    }




}
