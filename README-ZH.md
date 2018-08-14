
# ClcokView
一个可爱的时钟

<br/>

[![Api reqeust](https://img.shields.io/badge/api-11+-green.svg)](https://github.com/samlss/ClcokView)  [![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/ClcokView/blob/master/LICENSE) [![Blog](https://img.shields.io/badge/samlss-blog-orange.svg)](https://blog.csdn.net/Samlss)

### 平常情况下效果:
![gif1](https://github.com/samlss/ClcokView/blob/master/screenshots/screenshot1.gif)

### 闹钟响铃效果:
![gif2](https://github.com/samlss/ClcokView/blob/master/screenshots/screenshot2.gif)


### 设置自定义颜色时的效果:
![gif4](https://github.com/samlss/ClcokView/blob/master/screenshots/screenshot4.gif)


## Use<br>
在根目录的build.gradle添加这一句代码：
```
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

在app目录下的build.gradle添加依赖使用：
```
dependencies {
    implementation 'com.github.samlss:ClcokView:1.0'
}
```

### 属性说明：

#### 开始属性说明之前，先看一张图
![picture](https://github.com/samlss/ClcokView/blob/master/screenshots/screenshot3.png)

| 属性        | 说明           |
| ------------- |:-------------:|
| ear_color      | 耳朵的颜色 |
| foot_color | 脚的颜色 |
| head_color | 头的颜色 |
| edge_color | 时钟边缘的颜色  |
| scale_color | 时钟刻度的颜色 |
| center_point_color | 时钟中心圆的颜色 |
| hour_hand_color | 时针颜色 |
| minute_hand_color |分针颜色 |
| second_hand_color | 秒针颜色 |

<br/>


### 布局中使用
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

### 代码中使用：
```
  mClockView.setTime(hour, minute, second); //将时钟与现实时间绑定
```

### 同时你可以使用 [ClockHelper.java](https://github.com/samlss/ClcokView/blob/master/library/src/main/java/com/iigo/library/ClockHelper.java) 将ClockView与现实时间联系并且测试闹钟响铃效果
```
  clockHelper = new ClockHelper(clockView); //创建 'ClockHelper' 对象.
  clockHelper.start(); //开始将'ClockView' 与现实时间绑定.
  clockHelper.stop(); //当你不需要使用'ClockView'的时候，调用该接口，例如在activity#onDestroy()中调用.
  clockHelper.goOff(); //测试响铃效果.
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
