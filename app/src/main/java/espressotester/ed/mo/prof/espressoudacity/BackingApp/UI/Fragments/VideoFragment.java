package espressotester.ed.mo.prof.espressoudacity.BackingApp.UI.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.OptionsEntity;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Data.SharedPref;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Services.IngredientsWidgetService;
import espressotester.ed.mo.prof.espressoudacity.BackingApp.Util;
import espressotester.ed.mo.prof.espressoudacity.R;


/**
 * Created by Prof-Mohamed Atef on 10/26/2018.
 */

public class VideoFragment extends android.support.v4.app.Fragment implements ExoPlayer.EventListener{

    @BindView(R.id.player_view) PlayerView playerView;
    @BindView(R.id.tv_step_desc) TextView Description_txt;
    TrackSelector trackSelector;
    MediaSource videoSource;
    DefaultDataSourceFactory dataSourceFactory;
    SimpleExoPlayer mSimpleExoPlayer;
    private String AppName="MoAtefBackingApp";
    private String TwoPaneExtras="twoPaneExtras";
    private View v;
    private Unbinder unbinder;

    public void initializePlayer(Uri VideoUri){
        try {
            if (mSimpleExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
                playerView.setPlayer(mSimpleExoPlayer);
                // Set the ExoPlayer.EventListener to this activity.
                mSimpleExoPlayer.addListener(this);
                // Prepare the MediaSource.
                String userAgent = com.google.android.exoplayer2.util.Util.getUserAgent(getActivity(), AppName);
                MediaSource mediaSource = new ExtractorMediaSource(VideoUri, new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                mSimpleExoPlayer.prepare(mediaSource);
                mSimpleExoPlayer.setPlayWhenReady(true);
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), getString(R.string.not_exist), Toast.LENGTH_LONG).show();
        }
    }



    String VideoString, Description;
    Uri VideoUri;
    public static OptionsEntity optionsEntity;
    public static String KEY_optionsEntity="Options";
    SharedPref sharedPref;

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
            DisplayData(optionsEntity);
        }else if (savedInstanceState==null){
            final Bundle bundle = getArguments();
            if (bundle != null) {
                optionsEntity = (OptionsEntity) bundle.getSerializable(TwoPaneExtras);
                DisplayData(optionsEntity);
            }
        }
        if (Util.IngredientsList!=null){
            sharedPref.createIngredientsIntoPrefs(Util.IngredientsList);
        }
        IngredientsWidgetService.startActionFillWidget(getActivity());
    }

    private void DisplayData(OptionsEntity optionsEntity) {
        VideoString = optionsEntity.getVideoUrl();
        VideoUri= Uri.parse(VideoString);
        initializePlayer(VideoUri);
        if (getActivity().findViewById(R.id.StepInstruction_container)==null){
            Description = optionsEntity.getDescription();
            Description_txt.setText(Description);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_video_player,container,false);
        unbinder= ButterKnife.bind(this,v);
        sharedPref=new SharedPref(getActivity());
        return  v;
    }

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
            dataSourceFactory = null;
            videoSource = null;
            trackSelector = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
//        initVideoClipPlayer(VideoUri);
        initializePlayer(VideoUri);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    @Override
    public void onSeekProcessed() {
    }
}