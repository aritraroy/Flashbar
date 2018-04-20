package com.andrognito.flashbardemo;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andrognito.flashbar.Flashbar;

import org.jetbrains.annotations.NotNull;

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
                    flashbar = progressAdvanced();
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

    private Flashbar basicDuration() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .duration(500)
                .message("This is a flashbar with duration")
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
                                + "withView will dynamically adjust itself.")
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

    private Flashbar background() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("The background color can be changed to any color of your choice.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .build();
    }

    private Flashbar backgroundDrawable() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("The background color can be changed to any color of your choice.")
                .backgroundDrawable(R.drawable.bg_gradient)
                .build();
    }

    private Flashbar overlay() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a modal overlay to give a dim effect in the entire screen.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showOverlay()
                .build();
    }

    private Flashbar overlayAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a modal overlay to give a dim effect in the entire screen.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showOverlay()
                .overlayColorRes(R.color.modal)
                .overlayBlockable()
                .build();
    }

    private Flashbar primaryActionBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can click on the primary action button.")
                .primaryActionText("TRY")
                .build();
    }

    private Flashbar primaryActionAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .message("You can click on the primary action button.")
                .primaryActionText("TRY")
                .primaryActionTextColorRes(R.color.colorAccent)
                .primaryActionTextSizeInSp(20)
                .build();
    }

    private Flashbar primaryActionListener() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can click on the primary action button.")
                .primaryActionText("TRY")
                .primaryActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .build();
    }

    private Flashbar positiveNegativeAction() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message(
                        "You can show either or both of the positive/negative buttons and "
                                + "customize them similar to the primary button.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .positiveActionText("YES")
                .negativeActionText("NO")
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .negativeActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .positiveActionTextColorRes(R.color.colorAccent)
                .negativeActionTextColorRes(R.color.colorAccent)
                .build();
    }

    private Flashbar iconBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a default icon on the left side of the with view.")
                .showIcon()
                .build();
    }

    private Flashbar iconAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a default icon on the left side of the with view.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showIcon()
                .icon(R.drawable.ic_drop)
                .iconColorFilterRes(R.color.colorAccent)
                .build();
    }

    private Flashbar progressBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show the progress bar on either the left or right side of the view")
                .showProgress(Flashbar.ProgressPosition.LEFT)
                .build();
    }

    private Flashbar progressAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show the progress bar on either the left or right side of the view")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showProgress(Flashbar.ProgressPosition.RIGHT)
                .progressTintRes(R.color.colorAccent, PorterDuff.Mode.SRC_ATOP)
                .build();
    }
}