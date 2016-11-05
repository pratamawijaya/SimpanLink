package com.pratamawijaya.simpanlink.presentation.ui.home.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pratamawijaya.simpanlink.data.entity.Article;
import com.pratamawijaya.simpanlink.presentation.ui.home.HomeView;
import timber.log.Timber;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class HomePresenter {
  private HomeView view;
  private DatabaseReference databaseReference;

  public HomePresenter(HomeView view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  public void getLink() {
    databaseReference.child("articles").addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
          Article article = data.getValue(Article.class);
          Timber.d("onDataChange() :  %s", article.getTitle());
          Timber.d("onDataChange() :  %s", article.getImage());
          view.setArticle(article);
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
}
