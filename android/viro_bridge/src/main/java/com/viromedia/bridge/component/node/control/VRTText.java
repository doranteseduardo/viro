/**
 * Copyright © 2016 Viro Media. All rights reserved.
 */
package com.viromedia.bridge.component.node.control;

import android.graphics.Color;

import com.facebook.react.bridge.ReactApplicationContext;
import com.viro.renderer.jni.RenderContext;
import com.viro.renderer.jni.Text;

public class VRTText extends VRTControl {
    static final String DEFAULT_FONT = "Roboto";
    static final float DEFAULT_WIDTH = 1f;
    static final float DEFAULT_HEIGHT = 1f;
    static final long DEFAULT_COLOR = Color.WHITE;
    static final int DEFAULT_MAX_LINES = 0;
    static final int DEFAULT_FONT_SIZE = 14;

    private Text mNativeText = null;
    private long mColor = DEFAULT_COLOR;
    private int mMaxLines = DEFAULT_MAX_LINES;
    private float mWidth = DEFAULT_WIDTH;
    private float mHeight = DEFAULT_HEIGHT;
    private String mText;
    private String mFontFamilyName = DEFAULT_FONT;
    private int mSize = DEFAULT_FONT_SIZE;
    private String mHorizontalAlignment = "Left";
    private String mVerticalAlignment = "Top";
    private String mTextLineBreakMode = "WordWrap";
    private String mTextClipMode = "ClipToBounds";
    public VRTText(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public void onTearDown() {
        if (mNativeText != null) {
            mNativeText.destroy();
            mNativeText = null;
        }

        super.onTearDown();
    }
    public void setWidth(float width) {
        mWidth = width;
    }

    public void setHeight(float height) {
        mHeight = height;
    }

    public void setColor(long color) {
        mColor = color;
    }

    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setFontFamilyName(String fontFamilyName) {
        if (fontFamilyName == null || fontFamilyName.trim().isEmpty()) {
            return;
        }
        mFontFamilyName = fontFamilyName;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public void setHorizontalAlignment(String horizontalAlignment) {
        mHorizontalAlignment = horizontalAlignment;
    }

    public void setVerticalAlignment(String verticalAlignment) {
        mVerticalAlignment = verticalAlignment;
    }

    public void setTextClipMode(String textClipMode) {
        mTextClipMode = textClipMode;
    }

    public void setTextLineBreakMode(String textLineBreakMode) {
        mTextLineBreakMode = textLineBreakMode;
    }

    private void updateLabel() {
        if (mRenderContext == null || isTornDown() || mText == null) {
            return;
        }

        if (mNativeText != null) {
            mNativeText.destroy();
            mNativeText = null;
        }

        // Create text
        mNativeText = new Text(mRenderContext, mText, mFontFamilyName, mSize, mColor, mWidth,
                mHeight, mHorizontalAlignment, mVerticalAlignment, mTextLineBreakMode,
                mTextClipMode, mMaxLines);

        // Add geometry
        getNodeJni().setGeometry(mNativeText);
    }

    @Override
    public void setRenderContext(RenderContext context) {
        super.setRenderContext(context);
        updateLabel();
    }

    @Override
    public void onPropsSet() {
        super.onPropsSet();
        updateLabel();
    }
}