package com.pratamawijaya.simpanlink.presentation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.data.entity.Article;
import com.pratamawijaya.simpanlink.presentation.model.event.HomeArcticleClickEvent;
import com.pratamawijaya.simpanlink.presentation.ui.add.AddLinkActivity;
import com.pratamawijaya.simpanlink.presentation.ui.detail.ArticleDetailActivity;
import com.pratamawijaya.simpanlink.presentation.ui.home.adapter.HomeAdapter;
import com.pratamawijaya.simpanlink.presentation.ui.home.presenter.HomePresenter;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity
    implements SearchView.OnQueryTextListener, HomeView {

  private static final int ADD_LINK = 1;

  @BindView(R.id.rvHome) RecyclerView recyclerView;
  @BindView(R.id.fabHomeAdd) FloatingActionButton fabHomeAdd;

  private HomePresenter presenter;
  private HomeAdapter homeAdapter;
  private List<Article> articles;
  private FirebaseRemoteConfig firebaseRemoteConfig;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);

    firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    final boolean isFabShow = firebaseRemoteConfig.getBoolean("FAB_HOME_ADD_SHOW");
    if (isFabShow) {
      fabHomeAdd.setVisibility(View.VISIBLE);
    } else {
      fabHomeAdd.setVisibility(View.GONE);
    }

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

  @Override protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override protected void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onArticleClick(HomeArcticleClickEvent homeArcticleClickEvent) {
    Bundle bundle = new Bundle();
    bundle.putParcelable("data", homeArcticleClickEvent.getArticle());
    startActivity(new Intent(this, ArticleDetailActivity.class).putExtras(bundle));
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
    Timber.d("setArticle() :  %s", articles.size());
    if (this.articles.size() > 0) this.articles.clear();
    this.articles.addAll(articles);
    homeAdapter.notifyDataSetChanged();
  }
}
