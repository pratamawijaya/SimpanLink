package com.pratamawijaya.simpanlink.presentation.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pratamawijaya.simpanlink.BR;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.data.entity.Article;
import com.pratamawijaya.simpanlink.presentation.model.ArticleViewModel;
import com.pratamawijaya.simpanlink.presentation.ui.add.AddLinkActivity;
import com.pratamawijaya.simpanlink.presentation.ui.home.presenter.HomePresenter;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity
    implements SearchView.OnQueryTextListener, HomeView {

  private static final int ADD_LINK = 1;

  private HomePresenter presenter;
  private List<ArticleViewModel> articleViewModels;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    articleViewModels = new ArrayList<>();
    binding.setVariable(BR.model, new HomeActivityModel(articleViewModels));
    ButterKnife.bind(this);

    presenter = new HomePresenter(this);
    presenter.getLink();
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

  @Override public void setArticle(Article article) {
    Timber.d("setArticle() :add title  %s", article.getTitle());
    articleViewModels.add(
        new ArticleViewModel.Builder(article.getTitle(), article.getImage()).build());
  }
}
