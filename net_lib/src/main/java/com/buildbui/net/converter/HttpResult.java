package com.buildbui.net.converter;

import com.google.gson.annotations.SerializedName;

public class HttpResult<T> {
  @SerializedName("data") private T entity;
  private String msg;
  private int code;
  private String dateTime;
  private String status;

  private Object validMessage;

  public T getEntity() {
    return entity;
  }

  public void setEntity(T entity) {
    this.entity = entity;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
