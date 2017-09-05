package com.DesignQuads.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DesignQuads.AssistanceFinder.ProfileActivity;
import com.DesignQuads.AssistanceFinder.R;
import com.DesignQuads.AssistanceFinder.UntagLocation;
import com.DesignQuads.modal.AbstractAddress;
import com.DesignQuads.modal.Fuel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Jiwan on 2/05/2017.
 */

public class UnTagAdapter extends BaseAdapter {
    Context context;
    List<AbstractAddress> rowItems;

    public UnTagAdapter(Context context, List<AbstractAddress> items) {
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AbstractAddress rowItem = (AbstractAddress) getItem(position);
        holder.id = rowItem.FuelID;
        holder.txtDesc.setText(rowItem.LocationPhone);
        holder.txtTitle.setText(rowItem.PlaceName);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context,rowItem.FuelID,Toast.LENGTH_LONG).show();

                FirebaseDatabase.getInstance().getReference().child("FuelPumps").child(rowItem.FuelID).removeValue();

                Intent intent = new Intent(context, UntagLocation.class);
                context.startActivity(intent);

                notifyDataSetChanged();
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