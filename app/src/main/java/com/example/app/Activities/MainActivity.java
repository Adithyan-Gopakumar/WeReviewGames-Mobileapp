package com.example.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.Adapters.GameListAdapter;
import com.example.app.Adapters.SliderAdapters;
import com.example.app.Domain.SliderItems;
import com.example.app.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterLatestGames, AdapterPopularGames;
    private RecyclerView recyclerViewLatestGames, recyclerviewPopularGames;
    private ViewPager2 viewPager2;
    private List<Integer> latestGamesImages, popularGamesImages;
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setupImages();
        setupAdapters();
        banners();
    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewPagerSlider);
        recyclerViewLatestGames = findViewById(R.id.view1);
        recyclerViewLatestGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerviewPopularGames = findViewById(R.id.view2);
        recyclerviewPopularGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ImageView imageProfile = findViewById(R.id.imageProfile);
        ImageView imageAdd = findViewById(R.id.imageAdd);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Profile activity
                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the AddGame activity
                startActivity(new Intent(MainActivity.this, AddGame.class));
            }
        });
    }

    private void setupImages() {
        latestGamesImages = new ArrayList<>();
        latestGamesImages.add(R.drawable.game0);
        latestGamesImages.add(R.drawable.game1);
        latestGamesImages.add(R.drawable.game2);
        latestGamesImages.add(R.drawable.game3);

        popularGamesImages = new ArrayList<>();
        popularGamesImages.add(R.drawable.game4);
        popularGamesImages.add(R.drawable.game5);
        popularGamesImages.add(R.drawable.game6);
        popularGamesImages.add(R.drawable.game7);
    }

    private void setupAdapters() {
        adapterLatestGames = new GameListAdapter(latestGamesImages);
        recyclerViewLatestGames.setAdapter(adapterLatestGames);

        AdapterPopularGames = new GameListAdapter(popularGamesImages);
        recyclerviewPopularGames.setAdapter(AdapterPopularGames);
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide0));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide2));
        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
    }
}
