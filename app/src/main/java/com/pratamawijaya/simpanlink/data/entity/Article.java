package com.pratamawijaya.simpanlink.data.entity;

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

  private Article(Builder builder) {
    setTitle(builder.title);
    setDescription(builder.description);
    setBody(builder.body);
    setImage(builder.image);
    setUrl(builder.url);
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

    public Article build() {
      return new Article(this);
    }
  }
}
