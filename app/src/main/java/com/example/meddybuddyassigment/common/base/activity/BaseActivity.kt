package com.example.meddybuddyassigment.common.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Base Activity class that takes in
 * @param screenLayoutId layout resource ID for the activity
 * and inflates the layout.
 *
 * This should be used for skeleton classes that wrap around a fragment and majorly do nothing else.
 */
abstract class BaseActivity(@LayoutRes private val screenLayoutId: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        processIntent(intent)
        injectDaggerComponent()
        setContentLayout(screenLayoutId)
    }

    open fun setContentLayout(@LayoutRes layoutRes: Int) {
        setContentView(layoutRes)
    }

    abstract fun injectDaggerComponent()

    open fun processIntent(intent: Intent) {}

}