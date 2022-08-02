package com.example.golfmax.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.example.golfmax.R;
import com.example.golfmax.backend.GolfMaxLocalDatabase;
import com.example.golfmax.backend.PlayerStatisticsRepository;
import com.example.golfmax.backend.SharedPreferencesManager;
import com.example.golfmax.contracts.PlayerStatisticsContract;
import com.example.golfmax.databinding.ActivityHomeBinding;
import com.example.golfmax.models.PlayerStatistics;
import com.example.golfmax.models.User;

public class HomeActivity extends Activity implements PlayerStatisticsContract.View {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        PlayerStatisticsRepository playerStatisticsRepository = new PlayerStatisticsRepository();
        User user = new User();
        PlayerStatistics stats = new PlayerStatistics();

        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_home);

        String username = SharedPreferencesManager.getInstance(HomeActivity.this).getUsername();
        long userId = getUserIdByUsername(username);
        user.setId(userId);
        stats.setUser(user);

        playerStatisticsRepository.updateUserStats(stats, user.getId());
        binding.setStats(playerStatisticsRepository.displayStatsSummary(userId));
    }

    @Override
    public void showData(PlayerStatistics playerStatistics) {}

    private long getUserIdByUsername(String username) {
        GolfMaxLocalDatabase db = new GolfMaxLocalDatabase(this);
        return db.getUserId(username);
    }

    public void goToPersonalScoresActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, PersonalScoresActivity.class);
        startActivity(intent);
    }

    public void goToNewRoundActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, NewRoundActivity.class);
        startActivity(intent);
    }

    public void goToCourseLeaderboardsActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, CourseListActivity.class);
        startActivity(intent);
    }

    public void goToUserProfileActivity(View view) {
        Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
        startActivity(intent);
    }
}