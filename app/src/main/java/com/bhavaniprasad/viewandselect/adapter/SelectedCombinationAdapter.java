package com.bhavaniprasad.viewandselect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavaniprasad.viewandselect.R;
import com.bhavaniprasad.viewandselect.databinding.ResultsRowBinding;
import com.bhavaniprasad.viewandselect.model.features;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectedCombinationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Map<String,String> mapList;
    private ArrayList<features> featuresList;
    private Context cnt;
    public SelectedCombinationAdapter(Context selectedCombination, Map<String, String> resultslist, ArrayList<features> featuresList) {
        this.mapList= resultslist;
        this.cnt=selectedCombination;
        this.featuresList=featuresList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        ResultsRowBinding resultsRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.results_row, parent, false);
        viewHolder = new ResultsVH(resultsRowBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List featureidlist=new ArrayList<>();
        List optionidlist=new ArrayList<>();
        for (Map.Entry<String, String> pair : mapList.entrySet()) {
            featureidlist.add(pair.getValue());
            optionidlist.add(pair.getKey());
        }
        Integer resultantfeatureidposition= Integer.parseInt((String) featureidlist.get(position));
        int resultantoptionposition= Integer.parseInt((String) optionidlist.get(position));

        final SelectedCombinationAdapter.ResultsVH resultsVH = (SelectedCombinationAdapter.ResultsVH) holder;
        if(position!=0) resultsVH.resultsRowBinding.ResultsHeading.setVisibility(View.GONE);
        if(resultantfeatureidposition==2){
            int listsize=featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().size();
            resultsVH.resultsRowBinding.resultsId.setText("Feature ID: "+resultantfeatureidposition+"");
            resultsVH.resultsRowBinding.resultsName.setText("Feature Name: "+featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition%listsize).getName());
            resultsVH.resultsRowBinding.resultsoptionId.setText("Option ID: "+featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition%listsize).getId());
            Picasso.with(cnt).load(featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition%listsize).getIcon()).into(resultsVH.resultsRowBinding.resultssicon);
        }
        else if(resultantfeatureidposition==3){
            resultsVH.resultsRowBinding.resultsId.setText("Feature ID: "+resultantfeatureidposition+"");
            resultsVH.resultsRowBinding.resultsName.setText("Feature Name: "+featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition%10).getName());
            resultsVH.resultsRowBinding.resultsoptionId.setText("Option ID: "+featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition%10).getId());
            Picasso.with(cnt).load(featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition%10).getIcon()).into(resultsVH.resultsRowBinding.resultssicon);
        }
        else{
            resultsVH.resultsRowBinding.resultsId.setText("Feature ID: "+resultantfeatureidposition+"");
            resultsVH.resultsRowBinding.resultsName.setText("Feature Name: "+featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition-1).getName());
            resultsVH.resultsRowBinding.resultsoptionId.setText("Option ID: "+featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition-1).getId());
            Picasso.with(cnt).load(featuresList.get(0).getFeatures().get(resultantfeatureidposition-1).getOptions().get(resultantoptionposition-1).getIcon()).into(resultsVH.resultsRowBinding.resultssicon);
        }
  }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public class ResultsVH extends RecyclerView.ViewHolder{
        ResultsRowBinding resultsRowBinding;
        public ResultsVH(@NonNull ResultsRowBinding itemView) {
            super(itemView.getRoot());
            resultsRowBinding=itemView;
        }

    }
}
