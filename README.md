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
 

#### 2.3 PhotoPickerImageView

布局文件

```xml
    <com.dong.circleimageview.widget.PhotoPickerImageView
        android:id="@+id/photopickerimageview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
代码文件
```java
    private void initPhotoPickerImageView(Bitmap bitmap) {
        photoPickerImageView
//                .setFilePath(FILE_PATH)//设置头像源图片路径（本地文件路径）
                .setBitmap(bitmap)////设置头像源Bitmap,此处为相册选择返回的bitmap
                .setRadius((int) getResources().getDimension(R.dimen.x100))//设置头像选择器半径/边长
                .setMaskID(R.drawable.photo_mask_img)//设置遮罩层
                .setShape(PhotoPickerImageView.CICLE_IMG)//设置遮罩层形状，CICLE_IMG圆形,SQUARE_IMG正方形
                .setMaxScale(3)//设置图片最大放大倍数，默认为4
                .setMinScale(0.6f);//设置图片最小缩小倍数，默认为0.5
    }
```
```java
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.reset:
                photoPickerImageView.reset();//截取效果还原
                break;
            case R.id.commit:
                Bitmap bitmap = photoPickerImageView.saveBitmap(true);//截取头像（return截取的bitmap， param1是否保存到本地）
                break;
        }
    }
```
效果：

 ![image](https://github.com/Eric3023/CircleImageView/blob/master/app/screenshoot/1.gif)
 
 
