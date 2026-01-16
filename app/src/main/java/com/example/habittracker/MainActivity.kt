package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.theme.HabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HabitTrackerTheme {
                Surface(color = Color.Transparent, modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
                    MyApplication()
                }
            }
        }
    }
}

@Composable
fun MyApplication() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HabitTrackerTheme {
        MyApplication()
    }
}