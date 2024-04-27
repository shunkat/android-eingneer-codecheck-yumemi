/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    // TODO: おそらく不要なので検証する
    companion object {
        lateinit var lastSearchDate: Date
    }
}
