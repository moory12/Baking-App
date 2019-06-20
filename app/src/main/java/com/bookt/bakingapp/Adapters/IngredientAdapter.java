package com.bookt.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookt.bakingapp.Classes.Ingredient;
import com.bookt.bakingapp.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mArrayList;


    public IngredientAdapter(Context mContext, ArrayList<Ingredient> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.ingredient_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.ingredient.setText(mArrayList.get(position).getIngredient());
        holder.measure.setText(mArrayList.get(position).getMeasure());
        holder.quantity.setText(Double.toString(mArrayList.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ingredient;
        TextView measure;
        TextView quantity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient_text);
            measure    = itemView.findViewById(R.id.measure_text);
            quantity = itemView.findViewById(R.id.quantity_text);
        }
    }
}
