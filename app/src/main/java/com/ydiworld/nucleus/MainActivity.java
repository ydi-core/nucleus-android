package com.ydiworld.nucleus;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by sammy on 11/4/17.
 */

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    Fragment personFrag = new PersonFrag();
    Fragment eventFrag = new EventFrag();
    String fromActivity;
    SharedPreferences sharedPreferences;
    Bundle bundle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parent);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainArea, personFrag);
        setThingsUp();
    }

    private void changeScreens(String screen){

        switch (screen){
            case "SignInForm":
            {

                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainArea, personFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            case "RegisterForm":
            {
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainArea, eventFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        }

    }

    private void setThingsUp(){
        setWinFlags();

        ImageView location = findViewById(R.id.location);
        ImageView calendar = findViewById(R.id.calendar);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("HY", "Clicked");
                fragmentTransaction.replace(R.id.mainArea, eventFrag);
                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });

        ImageView imageView = findViewById(R.id.back_bg);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        String Uri = sharedPref.getString(getString(R.string.avatar), "");

        Picasso.with(this).load(Uri).into(imageView);
        Log.e("Any","outside");
        fragmentTransaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void clearWinFlags(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void setWinFlags(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
