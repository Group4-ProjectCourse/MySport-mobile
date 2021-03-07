package com.mysport.mysport_mobile.utils;

import android.graphics.RectF;

public class TouchEventUtils {
    /**
     * Returns true if (x,y) is inside the rectangle. The left and top are
     * considered to be inside, while the right and bottom are not. This means
     * that for a x,y to be contained: left <= x < right and top <= y < bottom.
     * An empty rectangle never contains any point.
     *
     * @param x The X coordinate of the point being tested for containment
     * @param y The Y coordinate of the point being tested for containment
     * @return true iff (x,y) are contained by the rectangle, where containment
     *              means left <= x < right and top <= y < bottom
     */
    public static boolean contains(float left, float top, float right, float bottom, float x, float y) {
        return left < right && top < bottom  // check for empty first
                && x >= left && x < right && y >= top && y < bottom;
    }

    public static boolean contains(RectF rect, float x, float y) {
        return contains(rect.left, rect.top, rect.right, rect.bottom, x, y);
    }
}
