package br.com.codelogic.getmacaddressphonemodel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.wifi.WifiInfo
import android.content.Context.WIFI_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.wifi.WifiManager
import androidx.core.app.ComponentActivity.ExtraData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import br.com.codelogic.getmacaddressphonemodel.Util.Companion.getPhoneDeviceName
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wInfo = wifiManager.connectionInfo
        val macAddress = wInfo.macAddress

        // Para obter o MAC Address
        tv_macaddress.text = macAddress.toString()

        //using kotlin function getPhoneDeviceName()
        tv_phonemodel.text ="Using Kotlin version of Function::"+ getPhoneDeviceName();
    }
}

class Util{
    companion object {
        @JvmStatic
        fun getPhoneDeviceName():String {
            val model = Build.MODEL // returns model name
            return model;
        }

    }
}
