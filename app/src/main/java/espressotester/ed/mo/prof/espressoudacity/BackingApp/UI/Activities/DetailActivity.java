package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.MasterListFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.PartitionFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.UIIdentifierFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.VideoFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;


/*
Created by Prof-Mohamed Atef on 21/10/2018
 */
public class DetailActivity extends AppCompatActivity implements MasterListFragment.OnRecipeStepSelectedListener,
        PartitionFragment.OnNextOrPrevSelected{

    @BindView(R.id.fragment_container) LinearLayout linearLayout;

    private boolean mTowPane=false;
    private String FragmentTag="frags";
    private String TwoPaneExtras="twoPaneExtras";
    private String RecipesSerializable="Recipes";
    private String PositionsExtras="position";
    private String VidFragTag="Video";
    private String PartitionFragTag="content";
    private String MovieInfo="movieInfo";
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder= ButterKnife.bind(this);
        MasterListFragment masterListFragment= new MasterListFragment();
        getSupportFragmentManager().beginTransaction().replace(linearLayout.getId(),masterListFragment, null).commitAllowingStateLoss();
        VideoFragment videoFrag=new VideoFragment();
        PartitionFragment stepInstructionFrag=new PartitionFragment();
        if (findViewById(R.id.StepDetails)!=null){
            mTowPane=true;
            Util.TwoPane_testing=true;
            if (savedInstanceState!=null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Video_container, videoFrag, FragmentTag)
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.StepInstruction_container, stepInstructionFrag, FragmentTag)
                        .commit();
            }
        }else {
            mTowPane=false;
            Util.TwoPane_testing=false;
        }
    }

    @Override
    public void OnRecipeStepSelected(OptionsEntity optionsEntity, ArrayList<OptionsEntity> RecipeSteps , int position) {
        if (mTowPane) {
            Bundle twoPaneExtras = new Bundle();
            twoPaneExtras.putSerializable(TwoPaneExtras, optionsEntity);
            Util.StepsList_testing=RecipeSteps;
            twoPaneExtras.putSerializable(RecipesSerializable,RecipeSteps);
            twoPaneExtras.putInt(PositionsExtras,position);
            VideoFragment videoFrag=new VideoFragment();
            PartitionFragment stepInstructionFrag=new PartitionFragment();
                videoFrag.setArguments(twoPaneExtras);
                stepInstructionFrag.setArguments(twoPaneExtras);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Video_container, videoFrag, VidFragTag)
                        .commit();
            getSupportFragmentManager().beginTransaction()
                        .replace(R.id.StepInstruction_container, stepInstructionFrag, PartitionFragTag)
                        .commit();
        }else if (!mTowPane){
            Intent intent = new Intent(this, ExoPlayerActivity.class)
                    .putExtra(MovieInfo, optionsEntity)
                    .putExtra(RecipesSerializable,RecipeSteps)
                    .putExtra(PositionsExtras, position);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
    }

    @Override
    public void OnNextPrevSelected(OptionsEntity optionsEntity) {
        VideoFragment videoFragment= new VideoFragment();
        Bundle onePaneExtras = new Bundle();
        onePaneExtras.putSerializable(TwoPaneExtras,optionsEntity);
        videoFragment.setArguments(onePaneExtras);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Video_container, videoFragment, VidFragTag).commit();
    }
}