package com.pratamawijaya.simpanlink.presentation.ui.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import butterknife.ButterKnife;
import com.pratamawijaya.simpanlink.R;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
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
