package com.bage.tutorials.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ImageViewUtils {

    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, null, null);
    }

    public static void loadImage(Context context, String url, ImageView imageView, Integer placeholderId, Integer errorId) {
        if (StringUtils.isNotNullAndNotEmpty(url)) {
            if (!url.startsWith("http")) {
                url = UrlUtils.rewriteUrl(url);
            }
        }
        if (Objects.isNull(placeholderId) || Objects.isNull(errorId)) {
            Picasso.with(context)
                    .load(url)
                    .into(imageView);
            return;
        }
        Picasso.with(context)
                .load(url)
                .placeholder(placeholderId)
                .error(errorId)
                .into(imageView);
    }

}
