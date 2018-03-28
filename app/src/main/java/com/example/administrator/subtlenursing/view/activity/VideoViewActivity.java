package com.example.administrator.subtlenursing.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.subtlenursing.R;
import com.example.administrator.subtlenursing.controller.PixelUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewActivity extends BaseActivity {
    @BindView(R.id.video)
    CustomVideoView videoView;
    @BindView(R.id.controllerbar_layout)
    LinearLayout controller_layout;
    @BindView(R.id.progress_layout)
    LinearLayout progress_layout;
    @BindView(R.id.speed_layout)
    FrameLayout speed_layout;

    @BindView(R.id.pause_img)
    ImageView play_controller_img;
    @BindView(R.id.screen_img)
    ImageView screen_img;
    @BindView(R.id.volume_img)
    ImageView volume_img;
    @BindView(R.id.operation_bg)
    ImageView operation_bg;
    @BindView(R.id.operation_percent)
    ImageView operation_percent;
    @BindView(R.id.fast)
    ImageView fast;
    @BindView(R.id.rewind)
    ImageView rewind;

    @BindView(R.id.time_current_tv)
    TextView time_current_tv;
    @BindView(R.id.time_total_tv)
    TextView time_total_tv;

    @BindView(R.id.play_seek)
    SeekBar play_seek;
    @BindView(R.id.volume_seek)
    SeekBar volume_seek;

    @BindView(R.id.videoLayout)
    RelativeLayout videoLayout;
    private int screen_width, screen_height;
    private AudioManager mAudioManager;
    private boolean isFullScreen = false;
    private boolean isAdjust = false;
    private int threshold = 108;
    private boolean isSpeed = false;
    public static final int UPDATE_UI = 1;
    private Handler UIhandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            if (msg.what == UPDATE_UI) {
                // 获取视频当前的播放时间
                int currentPosition = videoView.getCurrentPosition();
                // 获取视频播放的总时间
                int totalDuration = videoView.getDuration();

                // 格式化视频播放时间
                updateTextViewWithTimeFormat(time_current_tv, currentPosition);
                updateTextViewWithTimeFormat(time_total_tv, totalDuration);

                play_seek.setMax(totalDuration);
                play_seek.setProgress(currentPosition);

                UIhandler.sendEmptyMessageDelayed(UPDATE_UI, 300);
            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_view);
        ButterKnife.bind(this);
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        UIhandler.sendEmptyMessage(UPDATE_UI);
        init();
        String path ="http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
    }

    private void init() {
        PixelUtil.initContext(this);
        screen_width = getResources().getDisplayMetrics().widthPixels;
        screen_height = getResources().getDisplayMetrics().heightPixels;
        /*
         * 当前设备的最大音量
         */
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        /*
         * 获取设备当前的音量
         */
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume_seek.setMax(streamMaxVolume);
        volume_seek.setProgress(streamVolume);
        setPlayerEvent();

    }
    private void updateTextViewWithTimeFormat(TextView textView, int millisecond) {

        int second = millisecond / 1000;
        int hh = second / 3600;
        int mm = second % 3600 / 60;
        int ss = second % 60;
        String str = null;
        if (hh != 0) {
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }
    private void setPlayerEvent() {

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                play_controller_img.setImageResource(R.drawable.play_btn_style);

            }
        });

        /*
         * 控制视频的暂停与播放
         */
        play_controller_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (videoView.isPlaying()) {
                    play_controller_img.setImageResource(R.drawable.play_btn_style);
                    // 暂停播放
                    videoView.pause();
                    UIhandler.removeMessages(UPDATE_UI);
                } else {
                    play_controller_img.setImageResource(R.drawable.pause_btn_style);
                    // 继续播放
                    videoView.start();
                    UIhandler.sendEmptyMessage(UPDATE_UI);
                }
            }
        });
        /*
        播放进度条
         */
        play_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                int progress = seekBar.getProgress();
                // 令视频播放进度遵循seekBar停止拖动这一刻的进度
                videoView.seekTo(progress);
                play_controller_img.setImageResource(R.drawable.pause_btn_style);
                videoView.start();
                UIhandler.sendEmptyMessage(UPDATE_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                UIhandler.removeMessages(UPDATE_UI);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                updateTextViewWithTimeFormat(time_current_tv, progress);
            }
        });
        /*
        音量播放进度条
         */
        volume_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
        /*
         * 设置当前设备的的音量
         */
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
        });
        /*
        转换屏幕方向
         */
        screen_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (isFullScreen) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    screen_img.setImageResource(R.mipmap.fullscreen);//扩大屏幕图标
                    Log.e("Videoview","我被点击了，变成小屏幕拉");
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    screen_img.setImageResource(R.mipmap.fullshrink);//收缩屏幕图标
                }
            }
        });
        /*
        处理在屏幕上滑动触发音量和亮度的变化的事件
         */
        videoView.setOnTouchListener(new View.OnTouchListener() {

            private float lastX;
            private float lastY;
            private float x;
            private float y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(isFullScreen)
                    switch (event.getAction()) {

                        // 手指落下屏幕的那一刻，只会调用一次
                        case MotionEvent.ACTION_DOWN: {
                            x = event.getRawX();
                            y = event.getRawY();
                            lastX = x;
                            lastY = y;
                            break;
                        }
                        // 手指在屏幕上移动，调用多次
                        case MotionEvent.ACTION_MOVE: {
                            x = event.getRawX();
                            y = event.getRawY();
                            float detlaX = x - lastX;
                            float detlaY = y - lastY;
                            float absdetlaX = Math.abs(detlaX);
                            float absdetlaY = Math.abs(detlaY);
                            if (absdetlaX > threshold && absdetlaY > threshold) {
                                if (absdetlaX < absdetlaY) {
                                    isAdjust = true;
                                    isSpeed = false;
                                } else {
                                    isAdjust = false;
                                    isSpeed = true;
                                }
                            } else if (absdetlaX < threshold && absdetlaY > threshold) {
                                isAdjust = true;
                                isSpeed = false;
                            } else if (absdetlaX > threshold && absdetlaY < threshold) {
                                isAdjust = false;
                                isSpeed = true;
                            }

                            if (isAdjust) {
                        /*
                         * 在判断好当前手势事件已经合法的前提下，去区分此时手势应该调节亮度还是调节声音
                         */
                                if (x < screen_height / 2) {

                                    changeBrightness(-detlaY);
                                } else {
                                    // 调节声音
                                    changeVolume(-detlaY);
                                }
                            }
                            else if (isSpeed && isFullScreen) {
                                int position = videoView.getCurrentPosition();

                                if (detlaX > 300) {
                                    if (speed_layout.getVisibility() == View.GONE)
                                        speed_layout.setVisibility(View.VISIBLE);
                                    fast.setVisibility(View.VISIBLE);
                                    rewind.setVisibility(View.GONE);
                                    position = Math.min(position + 2000, videoView.getDuration());
                                } else if (detlaX < -300) {
                                    if (speed_layout.getVisibility() == View.GONE)
                                        speed_layout.setVisibility(View.VISIBLE);
                                    rewind.setVisibility(View.VISIBLE);
                                    fast.setVisibility(View.GONE);
                                    position = Math.max(position - 2000, 0);
                                }
                                videoView.seekTo(position);
                                play_seek.setProgress(position);
                            }
                            break;
                        }
                        // 手指从屏幕上抬起
                        case MotionEvent.ACTION_UP: {

                            progress_layout.setVisibility(View.GONE);
                            speed_layout.setVisibility(View.GONE);
                            rewind.setVisibility(View.GONE);
                            fast.setVisibility(View.GONE);
                            isSpeed = false;
                            isAdjust = false;
                            break;
                        }
                    }
                return true;
            }
        });
    }
    private void changeVolume(float detalY) {
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (detalY / screen_height * max * 3);
        int volume = Math.max(current + index, 0);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        if (progress_layout.getVisibility() == View.GONE) {
            progress_layout.setVisibility(View.VISIBLE);
        }
        if (volume > max) {
            volume = max;
        }
        operation_bg.setImageResource(R.drawable.bigvioce);//图片修改
        ViewGroup.LayoutParams layoutParams = operation_percent.getLayoutParams();
        layoutParams.width = (int) (PixelUtil.dp2px(120)*(float)volume/max);
        operation_percent.setLayoutParams(layoutParams);
        volume_seek.setProgress(volume);
    }

    private void changeBrightness(float detlaY) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        float mBrightness = attributes.screenBrightness;
        float index = detlaY / screen_height / 3;
        mBrightness += index;
        if (mBrightness > 1.0f) {
            mBrightness = 1.0f;
        }
        if (mBrightness < 0.01f) {
            mBrightness = 0.01f;
        }
        attributes.screenBrightness = mBrightness;
        if (progress_layout.getVisibility() == View.GONE) {
            progress_layout.setVisibility(View.VISIBLE);
        }
        operation_bg.setImageResource(R.mipmap.bright);//图片修改
        ViewGroup.LayoutParams layoutParams = operation_percent.getLayoutParams();
        layoutParams.width = (int) (PixelUtil.dp2px(120)*mBrightness);
        operation_percent.setLayoutParams(layoutParams);
        getWindow().setAttributes(attributes);
    }
    private void setVideoViewScale(int width, int height) {
        ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        videoView.setLayoutParams(layoutParams);

        ViewGroup.LayoutParams layoutParams2 = videoLayout.getLayoutParams();
        layoutParams2.width = width;
        layoutParams2.height = height;
        videoLayout.setLayoutParams(layoutParams2);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /*
     * 当屏幕方向为横屏的时候
     */
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            volume_img.setVisibility(View.VISIBLE);
            volume_seek.setVisibility(View.VISIBLE);
            isFullScreen = true;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    /*
     * 当屏幕方向为竖屏的时候
     */
        else {
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtil.dp2px(240));
            volume_img.setVisibility(View.GONE);
            volume_seek.setVisibility(View.GONE);
            isFullScreen = false;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        play_controller_img.setImageResource(R.drawable.play_btn_style);
        // 暂停播放
        videoView.pause();
        UIhandler.removeMessages(UPDATE_UI);
    }
    //对物理返回键进行操作
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isFullScreen) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                screen_img.setImageResource(R.mipmap.fullscreen);//扩大屏幕图标
                Log.e("Videoview","物理返回，变成小屏幕拉");
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
