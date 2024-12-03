package com.huaguang.whattoeat

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置沉浸式状态栏，让应用内容延伸到状态栏区域
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //以上方法比这个方法要好，它实现了兼容（下面的方法只能用于 API 30 以上的手机）
//        window.setDecorFitsSystemWindows(false)

        setContent {
            AppContent()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 在这里处理返回键的点击事件，例如退出应用
            // 代码AlertDialog弹窗确认是否退出
            val builder = AlertDialog.Builder(this)
            builder.setMessage("确定要退出应用吗？").setPositiveButton("确定") { _, _ ->
                    finish() // 退出应用
                }.setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss() // 取消对话框
                }
            val alert = builder.create()
            alert.show()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

@Composable
fun AppContent() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true

    SideEffect {
        // 设置状态栏图标和文字颜色
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, // 状态栏背景颜色
            darkIcons = useDarkIcons // 是否使用深色图标和文字
        )
    }

    // 你的其他 UI 代码...
    WhatToEat()
}
