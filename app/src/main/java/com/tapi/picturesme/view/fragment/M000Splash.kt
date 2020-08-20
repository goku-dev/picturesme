package com.tapi.picturesme.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Handler
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.tapi.picturesme.R
import com.tapi.picturesme.functions.home.screen.M001HomeFrg
import com.tapi.picturesme.view.base.BaseFragment

class M000Splash : BaseFragment() {
    val TAG = M000Splash::class.java.name
    override fun initViews() {

        Handler().postDelayed(Runnable {
            mCallback.showFragment(M001HomeFrg().TAG)
        }, 2000)
    }

    override fun getLayoutByID(): Int {
        return R.layout.frg_splash
    }

    override fun showPreviousFrg() {
        throw NullPointerException()
    }

}