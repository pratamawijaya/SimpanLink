package com.pratamawijaya.simpanlink.presentation.ui.add;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public interface AddLinkView {

  void showError(String message);

  void showLoading();

  void hideLoading();

  void saveSuccess();
}
