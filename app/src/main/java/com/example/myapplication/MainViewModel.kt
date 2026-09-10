package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.Post
import com.example.myapplication.data.Settings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).postDao()
    private val settings = Settings(application)

    val posts: StateFlow<List<Post>> = dao.getAllPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val darkTheme: StateFlow<Boolean> = settings.darkTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    fun setDarkTheme(enabled: Boolean) = viewModelScope.launch { settings.setDarkTheme(enabled) }

    fun addPost(text: String) = viewModelScope.launch { dao.insert(Post(text = text)) }

    fun updatePost(post: Post) = viewModelScope.launch { dao.update(post) }

    fun deletePost(post: Post) = viewModelScope.launch { dao.deleteById(post.id) }
}