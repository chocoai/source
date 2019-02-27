package com.jeesite.modules.storevideo.faceplus;

import java.util.List;

public class SearchResult {
    private String request_id;
    private Integer time_used;
    private List<SearchResultItem> results;
    private String error_message;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Integer getTime_used() {
        return time_used;
    }

    public void setTime_used(Integer time_used) {
        this.time_used = time_used;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public List<SearchResultItem> getResults() {
        return results;
    }

    public void setResults(List<SearchResultItem> results) {
        this.results = results;
    }
}
