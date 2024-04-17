package com.sookmyung.umbrellafriend.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityMainBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}