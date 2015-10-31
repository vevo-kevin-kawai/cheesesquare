package com.support.android.designlibdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;

/**
 * Created by kkawai on 10/26/15.
 */
public class VODFragment extends Fragment {

   private static final boolean IS_ADJUST_ON_CONFIG_CHANGE = true;
   private DraggablePanel mDraggablePanel;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_vod, container, false);
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      initializeDraggablePanel();
      adjustByConfiguration(getResources().getConfiguration().orientation);
   }

   @Override
   public void onSaveInstanceState(Bundle outState) {
      super.onSaveInstanceState(outState);
      Log.i("test", "VODFragment.onSaveInstanceState()");
   }

   private void adjustByConfiguration(final int orientation) {

      if (!IS_ADJUST_ON_CONFIG_CHANGE)
         return;
      if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

         if (isMinimizedInPortrait) {

            mDraggablePanel.maximizeFast();
            mDraggablePanel.setFullScreen(true);

         } else {
            Log.i("test", "VODFragment.adjustByConfiguration() landscape.  was not previously minimized. " + isMinimizedInPortrait);
            mDraggablePanel.setFullScreen(true);
         }
         ScreenUtil.hideSystemUI(getActivity().getWindow());

      } else {
         ScreenUtil.showSystemUI(getActivity().getWindow());
         mDraggablePanel.setFullScreen(false);
         if (isMinimizedInPortrait) {
            mDraggablePanel.minimizeFast();
         }
      }
   }

   /**
    * Initialize and configure the DraggablePanel widget with two fragments and some attributes.
    */
   private void initializeDraggablePanel() {

      final TopVODFragment topFragment = new TopVODFragment();
      mDraggablePanel = (DraggablePanel) getView().findViewById(R.id.draggable_panel);
      mDraggablePanel.setClickToMaximizeEnabled(true);
      mDraggablePanel.setVisibility(View.VISIBLE);
      mDraggablePanel.setFragmentManager(getFragmentManager());
      setupDraggablePanelListener();
      mDraggablePanel.setTopFragment(topFragment);

      final BottomVODFragment bottomFragment = new BottomVODFragment();
      mDraggablePanel.setBottomFragment(bottomFragment);
      mDraggablePanel.initializeView();

   }

   private void setupDraggablePanelListener() {
      mDraggablePanel.setDraggableListener(new DraggableListener() {
         @Override
         public void onMaximized() {
            //Log.i("test", "onMaximized(): minimized: " + mDraggablePanel.isMinimized() + " maximized: " + mDraggablePanel.isMaximized() + " click enabled:" + mDraggablePanel.isClickToMaximizeEnabled());
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
               isMinimizedInPortrait = false;
            }
         }

         @Override
         public void onMinimized() {
            //Log.i("test", "onMinimized(): minimized: " + mDraggablePanel.isMinimized() + " maximized: " + mDraggablePanel.isMaximized() + " click enabled:" + mDraggablePanel.isClickToMaximizeEnabled());
            isMinimizedInPortrait = true;
         }

         @Override
         public void onClosedToLeft() {
            Log.i("test", "onClosedToLeft");
            ((MainActivity) getActivity()).closeVOD();
         }

         @Override
         public void onClosedToRight() {
            Log.i("test", "onClosedToRight");
            ((MainActivity) getActivity()).closeVOD();
         }
      });
   }

   private boolean isMinimizedInPortrait;

   @Override
   public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
//      final View top = mDraggablePanel.getDraggableView().getTopView();
//      final View bottom = mDraggablePanel.getDraggableView().getBottomView();
//      final Transformer transformer = mDraggablePanel.getDraggableView().getTransformer();
      final int screenHeight = ScreenUtil.getScreenHeight(getActivity());
      final int screenWidth = ScreenUtil.getScreenWidth(getActivity());
      adjustByConfiguration(newConfig.orientation);

   }

   public DraggablePanel getDraggablePanel() {
      return mDraggablePanel;
   }
}
