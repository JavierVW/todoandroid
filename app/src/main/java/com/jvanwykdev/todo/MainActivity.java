package com.jvanwykdev.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.jvanwykdev.todo.ui.main.SectionsPagerAdapter;



public class MainActivity extends AppCompatActivity implements AddDialog.AddDialogListener, EditDialog.EditDialogListener, SharedPreferences.OnSharedPreferenceChangeListener {


    ToDo todoAdd = new ToDo();
    private Toolbar mtoolbar;


    //SharedPreferences sharedPrefer = PreferenceManager.getDefaultSharedPreferences(this);
//    boolean dmMode = sharedPrefer.getBoolean("pref_dark", true);




    @Override
    public void applyTexts(String addingStr) {
        todoAdd.AddInput(addingStr);
    }

    @Override
    public void editTexts(String addingStr) {
        todoAdd.AddInput(addingStr);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupShared();
        //setGlobalTheme(dmMode);

        //setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);


        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        //setupShared();

//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()==R.id.settings) {

            Log.i("Menu item selected", "Settings");

            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
            else
                return false;
    }


    //TODO fix the dark mode switch, code below

    private void setupShared(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("pref_dark")){
            setGlobalTheme(sharedPreferences.getBoolean("pref_dark",true));

        }
    }

    public void setGlobalTheme(boolean pref_dark){
        if (pref_dark==true){
            Log.i("onclick"," setGlobTheme Reached!");
            //setTheme(R.style.ThemeDark);
            //this.setTheme(R.style.AppTheme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
        }
        else {
            //setTheme(R.style.AppTheme);
            //setTheme(R.style.AppTheme_Dark);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}