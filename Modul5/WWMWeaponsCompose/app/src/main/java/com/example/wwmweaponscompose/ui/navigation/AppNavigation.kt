package com.example.wwmweaponscompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wwmweaponscompose.ui.screen.DetailScreen
import com.example.wwmweaponscompose.ui.screen.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                onNavigateToDetail = { weaponId ->
                    navController.navigate("detail/$weaponId")
                }
            )
        }

        composable(
            route = "detail/{weaponId}",
            arguments = listOf(navArgument("weaponId") { type = NavType.Companion.IntType })
        ) { backStackEntry ->
            val weaponId = backStackEntry.arguments?.getInt("weaponId")

            DetailScreen(weaponId = weaponId)
        }
    }
}