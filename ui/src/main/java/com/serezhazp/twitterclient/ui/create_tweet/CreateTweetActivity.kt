package com.serezhazp.twitterclient.ui.create_tweet

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.serezhazp.twitterclient.domain.TweetToPost
import com.serezhazp.twitterclient.presentation.create_tweet.CreateTweetPresenter
import com.serezhazp.twitterclient.presentation.create_tweet.CreateTweetView
import com.serezhazp.twitterclient.ui.FILE_TO_POST_KEY
import com.serezhazp.twitterclient.ui.R
import com.serezhazp.twitterclient.ui.TWEET_TO_POST_KEY
import com.serezhazp.twitterclient.ui.xsupport.MvpXActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_create_tweet.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class CreateTweetActivity : MvpXActivity(), CreateTweetView {

    @Inject
    @InjectPresenter
    lateinit var presenter: CreateTweetPresenter

    @ProvidePresenter
    fun providePresenter(): CreateTweetPresenter = presenter

    var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tweet)

        post_tweet.setOnClickListener {
            val testTweetText = tweet_input.text.toString()
            presenter.postTweet(testTweetText)
        }

        take_photo.setOnClickListener {
            presenter.attachPhoto()
        }

        pick_image.setOnClickListener {
            presenter.attachImage()
        }

        close.setOnClickListener {
            presenter.close()
        }
    }

    override fun takePhoto() {
        dispatchTakePhotoIntent()
    }

    override fun pickImage() {
        dispatchPickImageIntent()
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun closeScreen() {
        onBackPressed()
    }

    override fun showEmptyTweetError() {
        showError(getString(R.string.error_empty_tweet))
    }

    override fun postTweet(tweet: TweetToPost) {
        val intent = Intent()
        intent.putExtra(TWEET_TO_POST_KEY, tweet.text)
        if (tweet.file != null) {
            intent.putExtra(FILE_TO_POST_KEY, tweet.file)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_PICTURE_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) return

                if (photoUri != null) {
                    CropImage.activity(photoUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this)
                }

            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) return

                val imageFile = File(CropImage.getActivityResult(data).uri.path)

                val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                tweet_image_preview.setImageBitmap(imageBitmap)

                val roundedCornersTransform =
                    RequestOptions().transform(RoundedCorners(resources.getDimension(R.dimen.common_24dp).toInt()))

                val anim = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

                Glide.with(this)
                    .load(imageBitmap)
                    .apply(roundedCornersTransform)
                    .transition(withCrossFade(anim))
                    .into(tweet_image_preview)

                presenter.setPhotoFile(imageFile)
            }
            IMAGE_PICKER_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) return

                photoUri = data?.data
                CropImage.activity(photoUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this)

            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun dispatchTakePhotoIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }

                photoFile?.also {
                    photoUri = FileProvider.getUriForFile(
                        this,
                        "com.serezhazp.twitterclient.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_PICTURE_REQUEST_CODE)
                }
            }
        }
    }

    private fun dispatchPickImageIntent() {
        Intent(Intent.ACTION_GET_CONTENT).also { pickImageIntent ->
            pickImageIntent.addCategory(Intent.CATEGORY_OPENABLE)
            pickImageIntent.type = "image/*"
            startActivityForResult(
                Intent.createChooser(pickImageIntent, getString(R.string.image_picker_title)),
                IMAGE_PICKER_REQUEST_CODE
            )
        }
    }

    companion object {
        const val CREATE_TWEET_REQUEST_CODE = 42

        private const val REQUEST_PICTURE_REQUEST_CODE = 43
        private const val IMAGE_PICKER_REQUEST_CODE = 44
    }
}