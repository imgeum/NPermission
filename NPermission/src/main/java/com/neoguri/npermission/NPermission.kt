package com.neoguri.npermission

import android.R
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*


class NPermission {

    fun isCheck(act: Activity, permissions: ArrayList<String>, permissionReturn: Int) {

        val trueFalseCheck = IntArray(5)
        var trueFalseCheckFlag = 0

        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(
                    act,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) { // this line NullPointerException

                trueFalseCheck[trueFalseCheckFlag] = i
                trueFalseCheckFlag += 1

            } else {

            }
        }

        val arr: Array<String> = permissions.toArray(arrayOfNulls<String>(0))
        ActivityCompat.requestPermissions(
            act, arr,
            permissionReturn
        )

    }

    fun permissionDialog(context: Context, permissions: ArrayList<String>, packageName: String) {

        var failNames = ""

        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            ) { // this line NullPointerException

                failNames += "\n" + permissions[i]

            } else {

            }
        }

        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("권한 설정")
        dialog.setMessage("권한 거절로 인해 일부기능이 제한됩니다.$failNames")
        dialog.setPositiveButton(
            "권한 설정하러 가기"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
            try {
                val intent: Intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse(packageName))
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                val intent =
                    Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                context.startActivity(intent)
            }
        }
        dialog.setNegativeButton(
            "취소하기"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
            Toast.makeText(
                context,
                "Permission denied.",
                Toast.LENGTH_SHORT
            ).show()
        }

        val alertDialog: AlertDialog = dialog.show()
        val textView = alertDialog.findViewById<TextView>(R.id.message)
        textView?.textSize = 16f
        alertDialog.setCanceledOnTouchOutside(false)
    }

}