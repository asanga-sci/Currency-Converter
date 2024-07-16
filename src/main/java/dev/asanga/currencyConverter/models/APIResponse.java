package dev.asanga.currencyConverter.models;

import java.util.Map;

public class APIResponse {

    private Map<String, Object> meta;
    private Map<String,Map<String,Object>> data;

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public Map<String, Map<String, Object>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, Object>> data) {
        this.data = data;
    }
}
