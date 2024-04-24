package com.dobson.comics.ui.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.dobson.comics.model.Comic
import com.dobson.comics.ui.theme.ComicsTheme
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun title() {
        composeTestRule.setContent {
            ComicsTheme {
                DetailsScreen(comic = Comic(
                    id = 1,
                    title = "MY TITLE",
                ), onBackClick = { })
            }
        }
        composeTestRule.onNodeWithText("MY TITLE").assertIsDisplayed()
    }

    @Test
    fun previousDisabled() {
        composeTestRule.setContent {
            ComicsTheme {
                DetailsScreen(
                    comic = Comic(
                        id = 1,
                    ), onBackClick = { }, onPreviousClick = null
                )
            }
        }
        composeTestRule.onNodeWithText("PREVIOUS").assertIsNotEnabled()
    }

    @Test
    fun previousEnabledAndClicked() {
        val mockClickListener = mockk<() -> Unit>(relaxed = true)
        composeTestRule.setContent {
            ComicsTheme {
                DetailsScreen(
                    comic = Comic(
                        id = 1,
                    ), onBackClick = { }, onPreviousClick = mockClickListener
                )
            }
        }
        composeTestRule.onNodeWithText("PREVIOUS")
            .assertIsEnabled()
            .performClick()
        verify { mockClickListener.invoke() }
    }
}