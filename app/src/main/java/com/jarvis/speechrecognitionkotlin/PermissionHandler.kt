package com.jarvis.speechrecognitionkotlin

/**
 * Created by user on 9/1/2017.
 */
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast


/**
 * Created by root on 19/10/15.
 */
object PermissionHandler {


    val RECORD_AUDIO = 1


    fun askForPermission(which: Int, activity: Activity) {
        if (Build.VERSION.SDK_INT < 23) {
            return
        } else
        //We are running on Android M
        {
            when (which) {
                RECORD_AUDIO -> if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED)
                    return
                else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                        Toast.makeText(activity, "Recording Audio is required", Toast.LENGTH_LONG).show()
                    } else {
                        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO)
                    }
                }
            }
        }
    }

    fun checkPermission(activity: Activity, which: Int): Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        } else {
            when (which) {
                RECORD_AUDIO -> return ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

}