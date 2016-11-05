package com.pratamawijaya.simpanlink.presentation.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class ArticleViewModel extends BaseObservable {
  private final String title;
  private final String img;

  public ArticleViewModel(String title, String img) {
    this.title = title;
    this.img = img;
  }

  private ArticleViewModel(Builder builder) {
    title = builder.title;
    img = builder.img;
  }

  @Bindable public String getTitle() {
    return title;
  }

  @Bindable public String getImg() {
    return img;
  }

  public static final class Builder {
    private final String title;
    private final String img;

    public Builder(String title, String img) {
      this.title = title;
      this.img = img;
    }

    public ArticleViewModel build() {
      return new ArticleViewModel(this);
    }
  }
}
