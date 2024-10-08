package com.atabekdev.mytaxitest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atabekdev.mytaxitest.R

@Preview
@Composable
fun BottomSheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .background(
                    MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            SheetCard(title = "Tarif", number = "6/8", R.drawable.ic_tariff)
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            SheetCard(title = "Buyurtmalar", number = "0", R.drawable.ic_order)
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            SheetCard(title = "Bordur", number = "", R.drawable.ic_rocket)
        }
    }
}

@Composable
fun SheetCard(title: String, number: String, resId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "tarif",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = number,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(end = 8.dp)
        )
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = "next"
        )
    }
}
