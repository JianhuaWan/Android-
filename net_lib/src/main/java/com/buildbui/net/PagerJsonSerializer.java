package com.buildbui.net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * desc:
 * author: luoh17
 * time: 2018/10/23 10:18
 */
public class PagerJsonSerializer implements JsonSerializer<PagerReqMix> {

  @Override
  public JsonElement serialize(PagerReqMix src, Type typeOfSrc, JsonSerializationContext context) {
    JsonElement serialize = context.serialize(src.getEntity());
    JsonObject jsonObject = serialize.getAsJsonObject();
    //若要自定义分页的字段名，可以在这里改，比如jsonObject.addProperty("pageNum",src.getPageNo());
    jsonObject.addProperty("pageNo", src.getPageNo());
    jsonObject.addProperty("pageSize", src.getPageSize());
    return serialize;
  }
}
