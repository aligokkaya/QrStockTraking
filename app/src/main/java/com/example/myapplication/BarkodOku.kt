package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.example.myapplication.DBHelper.DBHelper
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_main.*

class BarkodOku : AppCompatActivity() {

    private val requestCodeCameraPermission =1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        git.isEnabled = false
        git.isClickable=false
        if(ContextCompat.checkSelfPermission(
                this@BarkodOku,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            ){
            askForCameraPermission()
        }
        else
        {
            setupControls()
        }
    }
    private fun setupControls()
    {
      detector = BarcodeDetector.Builder(this@BarkodOku).build()
        cameraSource = CameraSource.Builder(this@BarkodOku,detector).setAutoFocusEnabled(true).build()
        cameraSurfaceView.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
    }
    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(
            this@BarkodOku,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
            )
    }
    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==requestCodeCameraPermission && grantResults.isNotEmpty())
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                setupControls()
            }
            else
            {
                Toast.makeText(applicationContext,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val surfaceCallBack = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        }
        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            cameraSource.stop()
        }
        override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
            try {
                cameraSource.start(surfaceHolder)
            }catch (exception: Exception)
            {
                Toast.makeText(applicationContext, "something went wrong",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val processor = object : Detector.Processor<Barcode>{
        override fun release() {
        }
        override fun receiveDetections(p0: Detector.Detections<Barcode>?) {
            if(p0 != null && p0.detectedItems.isNotEmpty())
            {
           val qrCodes: SparseArray<Barcode> =p0.detectedItems
           val code =qrCodes.valueAt(0)
            textScanResult.text=code.displayValue
                val barko=textScanResult.text.toString()
                val db = DBHelper(this@BarkodOku,null,null,1)
                val cont=db.getOneName(barko)

                if(cont?.barcode.toString() == barko)
                {
                    textScanResult.text="Kayıtlı ürün"
                    Toast.makeText(applicationContext, "Kayıtlı Ürün", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    if (textScanResult.text.length>12 )
                    {
                        git.isEnabled = true
                        git.isClickable=true
                        git.setOnClickListener {
                            val intent = Intent(applicationContext, Kayit::class.java)
                            intent.putExtra("barcode", textScanResult.text.toString())
                            startActivity(intent)
                            textScanResult.text=""
                            git.isEnabled = false
                            git.isClickable=false

                        }
                    }
                }
        }
    }
}
}

