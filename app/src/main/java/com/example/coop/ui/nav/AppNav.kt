package com.example.coop.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coop.models.LoginResponse
import com.example.coop.ui.logincomponents.LoginScreen
import com.example.coop.ui.welcomecomponents.screens.WelcomeScreen

@Composable
fun AppNav(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN_SCREEN,
    ) {
        composable(
            route = Screens.LOGIN_SCREEN,

            ) {
            LoginScreen(
                navigateToWelcomeScreen = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "user",value = it)
                    navController.navigate(Screens.WELCOME_SCREEN)
                }
            )
        }
        composable(
            route = Screens.WELCOME_SCREEN,

            ) {
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<LoginResponse>(key ="user")
            WelcomeScreen(
                navigateToLoginScreen = {
                    navController.navigate(Screens.LOGIN_SCREEN)
                },
                loggedInUser = user
            )
        }
    }

}

object Screens {
    const val LOGIN_SCREEN = "LOGIN_SCREEN"
    const val WELCOME_SCREEN = "WELCOME_SCREEN"
}