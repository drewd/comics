package com.dobson.comics.ui.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dobson.comics.R
import com.dobson.comics.model.Comic
import com.dobson.comics.model.Image
import com.dobson.comics.ui.theme.AppIcons
import com.dobson.comics.ui.theme.AutoMirrored
import com.dobson.comics.ui.theme.ComicsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    comic: Comic,
    onBackClick: () -> Unit,
    onPreviousClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
) {
    Scaffold(topBar = {
        TopAppBar(title = { }, colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ), actions = {
            TextButton(
                onClick = { onBackClick() }, colors = textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    AppIcons.Close, contentDescription = stringResource(R.string.close)
                )
            }
        })
    }, bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            BottomNavigation(
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick,
            )
        }
    }) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
        ) {
            Header(thumbnail = comic.thumbnail)
            Text(
                // TODO What should this display if it is not available?
                text = comic.title ?: "",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(
                    horizontal = 8.dp, vertical = 16.dp
                ),
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 8.dp)

            )
            Text(
                text = stringResource(R.string.details_header_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    top = 16.dp, start = 8.dp, end = 8.dp
                ),
            )
            Text(
                // TODO What should be displayed if this is not available?
                text = comic.textObjects?.find { o -> o.type == "issue_solicit_text" }?.text?.run {
                    replace("<br>", "\n")
                } ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Header(
    thumbnail: Image?,
) {
    Box(
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        GlideImage(
            // TODO Get Loading and failure images
            model = thumbnail?.url,
            contentDescription = stringResource(R.string.content_comic_cover),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .blur(
                    radius = 16.dp
                )
        )

        Row(
            modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO Get Loading and failure images
            GlideImage(
                model = thumbnail?.url,
                contentDescription = stringResource(R.string.content_comic_cover),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(200.dp)
                    .weight(weight = 1f),
            )
            Column(
                modifier = Modifier.weight(weight = 2f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = { },
                ) {
                    Text(
                        stringResource(R.string.read_now).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 12.dp),
                    )
                }
                IconButton(
                    stringResource(R.string.mark_as_read),
                    rememberVectorPainter(AppIcons.CheckCircle)
                )
                IconButton(
                    stringResource(R.string.add_to_library),
                    rememberVectorPainter(AppIcons.AddCircle)
                )
                IconButton(
                    stringResource(R.string.read_offline),
                    painterResource(id = R.drawable.ic_download)
                )
            }
        }
    }
}

@Composable
fun IconButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit = { },
) {
    FilledTonalButton(
        modifier = Modifier.height(40.dp),
        shape = RectangleShape, onClick = onClick, contentPadding = PaddingValues(all = 4.dp)
    ) {
        Icon(
            icon,
            contentDescription = text,
            modifier = Modifier.padding(4.dp),
        )
        VerticalDivider(
            modifier = Modifier.padding(horizontal = 4.dp),
        )
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomNavigation(
    onPreviousClick: (() -> Unit)? = null,
    onNextClick: (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = { onPreviousClick?.invoke() },
            enabled = onPreviousClick != null,
            modifier = Modifier,
            colors = textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            shape = RectangleShape,
        ) {
            Icon(
                AutoMirrored.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.previous),
            )
            Text(
                text = stringResource(R.string.previous).uppercase(),
            )
        }
        TextButton(
            onClick = { onNextClick?.invoke() },
            shape = RectangleShape,
            enabled = onNextClick != null,
            colors = textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text(
                text = stringResource(R.string.next).uppercase(),
            )
            Icon(
                AutoMirrored.KeyboardArrowRight,
                contentDescription = stringResource(R.string.next),
            )
        }
    }

}


@Preview(
    name = "Bottom Navigation",
    showBackground = true,
)
@Composable
fun PreviewBottomNavigation() {
    ComicsTheme {
        BottomNavigation()
    }
}

@Preview(
    name = "IconButton"
)
@Composable
fun PreviewIconButton() {
    ComicsTheme {
        IconButton(
            text = "A button", icon = rememberVectorPainter(AppIcons.CheckCircle)
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
)
@Composable
fun PreviewDetailsScreen() {
    ComicsTheme {
        DetailsScreen(comic = Comic(
            id = 1,
            title = "My first comic",
            description = "Some long description",
        ), onBackClick = {})
    }
}
