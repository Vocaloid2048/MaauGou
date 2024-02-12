package com.voc2048.maogou.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.squareup.picasso.Picasso
import com.voc2048.maogou.R
import com.voc2048.maogou.data.MaoGou
import java.util.Date
import java.util.Objects
import kotlin.math.roundToInt
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private var context: Context = this
    private var activity: Activity = this
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var displayMetrics: DisplayMetrics

    lateinit var maogou_body_img : ImageView
    lateinit var maogou_eyes_img : ImageView
    lateinit var maogou_fur_img : ImageView
    lateinit var maogou_head_img : ImageView
    lateinit var maogou_tail_img : ImageView
    lateinit var maogou_sticker_img : ImageView
    lateinit var maogou_special : ImageView
    lateinit var maogou_body_ll : LinearLayout
    lateinit var maogou_eyes_ll : LinearLayout
    lateinit var maogou_fur_ll : LinearLayout
    lateinit var maogou_head_ll : LinearLayout
    lateinit var maogou_tail_ll : LinearLayout
    lateinit var maogou_sticker_ll : LinearLayout
    lateinit var maogou_merge : FrameLayout
    lateinit var maogou_rand_gen_btn : Button
    lateinit var maogou_save_btn : CardView
    lateinit var maogou_id_tv : TextView

    var maogouNow : MaoGou = MaoGou(5,2,0,4,0,0,520400)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize global variable
        displayMetrics = resources.displayMetrics

        //initialize main page
        maogou_body_img = findViewById<ImageView>(R.id.maogou_body_img)
        maogou_eyes_img = findViewById<ImageView>(R.id.maogou_eyes_img)
        maogou_fur_img = findViewById<ImageView>(R.id.maogou_fur_img)
        maogou_head_img = findViewById<ImageView>(R.id.maogou_head_img)
        maogou_tail_img = findViewById<ImageView>(R.id.maogou_tail_img)
        maogou_sticker_img = findViewById<ImageView>(R.id.maogou_sticker_img)
        maogou_special = findViewById<ImageView>(R.id.maogou_special)
        maogou_body_ll = findViewById<LinearLayout>(R.id.maogou_body_ll)
        maogou_eyes_ll = findViewById<LinearLayout>(R.id.maogou_eyes_ll)
        maogou_fur_ll = findViewById<LinearLayout>(R.id.maogou_fur_ll)
        maogou_head_ll = findViewById<LinearLayout>(R.id.maogou_head_ll)
        maogou_tail_ll = findViewById<LinearLayout>(R.id.maogou_tail_ll)
        maogou_sticker_ll = findViewById<LinearLayout>(R.id.maogou_sticker_ll)
        maogou_rand_gen_btn = findViewById<Button>(R.id.maogou_rand_gen_btn)
        maogou_merge = findViewById<FrameLayout>(R.id.maogou_merge)
        maogou_save_btn = findViewById<CardView>(R.id.maogou_download_btn)
        maogou_id_tv = findViewById<TextView>(R.id.maogou_id_tv)

        //initialize list of maogou's part
        addMaogouInLL(maogou_body_ll,MaoGou.MaoGouType.BODY,maogou_body_img)
        addMaogouInLL(maogou_eyes_ll,MaoGou.MaoGouType.EYES,maogou_eyes_img)
        addMaogouInLL(maogou_fur_ll,MaoGou.MaoGouType.FUR,maogou_fur_img)
        addMaogouInLL(maogou_head_ll,MaoGou.MaoGouType.HEAD,maogou_head_img)
        addMaogouInLL(maogou_tail_ll,MaoGou.MaoGouType.TAIL,maogou_tail_img)
        addMaogouInLL(maogou_sticker_ll,MaoGou.MaoGouType.STICKER,maogou_sticker_img)

        //initialize maogou random button
        maogou_rand_gen_btn.setOnClickListener{
            randomMaoGou(0,20)
        }

        //initialize save maogou image
        maogou_save_btn.setOnClickListener{
            requestPermission()
            if (isWritePermissionGranted) {
                val fileName = "maogou_"+System.currentTimeMillis();
                if (saveImageToExternalStorage(fileName,getBitmapFromView(maogou_merge,context))) {
                    Toast.makeText(context,"Saved "+fileName+".png successfully!",Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context,"Unable to get storage permission ...",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun randomMaoGou(loopTimes : Int, targetTimes : Int){
        if(loopTimes >= targetTimes) {
            //init maogou linearlayout
            val maogouLLArray = arrayOf(maogou_body_ll,maogou_eyes_ll,maogou_fur_ll,maogou_head_ll,maogou_tail_ll,maogou_sticker_ll) //Body, Eyes, Fur, Head, Tail, Sticker
            for (linearlayout in maogouLLArray){
                for(child in linearlayout.children){
                    child.alpha = if(linearlayout.indexOfChild(child) == maogouNow.getMaoGouPartValue(maogouLLArray.indexOf(linearlayout))){1f} else 0.2f
                }
            }

            //Check whether user got the 1% of showing rare maogou
            if(Random.nextDouble(0.0,1.0) >= 0.99){
                maogou_special.setImageResource(MaoGou.getMaoGouSpecialArray()[Random(System.currentTimeMillis()).nextInt(MaoGou.getMaoGouSpecialArray().size)])
            }else{
                maogou_special.setImageResource(R.drawable.bg_transparent)
            }
            updateMaogouId()
            return //Done !
        };

        //Randomize and save the record of each maogou parts
        for (index in 0..5){
            maogouNow.setMaoGouPartValue(index,  Random(System.currentTimeMillis()+index*1024).nextInt(MaoGou.getMaoGouImageArray()[index].size))
        }

        //Refresh maogou's id
        maogouNow.updateMaoGouId()

        //Randomizing, the main ImageView must show each randomize result
        val maogouArrays = MaoGou.getMaoGouImageArray()
        maogou_special.setImageResource(R.drawable.bg_transparent)
        maogou_body_img.setImageResource(maogouArrays[0][maogouNow.body])
        maogou_eyes_img.setImageResource(maogouArrays[1][maogouNow.eyes])
        maogou_fur_img.setImageResource(maogouArrays[2][maogouNow.fur])
        maogou_head_img.setImageResource(maogouArrays[3][maogouNow.head])
        maogou_tail_img.setImageResource(maogouArrays[4][maogouNow.tail])
        maogou_sticker_img.setImageResource(maogouArrays[5][maogouNow.sticker])
        Handler().postDelayed({
            randomMaoGou(loopTimes + 1, targetTimes)
        },20)
    }

    fun addMaogouInLL(maogouLL : LinearLayout, maoGouType: MaoGou.MaoGouType, displayImage : ImageView){
        val maogouUIArr = MaoGou.findMaoGouUIIDByType(maoGouType)
        val maogouArr = MaoGou.findMaoGouImageIDByType(maoGouType)
        val maogouPartIndex = maoGouType.ordinal
        //init layoutParams
        var layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        var widthOfIcons = (
                if (displayMetrics.widthPixels < maogouUIArr.size * (48+16) * displayMetrics.density ) {
                    displayMetrics.widthPixels / maogouUIArr.size - 16 * displayMetrics.density
                } else {
                    48 * displayMetrics.density
                }).roundToInt()
        layoutParams.height = widthOfIcons
        layoutParams.width = widthOfIcons
        layoutParams.leftMargin = (8 * displayMetrics.density).toInt()
        layoutParams.rightMargin = (8 * displayMetrics.density).toInt()

        //Setup each maogou part choicec in each maogou linearlayout
        for(maogouUI in maogouUIArr){
            val uiImage = ImageView(context)
            uiImage.layoutParams = layoutParams
            uiImage.scaleType = ScaleType.CENTER_INSIDE
            Picasso.get()
                .load(maogouUI)
                .fit()
                .centerInside()
                .error(R.drawable.baseline_do_not_disturb_24)
                .into(uiImage)

            uiImage.setOnClickListener {
                displayImage.setImageDrawable(context.getDrawable(maogouArr[maogouUIArr.indexOf(maogouUI)]))
                maogouNow.setMaoGouPartValue(maogouPartIndex, maogouUIArr.indexOf(maogouUI))
                for (maogouView in maogouLL.children){
                    maogouView.alpha = 0.2f
                }
                uiImage.alpha = 1f
                updateMaogouId()
            }

            uiImage.alpha = 0.2f
            if(maogouNow.getMaoGouPartValue(maogouPartIndex) == maogouUIArr.indexOf(maogouUI)){
                uiImage.alpha = 1f
            }

            maogouLL.addView(uiImage)
        }
        maogouLL.setVerticalGravity(Gravity.CENTER_VERTICAL)
        maogouLL.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
    }

    fun updateMaogouId(){
        maogouNow.updateMaoGouId()
        maogou_id_tv.text = (if(maogouNow.maoGouId < 100000){"0"} else "") + maogouNow.maoGouId.toString();
    }


    /**
     * Tools for saving image
     */
    private fun saveImageToExternalStorage(imgName: String, bmp: Bitmap): Boolean {
        val resolver = context.contentResolver
        var ImageCollection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "$imgName.png")
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, Date().time)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        val imageUri = resolver.insert(ImageCollection, contentValues)

        if(imageUri == null){
            println("imageUri is null!")
            return false;
        }

        try {
            val outputStream = resolver.openOutputStream(Objects.requireNonNull(imageUri))
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream!!)
            Objects.requireNonNull(outputStream)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun getBitmapFromView(view: View, context: Context): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.background = context.getDrawable(R.drawable.bg_transparent)
        view.draw(canvas)
        return bitmap
    }


    private fun requestPermission() {
        val minSDK = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        isWritePermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        isWritePermissionGranted = isWritePermissionGranted || minSDK
    }

}