package com.example.mylibrary;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * A customizable view that displays centered text with background color, border, and animation on
 * click. All attributes can be controlled from XML or programmatically.
 */
public class MyCustomView extends View implements IMyCustomizable {

  private Paint paint, textPaint, borderPaint;
  private String labelText = "Hello!";
  private int bgColor = Color.LTGRAY;
  private int borderColor = Color.BLACK;
  private float textSize = 40f;
  private int fontStyle = Typeface.NORMAL;

  public MyCustomView(Context context) {
    super(context);
    init(null);
  }

  public MyCustomView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    borderPaint.setStyle(Paint.Style.STROKE);
    borderPaint.setStrokeWidth(6);
    textPaint.setTextAlign(Paint.Align.CENTER);

    

    if (attrs != null) {
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView);
    fontStyle = a.getInt(R.styleable.MyCustomView_fontStyle, Typeface.NORMAL);
    textPaint.setTypeface(Typeface.defaultFromStyle(fontStyle));

    labelText = a.getString(R.styleable.MyCustomView_labelText);
    if (labelText == null || labelText.trim().isEmpty()) {
        labelText = "Hello!";
    }
    textSize = a.getDimension(R.styleable.MyCustomView_textSize, 40f);
    bgColor = a.getColor(R.styleable.MyCustomView_bgColor, Color.LTGRAY);
    borderColor = a.getColor(R.styleable.MyCustomView_borderColor, Color.BLACK);
    fontStyle = a.getInt(R.styleable.MyCustomView_fontStyle, Typeface.NORMAL); // هنا مكانه الصحيح

    a.recycle();
}


    paint.setColor(bgColor);
    textPaint.setColor(Color.WHITE);
    textPaint.setTextSize(textSize);
    borderPaint.setColor(borderColor);

    setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            animateClick();
          }
        });
  }

  private void animateClick() {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.1f, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.1f, 1f);
    scaleX.setInterpolator(new OvershootInterpolator());
    scaleY.setInterpolator(new OvershootInterpolator());
    scaleX.setDuration(300);
    scaleY.setDuration(300);
    scaleX.start();
    scaleY.start();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    float x = getWidth() / 2f;
    float y = getHeight() / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f);
    canvas.drawText(labelText, x, y, textPaint);
    canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint);
  }

  /** Sets the text to be displayed in the center of the view. */
  @Override
  public void setLabelText(String txt) {
    if (txt != null && !txt.trim().isEmpty()) {
      this.labelText = txt;
      invalidate();
    }
  }

  /** Sets the background color of the view. */
  @Override
  public void setBgColor(int color) {
    this.bgColor = color;
    paint.setColor(color);
    invalidate();
  }

  /** Sets the border color of the view. */
  @Override
  public void setBorderColor(int color) {
    this.borderColor = color;
    borderPaint.setColor(color);
    invalidate();
  }

  /** Sets the size of the text displayed in the view. */
  @Override
  public void setTextSizeCustom(float size) {
    this.textSize = size;
    textPaint.setTextSize(size);
    invalidate();
  }

  /** Triggers the animation manually. */
  @Override
  public void playAnimation() {
    animateClick();
  }

  /** Sets the font style (NORMAL, BOLD, ITALIC, BOLD_ITALIC) */
  @Override
  public void setFontStyle(int style) {
    this.fontStyle = style;
    textPaint.setTypeface(Typeface.defaultFromStyle(style));
    invalidate();
  }

  /** Get the current font style. */
  public int getFontStyle() {
    return fontStyle;
  }
}
