package com.pratamawijaya.simpanlink.presentation.ui.add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.presentation.ui.add.presenter.AddLinkPresenter;

public class AddLinkActivity extends AppCompatActivity implements AddLinkView {

  private AddLinkPresenter presenter;

  @BindView(R.id.etAddLinkInputLink) EditText etInputLink;
  @BindView(R.id.activity_add_link) CoordinatorLayout parentView;

  private ProgressDialog progressDialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_link);
    ButterKnife.bind(this);

    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading...");

    presenter = new AddLinkPresenter(this);

    Intent intent = getIntent();
    String action = intent.getAction();
    String type = intent.getType();

    if (Intent.ACTION_SEND.equals(action) && type != null) {
      if ("text/plain".equals(type)) {
        handleShareableLink(intent);
      }
    }
  }

  private void handleShareableLink(Intent intent) {
    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
    if (sharedText != null) {
      if (!TextUtils.isEmpty(etInputLink.getText().toString())) {
        etInputLink.setText("");
      }
      etInputLink.setText(sharedText);
    }
  }

  @Override protected void onStart() {
    super.onStart();
    presenter.onAttachView();
  }

  @Override protected void onStop() {
    super.onStop();
    presenter.onDetachView();
  }

  @OnClick(R.id.btnSave) void onSaveClick() {
    presenter.saveLink(etInputLink.getText().toString());
  }

  @Override public void showError(String message) {
    Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
  }

  @Override public void showLoading() {
    progressDialog.show();
  }

  @Override public void hideLoading() {
    progressDialog.dismiss();
  }

  @Override public void saveSuccess() {
    Snackbar.make(parentView, "Sukses", Snackbar.LENGTH_SHORT).show();
  }
}
