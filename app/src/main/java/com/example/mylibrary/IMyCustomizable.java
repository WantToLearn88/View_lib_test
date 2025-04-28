package com.example.mylibrary;

/**
 * Interface for customizing view properties programmatically.
 */
public interface IMyCustomizable {
    void setLabelText(String text);
    void setBgColor(int color);
    void setBorderColor(int color);
    void setTextSizeCustom(float size);
    void playAnimation();
    void setFontStyle(int style);

}