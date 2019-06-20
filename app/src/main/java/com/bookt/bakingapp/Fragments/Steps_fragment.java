package com.bookt.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bookt.bakingapp.Adapters.StepsAdapter;
import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.JSONUtilities.JsonUtilities;
import com.bookt.bakingapp.R;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class Steps_fragment extends Fragment {

    public Steps_fragment() {
    }


    OnItemClickListener itemClickListener;
    int Id;
    static int POSITION;
    ArrayList<Recipe> recipes;





    public interface OnItemClickListener{
        void onItemSelected(int position);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            itemClickListener = (OnItemClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                        " must implement onItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment,container,false);


        ListView listView = view.findViewById(R.id.steps_list_view);

        StepsAdapter stepsAdapter ;
        if(savedInstanceState!=null) {
           Recipe recipe = savedInstanceState.getParcelable("RECPIE");
           Id = savedInstanceState.getInt("ID");
            stepsAdapter = new StepsAdapter(getContext(), recipe.getSteps());
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

            stepsAdapter = new StepsAdapter(getContext(), recipes.get(Id).getSteps());

        }

        listView.setAdapter(stepsAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                POSITION = position;
                itemClickListener.onItemSelected(position);
            }
        });



        return view;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("POSITION",POSITION);
        outState.putParcelable("RECPIE",recipes.get(Id));
        outState.putInt("ID",Id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
