/*****************************************************************************
 * JavaActivity.java
 *****************************************************************************
 * Copyright (C) 2016-2019 VideoLAN
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *****************************************************************************/

package com.bage.tutorials.component.media;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class MediaPlay {

    private SimpleExoPlayer player = null;

    public MediaPlay() {

    }

    public void init(Context context, PlayerView playerView, String url) {
        player = new SimpleExoPlayer.Builder(context).build();

        // Bind the player to the view.
        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "com.bage.tutorials"));

        // This is the MediaSource representing the media to be played.
        HlsMediaSource hlsMediaSource =
                new HlsMediaSource.Factory(dataSourceFactory)
                        .setAllowChunklessPreparation(true)
                        .createMediaSource(Uri.parse(url));

        // Prepare the player with the source.
        player.prepare(hlsMediaSource);

    }

    public void play() {
        player.setPlayWhenReady(true);
    }


    public void stop() {
        player.setPlayWhenReady(false);
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

}
