package com.services.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.services.R;
import com.services.bean.StateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ripal on 5/3/2017.
 */

public class StateAdapter extends ArrayAdapter<StateBean> {

        ArrayList<StateBean> stateList=new ArrayList<>();
        Context context;
    public StateAdapter(Context context, int resource, ArrayList<StateBean> stateList) {
        super(context, resource, stateList);
        this.context=context;
        this.stateList=stateList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater infalInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = infalInflater.inflate(R.layout.layout_entry, parent, false);


        return view;
    }
}
