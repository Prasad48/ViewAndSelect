package com.bhavaniprasad.viewandselect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bhavaniprasad.viewandselect.adapter.FeaturesAdapter;
import com.bhavaniprasad.viewandselect.databinding.ActivityMainBinding;
import com.bhavaniprasad.viewandselect.model.OptionsList;
import com.bhavaniprasad.viewandselect.model.exclusionsList;
import com.bhavaniprasad.viewandselect.model.features;
import com.bhavaniprasad.viewandselect.remote.ApiMaker;
import com.bhavaniprasad.viewandselect.remote.ApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.view.ActionMode;

public class MainActivity extends AppCompatActivity {
//    private ArrayList<Features> arrlist;
    private ArrayList<features> arrlist;
    private FeaturesAdapter featuresAdapter;
    Call<features> featuresListcall;
    ActionMode actionMode;
    private RecyclerView recyclerView;
    ActivityMainBinding activityMainBinding;
    ActionCallback actionCallback;
    Boolean Invalid=false;;
    Map<String,String> maplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(activityMainBinding.toolbar);
//        getSupportActionBar().setTitle("Items");


        ApiService apiService = new ApiMaker().getService();
        recyclerView=activityMainBinding.featuresrecyclerview;

        featuresListcall= apiService.getList();
        featuresListcall.enqueue(new Callback<features>() {
            @Override
            public void onResponse(Call<features> call, Response<features> response) {
                if (response.isSuccessful()) {
                    arrlist=new ArrayList<>();
                    arrlist.add(response.body());
                    actionCallback = new ActionCallback();
                    featuresAdapter = new FeaturesAdapter(MainActivity.this, arrlist);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(featuresAdapter);
                    featuresAdapter.setItemClick(new FeaturesAdapter.OnItemClick() {
                        @Override
                        public void onItemClick(View view, OptionsList features, String feature_id, int position) {
                            if (featuresAdapter.selectedItemCount() > 0) {
                                toggleActionBar(feature_id, features, position);
                            } else {
                                Toast.makeText(MainActivity.this, "clicked " + features.getName(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onLongPress(View view, OptionsList features, String feature_id, int position) {
                            toggleActionBar(feature_id,features,position);
                        }
                    });
                } else {
                    Log.d("error message", "error");
                }
            }

            @Override
            public void onFailure(Call<features> call, Throwable t) {
                String error_message= t.getMessage();
                Log.d("Error loading data", error_message);
            }

        });



    }

        /*
       toggling action bar that will change the color and option
     */

    private void toggleActionBar(String feature_id, OptionsList features, int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionCallback);
        }
        toggleSelection(feature_id,features,position);
    }

    private void toggleSelection(String feature_id, OptionsList features, int position) {
        featuresAdapter.toggleSelection(feature_id,features,position);
        int count = featuresAdapter.selectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
        maplist = featuresAdapter.getSelectedItems();
       List<List<exclusionsList>> exclusionlist = arrlist.get(0).getExclusions();
        for(List<exclusionsList> list : exclusionlist){
            int resultcount=0;
            for(Map.Entry m : maplist.entrySet()){
                int i=0;
                while(i<list.size()){
                    if(m.getKey().equals(list.get(i).getOptions_id()) && m.getValue().equals(list.get(i).getFeature_id())){
                        resultcount++;
                    }
                    i++;
                }
                if(resultcount>=list.size()){
                    Invalid=true;
                    break;
                }
            }
        }
    }

    private void openDialog(Boolean invalid, final Map<String, String> maplist) {
        if(invalid==true){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this);
            alertDialog.setTitle("Combination");
            alertDialog.setMessage("Selected Combo is not Available");
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Select another combo", Toast.LENGTH_SHORT)
                                    .show();
                            Invalid=false;
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        }
        else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this);
            alertDialog.setTitle("Combination");
            alertDialog.setMessage("Selected Combination is available, Click Submit to Proceed");
            alertDialog.setPositiveButton("Submit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Intent resultsIntent = new Intent(MainActivity.this, SelectedCombination.class);
                            Results obj= Results.getInstance();
                            obj.resultsList=new HashMap<>();
                            obj.featuresList=new ArrayList<>();
                            obj.featuresList=arrlist;
                            obj.resultsList=maplist;
                            startActivity(resultsIntent);
                            dialog.cancel();
                        }
                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Toast.makeText(getApplicationContext(),
                                    "", Toast.LENGTH_SHORT)
                                    .show();
                            dialog.cancel();
                        }
                    });
            alertDialog.show();
        }
    }

    private class ActionCallback implements ActionMode.Callback {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            toggleStatusBarColor(MainActivity.this, R.color.blue_grey_700);
            mode.getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.applyItem:
                    openDialog(Invalid,maplist);
                    mode.finish();
                    return true;
            }
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            featuresAdapter.clearSelection();
            actionMode = null;
            toggleStatusBarColor(MainActivity.this, R.color.skyBlue);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void toggleStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.skyBlue));
    }


}