package com.example.test_neurone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.test_neurone.core.navigation.AppNavGraph
import com.example.test_neurone.core.theme.Test_neuroneTheme
import com.example.test_neurone.core.ui.Header

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test_neuroneTheme (
                dynamicColor = false
            ){
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}