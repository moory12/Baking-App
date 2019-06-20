package com.bookt.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.R;
import com.bookt.bakingapp.RecipeDetailActivity;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Recipe> mRecipes;

    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipes) {
        this.mContext = mContext;
        this.mRecipes = mRecipes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.recipe_card,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.recipe_name.setText(mRecipes.get(i).getName());



        myViewHolder.recipe_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("ID",i);
                intent.putParcelableArrayListExtra(mContext.getString(R.string.recipe),mRecipes);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("ID",i);
                intent.putParcelableArrayListExtra(mContext.getString(R.string.recipe),mRecipes);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView recipe_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.recipe_card);
            recipe_name = itemView.findViewById(R.id.recipe_name);
        }
    }

}
