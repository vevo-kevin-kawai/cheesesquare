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
import android.widget.FrameLayout;

import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.github.pedrovgs.transformer.Transformer;

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
//         mDraggablePanel.getDraggableView().getBottomView().setVisibility(View.GONE);
//         final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mDraggablePanel.getDraggableView().getLayoutParams();
//         //params.width = FrameLayout.LayoutParams.MATCH_PARENT;
//         params.height = FrameLayout.LayoutParams.MATCH_PARENT;
//         mDraggablePanel.getDraggableView().setLayoutParams(params);
//         mDraggablePanel.getDraggableView().requestLayout();
//         mDraggablePanel.getDraggableView().setTopViewHeight(ScreenUtil.getScreenHeight(getActivity()));
//         mDraggablePanel.getDraggableView().setTouchEnabled(false);
//         mDraggablePanel.setClickToMaximizeEnabled(false);

         if (mDraggablePanel.isMinimized()) {
            new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                  if (getActivity() == null) {
                     return;
                  }
                  mDraggablePanel.maximize();

                  new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                        if (getActivity() == null) {
                           return;
                        }
                        mDraggablePanel.setFullScreen(true);

                     }
                  }, 500);


               }
            }, 250);
         } else {
            mDraggablePanel.setFullScreen(true);
         }

         Log.i("test", "VODFragment.adjustByConfiguration() landscape");

         ScreenUtil.hideSystemUI(getActivity().getWindow());
      } else {
         ScreenUtil.showSystemUI(getActivity().getWindow());
         mDraggablePanel.setFullScreen(false);
//         final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mDraggablePanel.getDraggableView().getLayoutParams();
//         //         params.width = FrameLayout.LayoutParams.MATCH_PARENT;
//         params.height = -1;
//         mDraggablePanel.getDraggableView().setLayoutParams(params);
//         mDraggablePanel.getDraggableView().requestLayout();
//
//         mDraggablePanel.setTopViewHeight(ScreenUtil.getPortraitVideoHeight(getActivity()));
//         mDraggablePanel.getDraggableView().setTopViewHeight(ScreenUtil.getPortraitVideoHeight(getActivity()));
//         mDraggablePanel.getDraggableView().getBottomView().setVisibility(View.VISIBLE);
//         mDraggablePanel.getDraggableView().setTouchEnabled(true);
//         mDraggablePanel.setClickToMaximizeEnabled(true);
//         Log.i("test", "VODFragment.adjustByConfiguration() portrait");
         if (isMinimizedInPortrait) {
            new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                  if (getActivity() == null || mDraggablePanel.isMinimized()) {
                     return;
                  }
                  mDraggablePanel.minimize();
               }
            }, 250);
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
