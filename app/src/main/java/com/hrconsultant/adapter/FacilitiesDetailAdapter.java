package com.hrconsultant.adapter;

/**
 * Created by GurvinderS on 8/24/2017.
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.HashMap;

import com.hrconsultant.R;

public class FacilitiesDetailAdapter extends BaseAdapter {

    ArrayList<FaclitiesDetailItem> list = new ArrayList<>();
    LayoutInflater inflter;
    Context mContext;
    public FacilitiesDetailAdapter(Context context, ArrayList<FaclitiesDetailItem> objects) {
        inflter = (LayoutInflater.from(context));
        list = objects;
        mContext=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //view = inflter.inflate(R.layout.simple_spinner_dropdown_item, null);

       // TextView names = (TextView) view.findViewById(R.id.tvFacility);
        TextView names = new TextView(mContext);
        names.setText("\u2022 "+list.get(i).getFaclityName());
       names.setPadding(10,7,10,7);
       /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,10,5,10);
        names.setLayoutParams(params);*/

        names.setTextSize(20);
        names.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        RelativeLayout rtlContainer = new RelativeLayout(mContext);
        rtlContainer.addView(names);
        return rtlContainer;

       // return view;
    }

    public ArrayList<FaclitiesDetailItem> getList(){

        return list;
    }

}
