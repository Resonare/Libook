package com.example.test3.ui.components.book.show

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test3.R
import com.example.test3.data.entities.Thought
import kotlin.math.absoluteValue
import kotlin.math.min

@Composable
fun MyThoughtCard(thought: Thought, handleDeleteThought: (String) -> Unit) {
    val density = LocalDensity.current

    val bountyOffset = 50.dp
    val maxOffset = 80.dp

    var offset by remember { mutableFloatStateOf(0f) }
    val animateOffset by animateFloatAsState(
        targetValue = min(
            with(density) { maxOffset.toPx() },
            offset.absoluteValue
        ),
    )

    var deleteButtonIsShown by remember { mutableStateOf(false) }

    Box (
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
            .background(MaterialTheme.colorScheme.error)
    ) {
        Box (
            modifier = Modifier
                .width(bountyOffset)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .clickable {
                    handleDeleteThought(thought.id)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_bin),
                contentDescription = "Delete thought"
            )
        }

        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    end =
                        with(density) {
                            animateOffset.toDp()
                        }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 10.dp)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures (
                            onDragEnd = {
                                offset =
                                    if (offset < -with(density) { bountyOffset.toPx() }) {
                                        deleteButtonIsShown = !deleteButtonIsShown

                                        if (deleteButtonIsShown) {
                                            -with(density) { bountyOffset.toPx() }
                                        } else {
                                            0f
                                        }
                                    } else {
                                        deleteButtonIsShown = false

                                        0f
                                    }
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                offset += dragAmount
                                if (offset > 0) offset = 0f

                                change.consume()
                            }
                        )
                    },
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.CenterStart),
                    text = thought.content,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.secondary,
                )

                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .background(thought.color)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}