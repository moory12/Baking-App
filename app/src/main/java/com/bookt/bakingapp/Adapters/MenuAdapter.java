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


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mArrayList;

    public MenuAdapter(Context mContext, ArrayList<Recipe> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.menu_card,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.step.setText((i+1)+"- "+mArrayList.get(i).getName());
        myViewHolder.step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("ID",i);
                intent.putParcelableArrayListExtra(mContext.getString(R.string.recipe),mArrayList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("ID",i);
                intent.putParcelableArrayListExtra(mContext.getString(R.string.recipe),mArrayList);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView step;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            step = itemView.findViewById(R.id.step_name);
            cardView = itemView.findViewById(R.id.menu_card);
        }
    }

}
