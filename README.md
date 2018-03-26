# CircleImageView
### 使用手册


github地址：https://github.com/Eric3023/CircleImageView
### 1.工程的build.gradle文件中添加依赖  
allprojects {

    repositories {  
        maven { url 'https://jitpack.io' }  
    }
}


dependencies {

    compile 'com.github.Eric3023:CircleImageView:v1.1.0'
    
}

### 2.控件使用
#### 2.1 CircleImageView

布局文件中添加CircleImageView控件：

```xml
    <com.dong.circleimageview.widget.CircleImageView
        android:id="@+id/cicleimageview"
        android:layout_width="@dimen/x160"
        android:layout_height="@dimen/y160"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

java代码中设置控件的属性,根据需要设置部分属性，不用全部设置

```java
//配置LoopViewPager参数
        CircleImageView circleImageView = findViewById(R.id.cicleimageview);
        circleImageView
                .setResourceID(R.drawable.img0)//圆形图片源为本地资源文件时
                .setPath(UrlConfige.IMG_URL)//圆形图片源为网络图片
                .setEdge(true)//设置是否显示边缘圆环
                .setEdgeColor(Color.WHITE)//设置边缘颜色
                .setEdgeWidth((int) getResources().getDimension(R.dimen.x3));//设置边缘宽度
```


##### 效果：
 ![image](https://github.com/Eric3023/CircleImageView/blob/master/app/screenshoot/1.png)

#### 2.2 RoundImageView
布局文件中添加RoundImageView控件：

```xml
    <com.dong.circleimageview.widget.RoundImageView
        android:id="@+id/roundimageview"
        android:layout_width="@dimen/x280"
        android:layout_height="@dimen/y120"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

java代码中设置控件的属性

```java
        RoundImageView roundImageView =findViewById(R.id.roundimageview);
        roundImageView
                .setResourceID(R.drawable.img0)//圆角图片源为资源图片时
                .setPath(UrlConfige.IMG_URL)//圆角图片源为网咯图片
                .setRadius((int) getResources().getDimension(R.dimen.x10))//圆角弧度
                .setImageScale(RoundImageView.CENTER_CROP)//填充类型
                .setBackgroudColor(Color.WHITE);//圆角图片的背景颜色
```
##### 效果：


 ![image](https://github.com/Eric3023/CircleImageView/blob/master/app/screenshoot/3.png)
 

#### 2.3 GalleryViewPager

布局文件

```xml
<com.dong.pointviewpager.widget.GalleryViewPager
    android:id="@+id/galleryviewpager"
    android:layout_width="match_parent"
    android:layout_height="@dimen/y120"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintBottom_toBottomOf="parent">
</com.dong.pointviewpager.widget.GalleryViewPager>
```
代码文件
```java
GalleryViewPager galleryViewPager = findViewById(R.id.galleryviewpager);
LoopViewPager loopViewPager = galleryViewPager.getLoopViewPager();

//设置Gallery中LoopViewPager的参数(方法同上2.1， LoopViewPager参数设置)
initLoopViewPager(loopViewPager);
//设置Gallery中其他参数
initGalleryViewPager(galleryViewPager);
```
```java
    private void initGalleryViewPager(GalleryViewPager galleryViewPager) {
        galleryViewPager.setPageWidth((int) getResources().getDimension(R.dimen.x280))//设置ViewPager的宽度，适当小于GalleryViewPager的宽度
                .setPageHeight(RelativeLayout.LayoutParams.MATCH_PARENT)//设置ViewPager的高度
                .setPageScale((float) 0.8)//设置两侧隐藏页面的缩放比例
                .setPageAlpha((float) 0.5)//设置两侧隐藏页面的透明度
                .initialise();
    }
```
效果：

 ![image](https://github.com/Eric3023/PointViewPager/blob/master/app/screenshoot/3.gif)
 
 
 #### 2.4 PointGalleryViewPager
 布局文件
```xml
<com.dong.pointviewpager.widget.PointGalleryViewPager
    android:id="@+id/pointGalleryViewPager"
    android:layout_width="match_parent"
    android:layout_height="@dimen/y120"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```
代码文件
```java
//设置PointGallery中LoopViewPager的参数(方法同2.1)
initLoopViewPager(loopViewPager);
//设置PointGallery中PoitView的参数（方法同2.2）
initPointView(pointView);
//设置PointGallery中其他参数
initGalleryViewPager(pointGalleryViewPager);
```
```
private void initGalleryViewPager(PointGalleryViewPager galleryViewPager) {
   galleryViewPager.setPageWidth((int) getResources().getDimension(R.dimen.x280))//设置ViewPager的宽度，适当小于GalleryViewPager的宽度
                .setPageHeight(RelativeLayout.LayoutParams.MATCH_PARENT)//设置ViewPager的高度
                .setPageScale((float) 0.8)//设置两侧隐藏页面的缩放比例
                .setPageAlpha((float) 0.5)//设置两侧隐藏页面的透明度
                .initialise();
    }
```


效果：

 ![image](https://github.com/Eric3023/PointViewPager/blob/master/app/screenshoot/4.gif)
