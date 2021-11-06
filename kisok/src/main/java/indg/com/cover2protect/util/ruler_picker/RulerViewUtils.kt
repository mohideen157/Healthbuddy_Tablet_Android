package indg.com.cover2protect.util.ruler_picker

import android.content.Context


internal object RulerViewUtils {

    /**
     * Convert SP to pixel.
     *
     * @param context Context.
     * @param spValue Value in sp to convert.
     *
     * @return Value in pixels.
     */
    fun sp2px(context: Context,
              spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}
