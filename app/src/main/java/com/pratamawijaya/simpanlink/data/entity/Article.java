package com.pratamawijaya.simpanlink.data.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pratama Nur Wijaya
 * Date : Nov - 11/4/16
 * Project Name : SimpanLink
 */

public class Article {
  private String title;
  private String description;
  private String body;
  private String image;
  private String url;
  private String uId;

  public Article() {
  }

  public String getuId() {
    return uId;
  }

  public void setuId(String uId) {
    this.uId = uId;
  }

  private Article(Builder builder) {
    setTitle(builder.title);
    setDescription(builder.description);
    setBody(builder.body);
    setImage(builder.image);
    setUrl(builder.url);
    setuId(builder.uId);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public static final class Builder {
    private String title;
    private String description;
    private String body;
    private String image;
    private String url;
    private String uId;

    public Builder() {
    }

    public Builder title(String val) {
      title = val;
      return this;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder body(String val) {
      body = val;
      return this;
    }

    public Builder image(String val) {
      image = val;
      return this;
    }

    public Builder url(String val) {
      url = val;
      return this;
    }

    public Builder uId(String val) {
      uId = val;
      return this;
    }

    public Article build() {
      return new Article(this);
    }
  }

  public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("userId", uId);
    result.put("title", title);
    result.put("url", url);
    result.put("image", image);
    return result;
  }
}
