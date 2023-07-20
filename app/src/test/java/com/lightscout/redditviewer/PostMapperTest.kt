package com.lightscout.redditviewer

import com.lightscout.redditviewer.model.data.Children
import com.lightscout.redditviewer.model.data.Data
import com.lightscout.redditviewer.model.data.DataX
import com.lightscout.redditviewer.model.data.Gildings
import com.lightscout.redditviewer.model.data.MediaEmbed
import com.lightscout.redditviewer.model.data.Preview
import com.lightscout.redditviewer.model.data.RedditResponse
import com.lightscout.redditviewer.model.data.SecureMediaEmbed
import com.lightscout.redditviewer.util.Common
import com.lightscout.redditviewer.util.PostMapperImpl
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PostMapperTest {
    private val postMapper = PostMapperImpl()

    @Test
    fun `map RedditResponse to List of Post`() {
        // Create a RedditResponse object
        val result = postMapper.map(redditResponse)

        // Check if there is only one post in the result
        assertEquals(1, result.size)

        // Check the properties of the post
        val post = result[0]

        assertEquals("154vzkn", post.id)
        assertEquals(
            "AT&amp;T says lead cables in Lake Tahoe “pose no danger” and should stay in place",
            post.title
        )
        assertEquals(
            "https://b.thumbs.redditmedia.com/39-Wco80VO3qkqvzqoP6YJ3Ehr8a6V-tSIMiCZHwB8k.jpg",
            post.imageUrl
        )
        assertEquals("blueredscreen", post.author)
        assertEquals(Common().secondsToDays(1689871016), post.timeStamp)
        assertEquals(1689871016, post.rawTimeStamp)
        assertEquals("Business", post.flairText)
        assertEquals("1", post.ups)
        assertEquals("0", post.downs)
        assertEquals("2", post.numComments)
    }

    companion object {
        private val redditResponseChild = Children(
            DataX(
                all_awardings = listOf(),
                allow_live_comments = false,
                approved_at_utc = 0,
                approved_by = 0,
                archived = false,
                author = "blueredscreen",
                author_flair_background_color = 0,
                author_flair_css_class = 0,
                author_flair_richtext = listOf(),
                author_flair_template_id = 0,
                author_flair_text = 0,
                author_flair_text_color = 0,
                author_flair_type = "text",
                author_fullname = "t2_qkw3h",
                author_is_blocked = false,
                author_patreon_flair = false,
                author_premium = false,
                awarders = listOf(),
                banned_at_utc = 0,
                banned_by = 0,
                can_gild = false,
                can_mod_post = false,
                category = 0,
                clicked = false,
                content_categories = 0,
                contest_mode = false,
                created = 1689871016.toDouble(),
                created_utc = 1689871016.toDouble(),
                discussion_type = 0,
                distinguished = 0,
                domain = "arstechnica.com",
                downs = 0,
                edited = false,
                gilded = 0,
                gildings = Gildings(),
                hidden = false,
                hide_score = true,
                id = "154vzkn",
                is_created_from_ads_ui = false,
                is_crosspostable = false,
                is_meta = false,
                is_original_content = false,
                is_reddit_media_domain = false,
                is_robot_indexable = true,
                is_self = false,
                is_video = false,
                likes = 0,
                link_flair_background_color = "",
                link_flair_css_class = "general",
                link_flair_richtext = listOf(),
                link_flair_template_id = "49cac61c-a816-11e9-be34-0ebbab5890a0",
                link_flair_text = "Business",
                link_flair_text_color = "dark",
                link_flair_type = "text",
                locked = false,
                media = 0,
                media_embed = MediaEmbed(),
                media_only = false,
                mod_note = 0,
                mod_reason_by = 0,
                mod_reason_title = 0,
                mod_reports = listOf(),
                name = "t3_154vzkn",
                no_follow = false,
                num_comments = 2,
                num_crossposts = 0,
                num_reports = 0,
                over_18 = false,
                parent_whitelist_status = "all_ads",
                permalink = "/r/technology/comments/154vzkn/att_says_lead_cables_in_lake_tahoe_pose_no_danger/",
                pinned = false,
                post_hint = "link",
                preview = Preview(false, listOf()),
                pwls = 6,
                quarantine = false,
                removal_reason = 0,
                removed_by = 0,
                removed_by_category = 0,
                report_reasons = 0,
                saved = false,
                score = 1,
                secure_media = 0,
                secure_media_embed = SecureMediaEmbed(),
                selftext = "",
                selftext_html = 0,
                send_replies = true,
                spoiler = false,
                stickied = false,
                subreddit = "technology",
                subreddit_id = "t5_2qh16",
                subreddit_name_prefixed = "r/technology",
                subreddit_subscribers = 14489466,
                subreddit_type = "public",
                suggested_sort = 0,
                thumbnail = "https://b.thumbs.redditmedia.com/39-Wco80VO3qkqvzqoP6YJ3Ehr8a6V-tSIMiCZHwB8k.jpg",
                thumbnail_height = 70,
                thumbnail_width = 140,
                title = "AT&amp;T says lead cables in Lake Tahoe “pose no danger” and should stay in place",
                top_awarded_type = 0,
                total_awards_received = 0,
                treatment_tags = listOf(),
                ups = 1,
                upvote_ratio = 1.toDouble(),
                url = "https://arstechnica.com/tech-policy/2023/07/att-may-have-nearly-200000-miles-of-lead-covered-phone-cables-across-us/",
                url_overridden_by_dest = "https://arstechnica.com/tech-policy/2023/07/att-may-have-nearly-200000-miles-of-lead-covered-phone-cables-across-us/",
                user_reports = listOf(),
                view_count = 0,
                visited = false,
                whitelist_status = "all_ads",
                wls = 6
            ),
            kind = "t1"
        )

        // create a RedditResponse using the child
        private val redditResponse = RedditResponse(
            Data(
                after = "after",
                before = "before",
                children = listOf(redditResponseChild),
                dist = 1,
                modhash = "",
                geo_filter = ""
            ),
            ""
        )
    }
}