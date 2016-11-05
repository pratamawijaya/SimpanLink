package com.pratamawijaya.simpanlink.presentation.ui.home;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import com.pratamawijaya.simpanlink.presentation.model.ArticleViewModel;
import eu.rampsoftware.recyclerbinding.BR;
import java.util.List;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class HomeActivityModel extends BaseObservable {
  private ObservableList<ArticleViewModel> items = new ObservableArrayList<>();

  public HomeActivityModel(List<ArticleViewModel> articleViewModels) {
    this.items.addAll(articleViewModels);
  }

  public ObservableList<ArticleViewModel> getItems() {
    return items;
  }

  public int getItemBindingId() {
    return BR.model;
  }
}
