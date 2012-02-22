package com.Venus.NakedSkin;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoPlayer extends Activity implements OnCompletionListener {

    private VideoView mVideoView;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.videoplayer );
        mVideoView = (VideoView)findViewById( R.id.surfacevideoview );
        //mVideoView.setVideoURI( Uri.parse("android.resource://com.venus.phone/" + R.raw.sample ) );
        mVideoView.setOnCompletionListener( this );
        mVideoView.start();
    }

    public void onCompletion( MediaPlayer mp ) {
        finish();
    }

}
