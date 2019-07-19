package com.kryptkode.footballfixtures.app.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment


abstract class BaseFragmentActivity<T : Fragment> : BaseActivity() {


    abstract val fragment: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var frag = supportFragmentManager.findFragmentById(android.R.id.content)


        if (frag == null) {
            frag = fragment
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(android.R.id.content, frag)
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.findFragmentById(android.R.id.content)?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
