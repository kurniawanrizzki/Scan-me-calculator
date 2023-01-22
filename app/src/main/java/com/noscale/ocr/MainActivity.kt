package com.noscale.ocr

import android.view.LayoutInflater
import com.noscale.core.ViewBindingActivity
import com.noscale.ocr.databinding.ActivityMainBinding

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {}
}