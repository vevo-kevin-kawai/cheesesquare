package com.support.android.designlibdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.github.pedrovgs.transformer.Transformer;

/**
 * Created by kkawai on 10/26/15.
 */
public class VODFragment extends Fragment {

   private static final boolean IS_ADJUST_ON_CONFIG_CHANGE=false;
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
      adjustConfigurationChange(getResources().getConfiguration().orientation);
   }

   private void adjustConfigurationChange(final int orientation) {
      if (!IS_ADJUST_ON_CONFIG_CHANGE)
         return;
      if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
         mDraggablePanel.getDraggableView().setTopViewHeight(ScreenUtil.getScreenHeight(getActivity()));
         mDraggablePanel.getDraggableView().getBottomView().setVisibility(View.GONE);
         mDraggablePanel.getDraggableView().setTouchEnabled(false);
         mDraggablePanel.setClickToMaximizeEnabled(false);
      } else {
         mDraggablePanel.getDraggableView().setTopViewHeight(ScreenUtil.getPortraitVideoHeight(getActivity()));
         mDraggablePanel.getDraggableView().getBottomView().setVisibility(View.VISIBLE);
         mDraggablePanel.getDraggableView().setTouchEnabled(true);
         mDraggablePanel.setClickToMaximizeEnabled(true);
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
      mDraggablePanel.setFragmentManager(getChildFragmentManager());
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
            Log.i("test", "onMaximized(): minimized: "+mDraggablePanel.isMinimized() + " maximized: " + mDraggablePanel.isMaximized() + " click enabled:" + mDraggablePanel.isClickToMaximizeEnabled());
         }

         @Override
         public void onMinimized() {
            Log.i("test", "onMinimized(): minimized: "+mDraggablePanel.isMinimized() + " maximized: " + mDraggablePanel.isMaximized() + " click enabled:" + mDraggablePanel.isClickToMaximizeEnabled());
         }

         @Override
         public void onClosedToLeft() {
            Log.i("test", "onClosedToLeft");
         }

         @Override
         public void onClosedToRight() {
            Log.i("test", "onClosedToRight");
         }
      });
   }

   @Override
   public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      Log.i("test", "onConfigurationChanged: " + (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "landscape" : "portrait"));

      final View top = mDraggablePanel.getDraggableView().getTopView();
      final View bottom = mDraggablePanel.getDraggableView().getBottomView();
      final Transformer transformer = mDraggablePanel.getDraggableView().getTransformer();
      final int screenHeight = ScreenUtil.getScreenHeight(getActivity());
      final int screenWidth=ScreenUtil.getScreenWidth(getActivity());
      adjustConfigurationChange(newConfig.orientation);
      Log.i("test","top: "+top.getMeasuredWidth() + "/" + top.getMeasuredHeight() + " bottom: "+bottom.getMeasuredWidth() + "/" + bottom.getMeasuredHeight() + " screenHeight: "+screenHeight);
   }
}
