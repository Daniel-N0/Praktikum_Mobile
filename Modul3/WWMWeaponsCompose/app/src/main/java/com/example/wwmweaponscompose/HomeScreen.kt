package com.example.wwmweaponscompose

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val weapons = WeaponDataSource.dummyWeapons
    val horizontalWeapons = weapons.reversed()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.top_weapons),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
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
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                            context.startActivity(intent)
                        },
                        onDetailClick = { onNavigateToDetail(weapon.id) }
                    )
                }
            }
        }

        item {
            Text(
                text = stringResource(R.string.all_weapons),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        if (isLandscape) {
            items(weapons.chunked(2)) { rowWeapons ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (weapon in rowWeapons) {
                        Box(modifier = Modifier.weight(1f)) {
                            WeaponListItem(
                                weapon = weapon,
                                onInfoClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                                    context.startActivity(intent)
                                },
                                onDetailClick = { onNavigateToDetail(weapon.id) }
                            )
                        }
                    }
                    if (rowWeapons.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        } else {
            items(weapons) { weapon ->
                WeaponListItem(
                    weapon = weapon,
                    onInfoClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(weapon.infoUrl))
                        context.startActivity(intent)
                    },
                    onDetailClick = { onNavigateToDetail(weapon.id) }
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
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                modifier = Modifier.size(100.dp, 140.dp)
            ) {
                Image(
                    painter = painterResource(id = weapon.imageResId),
                    contentDescription = weapon.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = weapon.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = weapon.type, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(text = stringResource(R.string.description_label), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        text = weapon.description,
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalButton(onClick = onInfoClick) {
                        Text(stringResource(R.string.btn_info))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
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
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onDetailClick,
        modifier = Modifier
            .padding(8.dp)
            .width(140.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(id = weapon.imageResId),
                    contentDescription = weapon.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weapon.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            FilledTonalButton(
                onClick = onInfoClick,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(stringResource(R.string.btn_web_info), fontSize = 12.sp)
            }
        }
    }
}