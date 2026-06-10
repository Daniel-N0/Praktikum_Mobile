package com.example.wwmweaponscompose.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.wwmweaponscompose.R
import com.example.wwmweaponscompose.di.WWMApplication
import com.example.wwmweaponscompose.ui.theme.WWMWeaponsComposeTheme
import com.example.wwmweaponscompose.viewmodel.WeaponViewModel
import com.example.wwmweaponscompose.viewmodel.WeaponViewModelFactory

@Composable
fun DetailScreen(
    weaponId: Int?,
    modifier: Modifier = Modifier.Companion
) {
    val context = LocalContext.current
    val app = context.applicationContext as WWMApplication

    val viewModel: WeaponViewModel = viewModel(
        factory = WeaponViewModelFactory(
            "Halaman Detail Senjata Compose",
            app.useCase
        )
    )

    val weapons by viewModel.weaponList.collectAsState()
    val weapon = weapons.find { it.id == weaponId }

    weapon?.let {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier.Companion
                    .wrapContentWidth()
                    .align(Alignment.Companion.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                AsyncImage(
                    model = it.imageUrl,
                    contentDescription = it.name,
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.size(250.dp, 350.dp)
                )
            }

            Spacer(modifier = Modifier.Companion.height(24.dp))

            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Companion.Bottom
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Companion.Bold,
                    modifier = Modifier.Companion.weight(1f)
                )
                Text(
                    text = it.type,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.Companion.height(16.dp))

            Text(
                text = stringResource(R.string.detail_description_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Companion.Bold
            )

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Text(
                text = it.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    WWMWeaponsComposeTheme {
        DetailScreen(weaponId = 1)
    }
}