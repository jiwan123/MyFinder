package com.DesignQuads.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.DesignQuads.AssistanceFinder.Hospital;
import com.DesignQuads.AssistanceFinder.R;
import com.DesignQuads.modal.Fuel;
import com.DesignQuads.modal.HospitalModel;

import java.util.List;

/**
 * Created by Jiwan on 2/05/2017.
 */

public class HospitalCallAdapter extends BaseAdapter {
    Context context;
    List<HospitalModel> rowItems;

    public HospitalCallAdapter(Context context, List<HospitalModel> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
        ImageView callBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.hospital_row_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.subtitle);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.name);
            holder.callBtn = (ImageView) convertView.findViewById(R.id.item_call);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HospitalModel rowItem = (HospitalModel) getItem(position);

        Log.v("ttt",rowItem.getName());

        holder.txtDesc.setText(rowItem.getName());
        holder.txtTitle.setText(rowItem.getSubTitle());
        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + rowItem.getPhone()));
                if (ActivityCompat.checkSelfPermission(HospitalCallAdapter.this.context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                HospitalCallAdapter.this.context.startActivity(callIntent);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}