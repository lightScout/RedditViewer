package com.lightscout.redditviewer

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lightscout.redditviewer.ui.compose.LoadingView
import com.lightscout.redditviewer.ui.theme.RedditViewerTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            RedditViewerTheme {
                LoadingView()
            }
        }
    }

    @Test
    fun test_LoadingView_Is_Visible() {
        composeTestRule.onNodeWithTag("loading_view").assertExists()
    }

}