package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;

/**
 * Created by Prof-Mohamed Atef on 10/7/2018.
 */

public class PartitionFragment extends Fragment{

    @BindView(R.id.Next_BTN) Button Next_BTN;
    @BindView(R.id.Pre_BTN) Button Pre_BTN;
    private ArrayList<OptionsEntity> RecipeSteps;
    private int position;
    private int RecipeStepsLength;
    private boolean BtnNextStatus;
    private boolean BtnPrevStatus;
    private int positionDiff;
    private String TwoPaneExtras="twoPaneExtras";
    private String RecipesStr="Recipes";
    private String PositionStr="position";
    private Unbinder unbinder;

    public PartitionFragment() {
    }

    public static OptionsEntity optionsEntity;
    public static String KEY_optionsEntity="Options";
    String Description;
    @BindView(R.id.description_txt) TextView description_txt;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_optionsEntity,optionsEntity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null){
            optionsEntity=(OptionsEntity) savedInstanceState.getSerializable(KEY_optionsEntity);
            Description = optionsEntity.getDescription();
            description_txt.setText(Description);
        }else if (savedInstanceState==null){
            final Bundle bundle = getArguments();
            if (bundle != null) {
                optionsEntity = (OptionsEntity) bundle.getSerializable(TwoPaneExtras);
                Description = optionsEntity.getDescription();
                description_txt.setText(Description);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_description,container,false);
        unbinder= ButterKnife.bind(this,rootView);
        final Bundle bundle = getArguments();
        if (bundle!=null){
            RecipeSteps= (ArrayList<OptionsEntity>) bundle.getSerializable(RecipesStr);
            position=bundle.getInt(PositionStr);
        }
        Pre_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecipeSteps.size()>0) {
                    RecipeStepsLength=RecipeSteps.size();
                    positionDiff= RecipeStepsLength- Util.position;
                    position=Util.position-1;
                    Util.position=position;
                    if (Util.position==0){
                        //hide next button
                        BtnNextStatus=true;
                        BtnPrevStatus=false;
                        Next_BTN.setVisibility(View.VISIBLE);
                        Pre_BTN.setVisibility(View.GONE);
                    }
                    if (position>=0){
                        if (position>=0&&positionDiff>=1){
                            Next_BTN.setVisibility(View.VISIBLE);
                            BtnNextStatus=true;
                        }
                        OptionsEntity optionsEntity = RecipeSteps.get(position);
                        SendRecipeStepPosition(optionsEntity);
                    }
                }
            }
        });
        Next_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecipeSteps.size()>0) {
                    RecipeStepsLength=RecipeSteps.size();
                    positionDiff= RecipeStepsLength- Util.position;
                    if (positionDiff>1){
                        if (BtnNextStatus==false){
                            Next_BTN.setVisibility(View.VISIBLE);
                        }else if(BtnPrevStatus==false){
                            BtnPrevStatus=true;
                            Pre_BTN.setVisibility(View.VISIBLE);
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
                        Next_BTN.setVisibility(View.GONE);
                    }
                }
            }
        });
        return rootView;
    }

    private void SendRecipeStepPosition(OptionsEntity optionsEntity) {
        Description = optionsEntity.getDescription();
        description_txt.setText(Description);
        ((OnNextOrPrevSelected) getActivity()).OnNextPrevSelected(optionsEntity);
    }

    public interface OnNextOrPrevSelected{
        void OnNextPrevSelected(OptionsEntity optionsEntity);
    }
}