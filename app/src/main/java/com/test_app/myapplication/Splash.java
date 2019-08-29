package com.test_app.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.jgabrielfreitas.core.BlurImageView;

public class Splash extends AppCompatActivity {
    BlurImageView mBlur;
    Button mGetStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mBlur = (BlurImageView)findViewById(R.id.blurSplash);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.transition);
        mBlur.startAnimation(animation);

        mGetStart = findViewById(R.id.get_started);
        mGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }
        });
    }
}
