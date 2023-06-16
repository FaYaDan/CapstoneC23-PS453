package com.bangkit2023.c23ps453.ui.measuringCam

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.bangkit2023.c23ps453.R
import com.bangkit2023.c23ps453.databinding.ActivityMeasuringCamBinding
import com.bangkit2023.c23ps453.ui.AppActivity
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat

class MeasuringCamActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var binding: ActivityMeasuringCamBinding

    private var mRgba: Mat? = null
    private var mGrey: Mat? = null
    private var mOpenCVCamera: CameraBridgeViewBase? = null
    private val mLoaderCallBack: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    run {
                        Log.i(TAG, "opencv is loaded")
                        mOpenCVCamera!!.enableView()
                    }
                    run { super.onManagerConnected(status) }
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mOpenCVCamera!!.setCameraPermissionGranted()
                } else {
                    Toast.makeText(this, "Tidak mendapatkan permission.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                return
            }
        }
    }

    companion object{
        const val TAG = "MeasuringCamActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasuringCamBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        ActivityCompat.requestPermissions(
            this@MeasuringCamActivity,
            arrayOf<String>(Manifest.permission.CAMERA),
            1
        )
        setContentView(binding.root)
        mOpenCVCamera = findViewById<View>(R.id.frame_surface) as CameraBridgeViewBase
        mOpenCVCamera!!.visibility = SurfaceView.VISIBLE
        mOpenCVCamera!!.setCvCameraViewListener(this)

        binding.buttonback.setOnClickListener {
            val intent = Intent(this, AppActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@MeasuringCamActivity.finish()
        }

    }

    override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallBack)
        }
    }

    override fun onPause() {
        super.onPause()
        if (mOpenCVCamera != null) {
            mOpenCVCamera!!.disableView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOpenCVCamera != null) {
            mOpenCVCamera!!.disableView()
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mRgba = Mat(height, width, CvType.CV_8UC4)
        mGrey = Mat(height, width, CvType.CV_8UC1)
    }

    override fun onCameraViewStopped() {
        mRgba!!.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat? {
        mRgba = inputFrame.rgba()
        mGrey = inputFrame.gray()
        return mRgba
    }
}