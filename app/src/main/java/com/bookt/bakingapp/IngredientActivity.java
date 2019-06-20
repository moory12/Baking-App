package com.bookt.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bookt.bakingapp.Adapters.IngredientAdapter;
import com.bookt.bakingapp.Classes.Ingredient;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra("INGREDIENT");

        RecyclerView recyclerView = findViewById(R.id.ingredient_recycler_view);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(this,ingredients);
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
