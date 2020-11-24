package com.bage.tutorials.api.android;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class TVItem implements Serializable {

    private Long id;
    private String name;
    private String logo;
    private AppFavorite appFavorite;
    @Deprecated
    private String url;
    private List<String> urls;

}
