package io.github.iamyours.smalltest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.wequick.small.Small

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Small.setUp(this) {
            Small.openUri("main", this@MainActivity)
            finish()
        }
    }
}
