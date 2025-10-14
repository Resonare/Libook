package com.example.test3.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test3.R

@Composable
fun BookDescriptionItem() {
    var annotationIsEllipsized by remember { mutableStateOf(false) }
    var showFullAnnotation by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Кубок Огня — приз за победу в Турнире трёх волшебников, который состоится в Хогвартсе при участии учеников.sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss",
            overflow = TextOverflow.Ellipsis,
            maxLines =
                if (showFullAnnotation) Int.MAX_VALUE
                else 3,
            style = MaterialTheme.typography.bodySmall.copy(textIndent = TextIndent(firstLine = 24.sp)),
            color = MaterialTheme.colorScheme.secondary,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                annotationIsEllipsized = textLayoutResult.hasVisualOverflow
            }
        )

        if (annotationIsEllipsized || showFullAnnotation) {
            Spacer(Modifier.height(3.dp))

            Text(
                modifier = Modifier.clickable {
                    showFullAnnotation = !showFullAnnotation
                },
                text = stringResource(
                    if (showFullAnnotation) R.string.hide_full_annotation
                    else R.string.show_full_annotation
                ),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}