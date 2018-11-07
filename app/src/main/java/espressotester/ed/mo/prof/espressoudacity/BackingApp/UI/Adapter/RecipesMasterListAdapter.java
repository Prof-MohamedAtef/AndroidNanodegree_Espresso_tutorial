package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.UIIdentifierFragment;
import espressotester.ed.mo.prof.espressoudacity.R;

/**
 * Created by Prof-Mohamed Atef on 10/21/2018.
 */

public class RecipesMasterListAdapter extends RecyclerView.Adapter<RecipesMasterListAdapter.ViewHOlder> implements Serializable {

    Context mContext;
    ArrayList<OptionsEntity> feedItemList;
    boolean TwoPane;

    public RecipesMasterListAdapter(Context mContext, ArrayList<OptionsEntity> feedItemList, boolean twoPane) {
        this.mContext = mContext;
        this.feedItemList = feedItemList;
        TwoPane = twoPane;

    }

    @NonNull
    @Override
    public RecipesMasterListAdapter.ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_layout, null);
        RecyclerView.ViewHolder viewHolder = new RecipesMasterListAdapter.ViewHOlder(view);
        return (RecipesMasterListAdapter.ViewHOlder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesMasterListAdapter.ViewHOlder holder, final int position) {
        final OptionsEntity feedItem = feedItemList.get(position);
        if (feedItem!=null){
            if (feedItem.getRecipeName()!=null&&feedItem.getRecipeImage()!=null){
                holder.recipeName.setText(feedItem.getRecipeName());
                if (position==0) {
                    Picasso.with(mContext).load(R.drawable.nutella_pie)
                            .error(R.drawable.pizza)
                            .into(holder.recipeImage);
                }else if (position==1) {
                    Picasso.with(mContext).load(R.drawable.brownies)
                            .error(R.drawable.pizza)
                            .into(holder.recipeImage);
                }else if (position==2) {
                    Picasso.with(mContext).load(R.drawable.yellow_cake)
                            .error(R.drawable.pizza)
                            .into(holder.recipeImage);
                }else if (position==3) {
                    Picasso.with(mContext).load(R.drawable.cheese_cake)
                            .error(R.drawable.pizza)
                            .into(holder.recipeImage);
                }
                holder.recipeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((UIIdentifierFragment.RecipeDataListener) mContext).onRecipeSelected(feedItemList.get(position),TwoPane);
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

        @BindView(R.id.recipe_name) protected TextView recipeName;
        @BindView(R.id.recipe_image) protected ImageView recipeImage;

        public ViewHOlder(View converview) {
            super(converview);
            ButterKnife.bind(this, converview);
        }
    }
}