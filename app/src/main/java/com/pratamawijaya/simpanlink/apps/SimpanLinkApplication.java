package com.pratamawijaya.simpanlink.apps;

import android.app.Application;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.pratamawijaya.simpanlink.BuildConfig;
import com.pratamawijaya.simpanlink.R;
import timber.log.Timber;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class SimpanLinkApplication extends Application {
  private static final long CACHE_IN_SEC = 5;

  @Override public void onCreate() {
    super.onCreate();

    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    firebaseRemoteConfig.setDefaults(R.xml.remote_config_default_value);
    firebaseRemoteConfig.fetch(CACHE_IN_SEC).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          Timber.d("onComplete() : remoteConfig fetched ");
          firebaseRemoteConfig.activateFetched();
        }
      }
    });
    if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
  }
}
