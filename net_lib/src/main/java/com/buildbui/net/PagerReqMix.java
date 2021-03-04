package com.buildbui.net;

/**
 * desc:
 * author: luoh17
 * time: 2018/10/22 18:38
 */
public class PagerReqMix<T> {
  private int pageNo;
  private int pageSize = 20;
  private T entity;

  public static <T> PagerReqMix<T> create(int pageNo, T entity) {
    return new PagerReqMix<>(pageNo, entity);
  }

  public PagerReqMix(int pageNo, T entity) {
    this.entity = entity;
    this.pageNo = pageNo;
  }

  public int getPageNo() {
    return pageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

  public PagerReqMix<T> resetPagerSize(int pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public T getEntity() {
    return entity;
  }
}
