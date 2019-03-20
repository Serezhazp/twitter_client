package com.serezhazp.twitterclient.framework

import com.twitter.sdk.android.core.models.*
import org.junit.Assert.assertEquals
import org.junit.Test

class TweetMapperTest {

    @Test
    fun isTweetMappingCorrect() {
        val mediaEntityOne = MediaEntity(
            mediaOne,
            mediaOne,
            mediaOne,
            0,
            1,
            12345,
            "12345",
            mediaOne,
            mediaOne,
            null,
            0,
            null,
            null,
            null,
            mediaOne
        )

        val mediaEntityTwo = MediaEntity(
            mediaTwo,
            mediaTwo,
            mediaTwo,
            0,
            1,
            54321,
            "54321",
            mediaTwo,
            mediaTwo,
            null,
            0,
            null,
            null,
            null,
            mediaTwo
        )

        val twitterEntities =
            TweetEntities(
                null, null, mutableListOf(mediaEntityOne, mediaEntityTwo), mutableListOf(
                    HashtagEntity(hashTagOne, 0, 1)
                ), null
            )

        val twitterUser = User(
            false,
            "Fri Mar 13 11:59:06 +0000 2009",
            false,
            false,
            "Capable and Ergonomic IDE for the JVM Platform",
            null,
            null,
            778,
            false,
            73525,
            19,
            false,
            24160034,
            "24160034",
            false,
            "en",
            1348,
            "JetBrains",
            "IntelliJ IDEA",
            "133463",
            "http://abs.twimg.com/images/themes/theme10/bg.gif",
            "https://abs.twimg.com/images/themes/theme10/bg.gif",
            true,
            "https://pbs.twimg.com/profile_banners/24160034/1490196598",
            "http://pbs.twimg.com/profile_images/803204448675856388/6eqoPNuy_normal.jpg",
            "https://pbs.twimg.com/profile_images/803204448675856388/6eqoPNuy_normal.jpg",
            "365FB7",
            "FFFFFF",
            "F4EFDC",
            "000000",
            true,
            false,
            "intellijidea",
            false,
            null,
            2820,
            null,
            "https://t.co/PfswqrFFQp",
            0,
            false,
            null,
            null
        )

        val tweet = Tweet(
            null,
            "Tue Mar 19 15:28:02 +0000 2019",
            null,
            twitterEntities,
            null,
            54,
            false,
            null,
            1108027355027726337,
            "1108027355027726337",
            null,
            0,
            null,
            0,
            null,
            "en",
            null,
            false,
            null,
            0,
            "0",
            null,
            21,
            false,
            null,
            "<a href=\"https://sproutsocial.com\" rel=\"nofollow\">Sprout Social</a>",
            "#Java12 is out today! IntelliJ IDEA 2019.1 has full support for Java 12 features, including new Switch Expressions. Check out this blog post https://t.co/pKn5JC8fBB, or this video for more details https://t.co/sN9d3hMYiR",
            null,
            false,
            twitterUser,
            false,
            null,
            null,
            null
        )

        val mappedTweet = tweet.mapTweet()

        assertEquals(
            "#Java12 is out today! IntelliJ IDEA 2019.1 has full support for Java 12 features, including new Switch Expressions. Check out this blog post https://t.co/pKn5JC8fBB, or this video for more details https://t.co/sN9d3hMYiR",
            mappedTweet.text
        )

        assertEquals("Tue Mar 19 15:28:02 +0000 2019", mappedTweet.createdAt)
        assertEquals(tweet.entities.hashtags.size, mappedTweet.hashTags?.size)
        assertEquals(tweet.entities.hashtags[0].text, mappedTweet.hashTags?.get(0))
        assertEquals(tweet.entities.media[0].mediaUrlHttps, mappedTweet.media)
        assertEquals(tweet.user.name, mappedTweet.user.userName)
        assertEquals(tweet.user.screenName, mappedTweet.user.nickName)
        assertEquals(tweet.user.profileImageUrlHttps, mappedTweet.user.avatar)
    }

    companion object {
        const val mediaOne = "media_one"
        const val mediaTwo = "media_two"
        const val hashTagOne = "hash_tag_one"
    }
}