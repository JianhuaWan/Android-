package com.buildbui.net;

public class ApiStateException extends RuntimeException {
  public static final int STATE_OK = 0;
  public static int ERR_ACCESS_TOKEN = -7;
  public static int ERR_MISS_INFO = -6;
  //public static final int ERR_REFRESH_TOKEN = 20015;
  private int code;
  private String msg;
  private String dateTime;
  private Object entity;

  public <T> ApiStateException(int code, String msg, String dateTime, T entity) {
    this.msg = msg;
    this.code = code;
    this.dateTime = dateTime;
    this.entity = entity;
  }

  public <T> T getEntity() {
    return (T) entity;
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getDateTime() {
    return dateTime;
  }
}
