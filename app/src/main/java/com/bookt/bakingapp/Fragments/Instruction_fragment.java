package com.bookt.bakingapp.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.JSONUtilities.JsonUtilities;
import com.bookt.bakingapp.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Instruction_fragment extends Fragment implements Steps_fragment.OnItemClickListener {

    ArrayList<Recipe> recipes;
    int Id;
    TextView textView;

    public void setId(int id) {
        Id = id;
    }

    public Instruction_fragment() {
    }

    public void setTextView(String text) {
        this.textView.setText(text);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instruction_fragment,container,false);


        textView = view.findViewById(R.id.instruction_textview);


        if(savedInstanceState!=null) {
            recipes = savedInstanceState.getParcelableArrayList("RECPIE");
            Id = savedInstanceState.getInt("ID");
        }
        else{
            Intent intent = getActivity().getIntent();
            if(intent.getParcelableArrayListExtra(getString(R.string.recipe))==null){
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("BAKING_APP_PREFERENCES",MODE_PRIVATE);
                recipes = JsonUtilities.JSONtoRecipe(sharedPreferences.getString("JSON",""));
                Id = sharedPreferences.getInt("ID",0);
            }else{
                recipes = getActivity().getIntent().getParcelableArrayListExtra(getString(R.string.recipe));
                Id = getActivity().getIntent().getIntExtra("ID", 0);
            }
        }


         return view;
    }

    @Override
    public void onItemSelected(int position) {
        textView.setText(recipes.get(Id).getSteps().get(position).getDescription());
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("RECPIE",recipes);
        outState.putInt("ID",Id);
        super.onSaveInstanceState(outState);
    }
}


