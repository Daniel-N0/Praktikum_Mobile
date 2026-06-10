package com.example.wwmweaponscompose.ui.screen

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.wwmweaponscompose.R
import com.example.wwmweaponscompose.di.WWMApplication
import com.example.wwmweaponscompose.model.ApiResult
import com.example.wwmweaponscompose.model.Weapon
import com.example.wwmweaponscompose.viewmodel.WeaponViewModel
import com.example.wwmweaponscompose.viewmodel.WeaponViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.chunked

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as WWMApplication
    val userPreferences = app.userPreferences
    val lastSync by userPreferences.lastSync.collectAsState(initial = 0L)

    val viewModel: WeaponViewModel = viewModel(
        factory = WeaponViewModelFactory("Halaman Utama Senjata Compose", app.useCase)
    )

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val weapons by viewModel.weaponList.collectAsState()
    val apiState by viewModel.apiState.collectAsState()
    val horizontalWeapons = weapons.reversed()

    LaunchedEffect(apiState) {
        if (apiState is ApiResult.Success) {
            userPreferences.saveLastSync(System.currentTimeMillis())
        }
    }

    LazyColumn(
        modifier = Modifier.Companion.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        if (apiState is ApiResult.Error) {
            item {
                Text(
                    text = (apiState as ApiResult.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.Companion.padding(16.dp)
                )
            }
        }

        item {
            Text(
                text = if (lastSync == 0L)
                    "Last Sync: Belum pernah"
                else
                    "Last Sync: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(lastSync))}",
                modifier = Modifier.Companion.padding(16.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }

        item {
            Text(
                text = stringResource(R.string.top_weapons),
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 18.sp,
                modifier = Modifier.Companion.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(horizontalWeapons) { weapon ->
                    WeaponHorizontalItem(
                        weapon = weapon,
                        onInfoClick = {
                            viewModel.onExplicitIntentClicked(weapon)
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                            intent.setPackage("com.android.chrome")
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                intent.setPackage(null)
                                context.startActivity(intent)
                            }
                        },
                        onDetailClick = {
                            viewModel.onDetailClicked(weapon)
                            onNavigateToDetail(weapon.id)
                        }
                    )
                }
            }
        }

        item {
            Text(
                text = stringResource(R.string.all_weapons),
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 18.sp,
                modifier = Modifier.Companion.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        if (isLandscape) {
            items(weapons.chunked(2)) { rowWeapons ->
                Row(modifier = Modifier.Companion.fillMaxWidth()) {
                    for (weapon in rowWeapons) {
                        Box(modifier = Modifier.Companion.weight(1f)) {
                            WeaponListItem(
                                weapon = weapon,
                                onInfoClick = {
                                    viewModel.onExplicitIntentClicked(weapon)
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                                    intent.setPackage("com.android.chrome")
                                    try {
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        intent.setPackage(null)
                                        context.startActivity(intent)
                                    }
                                },
                                onDetailClick = {
                                    viewModel.onDetailClicked(weapon)
                                    onNavigateToDetail(weapon.id)
                                }
                            )
                        }
                    }
                    if (rowWeapons.size == 1) {
                        Spacer(modifier = Modifier.Companion.weight(1f))
                    }
                }
            }
        } else {
            items(weapons) { weapon ->
                WeaponListItem(
                    weapon = weapon,
                    onInfoClick = {
                        viewModel.onExplicitIntentClicked(weapon)
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                        intent.setPackage("com.android.chrome")
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            intent.setPackage(null)
                            context.startActivity(intent)
                        }
                    },
                    onDetailClick = {
                        viewModel.onDetailClicked(weapon)
                        onNavigateToDetail(weapon.id)
                    }
                )
            }
        }
    }
}

@Composable
fun WeaponListItem(
    weapon: Weapon,
    onInfoClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.Companion
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.Companion.padding(12.dp)) {
            Surface(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                color = Color.Companion.White,
                modifier = Modifier.Companion.size(100.dp, 140.dp)
            ) {
                AsyncImage(
                    model = weapon.imageUrl,
                    contentDescription = weapon.name,
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.Companion.width(12.dp))

            Column(modifier = Modifier.Companion.fillMaxWidth()) {
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = weapon.name,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 18.sp
                    )
                    Text(text = weapon.type, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.Companion.height(8.dp))

                Row {
                    Text(
                        text = stringResource(R.string.description_label),
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = weapon.description,
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Companion.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.Companion.height(12.dp))

                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalButton(onClick = onInfoClick) {
                        Text(stringResource(R.string.btn_info))
                    }
                    Spacer(modifier = Modifier.Companion.width(8.dp))
                    Button(onClick = onDetailClick) {
                        Text(stringResource(R.string.btn_detail))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeaponHorizontalItem(
    weapon: Weapon,
    onInfoClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onDetailClick,
        modifier = Modifier.Companion
            .padding(8.dp)
            .width(140.dp)
    ) {
        Column(modifier = Modifier.Companion.padding(8.dp)) {
            Surface(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                color = Color.Companion.White,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                AsyncImage(
                    model = weapon.imageUrl,
                    contentDescription = weapon.name,
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Text(
                text = weapon.name,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis,
                modifier = Modifier.Companion.align(Alignment.Companion.CenterHorizontally)
            )

            Spacer(modifier = Modifier.Companion.height(4.dp))

            FilledTonalButton(
                onClick = onInfoClick,
                modifier = Modifier.Companion.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(stringResource(R.string.btn_web_info), fontSize = 12.sp)
            }
        }
    }
}