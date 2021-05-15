package com.ydy.taole.activists;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.ydy.taole.R;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private VideoView videoView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
        setVideoView();
        Uri uri = Uri.parse("https://interface.sina.cn/wap_api/video_location.d.html?cid=37766&table_id=36885&did=icezuev7840213&vt=4&creator_id=1001&vid=31096292401&video_id=310962924&r=video.sina.cn%2Fnews%2F2019-11-07%2Fdetail-iicezuev7840213.d.html&wm=28100025mpampampltbrampampgtlt&time=1575359326773&rd=0.4191317904145858");
        //设置视频控制器
        videoView2.setMediaController(new MediaController(this));
        //播放完成回调
        videoView2.setOnCompletionListener(new MyPlayerOnCompletionListener());
        //设置视频路径
        videoView2.setVideoURI(uri);
        //开始播放视频
        //videoView2.start();
    }

    /**
     * 亲测以下直播均可用
     * CCTV1高清：http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8
     * CCTV3高清：http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8
     * CCTV5+高清：http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8
     * CCTV6高清：http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8
     */
    private void setVideoView() {
        //本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        //String videoUrl1 = Environment.getExternalStorageDirectory().getPath()+"/fl1234.mp4" ;
        //videoView.setVideoPath(videoUrl1);

        // "rtsp://填写地址"
        Uri uri = Uri.parse("https://interface.sina.cn/wap_api/video_location.d.html?cid=195763&table_id=258&did=icezzrq4688848&vt=4&creator_id=&vid=30360263201&video_id=303602632&r=v.sina.cn%2Fweibo_ugc%2F2019-09-09%2Fdetail-iic");
        //获取焦点
        videoView.requestFocus();
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
        //设置视频路径
        videoView.setVideoURI(uri);
        //开始播放视频
        //videoView.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(VideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView2 = (VideoView) findViewById(R.id.videoView2);
    }
}