package com.ayushkushwaha2212.permissionrequest

import android.Manifest
import android.icu.text.CaseMap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
                isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
            }
        }


    private val cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
                permission ->
            permission.entries.forEach{
                val permissonName = it.key
                val isGranted = it.value
                if(isGranted){
                    if(permissonName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission granted for location",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else if(permissonName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission granted for COARSE",
                            Toast.LENGTH_LONG
                        ).show()
                    }else{
                        Toast.makeText(
                            this,
                            "Permission granted for Camera",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else{
                    if(permissonName == Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission denied for fine location",
                            Toast.LENGTH_LONG
                        ).show()
                    }else if(permissonName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(
                            this,
                            "Permission denied for COARSE",
                            Toast.LENGTH_LONG
                        ).show()
                    }else{
                        Toast.makeText(
                            this,
                            "Permission denied for Camera",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                val btnCameraPermission : Button = findViewById(R.id.btnCameraPermission)
                btnCameraPermission.setOnClickListener {
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M &&
                            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        showRationaleDialog("permission request requires camera access",
                        "Camera cannot be used because camera access is denied")
                    }else{
                        cameraAndLocationResultLauncher.launch(
                            arrayOf(Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                        )
                    }
                }
            }

    /*
    * shows rationale dialog for displaying why the apps needs permission
    * only shown if the user has denied the permission request previously
     */

    private fun showRationaleDialog(
        title: String,
        message: String,
    ){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("cancel"){
                dialog, _->dialog.dismiss()
            }
        builder.create().show()
    }
}