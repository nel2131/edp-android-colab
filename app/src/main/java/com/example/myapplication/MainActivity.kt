package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Home) {

                        composable<Home> {
                            HomeScreen(onShowGreeting = { typedName ->
                                // pass the name by creating a Greeting route object
                                navController.navigate(Greeting(userName = typedName))
                            })
                        }

                        composable<Greeting> { backStackEntry ->
                            // rebuild the typed Greeting object on this screen
                            val greeting: Greeting = backStackEntry.toRoute()
                            GreetingScreen(
                                userName = greeting.userName,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
