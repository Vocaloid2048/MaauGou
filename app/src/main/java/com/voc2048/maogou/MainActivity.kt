package com.voc2048.maogou

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import java.security.AccessController.getContext
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    var context: Context = this
    var activity: Activity = this
    lateinit var displayMetrics: DisplayMetrics
    val maogouBodyArr: Array<Int> = arrayOf(R.drawable.maogou_body1,R.drawable.maogou_body2,R.drawable.maogou_body3,R.drawable.maogou_body4,R.drawable.maogou_body5,R.drawable.maogou_body6)
    val maogouHeadArr: Array<Int> = arrayOf(R.drawable.maogou_head1,R.drawable.maogou_head2,R.drawable.maogou_head3,R.drawable.maogou_head4,R.drawable.maogou_head5,R.drawable.maogou_head6,R.drawable.maogou_head7)
    val maogouFurArr: Array<Int> = arrayOf(R.drawable.maogou_face1,R.drawable.maogou_face2,R.drawable.maogou_face3,R.drawable.maogou_face4,R.drawable.maogou_face5)
    val maogouTailArr: Array<Int> = arrayOf(R.drawable.maogou_tail1,R.drawable.maogou_tail2,R.drawable.maogou_tail3,R.drawable.maogou_tail4,R.drawable.maogou_tail5)
    val maogouStickerArr: Array<Int> = arrayOf(R.drawable.maogou_hat1,R.drawable.maogou_hat2,R.drawable.maogou_hat3,R.drawable.maogou_hat4,R.drawable.maogou_hat5)
    val maogouEyesArr: Array<Int> = arrayOf(R.drawable.maogou_eye1,R.drawable.maogou_eye2,R.drawable.maogou_eye3,R.drawable.maogou_eye4,R.drawable.maogou_eye5,R.drawable.maogou_eye6)

    val maogouBodyUIArr: Array<Int> = arrayOf(R.drawable.maogou_body1_ui,R.drawable.maogou_body2_ui,R.drawable.maogou_body3_ui,R.drawable.maogou_body4_ui,R.drawable.maogou_body5_ui,R.drawable.maogou_body6_ui)
    val maogouHeadUIArr: Array<Int> = arrayOf(R.drawable.maogou_head1_ui,R.drawable.maogou_head2_ui,R.drawable.maogou_head3_ui,R.drawable.maogou_head4_ui,R.drawable.maogou_head5_ui,R.drawable.maogou_head6_ui,R.drawable.maogou_head7)
    val maogouFurUIArr: Array<Int> = arrayOf(R.drawable.maogou_face1_ui,R.drawable.maogou_face2_ui,R.drawable.maogou_face3_ui,R.drawable.maogou_face4_ui,R.drawable.maogou_face5)
    val maogouTailUIArr: Array<Int> = arrayOf(R.drawable.maogou_tail1_ui,R.drawable.maogou_tail2_ui,R.drawable.maogou_tail3_ui,R.drawable.maogou_tail4_ui,R.drawable.maogou_tail5)
    val maogouStickerUIArr: Array<Int> = arrayOf(R.drawable.maogou_hat1_ui,R.drawable.maogou_hat2_ui,R.drawable.maogou_hat3_ui,R.drawable.maogou_hat4_ui,R.drawable.maogou_hat5)
    val maogouEyesUIArr: Array<Int> = arrayOf(R.drawable.maogou_eye1_ui,R.drawable.maogou_eye2_ui,R.drawable.maogou_eye3_ui,R.drawable.maogou_eye4_ui,R.drawable.maogou_eye5_ui,R.drawable.maogou_eye6)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize global variable
        displayMetrics = resources.displayMetrics

        //initialize main page
        val maogou_body_img = findViewById<ImageView>(R.id.maogou_body_img)
        val maogou_eyes_img = findViewById<ImageView>(R.id.maogou_eyes_img)
        val maogou_fur_img = findViewById<ImageView>(R.id.maogou_fur_img)
        val maogou_head_img = findViewById<ImageView>(R.id.maogou_head_img)
        val maogou_tail_img = findViewById<ImageView>(R.id.maogou_tail_img)
        val maogou_sticker_img = findViewById<ImageView>(R.id.maogou_sticker_img)
        val maogou_body_ll = findViewById<LinearLayout>(R.id.maogou_body_ll)
        val maogou_eyes_ll = findViewById<LinearLayout>(R.id.maogou_eyes_ll)
        val maogou_fur_ll = findViewById<LinearLayout>(R.id.maogou_fur_ll)
        val maogou_head_ll = findViewById<LinearLayout>(R.id.maogou_head_ll)
        val maogou_tail_ll = findViewById<LinearLayout>(R.id.maogou_tail_ll)
        val maogou_sticker_ll = findViewById<LinearLayout>(R.id.maogou_sticker_ll)
        val maogou_rand_gen_btn = findViewById<Button>(R.id.maogou_rand_gen_btn)

        addMaogouInLL(maogou_body_ll,maogouBodyUIArr,maogouBodyArr,maogou_body_img)
        addMaogouInLL(maogou_eyes_ll,maogouEyesUIArr,maogouEyesArr,maogou_eyes_img)
        addMaogouInLL(maogou_fur_ll,maogouFurUIArr,maogouFurUIArr,maogou_fur_img)
        addMaogouInLL(maogou_tail_ll,maogouTailArr,maogouTailArr,maogou_tail_img)
        addMaogouInLL(maogou_sticker_ll,maogouStickerUIArr,maogouStickerArr,maogou_sticker_img)
        addMaogouInLL(maogou_head_ll,maogouHeadUIArr,maogouHeadArr,maogou_head_img)
    }

    fun addMaogouInLL(maogouLL : LinearLayout, maogouUIArr : Array<Int>,maogouArr : Array<Int>, displayImage : ImageView){
        //init layoutParams
        var layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height = (
                if (displayMetrics.widthPixels < maogouUIArr.size * (48+16) * displayMetrics.density ) {
                    displayMetrics.widthPixels / maogouUIArr.size - 16 * displayMetrics.density
                } else {48 * displayMetrics.density}).roundToInt()
        layoutParams.width = (
                if (displayMetrics.widthPixels < maogouUIArr.size * (48+16) * displayMetrics.density ) {
                    displayMetrics.widthPixels / maogouUIArr.size - 16 * displayMetrics.density
                } else {48 * displayMetrics.density}).roundToInt()
        layoutParams.leftMargin = (8 * displayMetrics.density).toInt()
        layoutParams.rightMargin = (8 * displayMetrics.density).toInt()

        for(maogouUI in maogouUIArr){
            var uiImage = ImageView(context)

            uiImage.layoutParams = layoutParams
            uiImage.scaleType = ScaleType.CENTER_INSIDE
            Picasso.get()
                .load(maogouUI)
                .fit()
                .centerInside()
                .into(uiImage)
            uiImage.setOnClickListener {
                Picasso.get()
                    .load(maogouArr[maogouUIArr.indexOf(maogouUI)])
                    .into(displayImage)


            }

            maogouLL.addView(uiImage)

        }
        maogouLL.setVerticalGravity(Gravity.CENTER_VERTICAL)
        maogouLL.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
    }

    private fun View.addRipple() = with(TypedValue()) {
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
        setBackgroundResource(resourceId)
    }

}