package com.bhavaniprasad.viewandselect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bhavaniprasad.viewandselect.R;
import com.bhavaniprasad.viewandselect.databinding.FeaturesRowLayoutBinding;
import com.bhavaniprasad.viewandselect.databinding.OtherFeaturesRowBinding;
import com.bhavaniprasad.viewandselect.databinding.StorageRowLayoutBinding;
import com.bhavaniprasad.viewandselect.model.OptionsList;
import com.bhavaniprasad.viewandselect.model.features;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeaturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<features> arrList;
    private Context cnt;
    private LayoutInflater layoutInflater;
    private SparseBooleanArray selectedItems;
    private int selectedIndex = -1;
    private OnItemClick itemClick;

    private static final int MobileList = 0;
    private static final int StorageList = 1;
    private static final int OtherFeaturesList = 2;
    Map<String,String> multiValueMap;


    public FeaturesAdapter(Context context, ArrayList<features> userViewModels) {
        this.arrList= userViewModels;
        this.cnt=context;
        selectedItems = new SparseBooleanArray();
        multiValueMap = new HashMap<String, String>();
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case MobileList:
               FeaturesRowLayoutBinding featuresRowLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.features_row_layout, parent, false);
                viewHolder = new MobileVH(featuresRowLayoutBinding);
                break;
            case StorageList:
                StorageRowLayoutBinding storageRowLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.storage_row_layout, parent, false);
                viewHolder = new StorageVH(storageRowLayoutBinding);
                break;
            case OtherFeaturesList:
                OtherFeaturesRowBinding otherFeaturesRowBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.other_features_row, parent, false);
                viewHolder = new OtherFeaturesVH(otherFeaturesRowBinding);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case MobileList:
                final MobileVH mobileVH = (MobileVH) holder;
                if(position!=0)
                    mobileVH.mobileBinding.MobilePhonesHeading.setVisibility(View.GONE);
                else
                    mobileVH.mobileBinding.MobilePhonesHeading.setVisibility(View.VISIBLE);
                mobileVH.mobileBinding.featureId.setText("Feature ID: "+arrList.get(0).getFeatures().get(MobileList).getFeature_id());
                mobileVH.mobileBinding.mobileName.setText("Mobile Name: "+arrList.get(0).getFeatures().get(MobileList).getOptions().get(position).getName());
                mobileVH.mobileBinding.optionId.setText("Option ID: "+arrList.get(0).getFeatures().get(MobileList).getOptions().get(position).getId());
                Picasso.with(cnt).load(arrList.get(0).getFeatures().get(MobileList).getOptions().get(position).getIcon()).into(mobileVH.mobileBinding.mobilephone);

                //Changes the activated state of this view.
                mobileVH.mobileBinding.mobilecardview.setActivated(selectedItems.get(position, false));
                mobileVH.mobileBinding.mobilecardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick == null) return;
                        itemClick.onItemClick(view, arrList.get(0).getFeatures().get(MobileList).getOptions().get(position),
                                arrList.get(0).getFeatures().get(MobileList).getFeature_id(), position);
                    }
                });
                mobileVH.mobileBinding.mobilecardview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (itemClick == null) {
                            return false;
                        } else {
                            itemClick.onLongPress(view, arrList.get(0).getFeatures().get(MobileList).getOptions().get(position),
                                    arrList.get(0).getFeatures().get(MobileList).getFeature_id(),position);
                            return true;
                        }
                    }
                });
                toggleIcon(mobileVH.mobileBinding, position);

                break;

            case StorageList:
                final int storageOptionSize=arrList.get(0).getFeatures().get(StorageList).getOptions().size();
                final StorageVH storageVH = (StorageVH) holder;
                if(position%storageOptionSize!=0)
                    storageVH.storageBinding.StorageFeaturesHeading.setVisibility(View.GONE);
                else
                    storageVH.storageBinding.StorageFeaturesHeading.setVisibility(View.VISIBLE);

                storageVH.storageBinding.storagefeatureName.setText("Feature Name: "+arrList.get(0).getFeatures().get(StorageList).getOptions().get(position%storageOptionSize).getName()+"");
                    storageVH.storageBinding.storagefeatureId.setText("Feature ID: "+arrList.get(0).getFeatures().get(StorageList).getFeature_id());
                    storageVH.storageBinding.storageoptionId.setText("Option ID: "+arrList.get(0).getFeatures().get(StorageList).getOptions().get(position%storageOptionSize).getId()+"");
                    Picasso.with(cnt).load(arrList.get(0).getFeatures().get(StorageList).getOptions().get(position%storageOptionSize).getIcon()).into(storageVH.storageBinding.storageicon);

                //Changes the activated state of this view.
                storageVH.storageBinding.storagecardview.setActivated(selectedItems.get(position, false));
                storageVH.storageBinding.storagecardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick == null) return;
                        itemClick.onItemClick(view, arrList.get(0).getFeatures().get(StorageList).getOptions().get(position%2),
                                arrList.get(0).getFeatures().get(StorageList).getFeature_id(), position);
                    }
                });
                storageVH.storageBinding.storagecardview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (itemClick == null) {
                            return false;
                        } else {
                            itemClick.onLongPress(view, arrList.get(0).getFeatures().get(StorageList).getOptions().get(position%storageOptionSize),
                                    arrList.get(0).getFeatures().get(StorageList).getFeature_id(), position);
                            return true;
                        }
                    }
                });
                toggleIcon(storageVH.storageBinding, position);
                break;

            case OtherFeaturesList:
                final int otherFeaturesOptionSize=arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().size();
                final OtherFeaturesVH otherFeaturesVH = (OtherFeaturesVH) holder;
                if(position%otherFeaturesOptionSize!=0)
                    otherFeaturesVH.otherFeaturesRowBinding.OtherFeaturesHeading.setVisibility(View.GONE);
                else
                    otherFeaturesVH.otherFeaturesRowBinding.OtherFeaturesHeading.setVisibility(View.VISIBLE);

                otherFeaturesVH.otherFeaturesRowBinding.otherfeatureId.setText("Feature ID: "+arrList.get(0).getFeatures().get(OtherFeaturesList).getFeature_id());
                otherFeaturesVH.otherFeaturesRowBinding.otherfeatureName.setText("Feature Name: "+arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().get(position%otherFeaturesOptionSize).getName());
                otherFeaturesVH.otherFeaturesRowBinding.otheroptionId.setText("Option ID: "+arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().get(position%otherFeaturesOptionSize).getId());
                Picasso.with(cnt).load(arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().get(position%otherFeaturesOptionSize).getIcon()).into(otherFeaturesVH.otherFeaturesRowBinding.Otherfeaturesicon);

                //Changes the activated state of this view.
                otherFeaturesVH.otherFeaturesRowBinding.otherfeaturesCardView.setActivated(selectedItems.get(position, false));
                otherFeaturesVH.otherFeaturesRowBinding.otherfeaturesCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick == null) return;
                        itemClick.onItemClick(view, arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().get(position%otherFeaturesOptionSize),
                                arrList.get(0).getFeatures().get(OtherFeaturesList).getFeature_id(), position);
                    }
                });
                otherFeaturesVH.otherFeaturesRowBinding.otherfeaturesCardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (itemClick == null) {
                            return false;
                        } else {
                            itemClick.onLongPress(view, arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().get(position%otherFeaturesOptionSize),
                                    arrList.get(0).getFeatures().get(OtherFeaturesList).getFeature_id(), position);
                            return true;
                        }
                    }
                });
                toggleIcon(otherFeaturesVH.otherFeaturesRowBinding, position);

                break;
        }


    }


    @Override
    public int getItemViewType(int position) {
        int Mobilelistsize=arrList.get(0).getFeatures().get(MobileList).getOptions().size();
        int storagelistsize=arrList.get(0).getFeatures().get(StorageList).getOptions().size();

        if (position < Mobilelistsize) {
            return MobileList;
        } else if(position >= Mobilelistsize && position < (Mobilelistsize+storagelistsize)) {
            return StorageList;
        }
        else
            return OtherFeaturesList;
    }

    @Override
    public int getItemCount() {
        int size=arrList.get(0).getFeatures().get(MobileList).getOptions().size()+arrList.get(0).getFeatures().get(StorageList).getOptions().size()
                +arrList.get(0).getFeatures().get(OtherFeaturesList).getOptions().size();
        return size;
    }

    public class MobileVH extends RecyclerView.ViewHolder{
        FeaturesRowLayoutBinding mobileBinding;
        public MobileVH(@NonNull FeaturesRowLayoutBinding itemView) {
            super(itemView.getRoot());
            mobileBinding=itemView;
        }
    }

    public class StorageVH extends RecyclerView.ViewHolder{
        StorageRowLayoutBinding storageBinding;
        public StorageVH(@NonNull StorageRowLayoutBinding itemView) {
            super(itemView.getRoot());
            storageBinding=itemView;
        }
    }

    public class OtherFeaturesVH extends RecyclerView.ViewHolder{
        OtherFeaturesRowBinding otherFeaturesRowBinding;
        public OtherFeaturesVH(@NonNull OtherFeaturesRowBinding itemView) {
            super(itemView.getRoot());
            otherFeaturesRowBinding=itemView;
        }
    }


    /*
   This method will trigger when we we long press the item and it will change the icon of the item to check icon.
 */
    private void toggleIcon(FeaturesRowLayoutBinding bi, int position) {
        if (selectedItems.get(position, false)) {
            if (selectedIndex == position){
                bi.mobilecardview.setCardBackgroundColor(Color.YELLOW);
                selectedIndex = -1;
            }
            else{
                bi.mobilecardview.setCardBackgroundColor(Color.WHITE);
            }
        } else {
            bi.mobilecardview.setCardBackgroundColor(Color.WHITE);
            if (selectedIndex == position) selectedIndex = -1;
        }
    }
    private void toggleIcon(StorageRowLayoutBinding bi, int position) {
        if (selectedItems.get(position, false)) {
            if (selectedIndex == position){
                bi.storagecardview.setCardBackgroundColor(Color.YELLOW);
                selectedIndex = -1;
            }
        } else {
            bi.storagecardview.setCardBackgroundColor(Color.WHITE);
            if (selectedIndex == position) selectedIndex = -1;
        }
    }
    private void toggleIcon(OtherFeaturesRowBinding bi, int position) {
        if (selectedItems.get(position, false)) {
            if (selectedIndex == position){
                bi.otherfeaturesCardView.setCardBackgroundColor(Color.YELLOW);
                selectedIndex = -1;
            }
        } else {
            bi.otherfeaturesCardView.setCardBackgroundColor(Color.WHITE);
            if (selectedIndex == position) selectedIndex = -1;
        }
    }

    public Map<String, String> getSelectedItems() {
        return multiValueMap;
    }



        /*
             this function will toggle the selection of items
     */

    public void toggleSelection(String feature_id, OptionsList optionlist, int position) {
        selectedIndex = position;

        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
            multiValueMap.remove(optionlist.getId());
        } else {
            multiValueMap.put(optionlist.getId(),feature_id);
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }


    public int selectedItemCount() {
        return selectedItems.size();
    }


        /*
       for clearing our selection
     */

    public void clearSelection() {
        selectedItems.clear();
        multiValueMap = new HashMap<String, String>();
        notifyDataSetChanged();
    }

    public interface OnItemClick {

        void onItemClick(View view, OptionsList features, String feature_id, int position);

        void onLongPress(View view, OptionsList features, String feature_id, int position);
    }
}
