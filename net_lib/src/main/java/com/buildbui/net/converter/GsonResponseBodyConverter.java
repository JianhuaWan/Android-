package com.buildbui.net.converter;

import com.buildbui.net.ApiStateException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<HttpResult<T>> adapter;

  GsonResponseBodyConverter(Gson gson, TypeToken<T> typeToken) {
    this.gson = gson;

    Utils.ParameterizedTypeImpl parameterizedType =
        new Utils.ParameterizedTypeImpl(null, HttpResult.class, typeToken.getType());

    this.adapter = (TypeAdapter<HttpResult<T>>) gson.getAdapter(TypeToken.get(parameterizedType));
  }

  @Override public T convert(ResponseBody value) throws IOException {
    JsonReader jsonReader = gson.newJsonReader(value.charStream());
    HttpResult<T> result;
    try {
      result = adapter.read(jsonReader);
      if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
        throw new JsonIOException("JSON document was not fully consumed.");
      }
      if (result != null && result.getCode() != ApiStateException.STATE_OK) {
        throw new ApiStateException(result.getCode(), result.getMsg(), result.getDateTime(),
            result.getEntity());
      }
      return result == null ? null : result.getEntity();
    } finally {
      value.close();
    }
  }
}
