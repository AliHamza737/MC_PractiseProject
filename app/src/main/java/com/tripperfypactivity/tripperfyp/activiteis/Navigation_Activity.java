package com.tripperfypactivity.tripperfyp.activiteis;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.firebase.database.ServerValue;
import com.tripperfypactivity.tripperfyp.MyApplication;
import com.tripperfypactivity.tripperfyp.R;
import com.tripperfypactivity.tripperfyp.adapter.FragmentAdapter;
import com.tripperfypactivity.tripperfyp.utilites.Constans;
import com.tripperfypactivity.tripperfyp.utilites.Shared;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Navigation_Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.hamburgaer)
    ImageView hamburgaer;
    @BindView(R.id.toolbartitle)
    TextView toolbartitle;
    @BindView(R.id.notification)
    ImageView notification;
    @BindView(R.id.backarrow)
    ImageView backarrow;
    Map<String, String> date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        backarrow.setVisibility(View.GONE);
        date = ServerValue.TIMESTAMP;
        tabLayoutSetup();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, UserInfoActivity.class));
        } else if (id == R.id.nav_allTrip) {
            startActivity(new Intent(this, AllTripActivity.class));

        } else if (id == R.id.nav_manage) {

            startActivity(new Intent(this, Addtrip_Activity.class));

        } else if (id == R.id.nav_signout) {
            signOut();
        } else if (id == R.id.nav_share) {
            setShareapp();
        } else if (id == R.id.nav_send) {
            setShareTrip();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ShareActionProvider mShareActionProvider;

    // Call to update the share intent
    private void setShareTrip() {
        if (Shared.tripid != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sharesub = "your body here";
            String sharebody = "https://tripperfyp.page.link/fyp?tripid=" + 1234;
            intent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
            intent.putExtra(Intent.EXTRA_TEXT, sharebody);
            startActivity(Intent.createChooser(intent, "Share Via"));
        } else {
            invitePopup();
        }
    }

    private void setShareapp() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharebody = "your body here";
        String sharesub = "https://tripperfyp.page.link/fyp?offer=1234";
        intent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
        intent.putExtra(Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(intent, "Share Via"));

    }
    public void invitePopup() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Navigation_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle("");
        dialog.setMessage("To Invite a Friend, You Must be in a Trip");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();
    }

    private void signOut() {
        Shared.getAuthUser().signOut();
        startActivity(new Intent(this, Login_Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    public void tabLayoutSetup() {

        // Create an adapter that knows which fragment should be shown on each page
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);


        // Give the TabLayout the ViewPager
        final int[] ICONS = new int[]{
                R.drawable.home_icon,
                R.drawable.expences_icon,
                R.drawable.member_icon,
                R.drawable.chat_icon};
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(Constans.tabPostion0).setIcon(ICONS[0]);
        tabLayout.getTabAt(Constans.tabPostion1).setIcon(ICONS[1]);
        tabLayout.getTabAt(Constans.tabPostion2).setIcon(ICONS[2]);
        tabLayout.getTabAt(Constans.tabPostion3).setIcon(ICONS[3]);


        tabLayout.getTabAt(Constans.tabPostion0).getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(Constans.tabPostion1).getIcon().setColorFilter(getResources().getColor(R.color.gray_tab), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(Constans.tabPostion2).getIcon().setColorFilter(getResources().getColor(R.color.gray_tab), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(Constans.tabPostion3).getIcon().setColorFilter(getResources().getColor(R.color.gray_tab), PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(Constans.tabPostion0).setText(R.string.home_tab);
        tabLayout.getTabAt(Constans.tabPostion1).setText(R.string.expenses_tab);
        tabLayout.getTabAt(Constans.tabPostion2).setText(R.string.member_tab);
        tabLayout.getTabAt(Constans.tabPostion3).setText(R.string.chat_tab);
        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        if (tab.getPosition() == 3) {
                            Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                            tabLayout.startAnimation(slide);
                            hideSoftKeyboard();
                            tabLayout.setVisibility(View.GONE);
                        } else {
                            hideSoftKeyboard();
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_up);
                            tabLayout.setVisibility(View.VISIBLE);


                        }
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        tab.getIcon().setColorFilter(getResources().getColor(R.color.gray_tab), PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );
    }


    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    @OnClick(R.id.hamburgaer)
    public void onViewClicked() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);

    }


}


