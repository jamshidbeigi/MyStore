package com.example.mohamadreza.mystore.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.mohamadreza.mystore.R;

public class WelcomeActivity extends AppCompatActivity {

    LottieAnimationView av_from_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        av_from_code = findViewById(R.id.av_from_code);
        av_from_code.setAnimation(R.raw.store);
        av_from_code.playAnimation();

        av_from_code.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

}
