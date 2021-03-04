package com.buildbui.net;

import io.reactivex.Maybe;

/**
 * desc:
 * author: luoh17
 * time: 2018/11/8 11:46
 */
public interface TokenActionCallback<R> {

  Maybe<R> getUpdateTokenMaybe();

  void onUpdateTokenSuccess(R tokenResult);

  void onReLogin(int errCode);

  void onMissInfo();
}
