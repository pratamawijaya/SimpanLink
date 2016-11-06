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
import com.pratamawijaya.simpanlink.data.entity.Article;
import com.pratamawijaya.simpanlink.presentation.ui.add.AddLinkActivity;
import com.pratamawijaya.simpanlink.presentation.ui.home.adapter.HomeAdapter;
import com.pratamawijaya.simpanlink.presentation.ui.home.presenter.HomePresenter;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
    implements SearchView.OnQueryTextListener, HomeView {

  private static final int ADD_LINK = 1;

  @BindView(R.id.rvHome) RecyclerView recyclerView;

  private HomePresenter presenter;
  private HomeAdapter homeAdapter;
  private List<Article> articles;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);

    setupRecyclerView();

    presenter = new HomePresenter(this);
    presenter.getLink();
  }

  private void setupRecyclerView() {
    articles = new ArrayList<>();
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    homeAdapter = new HomeAdapter(this, articles);
    recyclerView.setAdapter(homeAdapter);
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

  @Override public void setArticle(List<Article> articles) {
    if (this.articles.size() > 0) articles.clear();
    this.articles.addAll(articles);
    homeAdapter.notifyDataSetChanged();
  }
}
