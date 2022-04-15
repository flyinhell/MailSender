package com.eland.tools.postBody;

import com.google.gson.JsonArray;

/**
 * Created by chienhaoyu on 2018/12/6.
 */
public class PostBody {
    private String DataProviderId;
    private JsonArray Items;

    public PostBody(String dataProviderId, JsonArray items) {
        DataProviderId = dataProviderId;
        Items = items;
    }

    public String getDataProviderId() {
        return DataProviderId;
    }

    public void setDataProviderId(String dataProviderId) {
        DataProviderId = dataProviderId;
    }

    public JsonArray getItems() {
        return Items;
    }

    public void setItems(JsonArray items) {
        Items = items;
    }

    @Override
    public String toString() {
        return "PostBody{" +
               "DataProviderId='" + DataProviderId + '\'' +
               ", Items=" + Items +
               '}';
    }
}
