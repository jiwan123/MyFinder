package com.DesignQuads.AssistanceFinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.DesignQuads.adapters.HospitalCallAdapter;
import com.DesignQuads.modal.HospitalModel;

import java.util.ArrayList;
import java.util.List;

public class HospitalCall extends AppCompatActivity {

    private ListView phonelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_call);

        phonelist = (ListView) findViewById(R.id.phonelist);

        final List<HospitalModel> list = new ArrayList<>();

        list.add(new HospitalModel("Hospital 1","08 080808 123","trauma center"));
        list.add(new HospitalModel("Hospital 2","08 080808 124","trauma center"));
        list.add(new HospitalModel("Hospital 3","08 080808 125","trauma center"));

        HospitalCallAdapter adapter = new HospitalCallAdapter(HospitalCall.this, list);
        phonelist.setAdapter(adapter);


    }



}
