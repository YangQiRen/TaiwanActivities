package com.yang.taiwanactivities.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.yang.taiwanactivities.BuildConfig
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListener()
        setUpNavigation()
    }

    private fun setUpListener() {
        binding.mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_info -> {
                    AlertDialog.Builder(this)
                        .setTitle(getString(R.string.about))
                        .setMessage(getString(R.string.about_info, BuildConfig.VERSION_NAME))
                        .setPositiveButton(getString(R.string.confirm)) { dialog, which -> dialog.dismiss() }
                        .setNegativeButton(getString(R.string.review)) { dialog, which -> }
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpNavigation() {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(binding.mBottomNav, navHostFragment.navController)
        }
    }

}