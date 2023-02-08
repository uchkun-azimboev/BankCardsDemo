package com.example.bankcardsdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bankcardsdemo.model.ConnectionModel
import com.example.bankcardsdemo.utils.ConnectionLiveData
import com.example.bankcardsdemo.utils.Extensions.isConnected
import com.example.bankcardsdemo.utils.Extensions.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectionLiveData = ConnectionLiveData(applicationContext)
        connectionLiveData.observe(this) {
            if (it!!.isConnected) {
                toast(getString(R.string.str_network_on))
            } else {
                toast(getString(R.string.str_network_off))
            }
        }
    }

}