package com.bangkit2023.c23ps453.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import org.opencv.android.*
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.core.CvType
import org.opencv.core.Mat


class TestActivity : Activity(), CameraBridgeViewBase.CvCameraViewListener2  {

    private val TAG = "TestActivity"
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        ActivityCompat.requestPermissions(
            this@TestActivity,
            arrayOf<String>(Manifest.permission.CAMERA),
            1
        )
        setContentView(com.bangkit2023.c23ps453.R.layout.activity_test)
        mOpenCVCamera = findViewById<View>(com.bangkit2023.c23ps453.R.id.frame_surface) as CameraBridgeViewBase
        mOpenCVCamera!!.visibility = SurfaceView.VISIBLE
        mOpenCVCamera!!.setCvCameraViewListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mOpenCVCamera!!.setCameraPermissionGranted()
                } else {
                    //permision denied
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "onResume")
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        } else {
            Log.i(TAG, "onResume Gagal")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallBack)
        }
    }

    override fun onPause() {
        super.onPause()
        if (mOpenCVCamera != null) {
            Log.i(TAG, "onPause")
            mOpenCVCamera!!.disableView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOpenCVCamera != null) {
            Log.i(TAG, "onDestroy")
            mOpenCVCamera!!.disableView()
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        Log.i(TAG, "onCameraViewStarted lebar = $width tinggi = $height")
        mRgba = Mat(height, width, CvType.CV_8UC4)
        mGrey = Mat(height, width, CvType.CV_8UC1,)

    }

    override fun onCameraViewStopped() {
        Log.i(TAG, "onCameraViewStopped ")
        mRgba!!.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat? {
        Log.i(TAG, "onCameraFrame ")
        mRgba = inputFrame.rgba()
        mGrey = inputFrame.gray()
        return mRgba
    }
}