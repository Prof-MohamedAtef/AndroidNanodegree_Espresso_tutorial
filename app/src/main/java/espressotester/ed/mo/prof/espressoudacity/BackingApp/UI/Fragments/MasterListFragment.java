package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.AsyncTasks.GetGredientsAsyncTask;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.AsyncTasks.GetRecipeStepsAsyncTask;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Adapter.RecipesIngredientsMasterListAdapter;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Adapter.RecipesSelectionMasterListAdapter;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;


/**
 * Created by Prof-Mohamed Atef on 10/7/2018.
 */

public class MasterListFragment extends android.support.v4.app.Fragment implements GetGredientsAsyncTask.OnTaskCompleted,
        GetRecipeStepsAsyncTask.OnStepsTaskCompleted{

    OnRecipeStepSelectedListener mCallback;

    @BindView(R.id.recycler_view_horizontal_ingredients)
    RecyclerView recyclerView_Horizontal;

    @BindView(R.id.recycler_view_recipes_steps)
    RecyclerView recyclerView_Vertical;

    GridLayoutManager layoutManager;
    private String LOG_TAG= MasterListFragment.class.getSimpleName();
    private boolean TwoPane=false;
    public static final String KEY_StepsArray = "StepsArray";
    public static final String KEY_IngredientsArray = "IngredientsArray";
    public static ArrayList<OptionsEntity> StepsList;
    public static ArrayList<OptionsEntity> IngredientsList;
    private String MustImplementListener=" must Implement OnRecipeStepSelectedListener";

    boolean isInternetConnected;
    private View rootView;
    private Unbinder unbinder;

    private boolean checkConnection() {
        return isInternetConnected=isConnected();
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE); //NetworkApplication.getInstance().getApplicationContext()/
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork!=null){
            return isInternetConnected= activeNetwork.isConnected();
        }else
            return isInternetConnected=false;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            if (StepsList.isEmpty()){
                StepsList= (ArrayList<OptionsEntity>) savedInstanceState.getSerializable(KEY_StepsArray);
                PopulateSteps(StepsList);
            }
            if (IngredientsList.isEmpty()){
                IngredientsList=(ArrayList<OptionsEntity>) savedInstanceState.getSerializable(KEY_IngredientsArray);
                PopulateIngredients(IngredientsList);
            }
        }else {
            checkConnection();
            if (isConnected()){
                GetGredientsAsyncTask GetGredientsAsyncTask =new GetGredientsAsyncTask(this);
                GetGredientsAsyncTask.execute(getResources().getString(R.string.Recipes_API));
                GetRecipeStepsAsyncTask getRecipeStepsAsyncTask=new GetRecipeStepsAsyncTask(this);
                getRecipeStepsAsyncTask.execute(getResources().getString(R.string.Recipes_API));
            }else {
                Toast.makeText(getActivity(), getResources().getString(R.string.pending_connection), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_master_list,container,false);
        unbinder= ButterKnife.bind(this,rootView);
        if (rootView.findViewById(R.id.two_pane)!=null){
            TwoPane=true;
        }
        return rootView;
    }

    @Override
    public void onTaskCompleted(ArrayList<OptionsEntity> result) {
        if (result!=null){
            PopulateIngredients(result);
        }
    }

    private void PopulateIngredients(ArrayList<OptionsEntity> result) {
        StringBuilder stringBuilder=new StringBuilder();

        if (IngredientsList!=null&&IngredientsList.size()>0) {
            IngredientsList.clear();
            IngredientsList = result;
        }else {
            IngredientsList = result;
        }
        if (result!=null){
            for (int i=0; i<result.size();i++){
                stringBuilder.append( result.get(i).getIngredientsName().toString()+"\n");
            }
        }

        Util.IngredientsList= stringBuilder.toString();
        RecipesIngredientsMasterListAdapter mAdapter= new RecipesIngredientsMasterListAdapter(getActivity(), result,TwoPane);
        mAdapter.notifyDataSetChanged();
        layoutManager = (GridLayoutManager) recyclerView_Horizontal.getLayoutManager();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView_Horizontal.setLayoutManager(mLayoutManager);
        recyclerView_Horizontal.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Horizontal.setAdapter(mAdapter);
    }

    @Override
    public void OnStepsTaskCompleted(ArrayList<OptionsEntity> result) {
        if (result!=null){
            PopulateSteps(result);
        }
    }

    private void PopulateSteps(ArrayList<OptionsEntity> result) {
        RecipesSelectionMasterListAdapter mAdapter=new RecipesSelectionMasterListAdapter(getActivity(),result,TwoPane);
        if (StepsList!=null&&StepsList.size()>0) {
            StepsList.clear();
            StepsList = result;
        }else {
            StepsList=result;
        }

        mAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView_Vertical.setLayoutManager(mLayoutManager);
        recyclerView_Vertical.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Vertical.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (StepsList!=null) {
            outState.putSerializable(KEY_StepsArray , StepsList);
            outState.putSerializable(KEY_IngredientsArray, IngredientsList);
        }
    }

    public interface OnRecipeStepSelectedListener {
        void OnRecipeStepSelected(OptionsEntity optionsEntity, ArrayList<OptionsEntity> OptionsArray, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback= (OnRecipeStepSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+MustImplementListener);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mCallback= (OnRecipeStepSelectedListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+MustImplementListener);
        }
    }
}