package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.AppViewModelFactory
import com.example.myapplication.ui.PostsScreen
import com.example.myapplication.ui.PostsViewModel
import com.example.myapplication.ui.ProfileScreen
import com.example.myapplication.ui.ThemeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val factory = AppViewModelFactory(applicationContext)
            val postsVm: PostsViewModel = viewModel(factory = factory)
            val themeVm: ThemeViewModel = viewModel(factory = factory)

            val darkTheme by themeVm.isDarkTheme.collectAsStateWithLifecycle()

            MyApplicationTheme(darkTheme = darkTheme, dynamicColor = false) {
                MySocialApp(postsVm, themeVm)
            }
        }
    }
}

@Composable
fun MySocialApp(postsVm: PostsViewModel, themeVm: ThemeViewModel) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = tab == 0, onClick = { tab = 0 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Posts") },
                )
                NavigationBarItem(
                    selected = tab == 1, onClick = { tab = 1 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") },
                )
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            if (tab == 0) PostsScreen(postsVm) else ProfileScreen(postsVm, themeVm)
        }
    }
}