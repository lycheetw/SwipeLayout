# SwipeLayout


## Screenshots
![alt tag](https://raw.githubusercontent.com/lycheetw/SwipeLayout/master/images/screenshot.gif)

## Usage
#### layout
```xml
<tw.lychee.swipelayout.SwipeLayout
        android:id="@+id/swipeLayout"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        app:dragable="true">

        <FrameLayout
            android:id="@+id/surfaceView"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            
            <!--    The surface content    -->
            
        </FrameLayout>

        <LinearLayout
            android:id="@+id/behindView"
            android:layout_width="120dp"
            android:layout_height="match_parent"
            android:orientation="horizontal">

            <!--    The behind content, note: layout_width must be specified.    -->
            
        </LinearLayout>

    </tw.lychee.swipelayout.SwipeLayout>
```


You can find complete demo code [here](https://github.com/lycheetw/SwipeLayout/tree/master/app/src/main)
