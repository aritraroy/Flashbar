package com.andrognito.flashbardemo;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView.ScaleType;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;

import org.jetbrains.annotations.NotNull;

public class JavaSampleActivity extends AppCompatActivity {

    private static final String TAG = "Flashbar";

    private Flashbar flashbar = null;

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
                    flashbar = swipeToDismiss();
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
                .gravity(Flashbar.Gravity.BOTTOM)
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
                .message(
                        "Flashbar is shown at the top. You can also have more than one line in "
                                + "the flashbar. The bar will dynamically adjust its size.")
                .build();
    }

    private Flashbar gravityBottom() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .message("Flashbar is shown at the bottom.")
                .build();
    }

    private Flashbar titleBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello Flashbar")
                .message("You can put any message of any length here.")
                .build();
    }

    private Flashbar titleAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World")
                .titleColorRes(R.color.white)
                .titleSizeInSp(28)
                .message("The font and size of the text is changed here.")
                .titleTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                .build();
    }

    private Flashbar messageBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World")
                .message(
                        "This is a short message. But your message can be of any length and the "
                                + "with view will dynamically adjust itself. You can try to put "
                                + "very long messages as well. This can be really useful for "
                                + "putting up a lot of information to the user like feature "
                                + "explanation, tutorials, etc.")
                .build();
    }

    private Flashbar messageAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World")
                .message("This is a very short message.")
                .messageColor(ContextCompat.getColor(this, R.color.white))
                .messageSizeInSp(24)
                .messageTypeface(
                        Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                .build();
    }

    private Flashbar background() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .message("The background color can be changed to any color.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .build();
    }

    private Flashbar backgroundDrawable() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .titleTypeface(Typeface.createFromAsset(getAssets(), "BeautifulAndOpenHearted.ttf"))
                .titleSizeInSp(32)
                .message(
                        "You can add background drawables which can allow you to have cool "
                                + "gradient effects like this.")
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
                .build();
    }

    private Flashbar primaryActionBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .backgroundColorRes(R.color.colorAccent)
                .title("Hello World!")
                .message("You can click on the primary action button.")
                .primaryActionText("DOWNLOAD")
                .build();
    }

    private Flashbar primaryActionAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .message("You can customize the the primary action button.")
                .primaryActionText("TRY NOW")
                .primaryActionTextColorRes(R.color.colorAccent)
                .primaryActionTextSizeInSp(16)
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
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message(
                        "You can show either or both of the positive/negative buttons and "
                                + "customize them similar to the primary button.")
                .backgroundColorRes(R.color.chalk_black)
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
                .positiveActionTextColorRes(R.color.yellow)
                .negativeActionTextColorRes(R.color.yellow)
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
                .showIcon(0.8f, ScaleType.CENTER_CROP)
                .icon(R.drawable.ic_drop)
                .iconColorFilterRes(R.color.colorAccent)
                .build();
    }

    private Flashbar progressBasic() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message(
                        "You can show the progress bar on either the left or right side of the "
                                + "view")
                .showProgress(Flashbar.ProgressPosition.LEFT)
                .build();
    }

    private Flashbar progressAdvanced() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .message(
                        "You can customize the look-and-fell of the progress bar.")
                .backgroundColorRes(R.color.chalk_black)
                .showIcon()
                .showProgress(Flashbar.ProgressPosition.RIGHT)
                .progressTintRes(R.color.colorAccent, PorterDuff.Mode.SRC_ATOP)
                .build();
    }

    private Flashbar enterExitAnimation() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can change the enter/exit animation of the flashbar.")
                .backgroundColorRes(R.color.colorAccent)
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(550)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(500)
                        .alpha()
                        .overshoot())
                .build();
    }

    private Flashbar enterExitAnimationSlide() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can make the flashbar slide from the left/right as well.")
                .backgroundColorRes(R.color.chalk_black)
                .showIcon()
                .icon(R.drawable.ic_drop)
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(400)
                        .slideFromLeft()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(250)
                        .slideFromLeft()
                        .accelerate())
                .build();
    }

    private Flashbar iconAnimation() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can add animation effects to the icon as well.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showIcon()
                .icon(R.drawable.ic_drop)
                .iconColorFilterRes(R.color.colorAccent)
                .iconAnimation(FlashAnim.with(this)
                        .animateIcon()
                        .pulse()
                        .alpha()
                        .duration(750)
                        .accelerate())
                .build();
    }

    private Flashbar showListener() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can listen to events when the flashbar is shown.")
                .barShowListener(new Flashbar.OnBarShowListener() {
                    @Override
                    public void onShowing(@NotNull Flashbar bar) {
                        Log.d(TAG, "Flashbar is showing");
                    }

                    @Override
                    public void onShowProgress(@NotNull Flashbar bar, float progress) {
                        Log.d(TAG, "Flashbar is showing with progress: " + progress);
                    }

                    @Override
                    public void onShown(@NotNull Flashbar bar) {
                        Log.d(TAG, "Flashbar is shown");
                    }
                })
                .build();
    }

    private Flashbar dismissListener() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .duration(500)
                .message("You can listen to events when the flashbar is dismissed.")
                .barDismissListener(new Flashbar.OnBarDismissListener() {
                    @Override
                    public void onDismissing(@NotNull Flashbar bar, boolean isSwiped) {
                        Log.d(TAG, "Flashbar is dismissing with " + isSwiped);
                    }

                    @Override
                    public void onDismissProgress(@NotNull Flashbar bar, float progress) {
                        Log.d(TAG, "Flashbar is dismissing with progress " + progress);
                    }

                    @Override
                    public void onDismissed(@NotNull Flashbar bar,
                            @NotNull Flashbar.DismissEvent event) {
                        Log.d(TAG, "Flashbar is dismissed with event " + event);
                    }
                })
                .build();
    }

    private Flashbar barTap() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can listen to tap events inside or outside te bar.")
                .listenBarTaps(new Flashbar.OnTapListener() {
                    @Override
                    public void onTap(@NotNull Flashbar flashbar) {
                        Log.d(TAG, "Bar tapped");
                    }
                })
                .listenOutsideTaps(new Flashbar.OnTapListener() {
                    @Override
                    public void onTap(@NotNull Flashbar flashbar) {
                        Log.d(TAG, "Outside tapped");
                    }
                })
                .build();
    }

    private Flashbar swipeToDismiss() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can swipe the flashbar to dismiss it.")
                .enableSwipeToDismiss()
                .build();
    }

    private Flashbar barShadow() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can swipe the flashbar to dismiss it.")
                .castShadow(true, 4)
                .build();
    }

    private Flashbar vibration() {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can swipe the flashbar to dismiss it.")
                .vibrateOn(Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS)
                .build();
    }
}