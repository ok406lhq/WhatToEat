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

data class Dish(val name: String)

val vegetableDishes = listOf(
    "素炒豆苗",
    "素炒青瓜",
    "素炒藕片",
    "素炒各类青菜",
    "炒西兰花",
    "炒土豆丝",
    "炒胡萝卜丝",
    "黑豆花生",
    "啫啫花菜/芥蓝头/豆角",
    "香于木耳芹菜",
    "白菜(油)豆腐",
    "炒空心菜",
    "炒包菜",
    "素炒豆芽",
    "炸小浮央腐",
    "酱油豆皮/千张",
    "炒白菜",
    "白灼芥兰/生菜"
)

val meatDishes = listOf(
    "毛豆肉末",
    "茄子肉末",
    "肉末蒸蛋",
    "青椒炒肉卷 / 鸡蛋 / 肉丝 / 鸡胸肉",
    "榨菜肉丝",
    "南瓜 + 虾皮 + 香菇",
    "各种丸类",
    "土豆苦瓜排骨",
    "梅菜肉饼",
    "青瓜炒肉片 / 蛋",
    "土豆丝炒肉",
    "豆腐焖肉",
    "菜脯煎蛋",
    "虾皮煎蛋",
    "苦瓜煎蛋",
    "番茄炒蛋",
    "青椒土豆炒肉",
    "油豆腐塞肉末",
    "鱿鱼白菜煲",
    "咸菜肉片",
    "腊肠荷兰豆",
    "豆角肉沫",
    "韭菜炒鸡蛋",
    "南瓜排骨"
)

val halfVMDishes = listOf(
    "煎鸡排",
    "炸鸡排",
    "煎各种丸类",
    "煎鸡大腿",
    "鸡蛋炒火腿",
    "煎盐焗鸡翅",
    "煎五花肉",
    "卤牛腱子",
    "炒腊肠"
)

val specialDishes = listOf(
    "炒饭（南瓜 + 虾皮 + 香菇）",
    "炒饭（胡萝卜 + 玉米 + 青豆 + 鸡蛋 + 虾皮）",
    "炒饭（豆角 + 鸡蛋 + 成莱粒）",
    "鸡排咖喱饭（胡萝卜 + 玉米 + 青豆 + 土豆）",
    "粉丝（鲜虾花甲粉丝）",
    "煎吐司三明治（鸡排/牛排 + 鸡蛋 + 芝士 + 生菜）",
    "炒面（火腿/肉丝 + 鸡蛋 + 胡萝卜丝 + 青菜/豆芽）"
)


const val displayDishDefaultValue = "随便啦 ~ (*^▽^*) ~"

/**
 * 根据菜谱返回一个随机菜肴，包括菜名。
 */
fun randomDish(dishes: List<String>): Dish {
    val randomIndex = Random.nextInt(0, dishes.size)
    return Dish(dishes[randomIndex])
}

@Preview(showBackground = true)
@Composable
fun WhatToEat() {
    val displayDish = remember { mutableStateOf(displayDishDefaultValue) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            onClick = {
                Toast.makeText(context, "Happy", Toast.LENGTH_SHORT).show()
                context.startActivities(arrayOf(Intent(context, SecondActivity::class.java)))
            }, modifier = Modifier.padding(0.dp, 35.dp, 0.dp, 35.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.momo), // 假设有一个名为ic_hello的图标资源
                contentDescription = "Hello Button"
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleText("momo吃么个？")
        DishText(displayDish)
        RandomComboButton(displayDish)

        // 设置上下两套组件的空间间隔
        Spacer(modifier = Modifier.height(40.dp))
    }
}


@Composable
fun TitleText(title: String) {
    Text(text = title, style = MaterialTheme.typography.titleLarge)
}

@Composable
fun DishText(displayDish: MutableState<String>) {
    Text(
        text = displayDish.value,
        modifier = Modifier
            .padding(0.dp, 35.dp, 0.dp, 35.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(10.dp)
    )
}

@Composable
fun RandomComboButton(displayDish: MutableState<String>) {
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
            Text(text = "随机组合")
        }
        AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        }
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            displayDish.value = "loading..."

            delay(1500) // 2 seconds delay
            updateRandomCombo(displayDish)
            Toast.makeText(context, displayDish.value, Toast.LENGTH_SHORT).show()
            isLoading = false
        }
    }
}

private fun updateRandomCombo(displayDish: MutableState<String>) {
    // Combine all dishes into a single list
    val allDishes = specialDishes + vegetableDishes + meatDishes + halfVMDishes
    val randomDish = randomDish(allDishes).name

    if (randomDish in specialDishes) {
        displayDish.value = randomDish
    } else {
        val vegetable = randomDish(vegetableDishes).name
        val meat = randomDish(meatDishes).name
        val halfVM = randomDish(halfVMDishes).name

        val comboDish = "【$vegetable】+【$meat】+【$halfVM】"
        displayDish.value = comboDish
    }
}
