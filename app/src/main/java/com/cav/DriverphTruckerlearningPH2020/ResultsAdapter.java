package com.cav.DriverphTruckerlearningPH2020;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

class ResultsAdapter implements ListAdapter {
    ArrayList<Results> arrayList;
    Context context;
    public ResultsAdapter(Context context, ArrayList<Results> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Results resultsData = arrayList.get(position);
        String incorrect = "Your answer was incorrect.\nThe correct answer is: ";
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.result_view, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView Question = convertView.findViewById(R.id.askedQ);
            TextView Answer = convertView.findViewById(R.id.askedAns);
            ImageView Image = convertView.findViewById(R.id.askedImg);
            Question.setText(resultsData.askedQuestion);
            Answer.setText(resultsData.askedAnswer);
            if (Answer.getText().toString().contains(incorrect)){
                Answer.setTextColor(Color.RED);
            }else{
                Answer.setTextColor(Color.GREEN);
            }
            if (resultsData.askedImage.isEmpty() || resultsData.askedImage.equals("null")){
                Image.setImageResource(R.drawable.white);
                Image.setVisibility(View.GONE);
            }else {
                Picasso.with(context)
                        .load(resultsData.askedImage)
                        .into(Image);
            }

        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}