package com.bookt.bakingapp.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.Classes.Step;
import com.bookt.bakingapp.R;
import com.bookt.bakingapp.StepDetailActivity;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mArrayList;
    private int Id;

    public StepAdapter(Context mContext, ArrayList<Recipe> mArrayList, int Id) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.Id = Id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.recipe_step_card,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.step_name.setText((i+1)+"- "+mArrayList.get(Id).getSteps().get(i).getShortDescription());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StepDetailActivity.class);
                intent.putParcelableArrayListExtra("RECIPE",mArrayList);
                intent.putExtra("ID",Id);
                intent.putExtra("SELECTED",i);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mArrayList.get(Id).getSteps().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView step_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.steps_card);
            step_name = itemView.findViewById(R.id.step_name);

        }
    }
}
