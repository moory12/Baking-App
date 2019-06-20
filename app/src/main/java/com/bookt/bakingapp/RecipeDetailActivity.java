package com.bookt.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.bookt.bakingapp.Fragments.Instruction_fragment;
import com.bookt.bakingapp.Fragments.Steps_fragment;
import com.bookt.bakingapp.Fragments.Video_fragment;
import com.bookt.bakingapp.JSONUtilities.JsonUtilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bookt.bakingapp.Adapters.MenuAdapter;
import com.bookt.bakingapp.Adapters.StepAdapter;
import com.bookt.bakingapp.Classes.Recipe;

import java.util.ArrayList;
import java.util.Set;


public class RecipeDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , Steps_fragment.OnItemClickListener {

    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    TextView menuHeader;
    Context context;
    Video_fragment video_fragment;
    Instruction_fragment instruction_fragment;
    boolean tablet = false;
    FragmentManager fragmentManager;
    RecyclerView recyclerView1;
    StepAdapter stepAdapter ;
    ArrayList<Recipe> recipes;
    int Id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            Intent intent = getIntent();
            context = this;

            if(intent.getParcelableArrayListExtra(getString(R.string.recipe))==null){
                SharedPreferences sharedPreferences = getSharedPreferences("BAKING_APP_PREFERENCES",MODE_PRIVATE);
                recipes = JsonUtilities.JSONtoRecipe(sharedPreferences.getString("JSON",""));
            }
            else {
                recipes = intent.getParcelableArrayListExtra(getString(R.string.recipe));
            }
            Id = intent.getIntExtra("ID", 0);


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);


            recyclerView = findViewById(R.id.menu_recycler_view);
            menuHeader = findViewById(R.id.menu_header);
            menuAdapter = new MenuAdapter(this, recipes);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(menuAdapter);


            Recipe recipe = recipes.get(Id);
            menuHeader.setText(this.getString(R.string.recipes_header));
            this.setTitle(recipe.getName());


            TextView recipe_ingredient = findViewById(R.id.recipe_ingredient);
            recipe_ingredient.setOnClickListener(v -> {
                Intent intent1 = new Intent(context, IngredientActivity.class);
                intent1.putParcelableArrayListExtra("INGREDIENT", recipe.getIngredients());
                context.startActivity(intent1);
            });

            if (findViewById(R.id.recipe_steps_recycler) != null) {
                recyclerView1 = findViewById(R.id.recipe_steps_recycler);
                stepAdapter = new StepAdapter(this, recipes,Id);
                recyclerView1.setLayoutManager(new LinearLayoutManager(this));
                recyclerView1.setAdapter(stepAdapter);


            } else {
                tablet = true;

                    fragmentManager = getSupportFragmentManager();


                    video_fragment = new Video_fragment();

                    fragmentManager.beginTransaction()
                            .add(R.id.simple_exo, video_fragment)
                            .commit();

                    instruction_fragment = new Instruction_fragment();

                    fragmentManager.beginTransaction()
                            .add(R.id.instruction_fragment, instruction_fragment)
                            .commit();



            }



        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("BAKING_APP_PREFERENCES",MODE_PRIVATE);
                    sharedPreferences.edit().putInt("size",recipe.getIngredients().size()).apply();
                    sharedPreferences.edit().putString("JSON",recipe.getJson()).apply();
                    sharedPreferences.edit().putString("recipe_name",recipe.getName()).apply();
                    sharedPreferences.edit().putInt("ID",Id).apply();
                    for(int i =0;i<recipe.getIngredients().size();i++){
                        String x = ""+(i+1)+"-"+recipe.getIngredients().get(i).getIngredient()+" "+recipe.getIngredients().get(i).getQuantity()+" "+recipe.getIngredients().get(i).getMeasure();
                        sharedPreferences.edit().putString(""+i,x).apply();
                    }
                    Toast.makeText(context, "added to widget", Toast.LENGTH_SHORT).show();
                }

            });




        }












    @Override
    protected void onPause() {
        super.onPause();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onItemSelected(int position) {

            video_fragment.onItemSelected(position);
            instruction_fragment.onItemSelected(position);

    }


}
