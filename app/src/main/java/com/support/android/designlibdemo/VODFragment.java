package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by kkawai on 10/26/15.
 */
public class VODFragment extends Fragment {

   private static final String YOUTUBE_API_KEY = "AIzaSyC1rMU-mkhoyTvBIdTnYU0dss0tU9vtK48";
   private static final String VIDEO_KEY = "gsjtg7m1MMM";
   private static final String VIDEO_POSTER_THUMBNAIL = "http://4.bp.blogspot.com/-BT6IshdVsoA/UjfnTo_TkBI/AAAAAAAAMWk/JvDCYCoFRlQ/s1600/" + "xmenDOFP.wobbly.1.jpg";
   private static final String SECOND_VIDEO_POSTER_THUMBNAIL = "http://media.comicbook.com/wp-content/uploads/2013/07/x-men-days-of-future-past" + "-wolverine-poster.jpg";
   private static final String VIDEO_POSTER_TITLE = "X-Men: Days of Future Past";

   DraggablePanel draggablePanel;

   private YouTubePlayer youtubePlayer;
   private YouTubePlayerSupportFragment youtubeFragment;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_vod, container, false);
   }

   /**
    * Initialize the Activity with some injected data.
    */
   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      initializeYoutubeFragment();
      initializeDraggablePanel();
      hookDraggablePanelListeners();
   }

   /**
    * Initialize the YouTubeSupportFrament attached as top fragment to the DraggablePanel widget and
    * reproduce the YouTube video represented with a YouTube url.
    */
   private void initializeYoutubeFragment() {
      youtubeFragment = new YouTubePlayerSupportFragment();
      youtubeFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

         @Override
         public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
            if (!wasRestored) {
               youtubePlayer = player;
               youtubePlayer.loadVideo(VIDEO_KEY);
               youtubePlayer.setShowFullscreenButton(true);
            }
         }

         @Override
         public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
         }
      });
   }

   /**
    * Initialize and configure the DraggablePanel widget with two fragments and some attributes.
    */
   private void initializeDraggablePanel() {

      TopVODFragment topFragment = new TopVODFragment();

      draggablePanel = (DraggablePanel) getView().findViewById(R.id.draggable_panel);
      draggablePanel.setVisibility(View.VISIBLE);
      draggablePanel.setFragmentManager(getFragmentManager());
      draggablePanel.setTopFragment(topFragment);
      BottomVODFragment moviePosterFragment = new BottomVODFragment();
      draggablePanel.setBottomFragment(moviePosterFragment);
      draggablePanel.initializeView();
      draggablePanel.setClickToMaximizeEnabled(true);

   }

   /**
    * Hook the DraggableListener to DraggablePanel to pause or resume the video when the
    * DragglabePanel is maximized or closed.
    */
   private void hookDraggablePanelListeners() {
      draggablePanel.setDraggableListener(new DraggableListener() {
         @Override
         public void onMaximized() {
            playVideo();
         }

         @Override
         public void onMinimized() {
            //Empty
         }

         @Override
         public void onClosedToLeft() {
            pauseVideo();
         }

         @Override
         public void onClosedToRight() {
            pauseVideo();
         }
      });
   }

   /**
    * Pause the video reproduced in the YouTubePlayer.
    */
   private void pauseVideo() {
      if (youtubePlayer.isPlaying()) {
         youtubePlayer.pause();
      }
   }

   /**
    * Resume the video reproduced in the YouTubePlayer.
    */
   private void playVideo() {
      if (!youtubePlayer.isPlaying()) {
         youtubePlayer.play();
      }
   }
}
