package com.pratamawijaya.simpanlink.presentation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.presentation.ui.add.AddLinkActivity;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

  private static final int ADD_LINK = 1;

  @BindView(R.id.rvHome) RecyclerView rvHome;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);

    setupRecyclerview();
  }

  private void setupRecyclerview() {
    rvHome.setLayoutManager(new LinearLayoutManager(this));
  }

  @OnClick(R.id.fabHomeAdd) void addLinkClick() {
    startActivityForResult(new Intent(this, AddLinkActivity.class), ADD_LINK);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_home, menu);

    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    setupSearchView(searchView);

    return true;
  }

  private void setupSearchView(SearchView searchView) {
    searchView.setOnQueryTextListener(this);
  }

  @Override public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override public boolean onQueryTextChange(String newText) {
    return false;
  }
}
