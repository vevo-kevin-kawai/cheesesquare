package com.support.android.designlibdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by kkawai on 10/26/15.
 */
public class CheeseDetailFragmentTest extends Fragment {

   public static final String EXTRA_NAME = "cheese_name";

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      setHasOptionsMenu(true);
      return inflater.inflate(R.layout.activity_detail, container, false);
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      final String cheeseName = "Blue Cheese Test";

      final Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
      getSupportActivity().setSupportActionBar(toolbar);
      getSupportActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
      collapsingToolbar.setTitle(cheeseName);

      loadBackdrop();

      System.out.println("onActivityCreated() test2");
   }

   private AppCompatActivity getSupportActivity() {
      return (AppCompatActivity) getActivity();
   }

   private void loadBackdrop() {
      final ImageView imageView = (ImageView) getView().findViewById(R.id.backdrop);
      Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      menu.clear();
      System.out.println(" test: onCreateOptionsMenu");
      inflater.inflate(R.menu.test_actions, menu);
   }

   @Override
   public boolean onOptionsItemSelected(final MenuItem item) {

      System.out.println(" test item: " + item.getItemId() + " android.R.id.home: " + android.R.id.home);
      switch (item.getItemId()) {
         case android.R.id.home:
            getSupportActivity().onBackPressed();
            return true;
         case R.id.action_settings:
            //todo
            return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
