package com.example.videotest.view;

import android.content.Context;

public interface MainView {

    Context getContext();

    void showViews();

    void hideViews();

    void showVideo(String video, int videoPosition);

    void showYoutubeVideo();

    int getCurrentVideoPosition();

    boolean isYoutubePlayerViewVisible();
}
