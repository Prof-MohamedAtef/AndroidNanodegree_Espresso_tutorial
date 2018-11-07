package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.UIIdentifierFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments.VideoFragment;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;


public class ExoPlayerActivity extends AppCompatActivity {

    @BindView(R.id.Video_player_container) FrameLayout FrameLayout;
    @BindView(R.id.Next_BTN) Button Next;
    @BindView(R.id.Pre_BTN) Button Prev;
    ArrayList<OptionsEntity> RecipeSteps;
    Bundle bundle;
    int position;
    private int RecipeStepsLength;
    private int positionDiff;
    private boolean BtnNextStatus=true;
    private boolean BtnPrevStatus=true;
    private String TwoPaneExtras="twoPaneExtras";
    private String MovieInfo="movieInfo";
    private String VidFragTag="Vid";
    private String RecipesBundle="Recipes";
    private java.lang.String PositionBundle="position";
    private Unbinder unbinder;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }else if (!hasFocus){
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        unbinder= ButterKnife.bind(this);
        if (savedInstanceState == null) {
            VideoFragment videoFragment= new VideoFragment();
            Bundle onePaneExtras = new Bundle();
            onePaneExtras.putSerializable(TwoPaneExtras, getIntent().getSerializableExtra(MovieInfo));
            videoFragment.setArguments(onePaneExtras);
            getSupportFragmentManager().beginTransaction()
                    .replace(FrameLayout.getId(), videoFragment, VidFragTag).commit();
        }

        bundle= getIntent().getExtras();
        RecipeSteps= (ArrayList<OptionsEntity>) bundle.getSerializable(RecipesBundle);
        position=bundle.getInt(PositionBundle);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecipeSteps.size()>0) {
                    RecipeStepsLength=RecipeSteps.size();
                    positionDiff= RecipeStepsLength- Util.position;
                    if (positionDiff>1){
                        if (BtnNextStatus==false){
                            Next.setVisibility(View.VISIBLE);
                        }else if(BtnPrevStatus==false){
                            BtnPrevStatus=true;
                            Prev.setVisibility(View.VISIBLE);
                        }
                        position=Util.position+1;
                        Util.position=position;
                        if (position>=0&&position<RecipeStepsLength){
                            OptionsEntity optionsEntity = RecipeSteps.get(position);
                            SendRecipeStepPosition(optionsEntity);
                        }
                    }else if (positionDiff==1){
                        //hide next button
                        BtnNextStatus=false;
                        Next.setVisibility(View.GONE);
                    }
                }
            }
        });
        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecipeSteps.size()>0) {
                    RecipeStepsLength=RecipeSteps.size();
                    positionDiff= RecipeStepsLength-Util.position;
                    position=Util.position-1;
                    Util.position=position;
                    if (Util.position==0){
                        //hide next button
                        BtnNextStatus=true;
                        BtnPrevStatus=false;
                        Next.setVisibility(View.VISIBLE);
                        Prev.setVisibility(View.GONE);
                    }
                    if (position>=0){
                        if (position>=0&&positionDiff>=1){
                            Next.setVisibility(View.VISIBLE);
                            BtnNextStatus=true;
                        }
                        OptionsEntity optionsEntity = RecipeSteps.get(position);
                        SendRecipeStepPosition(optionsEntity);
                    }
                }
            }
        });
    }

    private void SendRecipeStepPosition(OptionsEntity optionsEntity) {
        VideoFragment videoFragment= new VideoFragment();
        Bundle onePaneExtras = new Bundle();
        onePaneExtras.putSerializable(TwoPaneExtras,optionsEntity);
        videoFragment.setArguments(onePaneExtras);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Video_player_container, videoFragment, VidFragTag).commit();
    }
}