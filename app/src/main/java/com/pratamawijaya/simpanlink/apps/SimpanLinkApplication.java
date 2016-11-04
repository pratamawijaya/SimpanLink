package com.pratamawijaya.simpanlink.apps;

import android.app.Application;
import com.pratamawijaya.simpanlink.BuildConfig;
import timber.log.Timber;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class SimpanLinkApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
  }
}
