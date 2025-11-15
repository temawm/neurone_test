package com.example.test_neurone.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_neurone.R
import com.example.test_neurone.core.theme.Test_neuroneTheme


// хедер для всех экранов
@Composable
fun Header(backClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(48.dp)
                .clickable(
                    onClick = { backClick() }
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "arrow_back",
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Назад",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp
            )

        }
    }

}