# ClockView
A  lovely clock view.

### [中文](https://github.com/samlss/ClockView/blob/master/README-ZH.md)

<br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/ClockView)  [![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/ClockView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### Here's the normal effect:
![gif1](https://github.com/samlss/ClockView/blob/master/screenshots/screenshot1.gif)

### Here's the effect when the alarm go off:
![gif2](https://github.com/samlss/ClockView/blob/master/screenshots/screenshot2.gif)


### Here's the effect when using custom colors:
![gif4](https://github.com/samlss/ClockView/blob/master/screenshots/screenshot4.gif)


## Use<br>
Add it in your root build.gradle at the end of repositories：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Add it in your app build.gradle at the end of repositories:
```
dependencies {
    implementation 'com.github.samlss:ClockView:1.0'
}
```

### Attributes description：

#### Before we describe the attributes, let's look at a attributes piture firstly.
![picture](https://github.com/samlss/ClockView/blob/master/screenshots/screenshot3.png)

| attr        | description           |
| ------------- |:-------------:|
| ear_color      | The color of ears |
| foot_color | The color of foots |
| head_color | The color of head |
| edge_color | The color of edge  |
| scale_color | The color of the clock scale |
| center_point_color | The color of the clock center point |
| hour_hand_color | The color of hour hand |
| minute_hand_color |The color of minute hand |
| second_hand_color | The color of second hand |

<br/>


### in layout.xml
```
<com.iigo.library.ClockView
       android:id="@+id/clockView"
       app:minute_hand_color="@color/colorPrimary"
       app:hour_hand_color="@color/colorPrimary"
       app:center_point_color="@color/colorPrimary"
       app:ear_color="@color/colorPrimary"
       app:edge_color="@color/colorPrimary"
       app:foot_color="@color/colorPrimary"
       app:head_color="@color/colorPrimary"
       app:scale_color="@color/colorPrimary"
       app:second_hand_color="@color/colorPrimary"
       android:layout_centerInParent="true"
       android:layout_width="150dp"
       android:layout_height="150dp" />
```

### Used in the code， for example in an Activity:
```
  mClockView.setTime(hour, minute, second); //Bind the clock to real time.
```

### You can use the [ClockHelper.java](https://github.com/samlss/ClcokView/blob/master/library/src/main/java/com/iigo/library/ClockHelper.java) to connect with the real time, and test the go off effect.
```
  clockHelper = new ClockHelper(clockView); //create a 'ClockHelper' obj.
  clockHelper.start(); //Start to bind 'ClockView' to the real time.
  clockHelper.stop(); //Call this method when you don need to show 'ClockView', may in Activity.onDestroy().
  clockHelper.goOff(); //test the effect of alarm go off.
```



### License

```
Copyright 2018 samlss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
