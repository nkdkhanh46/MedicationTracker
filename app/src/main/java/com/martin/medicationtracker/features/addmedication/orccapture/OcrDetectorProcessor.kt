package com.martin.medicationtracker.features.addmedication.orccapture

import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock

class OcrDetectorProcessor<T: GraphicOverlay.Graphic>(private val graphicOverlay: GraphicOverlay<T>) :
    Detector.Processor<TextBlock> {

    override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
        graphicOverlay.clear()
        val items = detections.detectedItems
        for (i in 0 until items.size()) {
            val item = items.valueAt(i)
            val graphic = OcrGraphic(graphicOverlay, item) as T
            graphicOverlay.add(graphic)
        }
    }

    override fun release() {
        graphicOverlay.clear()
    }
}