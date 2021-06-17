package com.example.videotest.presenter;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.videotest.view.MainView;

public interface MainPresenter {

    void onPause();

    void onSaveInstanceState(Bundle savedInstanceState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void attachView(MainView mainView);

    void detachView();

    void playCurrentVideo();

    void playNextVideo();

    MediaPlayer.OnCompletionListener getOnCompletionListener();

    String getApiKey();
}
