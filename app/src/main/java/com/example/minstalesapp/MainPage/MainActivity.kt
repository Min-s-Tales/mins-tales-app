package com.example.minstalesapp.MainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleSwitchButton.check(R.id.stories_toggle_button)

        binding.toggleSwitchButton.setOnCheckedChangeListener { _, optionId ->
            when (optionId) {
                //Event handler on "Librairie" button's click
                R.id.stories_toggle_button -> {
                    Log.i("current displayed view", binding.mainViewPager.currentItem.toString())
                    binding.mainViewPager.setCurrentItem(0, true)
                }
                //Event handler on "Market Place" button's click
                R.id.marketplace_toggle_button -> {
                    Log.i("current displayed view", binding.mainViewPager.currentItem.toString())
                    binding.mainViewPager.setCurrentItem(1, true)
                }
            }
        }

        val listOfPagesId = arrayOf(R.layout.fragment_activity_main_librairie, R.layout.fragment_activity_main_marketplace)

        binding.mainViewPager.adapter = MainPagerAdapterMainActivity(this, listOfPagesId)
        binding.mainViewPager.setCurrentItem(0, false)
    }
}