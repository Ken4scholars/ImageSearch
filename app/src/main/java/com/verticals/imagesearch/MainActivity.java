package com.verticals.imagesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PhotoView {

    private EditText searchQuery;
    private RecyclerView recyclerView;
    private PhotoService service;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchQuery = (EditText) findViewById(R.id.search_text);
        recyclerView = (RecyclerView) findViewById(R.id.photo_recycler);

        adapter = new PhotoAdapter(new ArrayList<String>());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerView.addItemDecoration(new SpaceItemDecoration(3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 3, false));
        recyclerView.setAdapter(adapter);

        service = new PhotoDownloader(this);
    }

    public void search(View view){
        try {
            service.search(searchQuery.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillPhoto(List<String> photos) {
        adapter.fillRecycler(photos);
    }

    @Override
    public void addPhotos(List<String> photos) {
        adapter.setPhotos(photos);
    }
}
