package com.example.test3.ui.components.book.show

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test3.other.MyThought

@Composable
fun MyThoughtCard(myThought: MyThought) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .dropShadow(
                shape = RoundedCornerShape(5.dp),
                shadow = Shadow(
                    radius = 6.dp,
                    spread = 1.dp,
                    color = Color(0x40000000),
                )
            )
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 10.dp)

    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterStart),
            text = myThought.value,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
            color = MaterialTheme.colorScheme.secondary,
        )

        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(myThought.color)
                .align(Alignment.CenterStart)
        )
    }
}