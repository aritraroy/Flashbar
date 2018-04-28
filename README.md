
# Flashbar
A highly customizable, powerful and easy-to-use alerting library for Android.

### Specs
[![Download](https://api.bintray.com/packages/aritraroy/maven/flashbar/images/download.svg)](https://bintray.com/aritraroy/maven/flashbar/_latestVersion) [![API](https://img.shields.io/badge/API-14%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=14) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)  

This library allows you to show messages or alerts in your app **quickly and easily**. It can be used as an alternative to `Snackbar` or `Toast` and offers a plethora of useful features and customization options for you to play with.

It has been written **100% in Kotlin**. ❤️

![](/raw/banner.png)

# Table of Contents
* [Download](#download)
* [Sample Project](#sample-project)
* [Usage](#usage)
    * [Basics](#basics)
    * [Gravity](#gravity)
    * [Title](#title)
    * [Message](#message)
    * [Background & Overlay](#background-overlay)
      * [Background](#background)
      * [Overlay](#overlay)
    * [Actions](#actions)
      * [Primary](#primary)
      * [Positive/Negative](#positivenegative)
    * [Icon & Progress](#icon-progress)
      * [Icon](#icon)
      * [Progress](#progress)
    * [Animations](#animations)
      * [Enter/Exit](#enterexit)
      * [Icon](#icon)
    * [Event Listeners](#event-listeners)
      * [Show](#show)
      * [Dismiss](#dismiss)
      * [Taps](#taps)
    * [Miscellaneous](#miscellaneous)
      * [Swipe-to-dismiss](#swipe-to-dismiss)
      * [Shadow](#shadow)
      * [Vibration](#vibration)
* [Roadmap](#roadmap)
* [Contribution](#contribution)
* [License](#license)

### Spread Some :heart:
[![GitHub followers](https://img.shields.io/github/followers/aritraroy.svg?style=social&label=Follow)](https://github.com/aritraroy)  [![Twitter Follow](https://img.shields.io/twitter/follow/aritraroy.svg?style=social)](https://twitter.com/aritraroy) 

# Download

This library is available in **jCenter** which is the default Maven repository used in Android Studio. You can also import this library from source as a module.
 
```groovy
dependencies {
    // other dependencies here
    implementation 'com.andrognito.flashbar:flashbar:{latest_version}'
}
```

# Sample Project
We have an exhaustive sample project demonstrating almost every feature of the library in both languages - Java & Kotlin.

Checkout the Java samples [here](https://github.com/aritraroy/Flashbar/blob/develop/app/src/main/java/com/andrognito/flashbardemo/JavaSampleActivity.java) and the Kotlin samples [here](https://github.com/aritraroy/Flashbar/blob/develop/app/src/main/java/com/andrognito/flashbardemo/KotlinSampleActivity.kt).

# Usage
It is recommended to check the sample project to get a complete understanding of all the features offered by the library.

The library offers a huge amount of customization options and leverages the `Builder` pattern for ease of use. You will find details of each of these features described below.

## Basics

![](/raw/basic.png)

Flashbar attaches a full-height, full-width view (`FlashbarContainerView`) to the decor view of the `Activity` and places a full width, dynamic height view(`FlashbarView`) inside it. These classes are internal classes and are not exposed for public use.

Here's an example of showing a basic flashbar,

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .message("This is a basic flashbar")
        .build()
```

 You can specify the duration (in millis) for which you want the flashbar to be displayed. The default duration is infinite, i.e. it won't dismiss automatically if you do not specify any duration. You can also use these constants, `DURATION_SHORT` or `DURATION_LONG` for convenience.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .duration(500)
        .message("This is a flashbar with duration")
        .build()
```

## Gravity
![](/raw/gravity.png)

You can show the flashbar either at the top or at the bottom of the screen using the gravity property. By default, it is shown at the bottom.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message("Flashbar is shown at the top")
        .build()
```
Or,

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .message("Flashbar is shown at the bottom")
        .build()
```

## Title
![](/raw/title.png)

You can show an optional title in the flashbar. You can also customize the color, size, typeface and appearance of it.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .build();
```

![](/raw/title-advanced.png)

You can change the color using `titleColor()`, size using `titleSizeInSp()`, `titleSizeInPx()`, typeface using `titleTypeface()` and appearance using `titleAppearance()`. Also, look out for other variants of this methods.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .titleColorRes(R.color.white)
        .titleSizeInSp(12f)
        .titleAppearance(R.style.CustomTextStyle)
        .titleTypeface(Typeface.createFromAsset(getAssets(), "ShineBright.ttf"))
        .build();
```

## Message
![](/raw/message.png)

You can show an optional message in the flashbar. You can also customize the color, size and appearance of it.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .message("This is a short message. But your message can be of any length and the view will dynamically adjust itself.")
        .build();
```

![](/raw/message-advanced.png)

You can change the color using `messageColor()`, size using `messageSizeInSp()`, `messageSizeInPx()`, typeface using `messageTypeface()` and appearance using `messageAppearance()`. Also, look out for other variants of this methods.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message("This is a short message")
        .messageColor(ContextCompat.getColor(this, R.color.white))
        .messageSizeInSp(16f)
        .messageTypeface(Typeface.createFromAsset(assets, "BeautifulAndOpenHearted.ttf"))
        .build()
```

## Background & Overlay
![](/raw/background.png)

You can change the background color of the flashbar and add a modal overlay as well.

####  Background

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("The background color can be changed to any color of your choice.")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .build()
```

![](/raw/background-advanced.png)

You can also change the background using drawables, like the above, to have a cool gradient effect.


```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can have gradients by setting background drawable.")
        .backgroundDrawable(R.drawable.bg_gradient)
        .build()
```
####  Overlay
![](/raw/overlay.gif)

The overlay creates a dim effect over the entire screen bringing more focus on the flashbar and its content. It is automatically added/removed along with the flashbar.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show a modal overlay to give a dim effect in the entire screen.")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .showOverlay()
        .build()
```
You can also customize the overlay color using `overlayColor()` and also make the overlay block any click/touch events using `overlayBlockable()`.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show a modal overlay to give a dim effect in the entire screen.")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .showOverlay()
        .overlayColorRes(R.color.modal)
        .overlayBlockable()
        .build()
```

## Actions
There are three types of action buttons available - primary (placed at the right side), positive and negative (placed at the bottom).

### Primary
![](/raw/action-primary.png)

You can customize the primary action button's text color, size, typeface, appearance and also listen to its tap events.

The quickest way to show an action button is to put some text into it.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can click on the primary action button.")
        .primaryActionText("TRY NOW")
        .build()
```

![](/raw/action-primary-advanced.png)

You can also customize its appearance in a lot of ways,

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .message("You can click on the primary action button.")
        .primaryActionText("TRY")
        .primaryActionTextColorRes(R.color.colorAccent)
        .primaryActionTextSizeInSp(20f)
        .build()
```

You can also listen to its tap/click events through the `OnActionTapListener`,

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can click on the primary action button.")
        .primaryActionText("TRY")
        .primaryActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                bar.dismiss()
            }
        })
        .build()
```
### Positive/Negative
![](/raw/action-positive-negative.png)

You can customize the positive and negative buttons in the same way as the primary button. These buttons appear at the bottom part of the view. You can show the positive, or negative or both the buttons. You can also listen to the tap events in the same way as before.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show either or both of the positive/negative buttons and customize them similar to the primary button.")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .positiveActionText("YES")
        .negativeActionText("NO")
        .positiveActionTextColorRes(R.color.colorAccent)
        .negativeActionTextColorRes(R.color.colorAccent)
        .positiveActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                bar.dismiss()
            }
        })
        .negativeActionTapListener(object : Flashbar.OnActionTapListener {
            override fun onActionTapped(bar: Flashbar) {
                bar.dismiss()
            }
        })
        .build()
```

## Icon & Progress

You can show icon (left) and progress bar (left or right) in the flashbar. You can also customize their look & feel in a lot of ways.

### Icon
![](/raw/icon.png)

You can show an icon on the left side of the view using `showIcon()` which will show the default icon.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show a default icon on the left side of the withView")
        .showIcon()
        .build()
```

You can show any custom icon (drawable or bitmap) and apply color filters (change modes too) on it. You can also scale the icon up/down and specify scale type using the variants of `showIcon(scale, scaleType)`.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show a default icon on the left side of the withView")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .showIcon(0.8f, ScaleType.CENTER_CROP)
        .icon(R.drawable.ic_drop)
        .iconColorFilterRes(R.color.colorAccent)
        .build()

```

### Progress
![](/raw/progress_left.gif)

You might also want to show indeterminate progress bars to indicate that you are fetching some data, downloading a file, etc. The progress bar can be shown at either the left or the right hand side of the view.

**Caveat**: If the progress bar is shown on the left side, then you cannot show the icon with it. If the progress bar is shown on the right side, then you cannot show the action button along with it.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show the progress bar on either the left or right side of the view")
        .showProgress(Flashbar.ProgressPosition.LEFT)
        .build()
```
![](/raw/progress_right.gif)

You can change the color of the progress bar to any color of your choice.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show the progress bar on either the left or right side of the view")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .showProgress(Flashbar.ProgressPosition.RIGHT)
        .progressTintRes(R.color.colorAccent)
        .build()
```

## Animations

You can customize the enter/exit animation of the flashbar. You can also add custom animations to the icon for drawing attention towards it. The library provides a fluent API-styled animation framework to customize these animations.

### Enter/Exit
![](/raw/enter_exit_anim.gif)

You can start animating the bar using `FlashAnim.with(this).animateBar()`. You can change the duration of the animation using `duration()`, apply custom interpolators using `interpolator()` or choose from a set of interpolators available, add alpha transition using `alpha()`, etc.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can change the enter/exit animation of the flashbar")
        .backgroundColorRes(R.color.colorPrimaryDark)
        .enterAnimation(FlashAnim.with(this)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot())
        .exitAnimation(FlashAnim.with(this)
                .animateBar()
                .duration(400)
                .accelerateDecelerate())
        .build()
```

![](/raw/slide_left_anim.gif)

You can also make the flashbar enter/exit from the left/right side of the screen,

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can change the enter/exit animation of the flashbar")
        .backgroundColorRes(R.color.colorPrimaryDark)
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
        .build()
```

**Note** - You can configure the animations with your desired specifications in the builder and pass it on. You can not call `build()` on these animations as it is reserved to be used internally from inside the library only.

### Icon
![](/raw/icon_anim.gif)

You can start animating the icon using `FlashAnim.with(this).animateIcon()`. You can change the duration of the animation using `duration()`, apply custom interpolators using `interpolator()` or choose from a set of interpolators available, add pulsating effect using `pulse()` and alpha transition using `alpha()`, etc.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can show a default icon on the left side of the with view.")
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
        .build()
```

## Event Listeners
You can listen to events like when the flashbar is showing, or dismissing. You can also listen to progress updates when the flashbar is being shown or dismissed to perform animations on other views if needed.

You can also listen to tap events inside or outside the bar.

### Show
You can listen to events on `OnBarShowListener` like `onShowing`, `onShowProgress` and `onShown`. The progress ranges from from 0.0 to 1.0. But in some special cases (like with bounce interpolator) it can go below 0.0 or above 1.0.
 
```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .message("You can listen to events when the flashbar is shown")
        .barShowListener(object : Flashbar.OnBarShowListener {
            override fun onShowing(bar: Flashbar) {
                Log.d(TAG, "Flashbar is showing")
            }

            override fun onShowProgress(bar: Flashbar, progress: Float) {
                Log.d(TAG, "Flashbar is showing with progress: $progress")
            }

            override fun onShown(bar: Flashbar) {
                Log.d(TAG, "Flashbar is shown")
            }
        })
        .build()
```
### Dismiss
You can listen to events on `OnBarDismissListener` like `onDismissing`, `onDismissProgress` and `onDismissed`. The progress ranges from from 0.0 to 1.0. But in some special cases (like with bounce interpolator) it can go below 0.0 or above 1.0. 

You can also specifically get to know the reason behind the bar dismiss action - `TIMEOUT`, `MANUAL`, `TAP_OUTSIDE` and `SWIPE`.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .duration(500)
        .message("You can listen to events when the flashbar is dismissed")
        .barDismissListener(object : Flashbar.OnBarDismissListener {
            override fun onDismissing(bar: Flashbar, isSwiped: Boolean) {
                Log.d(TAG, "Flashbar is dismissing with $isSwiped")
            }

            override fun onDismissProgress(bar: Flashbar, progress: Float) {
                Log.d(TAG, "Flashbar is dismissing with progress $progress")
            }

            override fun onDismissed(bar: Flashbar, event: Flashbar.DismissEvent) {
                Log.d(TAG, "Flashbar is dismissed with event $event")
            }
        })
        .build()
```

### Taps
You can listen to tap events inside or outside of the bar.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can listen to tap events inside or outside te bar.")
        .listenBarTaps(object : Flashbar.OnTapListener {
            override fun onTap(flashbar: Flashbar) {
                Log.d(TAG, "Bar tapped")
            }
        })
        .listenOutsideTaps(object : Flashbar.OnTapListener {
            override fun onTap(flashbar: Flashbar) {
                Log.d(TAG, "Outside tapped")
            }
        })
        .build()
```

## Miscellaneous
A quick look at some of the miscellaneous features available in flashbar.

### Swipe-to-dismiss
![](/raw/swipe_bar.gif)

You can enable this feature to dismiss the flashbar by swiping it left/right. By default this feature is disabled. You can also know if the bar was dismissed by a swipe from the `DismissEvent` as `SWIPE`.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .title("Hello World!")
        .message("You can swipe the flasbar to dismiss it.")
        .enableSwipeToDismiss()
        .build() 
```

### Shadow
You can show a synthetically generated shadow on the flashbar irrespective of its position - top or bottom. By default the shadow is always shown.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .message("You can swipe the flashbar to dismiss it.")
        .castShadow(true, 4)
        .build()
```

### Vibration
The flashbar can produce a short vibration to seek the attention of the user when it is shown, dismissed or both.

```kotlin
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .message("You can swipe the flashbar to dismiss it.")
        .vibrateOn(Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS)
        .build()
```

# Roadmap
These are some of the prioritized features in the pipeline awaiting to be implemented in the near future - 

- [ ] Add coordinator layout support
- [ ] Add flashbar manager for queue management
- [ ] Add custom layout inflation support
- [ ] Improve shadow rendering

# Contribution

I highly encourage the community to step forward and improve this library further. You can fix any reported bug, propose or implement new features, write tests, etc.

Here is a quick list of things to remember -
* Check the open issues before creating a new one,
* Help me in reducing the number of open issues by fixing any existing bugs,
* Check the roadmap to see if you can help in implementing any new feature,
* You can contribute by writing unit and integration tests for this library,
* If you have any new idea that aligns with the goal of this library, feel free to raise a feature request and discuss it.

# About The Author

### Aritra Roy

Design-focussed Engineer. Full-stack Developer. Hardcore Android Geek. UI/UX Designer. Part-time Blogger.

<a href="https://play.google.com/store/apps/details?id=com.codexapps.andrognito&hl=en" target="_blank"><img src="https://github.com/aritraroy/social-icons/blob/master/play-store-icon.png?raw=true" width="60"></a> <a href="https://blog.aritraroy.in/" target="_blank"><img src="https://github.com/aritraroy/social-icons/blob/master/medium-icon.png?raw=true" width="60"></a>
<a href="http://stackoverflow.com/users/2858654/aritra-roy" target="_blank"><img src="https://github.com/aritraroy/social-icons/blob/master/stackoverflow-icon.png?raw=true" width="60"></a>
<a href="https://twitter.com/aritraroy" target="_blank"><img src="https://github.com/aritraroy/social-icons/blob/master/twitter-icon.png?raw=true" width="60"></a>
<a href="http://linkedin.com/in/aritra-roy"><img src="https://github.com/aritraroy/social-icons/blob/master/linkedin-icon.png?raw=true" width="60"></a>


# License

```
Copyright 2016 aritraroy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.