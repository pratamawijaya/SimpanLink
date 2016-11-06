package com.pratamawijaya.simpanlink.presentation.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.data.entity.Article;

public class ArticleDetailActivity extends AppCompatActivity {

  @BindView(R.id.webViewArticle) WebView webViewArticle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_detail);
    ButterKnife.bind(this);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    final Article article = getIntent().getExtras().getParcelable("data");

    webViewArticle.setWebViewClient(new WebViewClient());
    if (article != null) {
      webViewArticle.loadUrl(article.getUrl());
      getSupportActionBar().setTitle(article.getTitle());
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return true;
  }
}
