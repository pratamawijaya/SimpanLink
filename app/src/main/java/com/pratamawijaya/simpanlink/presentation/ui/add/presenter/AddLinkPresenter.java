package com.pratamawijaya.simpanlink.presentation.ui.add.presenter;

import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pratamawijaya.simpanlink.data.entity.Article;
import com.pratamawijaya.simpanlink.presentation.ui.add.AddLinkView;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class AddLinkPresenter {
  private AddLinkView view;
  private DatabaseReference databaseReference;
  private FirebaseAuth firebaseAuth;
  private FirebaseUser firebaseUser;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  public AddLinkPresenter(AddLinkView view) {
    this.view = view;
    databaseReference = FirebaseDatabase.getInstance().getReference();
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
  }

  public void onDetachView() {
    if (compositeSubscription != null) {
      compositeSubscription.unsubscribe();
    }
  }

  public void onAttachView() {
    compositeSubscription = new CompositeSubscription();
  }

  public void saveLink(String link) {
    if (!TextUtils.isEmpty(link)) {
      view.showLoading();
      compositeSubscription.add(scrapingURL(link).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(new Subscriber<Article>() {
            @Override public void onCompleted() {
              view.hideLoading();
            }

            @Override public void onError(Throwable e) {
              Timber.e("onError() :  %s", e.getLocalizedMessage());
              view.hideLoading();
              view.showError(e.getLocalizedMessage());
            }

            @Override public void onNext(Article article) {
              view.hideLoading();
              Timber.d("onNext() :  %s", article.getTitle());
              Timber.d("onNext() :  %s", article.getImage());

              String key = databaseReference.child("articles").push().getKey();
              Map<String, Object> postValues = article.toMap();
              Map<String, Object> childUpdates = new HashMap<>();

              childUpdates.put("/articles/" + key, postValues);
              databaseReference.updateChildren(childUpdates);
              view.saveSuccess();
            }
          }));
    } else {
      view.showError("Link Kosong");
    }
  }

  public Observable<Article> scrapingURL(final String URL) {
    return Observable.create(new Observable.OnSubscribe<Article>() {
      @Override public void call(Subscriber<? super Article> subscriber) {
        try {
          Timber.d("call() : scrapping url %s", URL);

          Document doc = Jsoup.connect(URL).get();

          String redirectedUrl = null;
          Elements meta = doc.select("html head meta");
          if (meta.attr("http-equiv").contains("REFRESH")) {
            redirectedUrl = meta.attr("content").split("=")[1];
          } else {
            if (doc.toString().contains("window.location.href")) {
              meta = doc.select("script");
              for (Element script : meta) {
                String s = script.data();
                if (!s.isEmpty() && s.startsWith("window.location.href")) {
                  int start = s.indexOf("=");
                  int end = s.indexOf(";");
                  if (start > 0 && end > start) {
                    s = s.substring(start + 1, end);
                    s = s.replace("'", "").replace("\"", "");
                    redirectedUrl = s.trim();
                    break;
                  }
                }
              }
            }
          }

          String title = getMetaTag(doc, "og:title");
          String img = getMetaTag(doc, "og:image");
          Timber.d("call() : title %s", title);
          Timber.d("call() : img %s", img);

          if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(img)) {
            subscriber.onNext(new Article.Builder().uId(firebaseUser.getUid())
                .title(title)
                .image(img)
                .url(URL)
                .build());
            subscriber.onCompleted();
          } else {
            subscriber.onError(new Throwable("Empty title or img"));
          }
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  private String getMetaTag(Document document, String attr) {
    Elements elements = document.select("meta[name=" + attr + "]");
    for (Element element : elements) {
      final String s = element.attr("content");
      if (s != null) return s;
    }
    elements = document.select("meta[property=" + attr + "]");
    for (Element element : elements) {
      final String s = element.attr("content");
      if (s != null) return s;
    }
    return null;
  }
}
