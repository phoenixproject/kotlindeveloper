@file:Suppress("DEPRECATION")

package br.com.codelogic.getsensororientation

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import android.R.attr.gravity
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.view.animation.Animation
import android.view.animation.RotateAnimation

private const val PERMISSION_REQUEST = 10

class MainActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var color = false
    private var mAzimuth = 0 // degree

    // Gravity rotational data
    private var gravity: FloatArray? = null
    // Magnetic rotational data
    private var magnetic: FloatArray? = null //for magnetic rotational data
    private var accels : FloatArray ? = null
    private var mags : FloatArray ? = null
    private var values = FloatArray(3)

    // azimuth, pitch and roll
    private var azimuth: Float = 0.toFloat()
    private var pitch: Float = 0.toFloat()
    private var roll: Float = 0.toFloat()

    // record the compass picture angle turned
    private var currentDegree = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accels = FloatArray(3)
        mags = FloatArray(3)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onAccuracyChanged(s: Sensor?, i: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if( event!!.sensor.type == Sensor.TYPE_MAGNETIC_FIELD
            || event!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            getAzimuth3(event)
        }
    }

    fun lowPass(input: FloatArray, output: FloatArray ?) {

        val alpha = 0.05f

        for (i in input.indices) {
            output!![i] = output[i] + alpha * (input[i] - output[i])
        }
    }

    private fun getAzimuth4(event: SensorEvent) {

        // get the angle around the z-axis rotated
        val degree = Math.round(event.values[0])

        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
            currentDegree,
            -degree.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        // how long the animation will take place
        ra.duration = 210

        // set the animation after the end of the reservation status
        ra.fillAfter = true

        // Start the animation
        currentDegree = -degree.toFloat()

        tvAzimuth.text = "Z Value : ".plus(currentDegree.toString())
    }

    private fun getAzimuth3(event: SensorEvent) {

        when (event.sensor.type) {
            Sensor.TYPE_MAGNETIC_FIELD -> mags = event.values.clone()
            Sensor.TYPE_ACCELEROMETER -> accels = event.values.clone()
        }

        if (mags != null && accels != null) {
            gravity = FloatArray(9)
            magnetic = FloatArray(9)
            SensorManager.getRotationMatrix(gravity, magnetic, accels, mags)
            val outGravity = FloatArray(9)
            SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X, SensorManager.AXIS_Z, outGravity)
            SensorManager.getOrientation(outGravity, values)

            azimuth = values[0] * 57.2957795f
            pitch = values[1] * 57.2957795f
            roll = values[2] * 57.2957795f
            mags = null
            accels = null
        }

        tvAzimuth.text = "AzimutZ?: ".plus(azimuth.toString())
        tvXAxiz.text = "Pitch Value: ".plus(pitch)
        tvYAxis.text = "Roll Value: ".plus(roll)
    }

    private fun getAzimuth2(event: SensorEvent){

        var mData : FloatArray = FloatArray(3)
        val iMat : FloatArray = FloatArray(9)
        val gData : FloatArray = FloatArray(3)

        val rMat : FloatArray = FloatArray(9)
        val orientation : FloatArray = FloatArray(3)

        mData = event.values.clone()

        if (SensorManager.getRotationMatrix( rMat, iMat, gData, mData))
        {
            mAzimuth=  (( Math.toDegrees((SensorManager.getOrientation(rMat, orientation)[0]).toDouble()) + 360 ) % 360).toInt()

            tvAzimuth.text = "Z Value: ".plus(mAzimuth.toString())
        }
    }

    private fun getAzimuth(event: SensorEvent) {

        //var g  = DoubleArray(8)
        var g : ArrayList<Double>

        //Get Rotation Vector Sensor Values
        g = convertFloatsToDoubles(event.values.clone())
        //g = event.values.clone().toCollection(

        g.add(0.0)

        //Normalise
        //val norm = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2] + g[3] * g[3])
        val norm = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2] + g[3] * g[3])
        g[0] /= norm
        g[1] /= norm
        g[2] /= norm
        g[3] /= norm

        //Set values to commonly known quaternion letter representatives
        val x = g[0]
        val y = g[1]
        val z = g[2]
        val w = g[3]

        //Calculate Pitch in degrees (-180 to 180)
        val sinP = 2.0 * (w * x + y * z)
        val cosP = 1.0 - 2.0 * (x * x + y * y)
        val pitch = Math.atan2(sinP, cosP) * (180 / Math.PI)

        //Calculate Tilt in degrees (-90 to 90)
        val tilt: Double
        val sinT = 2.0 * (w * y - z * x)
        if (Math.abs(sinT) >= 1)
            tilt = Math.copySign(Math.PI / 2, sinT) * (180 / Math.PI)
        else
            tilt = Math.asin(sinT) * (180 / Math.PI)

        //Calculate Azimuth in degrees (0 to 360; 0 = North, 90 = East, 180 = South, 270 = West)
        val sinA = 2.0 * (w * z + x * y)
        val cosA = 1.0 - 2.0 * (y * y + z * z)
        val azimuth = Math.atan2(sinA, cosA) * (180 / Math.PI)

        tvAzimuth.text = "Z Value: ".plus(azimuth.toString())
    }

    private fun convertFloatsToDoubles(floatvalues : FloatArray) : ArrayList<Double>{

        //var doublevalues = DoubleArray(8)
        //var doublevalues : Array<Double> = doubleArrayOf()
        //val doublevalues = arrayOf
        var doublevalues = ArrayList<Double>()

        var iterator = floatvalues.iterator()
        iterator.forEach {

            val elementodouble : Double

            elementodouble = it.toDouble()

            //doublevalues.plus(elementodouble)
            doublevalues.add(elementodouble)
        }

        return doublevalues
    }

    private fun getAccelerometer(event: SensorEvent) {
        // Movement
        val xVal = event.values[0]
        val yVal = event.values[1]
        val zVal = event.values[2]
        tvXAxiz.text = "X Value: ".plus(xVal.toString())
        tvYAxis.text = "Y Value: ".plus(yVal.toString())
        tvZAxis.text = "Z Value: ".plus(zVal.toString())

        val accelerationSquareRoot = (xVal * xVal + yVal * yVal + zVal * zVal) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH)

        if (accelerationSquareRoot >= 3) {
            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT).show()
            if (color) {
                relative.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                relative.setBackgroundColor(Color.YELLOW)
            }
            color = !color
        }
    }

    override fun onResume() {
        super.onResume()
        //sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        //sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL)

        ///sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        //sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL)

        // Accelerometer e Magnetic
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

}
