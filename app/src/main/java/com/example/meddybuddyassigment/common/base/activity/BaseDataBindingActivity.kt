package com.example.meddybuddyassigment.common.base.activity

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * BaseActivity class that takes in
 * @param DataBindingClass that serves as the binding for the activity
 * @param screenLayoutId layout resource ID for the activity
 *
 * This should be used for classes that use DataBinding to reference views.
 */
abstract class BaseDataBindingActivity<DataBindingClass : ViewDataBinding>(
    @LayoutRes private val screenLayoutId: Int
) : BaseActivity(
    screenLayoutId = screenLayoutId
) {

    protected lateinit var binding: DataBindingClass

    override fun setContentLayout(@LayoutRes layoutRes: Int) {
        initialiseDataBinding(layoutRes)
    }

    private fun initialiseDataBinding(@LayoutRes layoutRes: Int) {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        onDataBindingCreated()
    }

    abstract fun onDataBindingCreated()
}