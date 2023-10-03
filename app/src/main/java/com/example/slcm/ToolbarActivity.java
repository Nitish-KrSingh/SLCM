package com.example.slcm;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

public class ToolbarActivity {

    private AppCompatActivity activity;
    private Toolbar toolbar;

    public ToolbarActivity(AppCompatActivity activity, int menuResId) {
        this.activity = activity;
        setupToolbar();
        setupMenu(menuResId);
        setupHamburgerIcon();
        hideAppTitle();
    }

    private void setupToolbar() {
        toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
    }

    private void setupMenu(int menuResId) {
        if (menuResId != 0) {
            toolbar.inflateMenu(menuResId);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    handleMenuItemClick(item);
                    return true;
                }
            });
        }
    }

    private void handleMenuItemClick(MenuItem item) {
        // Handle menu item clicks here
    }

    private void setupHamburgerIcon() {
        ImageView hamburgerIcon = toolbar.findViewById(R.id.toolbar_hamburger);
        if (hamburgerIcon != null) {
            hamburgerIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHamburgerMenu(v);
                }
            });
        }
    }

    private void showHamburgerMenu(View v) {
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item clicks here
                return true;
            }
        });
        popup.inflate(R.menu.student_menu);
        popup.show();
    }
    private void hideAppTitle() {
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public void setBackArrowVisibility(int visibility) {
        // You can add this method to control back arrow visibility if needed
        // Example: toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        //          toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        //              @Override
        //              public void onClick(View v) {
        //                  // Handle back arrow click
        //              }
        //          });
    }
}
