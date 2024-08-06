package com.atabekdev.mytaxitest.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.atabekdev.mytaxitest.R

@Composable
fun MainCard(resId: Int) {
    Card(
        modifier = Modifier.size(56.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(14.dp),
        onClick = {}
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(resId),
                contentDescription = "zoom_plus"
            )
        }
    }
}

@Composable
fun LiftBottomSheetCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(56.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 4.dp, color = MaterialTheme.colorScheme.onBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_lift_btmsheet),
                contentDescription = "zoom_plus",
            )
        }
    }
}
