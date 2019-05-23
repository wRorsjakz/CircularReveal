package com.example.circularreveal;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.circularreveal.Transitions.AnimationUtils;
import com.example.circularreveal.Transitions.RevealAnimationSetting;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.rvItemClickedListener {

    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<ItemModel> itemModels = new ArrayList<>();
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupData();

        fragmentContainer = findViewById(R.id.main_fragment_container);
        recyclerView = findViewById(R.id.main_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), itemModels);
        adapter.setRVItemClickedListener(this);

        recyclerView.setAdapter(adapter);


    }

    private void setupData() {
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
        itemModels.add(new ItemModel("Folder"));
    }

    @Override
    public void itemClicked(int position, float[] coordinates) {

        RevealAnimationSetting animationSetting = new RevealAnimationSetting((int) coordinates[0], (int) coordinates[1],
                recyclerView.getWidth(), recyclerView.getHeight());

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DetailFragment detailFragment = new DetailFragment(animationSetting);

        fragmentTransaction.add(R.id.main_fragment_container, detailFragment, "detail_fragment").commit();
    }

    @Override
    public void onBackPressed() {

        final DetailFragment detailFragment = (DetailFragment) fragmentManager.findFragmentByTag("detail_fragment");

        if (detailFragment != null) {
            ((AnimationUtils.Dismissible) detailFragment).dismiss(new AnimationUtils.Dismissible.OnDismissedListener() {
                @Override
                public void onDismissed() {
                    getSupportFragmentManager().beginTransaction().remove(detailFragment).commitAllowingStateLoss();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
