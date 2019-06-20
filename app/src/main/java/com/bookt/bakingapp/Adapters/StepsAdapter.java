package com.bookt.bakingapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bookt.bakingapp.Classes.Step;
import com.bookt.bakingapp.R;

import java.util.ArrayList;

public class StepsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Step> stepArrayList;


    public StepsAdapter(Context mContext, ArrayList<Step> stepArrayList) {
        this.mContext = mContext;
        this.stepArrayList = stepArrayList;
    }

    @Override
    public int getCount() {
        return stepArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){

            textView = new TextView(mContext);
            textView.setPadding(8, 8, 8, 8);
        }
        else{
            textView = (TextView) convertView;
        }
        textView.setTextSize(24);
        textView.setTextColor(Color.WHITE);
        textView.setText(stepArrayList.get(position).getShortDescription());
        return textView;
    }
}
