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

/**
 * Created by kkawai on 10/26/15.
 */
public class VODFragment extends Fragment {

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
      Log.i("test","onConfigurationChanged: " + (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "landscape" : "portrait"));
   }
}
