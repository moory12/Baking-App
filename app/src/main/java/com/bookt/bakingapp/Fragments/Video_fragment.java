package com.bookt.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.JSONUtilities.JsonUtilities;
import com.bookt.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Video_fragment extends Fragment implements ExoPlayer.EventListener,Steps_fragment.OnItemClickListener {

    public Video_fragment() {
    }


    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView simpleExoPlayerView;
    ArrayList<Recipe> recipes;
    int Id;
    static int POSITION;




    public void setId(int id) {
        Id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);



        Intent intent = getActivity().getIntent();
        if(intent.getParcelableArrayListExtra(getString(R.string.recipe))==null){
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("BAKING_APP_PREFERENCES",MODE_PRIVATE);
            recipes = JsonUtilities.JSONtoRecipe(sharedPreferences.getString("JSON",""));
            Id = sharedPreferences.getInt("ID",0);
        }else{
            recipes = getActivity().getIntent().getParcelableArrayListExtra(getString(R.string.recipe));
            Id = getActivity().getIntent().getIntExtra("ID", 0);
        }

        simpleExoPlayerView = view.findViewById(R.id.simpleExoPlayerView);



        return view;
    }


    public void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            releasePlayer();
        }
    }


   public void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            exoPlayer.setPlayWhenReady(true);

        }


    }


    @Override
    public void onItemSelected(int position) {
        if (exoPlayer != null) {
            releasePlayer();
        }
        initializePlayer(Uri.parse(recipes.get(Id).getSteps().get(position).getVideoURL()));
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("POSITION",POSITION);
        outState.putParcelableArrayList("RECPIE",recipes);
        outState.putInt("ID",Id);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null) {
            recipes = savedInstanceState.getParcelableArrayList("RECPIE");
            Id = savedInstanceState.getInt("ID");
            initializePlayer(Uri.parse(recipes.get(Id).getSteps().get(savedInstanceState.getInt("POSITION")).getVideoURL()));
        }
    }
}
