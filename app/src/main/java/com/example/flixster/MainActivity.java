package com.example.flixster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//import Fragments.FavoritesFragment;
import Fragments.FavoritesFragment;
import Fragments.NowPlayingFragment;
import Fragments.SearchFragment;
import Fragments.TrendingFragment;
import Fragments.UpcomingFragment;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";


    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_nowplaying:
                        fragment = new NowPlayingFragment();
                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_favorites:
                        fragment = new FavoritesFragment();
                        break;
                    case R.id.action_upcoming:
                        fragment = new UpcomingFragment();
                        break;
                    case R.id.action_trending:
                    default:
                        //TODO: Update fragment
                        fragment = new TrendingFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_nowplaying);

    }
}

