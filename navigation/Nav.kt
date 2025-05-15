//package com.example.assignment.navigation
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.assignment.HomePage
//import com.example.assignment.page.AboutScreen
//import com.example.assignment.page.ProfileScreen
//import com.example.assignment.page.QuoteScreen
//import com.example.assignment.utils.DataStoreManger
//
////import com.example.assignment.screens.*
//
//@Composable
//fun NavGraph(
//    navController: NavHostController,
//    innerPadding: PaddingValues,
//    onLogout: () -> Unit,
//    dataStoreManger: DataStoreManger
//) {
//    NavHost(navController = navController, startDestination = Screen.HomePage.route) {
//        composable(Screen.HomePage.route) {
//            HomePage(
//                onLogout = onLogout,
//                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
//                onNavigateToSettings = { navController.navigate(Screen.About.route) },
//                dataStoreManger = dataStoreManger
//            )
//        }
//
//        composable(Screen.Profile.route) {
//            ProfileScreen(innerPadding)
//        }
//
//        composable(Screen.Quote.route) {
//            QuoteScreen(navController, innerPadding)
//        }
//
//        composable(Screen.About.route) {
//            AboutScreen(navController, innerPadding)
//        }
//    }
//}
//
//
//
//sealed class Screen(val route: String) {
//    object HomePage : Screen("home")
//    object Profile : Screen("profile")
//    object Quote : Screen("quote")
//    object About : Screen("about")
//}
//
