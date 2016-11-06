package com.pratamawijaya.simpanlink.presentation.model.event;

import com.pratamawijaya.simpanlink.data.entity.Article;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/6/16
 * Project Name : SimpanLink
 */

public class HomeArcticleClickEvent {
  private Article article;

  public HomeArcticleClickEvent(Article article) {
    this.article = article;
  }

  public Article getArticle() {
    return article;
  }
}
