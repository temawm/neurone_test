package com.example.test_neurone.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test_neurone.presentation.home_screen.screen.HomeScreenContainer
import com.example.test_neurone.presentation.purchases_screen.screen.PurchasesScreenContainer
import com.example.test_neurone.presentation.registration_screen.screen.RegistrationScreenContainer

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController,"home_screen") {
        composable("home_screen") {
            HomeScreenContainer(navController = navController)
        }
        composable("singup_screen") {
            RegistrationScreenContainer(navController = navController)

        }
        composable("purchases_screen") {
            PurchasesScreenContainer(navController = navController)
        }
    }

}