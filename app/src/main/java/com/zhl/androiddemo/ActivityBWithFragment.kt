package com.zhl.androiddemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhl.androiddemo.kotlin.Singleton

class ActivityBWithFragment : AppCompatActivity(),BlankFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Singleton.divider("Activity_onCreate")
        setContentView(R.layout.activity_bwith_fragment)
        supportFragmentManager.beginTransaction().add(R.id.layout_fragment,BlankFragment.newInstance()).commit()
    }

    override fun onResume() {
        super.onResume()
        Singleton.divider("Activity_onResume")
    }

    override fun onStart() {
        super.onStart()
        Singleton.divider("Activity_onStart")
    }

    override fun onPause() {
        super.onPause()
        Singleton.divider("Activity_onPause")
    }

    override fun onStop() {
        super.onStop()
        Singleton.divider("Activity_onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Singleton.divider("Activity_onDestroy")
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("Not yet implemented")
    }

}
