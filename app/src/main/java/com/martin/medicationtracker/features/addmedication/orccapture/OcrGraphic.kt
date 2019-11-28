package com.martin.medicationtracker.features.addmedication.orccapture

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.google.android.gms.vision.text.TextBlock

class OcrGraphic<T: GraphicOverlay.Graphic> constructor(overlay: GraphicOverlay<T>, val textBlock: TextBlock?) :
    GraphicOverlay.Graphic(overlay) {

    var id: Int = 0

    init {

        if (sRectPaint == null) {
            sRectPaint = Paint()
            sRectPaint!!.color = TEXT_COLOR
            sRectPaint!!.style = Paint.Style.STROKE
            sRectPaint!!.strokeWidth = 4.0f
        }

        if (sTextPaint == null) {
            sTextPaint = Paint()
            sTextPaint!!.color = TEXT_COLOR
            sTextPaint!!.textSize = 54.0f
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
    }

    override fun contains(x: Float, y: Float): Boolean {
        val text = textBlock ?: return false
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        return rect.left < x && rect.right > x && rect.top < y && rect.bottom > y
    }

    override fun draw(canvas: Canvas) {
        val text = textBlock ?: return

        // Draws the bounding box around the TextBlock.
        val rect = RectF(text.boundingBox)
        rect.left = translateX(rect.left)
        rect.top = translateY(rect.top)
        rect.right = translateX(rect.right)
        rect.bottom = translateY(rect.bottom)
        canvas.drawRect(rect, sRectPaint!!)

        // Break the text into multiple lines and draw each one according to its own bounding box.
        val textComponents = text.components
        for (currentText in textComponents) {
            val left = translateX(currentText.boundingBox.left*1f)
            val bottom = translateY(currentText.boundingBox.bottom*1f)
            canvas.drawText(currentText.value, left, bottom, sTextPaint!!)
        }
    }

    companion object {

        private const val TEXT_COLOR = Color.WHITE

        private var sRectPaint: Paint? = null
        private var sTextPaint: Paint? = null
    }
}