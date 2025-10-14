import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.test3.FilterOption
import com.example.test3.R

@Composable
fun FilterBar(onFilterSelect: (FilterOption) -> Unit) {
    val filterOptions = listOf(
        FilterOption(R.string.filter_new),
        FilterOption(R.string.filter_genre),
        FilterOption(R.string.filter_author),
        FilterOption(R.string.filter_popular),

        FilterOption(R.string.filter_new),
        FilterOption(R.string.filter_genre),
        FilterOption(R.string.filter_author),
        FilterOption(R.string.filter_popular),

        FilterOption(R.string.filter_new),
        FilterOption(R.string.filter_genre),
        FilterOption(R.string.filter_author),
        FilterOption(R.string.filter_popular),
    )

    val listState = rememberLazyListState()
    var leftFadeVisible by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { offset ->
                leftFadeVisible = offset != 0
            }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(filterOptions) { filterOption ->
                val rightPadding = if (filterOption == filterOptions.last()) 26.dp else 0.dp

                Text(
                    modifier = Modifier.padding(end = rightPadding),
                    text = stringResource(filterOption.titleId),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        if (leftFadeVisible) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .align(Alignment.CenterStart)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .align(Alignment.CenterEnd)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
    }
}