package com.huaguang.whattoeat

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

data class Dish2(val name: String)

val weekendDishes = listOf(
    "塔斯丁汉堡",
    "众友荔枝木烧鸡",
    "牛肉粿条",
    "麻辣烫",
    "螺蛳粉",
    "陶荔苑茶点",
    "啫火啫啫煲",
    "克茗冰室",
    "探鱼（抽到券版，不吃凌波鱼）",
    "喜家德水饺",
    "喜姐炸串",
    "韩香亭炭火烤肉",
    "凑凑火锅",
    "小野火锅",
    "自制火锅（鸳鸯锅，随便吃）",
    "乐凯撒披萨",
    "云吞 + 兰州炒拉面（实在不知道吃什么版）",
    "麦当劳"
)

const val weekendDishDefaultValue = "随便啦 ~ (*^▽^*) ~"

/**
 * 根据菜谱返回一个随机菜肴，包括菜名。
 */
fun randomDish2(dishes: List<String>): Dish2 {
    val randomIndex = Random.nextInt(0, dishes.size)
    return Dish2(dishes[randomIndex])
}

@Preview(showBackground = true)
@Composable
fun WhatToEat2() {
    val displayWeekendDish = remember { mutableStateOf(weekendDishDefaultValue) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = {
                Toast.makeText(context, "返回", Toast.LENGTH_SHORT).show()
                context.startActivities(arrayOf(Intent(context, MainActivity::class.java)))
            }, modifier = Modifier.padding(0.dp, 35.dp, 0.dp, 35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_back), // 假设有一个名为ic_hello的图标资源
                contentDescription = "Hello Button"
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TitleText2(title = "周末放纵一下？")
        DishText2(displayWeekendDish)
        WeekendButton2(displayWeekendDish)
        // 设置上下两套组件的空间间隔
        Spacer(modifier = Modifier.height(40.dp))

    }
}


@Composable
fun TitleText2(title: String) {
    Text(text = title, style = MaterialTheme.typography.titleLarge)
}

@Composable
fun DishText2(displayDish: MutableState<String>) {
    Text(
        text = displayDish.value,
        modifier = Modifier
            .padding(0.dp, 35.dp, 0.dp, 35.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(10.dp)
    )
}


@Composable
fun WeekendButton2(displayDish: MutableState<String>) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val buttonWidth = 120.dp

    Button(
        onClick = {
            isLoading = true
        },
        enabled = !isLoading,
        border = if (isLoading) BorderStroke(1.dp, Color.Gray) else null,
        modifier = Modifier.width(buttonWidth) // 设置固定宽度
    ) {
        AnimatedVisibility(visible = !isLoading, enter = fadeIn(), exit = fadeOut()) {
            Text(text = "随机外卖")
        }
        AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        }
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            displayDish.value = "loading..."
            Toast.makeText(context, "随机中...", Toast.LENGTH_SHORT).show()
            delay(1500) // 2 seconds delay
            updateWeekendRandomCombo(displayDish)
            isLoading = false
        }
    }

}

fun updateWeekendRandomCombo(displayDish: MutableState<String>) {
    val weekendDish = randomDish2(weekendDishes)
    displayDish.value = weekendDish.name
}
