package com.jgw.supercodeplatform.trace.dto.producttesting;

public class ImageItem {
    private String url;
    private String name;

    public ImageItem(){}

    public ImageItem(String url){
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
