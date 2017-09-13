package com.baidou.myswitch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MySwitch extends View {

	private Paint mPaint;
	private Bitmap mBitmapBg;
	private Bitmap mBitmapSlide;
	private int mSlideLeft;
	private int maxLeft;
	private boolean isOn = false;//开关状态,默认是关闭

	public MySwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initVeiw();
	}

	public MySwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		initVeiw();
	}

	public MySwitch(Context context) {
		super(context);
		initVeiw();
	}

	public void initVeiw() {
		mPaint = new Paint();// 创建画笔
		// 设置画笔颜色
		mPaint.setColor(Color.RED);
		// 初始化滑块背景图片
		mBitmapBg = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_background);
		// 初始化滑块图片
		mBitmapSlide = BitmapFactory.decodeResource(getResources(),
				R.drawable.slide_button);
		// 计算最大左边距
		maxLeft = mBitmapBg.getWidth() - mBitmapSlide.getWidth();
		// 设置点击事件
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击的时候,左右切换
				if (isOn) {
					System.out.println("关灯");
					mSlideLeft = 0;
					isOn = false;
				} else {
					System.out.println("开灯");
					mSlideLeft = maxLeft;
					isOn = true;
				}
				invalidate();
				// 通知回调
				if(mListener!=null){
					mListener.onStateChanged(MySwitch.this,isOn);
				}
			}
		});
		
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mBitmapBg.getWidth(), mBitmapBg.getHeight());// 设置图片宽高的大小.
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// canvas.drawRect(0, 0, 30, 30, mPaint);
		canvas.drawBitmap(mBitmapBg, 0, 0, mPaint);// 画开关背景
		canvas.drawBitmap(mBitmapSlide, mSlideLeft, 0, mPaint);// 画开关按钮

	}

	int startX;
	int moveX;
	private boolean isClick;

	// 重写触摸监听方法 onTpuchEvent
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("按钮被点击了");
			startX = (int) event.getX();// 记录下起点坐标
			break;
		case MotionEvent.ACTION_MOVE:
			// System.out.println("按钮被移动了");
			int newX = (int) event.getX();// 结束时的x坐标
			int dx = newX - startX;// 计算偏移量
			moveX += Math.abs(dx);
			mSlideLeft += dx;
			// 避免超出边界 左边界
			if (mSlideLeft <= 0) {
				mSlideLeft = 0;
			}
			// 避免右边界越界
			if (mSlideLeft > maxLeft) {
				mSlideLeft = maxLeft;
			}
			// 重绘
			invalidate();
			// 5. 重新初始化起点坐标
			startX = (int) event.getX();
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("停止移动");
			// 判断是点击还是移动
			if (moveX > 5) {
				isClick = false;// 移动的像素点大于5判断为移动
			} else {
				isClick = true;
			}
			moveX = 0;
			if (!isClick) {
				// 根据当前滑块的位置决定最红的滑块状态
				if (mSlideLeft < maxLeft / 2) {
					mSlideLeft = 0;
					isOn = true;
				} else {
					mSlideLeft = maxLeft;
					isOn = false;
				}
				invalidate();
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private SwitchStateChangedListener mListener;

	public void setOnSwichStateChangedListener(
			SwitchStateChangedListener listener) {
		mListener = listener;
	}

	// 定义一个借口,设置回调监听
	public interface SwitchStateChangedListener {
		public void onStateChanged(View view,boolean isOn);
	}

}
