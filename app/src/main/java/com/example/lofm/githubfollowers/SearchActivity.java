package com.example.lofm.githubfollowers;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lofm.githubfollowers.adapter.GridAdapter;
import com.example.lofm.githubfollowers.model.GHUser;
import com.example.lofm.githubfollowers.presenter.SearchPresenter;
import com.example.lofm.githubfollowers.ui.ImageListener;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, ImageListener, SearchPresenter.SearchListener {

    private SearchPresenter presenter;
    private RecyclerView recyclerView;
    private GridLayoutManager glm;
    private GridAdapter adapter;
    private ProgressBar progressBar;
    private List<GHUser> ghUsers;
    private TextView defaultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Set default text view
        defaultTextView = (TextView) findViewById(R.id.defaultTextView);
        //Setup progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Setup presenter
        presenter = new SearchPresenter(this);
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
        presenter.getFollowers(query);
        defaultTextView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
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
        if (ghUsers.size() == 0) {
            onError(getString(R.string.no_items_found));
        } else {
            this.ghUsers = ghUsers;
            defaultTextView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setGHUsers(this.ghUsers);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String errorMessage) {
        defaultTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        defaultTextView.setText(errorMessage);
    }

    @Override
    public void onImageClicked(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailActivity.GH_USER_KEY, ghUsers.get(position));
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "expandImage").toBundle());
        } else {
            startActivity(intent);
        }
    }

}
