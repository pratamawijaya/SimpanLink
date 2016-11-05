package com.pratamawijaya.simpanlink.presentation.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class DataBindingAdatpers {
  @BindingAdapter({ "bind:imageUrl" }) public static void setImageUri(ImageView view, String url) {
    Picasso.with(view.getContext()).load(url).into(view);
  }
}
