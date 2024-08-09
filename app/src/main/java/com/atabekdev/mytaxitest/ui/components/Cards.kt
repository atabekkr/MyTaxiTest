package com.atabekdev.mytaxitest.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.atabekdev.mytaxitest.R
import com.atabekdev.mytaxitest.ui.theme.Green
import com.atabekdev.mytaxitest.ui.theme.White90

@Composable
fun Controller(resId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(56.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(14.dp),
        onClick = {
            onClick()
        }
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
            .size(56.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 4.dp, color = MaterialTheme.colorScheme.onBackground),
        onClick = onClick
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

@Composable
fun HamburgerCard() {
    Card(
        modifier = Modifier.size(56.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(14.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.onSecondary)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "img1"
            )
        }
    }
}

@Composable
fun Card95() {
    Card(
        modifier = Modifier.size(56.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Green).clickable(
                    onClick = {},
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.onSecondary)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "95",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
