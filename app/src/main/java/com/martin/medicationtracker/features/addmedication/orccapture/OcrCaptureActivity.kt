package com.martin.medicationtracker.features.addmedication.orccapture

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.snackbar.Snackbar
import com.martin.medicationtracker.R
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.databinding.ActivityOcrCaptureBinding
import com.martin.medicationtracker.features.addmedication.AddMedicationActivity
import com.martin.medicationtracker.utils.Constants
import kotlinx.android.synthetic.main.activity_ocr_capture.*
import java.io.IOException

class OcrCaptureActivity : BaseActivity() {

    companion object {
        private const val TAG = "OcrCaptureActivity"
        private const val RC_HANDLE_GMS = 9001
        private const val RC_HANDLE_CAMERA_PERM = 2
    }

    private lateinit var binding: ActivityOcrCaptureBinding

    private var cameraSource: CameraSource? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var gestureDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
        setupEvents()
    }

    private fun setupBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ocr_capture)
        binding.lifecycleOwner = this
    }

    private fun initViews() {
        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource()
        } else {
            requestCameraPermission()
        }

        gestureDetector = GestureDetector(this, CaptureGestureListener())
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

        Snackbar.make(vGraphicOverlay, "Tap to capture", Snackbar.LENGTH_LONG).show()
    }

    private fun setupEvents() {
        btnSkip.setOnClickListener {
            openAddMedicationScreen("")
        }
    }

    private fun requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission")
        val permissions = arrayOf(Manifest.permission.CAMERA)

        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            )) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
            return
        }

        val thisActivity = this

        val listener = View.OnClickListener {
            ActivityCompat.requestPermissions(
                thisActivity, permissions,
                RC_HANDLE_CAMERA_PERM
            )
        }

        Snackbar.make(
            vGraphicOverlay, R.string.permission_camera_rationale,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.ok, listener)
            .show()
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        val b = scaleGestureDetector?.onTouchEvent(e) ?: false

        val c = gestureDetector?.onTouchEvent(e) ?: false

        return b || c || super.onTouchEvent(e)
    }

    @SuppressLint("InlinedApi")
    private fun createCameraSource() {
        val context = applicationContext

        val textRecognizer = TextRecognizer.Builder(context).build()
        textRecognizer.setProcessor(OcrDetectorProcessor(vGraphicOverlay))

        if (!textRecognizer.isOperational) {
            val lowStorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowStorageFilter) != null

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w(TAG, getString(R.string.low_storage_error))
            }
        }

        cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1280, 1024)
            .setRequestedFps(2.0f)
            .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
            .build()
    }

    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    override fun onPause() {
        super.onPause()
        if (vPreview != null) {
            vPreview.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (vPreview != null) {
            vPreview.release()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            createCameraSource()
            return
        }

        val listener = DialogInterface.OnClickListener { dialog, id -> finish() }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("MultiTracker sample")
            .setMessage(R.string.no_camera_permission)
            .setPositiveButton(R.string.ok, listener)
            .show()
    }

    @Throws(SecurityException::class)
    private fun startCameraSource() {
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
            applicationContext
        )
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }

        if (cameraSource != null) {
            try {
                vPreview.start(cameraSource!!, vGraphicOverlay)
            } catch (e: IOException) {
                Log.e(TAG, "Unable to start camera source.", e)
                cameraSource?.release()
                cameraSource = null
            }

        }
    }

    private fun onTap(rawX: Float, rawY: Float): Boolean {
        val graphic = vGraphicOverlay.getGraphicAtLocation(rawX, rawY) as OcrGraphic<*>?
        var text: TextBlock? = null
        if (graphic != null) {
            text = graphic.textBlock
            if (text != null && text.value != null) {
                openAddMedicationScreen(text.value)
            } else {
                Log.d(TAG, "text data is null")
            }
        } else {
            Log.d(TAG, "no text detected")
        }
        return text != null
    }

    private fun openAddMedicationScreen(value: String?) {
        val intent = Intent(this, AddMedicationActivity::class.java)
        intent.putExtra(Constants.INTENT_OCR_TEXT, value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private inner class CaptureGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return onTap(e.rawX, e.rawY) || super.onSingleTapConfirmed(e)
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.OnScaleGestureListener {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {}
    }
}