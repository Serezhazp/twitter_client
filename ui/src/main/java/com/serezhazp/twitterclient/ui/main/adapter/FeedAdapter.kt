package com.serezhazp.twitterclient.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.serezhazp.twitterclient.domain.Tweet
import com.serezhazp.twitterclient.ui.R
import java.text.SimpleDateFormat
import java.util.*

class FeedAdapter : RecyclerView.Adapter<FeedAdapter.TweetViewHolder>() {

    private var tweets = mutableListOf<Tweet>()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder =
        TweetViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tweet_layout, null)
        )

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = tweets[position]

        val userName = tweet.user.userName
        val userNickName = tweet.user.nickName
        val userInfoSource = "$userName @$userNickName ${tweet.getAge(holder.itemView.context)}"
        val userInfo = SpannableString(userInfoSource)
        userInfo.setSpan(
            ForegroundColorSpan(holder.itemView.context.resources.getColor(android.R.color.black)),
            0,
            userName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        userInfo.setSpan(
            ForegroundColorSpan(holder.itemView.context.resources.getColor(R.color.twiBlue)),
            userInfoSource.indexOf(userNickName) - 1,
            userInfoSource.indexOf(userNickName) + userNickName.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        holder.user.text = userInfo
        holder.text.text = tweet.text

        tweet.hashTags?.let {
            if (it.isNotEmpty()) {
                holder.hashTags.visibility = View.VISIBLE
                holder.hashTags.text = tweet.hashTags?.joinToString(transform = { entity ->
                    "#$entity"
                })
            } else {
                holder.hashTags.visibility = View.GONE
            }
        }

        val anim = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(holder.itemView.context)
            .load(tweet.user.avatar)
            .apply(RequestOptions.circleCropTransform())
            .transition(withCrossFade(anim))
            .into(holder.avatar)

        val roundedCornersTransform = RequestOptions().transform(
            RoundedCorners(
                holder.itemView.context.resources.getDimension(R.dimen.common_16dp).toInt()
            )
        )

        Glide.with(holder.itemView.context)
            .load(tweet.media)
            .apply(roundedCornersTransform)
            .transition(withCrossFade(anim))
            .into(holder.media)
    }

    fun setLastTweet(tweet: Tweet) {
        this.tweets.add(0, tweet)
        notifyItemInserted(0)
    }

    fun setData(tweets: List<Tweet>) {
        this.tweets.clear()
        this.tweets.addAll(tweets)
        notifyDataSetChanged()
    }

    class TweetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val user: TextView = view.findViewById(R.id.tweet_user_and_info)
        val text: TextView = view.findViewById(R.id.tweet_text)
        val avatar: ImageView = view.findViewById(R.id.tweet_avatar)
        val media: ImageView = view.findViewById(R.id.tweet_media)
        val hashTags: TextView = view.findViewById(R.id.tweet_hash_tags)
    }

    private fun Tweet.getAge(context: Context): String {
        val minuteMs = 60 * 1000
        val hourMs = 60 * 60 * 1000
        val dayMs = 24 * 60 * 60 * 1000

        val twitterDatePattern = "EEE MMM d HH:mm:ss Z yyyy"
        val dateFormatter = SimpleDateFormat(twitterDatePattern, Locale.ENGLISH)
        val dateFormatterToShow = SimpleDateFormat("d/MM/yyyy, HH:mm", Locale.ENGLISH)

        val date = dateFormatter.parse(createdAt).time
        val dateNow = Date().time

        val diffMs = dateNow - date

        return when (diffMs) {
            in 0..minuteMs -> context.getString(R.string.post_age_just_now)
            in minuteMs..hourMs -> {
                val minutes = diffMs / 60 / 1000
                String.format(context.getString(R.string.post_age_minutes), minutes)
            }
            in hourMs..dayMs -> {
                val hours = diffMs / 60 / 60 / 1000
                String.format(context.getString(R.string.post_age_hours), hours)
            }
            else -> dateFormatterToShow.format(Date(date))
        }
    }
}