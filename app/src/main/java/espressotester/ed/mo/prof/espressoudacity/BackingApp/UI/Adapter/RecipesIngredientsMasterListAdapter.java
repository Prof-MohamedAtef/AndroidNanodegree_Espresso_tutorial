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
import espressotester.ed.mo.prof.espressoudacity.R;


/**
 * Created by Prof-Mohamed Atef on 10/21/2018.
 */

public class RecipesIngredientsMasterListAdapter extends RecyclerView.Adapter<RecipesIngredientsMasterListAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<OptionsEntity> feedItemList;
    boolean TwoPane;

    public RecipesIngredientsMasterListAdapter(Context mContext, ArrayList<OptionsEntity> feedItemList, boolean twoPane) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        TwoPane = twoPane;

    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_layout, null);
        RecyclerView.ViewHolder viewHolder = new ViewHOlder(view);
        return (ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, int position) {
        final OptionsEntity feedItem = feedItemList.get(position);
        if (feedItem!=null){
            if (feedItem.getIngredientsName()!=null){
                holder.ingredient.setText(feedItem.getIngredientsName());
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class ViewHOlder extends RecyclerView.ViewHolder {
        @BindView(  R.id.ingredients_txt) protected TextView ingredient;

        public ViewHOlder(View converview) {
            super(converview);
            ButterKnife.bind(this, converview);
        }
    }
}