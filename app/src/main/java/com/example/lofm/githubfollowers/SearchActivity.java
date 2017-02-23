package com.example.lofm.githubfollowers;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.lofm.githubfollowers.adapter.GridAdapter;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.presenter.GridPresenter;
import com.example.lofm.githubfollowers.ui.ImageListener;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ImageListener, GridPresenter.GridListener {

    private GridPresenter presenter;
    private RecyclerView recyclerView;
    private GridLayoutManager glm;
    private GridAdapter adapter;
//    private List<GHUser> ghUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Setup presenter
        presenter = new GridPresenter(this);
        //Setup recycler view and layout manager
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);
        //Setup adapter
        adapter = new GridAdapter(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //TODO: Search followers
        presenter.getFollowers(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Do nothing
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.registerListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unRegisterListener();
    }

    @Override
    public void onSuccess(List<GHUser> ghUsers) {
//        this.ghUsers = ghUsers;
        adapter.setGHUsers(ghUsers);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageClicked(View view, int position) {
        //TODO: Add transition animation
    }

}
