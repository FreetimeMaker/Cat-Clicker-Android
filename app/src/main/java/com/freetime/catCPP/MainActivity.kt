package com.freetime.catCPP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatClickerApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatClickerApp() {
    val viewModel: ClickerGameViewModel = viewModel()
    val gameState by viewModel.gameState.collectAsState()
    val context = LocalContext.current
    
    // Unity-inspired colors
    val unityPrimary = Color(0xFF667eea)
    val unitySecondary = Color(0xFF764ba2)
    val unityAccent = Color(0xFFffd89b)
    val unityBackground = Color(0xFFff9a9e)
    
    // Export dialog state
    var showExportDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) { 
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Click area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Cat button
                        Card(
                            onClick = { viewModel.click() },
                            modifier = Modifier
                                .size(200.dp)
                                .shadow(10.dp, CircleShape),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(unityAccent, Color(0xFF19547b))
                                        ),
                                        CircleShape
                                    )
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(getCurrentCatImage(gameState.currentCatIndex))
                                        .build(),
                                    contentDescription = "Click me!",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Click me!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color.Black,
                                    blurRadius = 4f,
                                )
                            )
                        )
                        
                        // Cat navigation
                        Row(
                            modifier = Modifier.padding(top = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(onClick = { viewModel.previousCat() }) {
                                Icon(
                                    painter = painterResource(R.drawable.pfeill),
                                    contentDescription = "Previous cat"
                                )
                            }
                            IconButton(onClick = { viewModel.nextCat() }) {
                                Icon(
                                    painter = painterResource(R.drawable.pfeilr),
                                    contentDescription = "Next cat"
                                )
                            }
                        }
                    }
                }
                
                // Upgrades panel
                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .fillMaxHeight()
                        .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "Upgrades",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = unitySecondary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(gameState.upgrades) { upgrade ->
                                UpgradeItem(
                                    upgrade = upgrade,
                                    onPurchase = { viewModel.purchaseUpgrade(upgrade.id) },
                                    canAfford = gameState.money >= upgrade.cost
                                )
                            }
                        }
                    }
                }
            }
            
            // Footer
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { /* TODO: Implement fullscreen */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(unityPrimary, unitySecondary)
                                ),
                                CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "Fullscreen",
                            tint = Color.White
                        )
                    }
                    
                    Text(
                        text = "Cat Clicker v1.0.8",
                        fontSize = 14.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        
        // Export dialog
        if (showExportDialog) {
            AlertDialog(
                onDismissRequest = { showExportDialog = false },
                title = { Text("Export Upgrades") },
                text = { Text("Choose export format for Unity WebGL compatibility") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Export Unity format
                            UpgradeExporter.exportUnityFormatUpgrades(context, 0L)
                            showExportDialog = false
                        }
                    ) {
                        Text("Unity Format")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // Export current state
                            UpgradeExporter.exportCurrentUpgrades(context, 0L)
                            showExportDialog = false
                        }
                    ) {
                        Text("Current State")
                    }
                }
            )
        }
    }

@Composable
fun UpgradeItem(
    upgrade: UpgradeUI,
    onPurchase: () -> Unit,
    canAfford: Boolean
) {
    val unitySecondary = Color(0xFF764ba2)
    val isMaxLevel = upgrade.level >= getMaxLevel(upgrade.id)
    
    Card(
        onClick = { if (canAfford && !isMaxLevel) onPurchase() },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (canAfford && !isMaxLevel) 
                Color(0xFFf5f7fa) 
            else 
                Color(0xFFf5f7fa).copy(alpha = 0.6f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (canAfford && !isMaxLevel) 6.dp else 2.dp
        ),
        border = if (canAfford && !isMaxLevel)
            androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF667eea))
        else
            androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFcccccc))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getUpgradeIcon(upgrade.id))
                    .build(),
                contentDescription = upgrade.name,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF667eea), Color(0xFF764ba2))
                        )
                    )
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = upgrade.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    text = "Cost: ${formatNumber(upgrade.cost.toLong())} cats",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
                Text(
                    text = "Level: ${upgrade.level}/${getMaxLevel(upgrade.id)}",
                    fontSize = 12.sp,
                    color = unitySecondary
                )
            }
            
            Button(
                onClick = onPurchase,
                enabled = canAfford && !isMaxLevel,
                modifier = Modifier.width(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF667eea), Color(0xFF764ba2))
                            ),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isMaxLevel) "MAX" else "Buy",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private fun formatNumber(num: Long): String {
    return when {
        num >= 1_000_000 -> String.format("%.1fM", num / 1_000_000.0)
        num >= 1_000 -> String.format("%.1fK", num / 1_000.0)
        else -> num.toString()
    }
}

private fun getCurrentCatImage(index: Int): Int {
    val catImages = listOf(
        R.drawable.cat1, R.drawable.cat2, R.drawable.cat3, R.drawable.cat4, R.drawable.cat5,
        R.drawable.cat6, R.drawable.cat7, R.drawable.cat8, R.drawable.cat9, R.drawable.cat10,
        R.drawable.cat11, R.drawable.cat12, R.drawable.cat13, R.drawable.cat14, R.drawable.cat15,
        R.drawable.cat16, R.drawable.cat17, R.drawable.cat18, R.drawable.cat19, R.drawable.cat20
    )
    return catImages[index % catImages.size]
}

private fun getUpgradeIcon(upgradeId: Int): Int {
    return getCurrentCatImage(upgradeId)
}

private fun getMaxLevel(upgradeId: Int): Int {
    return when (upgradeId) {
        0 -> 10  // Catnip
        1 -> 8   // Yarn Ball
        2 -> 5   // Scratching Post
        3 -> 3   // Feather Toy
        4 -> 2   // Laser Pointer
        5 -> 1   // Cardboard Box
        else -> 10
    }
}
