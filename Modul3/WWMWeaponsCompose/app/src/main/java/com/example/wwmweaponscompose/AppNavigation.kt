package com.example.wwmweaponscompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
            arguments = listOf(navArgument("weaponId") { type = NavType.IntType })
        ) { backStackEntry ->
            val weaponId = backStackEntry.arguments?.getInt("weaponId")

            DetailScreen(weaponId = weaponId)
        }
    }
}