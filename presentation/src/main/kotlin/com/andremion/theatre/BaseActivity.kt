package com.andremion.theatre

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : ViewDataBinding> : DaggerAppCompatActivity() {
    lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
    }
}