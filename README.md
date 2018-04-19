# Flashbar
A highly customizable, powerful and easy-to-use alerting library for Android

### Specs
[![API](https://img.shields.io/badge/API-14%2B-orange.svg?style=flat)](https://android-arsenal.com/api?level=14) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) 


This library allows you to show messages or alerts in your app **quickly and easily**. It can be used as an alternative of Snackbar or Toast and offers a plethora of customization options for you to play with.

It has been written **100% in Kotlin**.

# Download

This library is available in **jCenter** which is the default Maven repository used in Android Studio. You can also import this library from source as a module.
 
```gradle
dependencies {
    // other dependencies here
    implementation 'com.andrognito.flashbar:flashbar:1.0.0'
}
```

### Spread Some :heart:
[![GitHub stars](https://img.shields.io/github/stars/aritraroy/PatternLockView.svg?style=social&label=Star)](https://github.com/aritraroy) [![GitHub followers](https://img.shields.io/github/followers/aritraroy.svg?style=social&label=Follow)](https://github.com/aritraroy)  [![Twitter Follow](https://img.shields.io/twitter/follow/aritraroy.svg?style=social)](https://twitter.com/aritraroy) 


# Usage
We recommend you to check the sample project to get a complete understanding of all the features offered by the library. You can find implementation for both Java and Kotlin.

The library offers a huge amount of customization options and leverages the `Builder` pattern for ease of use.

## Basic 

Flashbar attaches a full height, full width view (`FlashbarContainerView`) to the decor view of the `Activity` and places a full width, adjustable height view(`FlashbarView`) inside it. These classes are internal classes and are not exposed for public use.

Here's a basic example of showing a flashbar.

```
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message("This is a basic flashbar")
        .build()
```

## Gravity
You can show the flashbar either at the top or at the bottom of the screen using the gravity property. By default it is shown at the bottom.

```
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message("Flashbar is shown at the top")
        .build()
```
Or,

```
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .message("Flashbar is shown at the bottom")
        .build()
```

## Title
You can show an optional title in the flashbar. You can also customize the color, size, typeface and appearance of it.

```
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .title("Hello World!")
        .build();
```

You can change the color using `titleColor()`, size using `titleSizeInSp()`, `titleSizeInPx()`, typeface using `titleTypeface()` and appearance using `titleAppearance()`.

```
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
You can show an optional message in the flashbar. You can also customize the color, size and appearance of it.

```
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.BOTTOM)
        .message("This is a short message. But your message can be of any length and the view will dynamically adjust itself.")
        .build();
```

You can change the color using `messageColor()`, size using `messageSizeInSp()`, `messageSizeInPx()`, typeface using `messageTypeface()` and appearance using `messageAppearance()`.

```
Flashbar.Builder(this)
        .gravity(Flashbar.Gravity.TOP)
        .message("This is a short message")
        .messageColor(ContextCompat.getColor(this, R.color.white))
        .messageSizeInSp(16f)
        .messageTypeface(Typeface.createFromAsset(assets, "BeautifulAndOpenHearted.ttf"))
        .build()
```


