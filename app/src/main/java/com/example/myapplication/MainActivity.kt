package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCard(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()
    val brandColor = if (isDark) MaterialTheme.colorScheme.primary else Color(0xFF771C1B)
    val cardBackground = if (isDark) MaterialTheme.colorScheme.surfaceVariant else Color(0xFFF4F2F1)
    val subtitleColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else Color.DarkGray
    val contactTextColor = if (isDark) MaterialTheme.colorScheme.onSurface else Color.Black

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = cardBackground
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Step 3 - Build the circular avatar
                Image(
                    painter = painterResource(R.drawable.l),
                    contentDescription = "Profile Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, brandColor, CircleShape)
                )

                // Step 4 - Add the name and title
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "La Leunel B. Valmoria",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = brandColor,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Junior Developer",
                    fontSize = 16.sp,
                    color = subtitleColor,
                    textAlign = TextAlign.Center
                )

                // Step 5 - Contact rows
                Spacer(Modifier.height(24.dp))
                ContactRow(Icons.Default.Phone, "09365459287", brandColor, contactTextColor)
                ContactRow(Icons.Default.Email, "llvalmoria42157@liceo.edu.ph", brandColor, contactTextColor)
                ContactRow(Icons.Default.School, "Liceo de Cagayan University", brandColor, contactTextColor)
            }
        }
    }
}

@Composable
fun ContactRow(icon: ImageVector, label: String, tint: Color, textColor: Color) {
    Row(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .clickable { /* TODO: real action later */ },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "Light Mode", showBackground = true, widthDp = 360)
@Composable
fun BusinessCardPreview() {
    MyApplicationTheme {
        BusinessCard()
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BusinessCardDarkPreview() {
    MyApplicationTheme {
        BusinessCard()
    }
}

@Preview(name = "Large Font", showBackground = true, widthDp = 360, fontScale = 1.5f)
@Composable
fun BusinessCardLargeFontPreview() {
    MyApplicationTheme {
        BusinessCard()
    }
}