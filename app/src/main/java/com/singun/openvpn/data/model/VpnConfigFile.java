package com.singun.openvpn.data.model;

/**
 * Created by singun on 2017/3/4 0004.
 */

public class VpnConfigFile {
    private String id;
    private String name;
    private String url;

    public VpnConfigFile(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
