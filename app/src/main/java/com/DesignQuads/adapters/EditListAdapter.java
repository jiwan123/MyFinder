package com.DesignQuads.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DesignQuads.AssistanceFinder.Address;
import com.DesignQuads.AssistanceFinder.EditList;
import com.DesignQuads.AssistanceFinder.HospitalAddress;
import com.DesignQuads.AssistanceFinder.R;
import com.DesignQuads.AssistanceFinder.RoadSideAddress;
import com.DesignQuads.AssistanceFinder.ServiceAddress;
import com.DesignQuads.AssistanceFinder.UntagLocation;
import com.DesignQuads.modal.AbstractAddress;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Jiwan on 2/05/2017.
 */

public class EditListAdapter extends BaseAdapter {
    Context context;
    List<AbstractAddress> rowItems;

    public EditListAdapter(Context context, List<AbstractAddress> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        String id;
        TextView txtTitle;
        TextView txtDesc;
        ImageView deleteBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.untag_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.name);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.type);
            holder.deleteBtn = (ImageView) convertView.findViewById(R.id.item_delete);
            holder.deleteBtn.setVisibility(View.INVISIBLE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AbstractAddress rowItem = (AbstractAddress) getItem(position);
        holder.id = rowItem.FuelID;
        holder.txtDesc.setText(rowItem.LocationPhone);
        holder.txtTitle.setText(rowItem.PlaceName);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rowItem.type =="FuelPumps"){
                    Intent intent = new Intent(context, Address.class);
                    intent.putExtra("EXTRA_FUEL_ID", rowItem.FuelID);
                    context.startActivity(intent);
                }
                else if(rowItem.type =="Service_Stations"){
                    Intent intent = new Intent(context, ServiceAddress.class);
                    intent.putExtra("EXTRA_FUEL_ID", rowItem.FuelID);
                    context.startActivity(intent);
                }
                else if(rowItem.type =="RoadSide_Assistance"){
                    Intent intent = new Intent(context, RoadSideAddress.class);
                    intent.putExtra("EXTRA_FUEL_ID", rowItem.FuelID);
                    context.startActivity(intent);
                }
                else if(rowItem.type =="Hospitals"){
                    Intent intent = new Intent(context, HospitalAddress.class);
                    intent.putExtra("EXTRA_FUEL_ID", rowItem.FuelID);
                    context.startActivity(intent);
                }
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