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

import android.net.Uri;

import com.bage.tutorials.domain.TVItem;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

import java.util.ArrayList;

public class VLCMedia {
    private static final boolean USE_TEXTURE_VIEW = false;
    private static final boolean ENABLE_SUBTITLES = true;
    private VLCVideoLayout mVideoLayout = null;
    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;

    public VLCMedia(VLCVideoLayout mVideoLayout) {
        ArrayList<String> args = new ArrayList<>();
        args.add("-vvv");
        mLibVLC = new LibVLC(mVideoLayout.getContext(), args);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        this.mVideoLayout = mVideoLayout;
    }

    public void init(TVItem item) {
        mMediaPlayer.attachViews(mVideoLayout, null, ENABLE_SUBTITLES, USE_TEXTURE_VIEW);
        final Media media = new Media(mLibVLC, Uri.parse(item.getUrl()));
        mMediaPlayer.setMedia(media);
    }

    public void stop() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void play() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.play();
        }
    }

    public void onDestroy() {
        mMediaPlayer.release();
        mLibVLC.release();
    }

    public void onStop() {
        mMediaPlayer.stop();
        mMediaPlayer.detachViews();
    }

}
