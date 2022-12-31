package com.neoguri.npermission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class NPermission {
    private var permission_check = ArrayList<String>()
    private var true_false_check = IntArray(5)
    private var true_false_check_flag = 0

    private var mPermissionCheck: String? = null
    private var mPermissionReturn: Int? = 0
    private var mPermissionCheckSplit: Array<String>? = null

    fun isCheck(act: Activity, permissionText: String, permissionReturn: Int) {

        mPermissionCheck = permissionText
        mPermissionReturn = permissionReturn
        mPermissionCheckSplit = mPermissionCheck!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        permission_check.addAll(listOf(*mPermissionCheckSplit!!))

        for (i in permission_check.indices) {
            if (ContextCompat.checkSelfPermission(
                    act,
                    permission_check[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) { // this line NullPointerException

                true_false_check[true_false_check_flag] = i
                true_false_check_flag += 1

            } else {

            }
        }

        ActivityCompat.requestPermissions(
            act, mPermissionCheckSplit!!,
            mPermissionReturn!!
        )
    }

}