package com.andrognito.flashbardemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andrognito.flashbar.Flashbar;

public class JavaSampleActivity extends AppCompatActivity {

    Flashbar flashbar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button show = findViewById(R.id.show);
        Button dismiss = findViewById(R.id.dismiss);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashbar == null) {
                    flashbar = messageAdvanced();
                }
                flashbar.show();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashbar != null) {
                    flashbar.dismiss();
                    flashbar = null;
                }
            }
        });
    }

    private Flashbar basic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .message("This is a basic flashbar")
                .build();
    }

    private Flashbar gravityTop() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .message("Flashbar is shown at the top")
                .build();
    }

    private Flashbar gravityBottom() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .message("Flashbar is shown at the bottom")
                .build();
    }

    private Flashbar titleBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .build();
    }

    private Flashbar titleAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World")
                .titleColorRes(R.color.white)
                .titleSizeInSp(24)
                .titleTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                .build();
    }

    private Flashbar messageBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .message(
                        "This is a short message. But your message can be of any length and the "
                                + "view will dynamically adjust itself.")
                .build();
    }

    private Flashbar messageAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .message("This is a short message")
                .messageColor(ContextCompat.getColor(this, R.color.white))
                .messageSizeInSp(16)
                .messageTypeface(
                        Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                .build();
    }
}