package company.greatapp.moneycircle.view;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

import company.greatapp.moneycircle.R;


public class CircleButton extends Button {

 private static final int PRESSED_COLOR_LIGHTUP = 255 / 25;
 private static final int PRESSED_RING_ALPHA = 75;
 private static final int DEFAULT_PRESSED_RING_WIDTH_DIP = 7;
 private static final int ANIMATION_TIME_ID = android.R.integer.config_shortAnimTime;

 private int centerY;
 private int centerX;
 private int outerRadius;
 private int pressedRingRadius;

 private Paint circlePaint;
 private Paint focusPaint;
 private Paint textPaint;
 private float animationProgress;

 //Ashish
 private float left;
 private float top;
 private float right;
 private float bottom;

 private int pressedRingWidth;
 private int defaultColor = Color.BLACK;
 private int pressedColor;
 private ObjectAnimator pressedAnimator;
 private boolean isHolo;
 public CircleButton(Context context) {
 super(context);
 init(context, null);
 }

 public CircleButton(Context context, AttributeSet attrs) {
 super(context, attrs);
 init(context, attrs);
 }

 public CircleButton(Context context, AttributeSet attrs, int defStyle) {
 super(context, attrs, defStyle);
 init(context, attrs);
 }

 @Override
 public void setPressed(boolean pressed) {
 super.setPressed(pressed);

 if (circlePaint != null) {
  if(isHolo) {
     circlePaint.setColor(Color.TRANSPARENT);
  } else {
     circlePaint.setColor(pressed ? pressedColor : defaultColor);
  }
 }

 if (pressed) {
 showPressedRing();
 } else {
 hidePressedRing();
 }
 }

 @Override
 protected void onDraw(Canvas canvas) {
 canvas.drawCircle(centerX, centerY, pressedRingRadius + animationProgress, focusPaint);
  if(!isHolo()) {
     canvas.drawCircle(centerX, centerY, outerRadius - pressedRingWidth, circlePaint);
  }
  //canvas.drawText("INCOME X Y Z jhd dhjdhjhddj ", centerX, centerY, textPaint);



  super.onDraw(canvas);
 }

 @Override
 protected void onSizeChanged(int w, int h, int oldw, int oldh) {
  super.onSizeChanged(w, h, oldw, oldh);
 centerX = w / 2;
 centerY = h / 2;
 outerRadius = Math.max(w, h) / 2;
  setHeight(getWidth());
 pressedRingRadius = outerRadius - pressedRingWidth - pressedRingWidth / 2;
 }

 public float getAnimationProgress() {
 return animationProgress;
 }

 public void setAnimationProgress(float animationProgress) {
 this.animationProgress = animationProgress;
 this.invalidate();
 }

 public void setColor(int color) {
 this.defaultColor = color;
 this.pressedColor = getHighlightColor(color, PRESSED_COLOR_LIGHTUP);

 circlePaint.setColor(defaultColor);
 focusPaint.setColor(defaultColor);
 focusPaint.setAlpha(PRESSED_RING_ALPHA);

 this.invalidate();
 }

 private void hidePressedRing() {
 pressedAnimator.setFloatValues(pressedRingWidth, 0f);
 pressedAnimator.start();
 }

 private void showPressedRing() {
   if(isClickable()) {
     pressedAnimator.setFloatValues(animationProgress, pressedRingWidth);
     pressedAnimator.start();
   }
 }

 private void init(Context context, AttributeSet attrs) {
  this.setTextColor(Color.WHITE);
 this.setFocusable(true);
// this.setScaleType(ScaleType.CENTER_INSIDE);
 setClickable(true);

 circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
 circlePaint.setStyle(Paint.Style.FILL);



 focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
 focusPaint.setStyle(Paint.Style.STROKE);

 pressedRingWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PRESSED_RING_WIDTH_DIP, getResources()
 .getDisplayMetrics());

 int color = Color.GRAY;
 if (attrs != null) {
 final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
 color = a.getColor(R.styleable.CircleButton_cb_color, color);
 pressedRingWidth = (int) a.getDimension(R.styleable.CircleButton_cb_pressedRingWidth, pressedRingWidth);
 a.recycle();
 }

 setColor(color);

  setHighlightColor(Color.TRANSPARENT);
  setBackgroundColor(Color.TRANSPARENT);

 focusPaint.setStrokeWidth(pressedRingWidth);
 final int pressedAnimationTime = getResources().getInteger(ANIMATION_TIME_ID);
 pressedAnimator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, 0f);
 pressedAnimator.setDuration(pressedAnimationTime);
 }

 private int getHighlightColor(int color, int amount) {
 return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
 Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount));
 }

 public void setHolo(boolean isHolo) {
  this.isHolo = isHolo;
  circlePaint.setColor(Color.TRANSPARENT);
 }

 public boolean isHolo() {
  return this.isHolo;
 }
 }
