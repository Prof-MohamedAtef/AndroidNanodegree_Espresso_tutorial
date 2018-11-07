package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.MasterListFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;

/**
 * Created by Prof-Mohamed Atef on 10/21/2018.
 */

public class RecipesSelectionMasterListAdapter extends RecyclerView.Adapter<RecipesSelectionMasterListAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<OptionsEntity> feedItemList;
    boolean TwoPane;

    public RecipesSelectionMasterListAdapter(Context mContext, ArrayList<OptionsEntity> feedItemList, boolean twoPane) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        TwoPane = twoPane;
    }


    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_layout, null);
        RecyclerView.ViewHolder viewHolder = new RecipesSelectionMasterListAdapter.ViewHOlder(view);
        return (RecipesSelectionMasterListAdapter.ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, final int position) {
        final OptionsEntity feedItem = feedItemList.get(position);
        if (feedItem != null) {
            if (feedItem.getShortDescription() != null) {
                holder.steps.setText(feedItem.getShortDescription());
                holder.steps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MasterListFragment.OnRecipeStepSelectedListener) mContext).OnRecipeStepSelected(feedItemList.get(position), feedItemList,position);
                        Util.position=position;
                        Util.StepsList=feedItemList;
                        Util.optionsEntity=feedItemList.get(position);
                    }
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ViewHOlder extends RecyclerView.ViewHolder {
        @BindView(R.id.steps_title_txt) protected TextView steps;

        public ViewHOlder(View converview) {
            super(converview);
            ButterKnife.bind(this, converview);
        }
    }
}