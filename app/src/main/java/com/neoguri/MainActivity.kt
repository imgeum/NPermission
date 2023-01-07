package com.neoguri

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.neoguri.databinding.ActivityMainBinding
import com.neoguri.npermission.NPermission

class MainActivity : AppCompatActivity() {

    private val mPermission = ArrayList<String>()
    private val mPermissionCode = 123

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            mPermission.clear()
            mPermission.add("android.permission.CAMERA")
            mPermission.add("android.permission.ACCESS_FINE_LOCATION")
            NPermission().isCheck(
                this,
                mPermission,
                mPermissionCode
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {

        var isPermissionFlag = true

        if (requestCode == mPermissionCode) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    for (permission in mPermission){
                        if (permissions[i] == permission
                        ) {
                            isPermissionFlag = false
                        }
                    }
                }
            }

            if (isPermissionFlag) {
                Toast.makeText(this@MainActivity, "승인", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "거부", Toast.LENGTH_SHORT).show()

                val names = ArrayList<String>()
                for (permission in mPermission) {
                    val name = permission.split(".")
                    names.add(name[name.size - 1])
                }
                NPermission().permissionDialog(this, names, "package:$packageName")
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}