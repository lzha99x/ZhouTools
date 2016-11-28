# ZhouTools(常用的一些自定义控件)

This is a good android tools library.

博客地址：http://blog.csdn.net/u012301841

QQ：645521797

## 密码框
```
	<com.geek.widget.PasswordInputBox
        android:layout_width="match_parent"
        android:layout_height="50dp"
        nsz:boxPadding="5dp"
        nsz:boxType="fill"
        nsz:roundBox="true"
        nsz:defaultBoxColor="#cccccc"
        nsz:selectBoxColor="#ff7200"
        nsz:strokeColor="#969696" />
		
	 //	属性介绍
	 // nsz:boxSize 密码框个数,默认为6个
	 // nsz:textColor 文字颜色
	 // nsz:textSize 文字大小
	 // nsz:roundBox 是否为圆角密码框,默认为直角
	 // nsz:shownsz 输入的文字是否显示,默认不显示
	 // nsz:boxPadding 密码框之间的间距,默认为0
	 // nsz:boxRadius 圆角的大小,默认为10
	 
	 // nsz:boxType 密码框类型 fill:填充密码框 stroke：空心的密码框
	 // nsz:defaultBoxColor 默认密码框的颜色。只在boxType为fill生效
	 // nsz:selectBoxColor 选中密码框的颜色。只在boxType为fill生效
	 // nsz:strokeColor 边框的颜色。只在boxType为stroke生效
	 // nsz:strokeWidth 边框的宽度。只在boxType为stroke生效
```
![image](https://github.com/chuwuwang/ZhouTools/blob/master/Screenshots/201611281022.png)
![image](https://github.com/chuwuwang/ZhouTools/blob/master/Screenshots/201611281023.png)

## 圆形ImageView(可带边框)和圆角矩形ImageView
```
	<com.geek.widget.RoundImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/src"
        nsz:borderWidth="1.5"
        nsz:roundType="circle" />
		
	 //	属性介绍
	 // nsz:roundType 圆形类型 rect：圆角矩形 circle：圆形
	 //	nsz:radius 圆角大小。只在roundType为rect生效
     // nsz:borderColor 边框颜色
	 // nsz:borderWidth 边框宽度
```
![image](https://github.com/chuwuwang/ZhouTools/blob/master/Screenshots/201611262350.png)
![image](https://github.com/chuwuwang/ZhouTools/blob/master/Screenshots/201611262351.png)

## Android Studio 引用
```

compile 'com.knife:tools:1.2

// 这个l依赖地址有时忘记更新了，但我一定会上传到jcenter上，只要修改下版本号即可。
// jcenter访问地址：https://jcenter.bintray.com/com/knife/
```
