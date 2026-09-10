package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.data.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(vm: PostsViewModel) {

    val posts by vm.posts.collectAsStateWithLifecycle()

    var editing by remember { mutableStateOf<Post?>(null) }
    var showEditor by remember { mutableStateOf(false) }
    var postToDelete by remember { mutableStateOf<Post?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MySocial · ${posts.size} posts") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { editing = null; showEditor = true }) {
                Icon(Icons.Default.Add, contentDescription = "New post")
            }
        }
    ) { padding ->
        if (posts.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                Text("No posts yet. Tap + to write your first one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(posts, key = { it.id }) { post ->
                    PostCard(
                        post = post,
                        onEdit = { editing = post; showEditor = true },
                        onDelete = { postToDelete = post },
                    )
                }
            }
        }
    }

    if (showEditor) {
        PostEditorDialog(
            initialText = editing?.content ?: "",
            onDismiss = { showEditor = false },
            onSave = { newText ->
                val current = editing
                if (current == null) vm.addPost(newText) else vm.editPost(current, newText)
                showEditor = false
            },
        )
    }

    postToDelete?.let { post ->
        AlertDialog(
            onDismissRequest = { postToDelete = null },
            title = { Text("Delete this post?") },
            text = { Text("You cannot undo this.") },
            confirmButton = {
                TextButton(onClick = {
                    vm.deletePost(post)
                    postToDelete = null
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { postToDelete = null }) { Text("Cancel") }
            },
        )
    }
}

@Composable
fun PostCard(post: Post, onEdit: () -> Unit, onDelete: () -> Unit) {
    val time = remember(post.createdAt) {
        SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(post.createdAt))
    }
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(post.content, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(6.dp))
            Text(time, style = MaterialTheme.typography.labelSmall)
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onEdit) { Text("Edit") }
                TextButton(onClick = onDelete) { Text("Delete") }
            }
        }
    }
}

@Composable
fun PostEditorDialog(
    initialText: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialText.isEmpty()) "New post" else "Edit post") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("What's on your mind?") },
                minLines = 3,
            )
        },
        confirmButton = {
            TextButton(onClick = { onSave(text.trim()) }, enabled = text.isNotBlank()) {
                Text("Save")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}