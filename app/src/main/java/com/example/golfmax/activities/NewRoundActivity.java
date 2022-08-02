package com.example.golfmax.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.golfmax.backend.CourseRepository;
import com.example.golfmax.models.Course;
import com.example.golfmax.R;
import com.example.golfmax.databinding.ActivityNewRoundBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewRoundActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ActionBarDrawerToggle drawerToggle;
    private final String ACTION_BAR_TITLE = "New Round";
    private final String ACTION_BAR_COLOR = "#000f00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeWindowFeature();

        CourseRepository courseRepository = new CourseRepository();
        List<Course> courseNamesList = new ArrayList<>();

        ActivityNewRoundBinding binding = DataBindingUtil
                .setContentView(this,  R.layout.activity_new_round);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navigation_view_play_round);

        setDrawerToggleActions(drawerLayout);
        setActionBarTitle(ACTION_BAR_TITLE);
        setActionBarColor(ACTION_BAR_COLOR);
        setNavigationViewIntents(navView);

        courseRepository.setNewRoundBinding(binding);
        courseRepository.setCourseNamesList(courseNamesList);
        courseRepository.getCourseNamesForNewRound(NewRoundActivity.this);
    }

    private void removeWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setDrawerToggleActions(DrawerLayout drawerLayout) {
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.navOpen, R.string.navClose );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setActionBarTitle(String title) {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title);
    }

    private void setActionBarColor(String color) {
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(color));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return drawerToggle.onOptionsItemSelected(menuItem);
    }

    private void setNavigationViewIntents(@NonNull NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.navHome) {
                Intent goToHomeActivity = new Intent(NewRoundActivity.this,
                        HomeActivity.class);
                startActivity(goToHomeActivity);
                return true;
            }
            if (item.getItemId() == R.id.navMyScores) {
                Intent goToPersonalScoresActivity = new Intent(NewRoundActivity.this,
                        PersonalScoresActivity.class);
                startActivity(goToPersonalScoresActivity);
                return true;
            }
            if (item.getItemId() == R.id.navLeaderboard) {
                Intent goToLeaderboardActivity = new Intent(NewRoundActivity.this,
                        CourseListActivity.class);
                startActivity(goToLeaderboardActivity);
                return true;
            }
            if (item.getItemId() == R.id.navUserProfile) {
                Intent goToUserProfileActivity = new Intent(NewRoundActivity.this,
                        UserProfileActivity.class);
                startActivity(goToUserProfileActivity);
                return true;
            } else {
                return false;
            }
        });
    }

}