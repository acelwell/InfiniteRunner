package com.krytality.infiniterunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.concurrent.CompletionService;

public class MainActivity extends Activity {

    private RelativeLayout adRelativeLayout;
    private AdView adView;
    private AdRequest adRequest;

    private InterstitialAd interstitialAd;

    private GameHub gh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.dm = dm;
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        Constants.FLOOR_TOP = dm.heightPixels - dm.heightPixels / 10;

        Constants.PLAYER_START_X = dm.widthPixels / Constants.PLAYER_SIZE_MOD;
        Constants.PLAYER_WIDTH = dm.widthPixels / Constants.PLAYER_SIZE_MOD / 2;
        Constants.PLAYER_START_Y = dm.heightPixels - dm.heightPixels / Constants.PLAYER_SIZE_MOD * 4;
        Constants.PLAYER_HEIGHT = Constants.PLAYER_WIDTH * 2;

        Constants.JUMP_SPEED = dm.heightPixels / 50;

        Constants.OBSTABLE_SPEED = dm.widthPixels / 100;

        Constants.CONTEXT = this;

        Constants.BUNDLE = savedInstanceState;



        if(this.gh == null)
        {
            this.gh = new GameHub(this);
        }


        //while(true)
        {
            setContentView(gh);
        }

//        while (true)
//            System.out.println("in main act");

//        setContentView(R.layout.activity_main);
//
//        Button button = (Button)findViewById(R.id.button1);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, DeadAd.class));
//            }
//        });



        //startActivity(new Intent(MainActivity.this, DeadAd.class));



//        Constants.AD = new InterstitialAd(this);
//        Constants.AD.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//
//        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//        Constants.AD.loadAd(request);



//        while (true)
//        {
//            if (Constants.AD.isLoaded() && !gh.isPlaying()) {
//                Constants.AD.show();
//            }
//        }



    }
}
