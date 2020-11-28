package com.bage.tutorials.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageViewUtils {

    public static void loadImage(Context context, String url, ImageView imageView) {
        if(StringUtils.isNotNullAndNotEmpty(url)){
            if(!url.startsWith("http")){
                url = UrlUtils.rewriteUrl(url);
            }
        }
        Picasso.with(context).load(url).into(imageView);
    }

}
