package com.bhavaniprasad.viewandselect;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.bhavaniprasad.viewandselect.adapter.SelectedCombinationAdapter;
import com.bhavaniprasad.viewandselect.databinding.ActivitySelectedCombinationBinding;
import com.bhavaniprasad.viewandselect.model.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class SelectedCombination extends AppCompatActivity {
    private Map<String,String> maplist;
    private ArrayList<features> featuresList;
    private ArrayList checkMobile;

    private SelectedCombinationAdapter selectedCombinationAdapter;
    Call<features> featuresListcall;
    private RecyclerView recyclerView;
    ActivitySelectedCombinationBinding activitySelectedCombinationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySelectedCombinationBinding = DataBindingUtil.setContentView(this, R.layout.activity_selected_combination);

        recyclerView=activitySelectedCombinationBinding.resultsrecyclerview;
        maplist=new HashMap<String, String>();
        featuresList=new ArrayList<>();
        checkMobile=new ArrayList();
        Results ob= Results.getInstance();
        featuresList=ob.featuresList;
        maplist=ob.resultsList;
        checkMobile.addAll(maplist.values());

        if(checkMobile.contains("1")){
            selectedCombinationAdapter = new SelectedCombinationAdapter(SelectedCombination.this, maplist,featuresList);
            recyclerView.setLayoutManager(new LinearLayoutManager(SelectedCombination.this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(selectedCombinationAdapter);
        }
        else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    SelectedCombination.this);
            alertDialog.setTitle("SELECT");
            alertDialog.setMessage("Please Select atleast one Mobile");
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Select atleast one Mobile", Toast.LENGTH_SHORT)
                                    .show();
                            dialog.cancel();
                            finish();
                        }
                    });
            alertDialog.show();
        }

    }
}