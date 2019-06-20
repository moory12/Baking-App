package com.bookt.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bookt.bakingapp.Adapters.MenuAdapter;
import com.bookt.bakingapp.Adapters.StepAdapter;
import com.bookt.bakingapp.Adapters.StepsAdapter;
import com.bookt.bakingapp.Classes.Recipe;
import com.bookt.bakingapp.Classes.Step;
import com.bookt.bakingapp.Fragments.Instruction_fragment;
import com.bookt.bakingapp.Fragments.Steps_fragment;
import com.bookt.bakingapp.Fragments.Video_fragment;
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

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,  ExoPlayer.EventListener  {

     SimpleExoPlayer exoPlayer;
     SimpleExoPlayerView playerView;
    RecyclerView recyclerView;
    StepAdapter stepAdapter;
    TextView menuHeader;

    ArrayList<Recipe> recipes;

    int Id;
    int selected;
    TextView textView;
    long position = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);









        if (savedInstanceState == null){
            recipes = intent.getParcelableArrayListExtra("RECIPE");
            Id = intent.getIntExtra("ID", 0);
            selected = getIntent().getIntExtra("SELECTED",0);

        }else{
            recipes = savedInstanceState.getParcelableArrayList("RECIPE");
            Id = savedInstanceState.getInt("ID", 0);
            selected = savedInstanceState.getInt("SELECTED");
            position = savedInstanceState.getLong("POSITION");
        }


        textView = findViewById(R.id.instruction_tv);
        textView.setText(recipes.get(Id).getSteps().get(selected).getDescription());
        playerView = findViewById(R.id.simple_exo);


        recyclerView = findViewById(R.id.menu_recycler_view);
        menuHeader = findViewById(R.id.menu_header);
        stepAdapter = new StepAdapter(this, recipes,Id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(stepAdapter);

        menuHeader.setText("STEPS");

        this.setTitle(recipes.get(Id).getSteps().get(selected).getShortDescription());


        initializePlayer(Uri.parse(recipes.get(Id).getSteps().get(selected).getVideoURL()));




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(exoPlayer!=null) {
            releasePlayer();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    public void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }





    public void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            if(position>0){
                exoPlayer.seekTo(position);
            }
            exoPlayer.setPlayWhenReady(true);

        }


    }









    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("RECIPE",recipes);
        outState.putInt("SELECTED",selected);
        outState.putInt("ID",Id);
        if(exoPlayer!=null) {
            outState.putLong("POSITION", exoPlayer.getCurrentPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }




}
