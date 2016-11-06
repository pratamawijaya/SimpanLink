package com.pratamawijaya.simpanlink.presentation.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pratamawijaya.simpanlink.R;
import com.pratamawijaya.simpanlink.data.entity.Article;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
  private Context context;
  private List<Article> articles;

  public HomeAdapter(Context context, List<Article> articles) {
    this.context = context;
    this.articles = articles;
  }

  @Override public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new HomeHolder(
        LayoutInflater.from(context).inflate(R.layout.item_homearticle, parent, false));
  }

  @Override public void onBindViewHolder(HomeHolder holder, int position) {
    holder.bindItem(articles.get(position));
  }

  @Override public int getItemCount() {
    return articles.size();
  }

  public class HomeHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imgArticle) ImageView img;
    @BindView(R.id.tvTitle) TextView title;

    public HomeHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bindItem(Article article) {
      title.setText(article.getTitle());
      Picasso.with(context).load(article.getImage()).into(img);
    }
  }
}