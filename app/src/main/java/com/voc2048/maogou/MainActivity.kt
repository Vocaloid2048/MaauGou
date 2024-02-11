package com.voc2048.maogou

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
    val maogouBodyArr: Array<Int> = arrayOf(R.drawable.maogou_body1,R.drawable.maogou_body2,R.drawable.maogou_body3,R.drawable.maogou_body4,R.drawable.maogou_body5,R.drawable.maogou_body6)
    val maogouHeadArr: Array<Int> = arrayOf(R.drawable.maogou_head1,R.drawable.maogou_head2,R.drawable.maogou_head3,R.drawable.maogou_head4,R.drawable.maogou_head5,R.drawable.maogou_head6,R.drawable.maogou_head7)
    val maogouFurArr: Array<Int> = arrayOf(R.drawable.bg_transparent,R.drawable.maogou_face1,R.drawable.maogou_face2,R.drawable.maogou_face3,R.drawable.maogou_face4,R.drawable.maogou_face5)
    val maogouTailArr: Array<Int> = arrayOf(R.drawable.bg_transparent,R.drawable.maogou_tail1,R.drawable.maogou_tail2,R.drawable.maogou_tail3,R.drawable.maogou_tail4,R.drawable.maogou_tail5)
    val maogouStickerArr: Array<Int> = arrayOf(R.drawable.bg_transparent,R.drawable.maogou_hat1,R.drawable.maogou_hat2,R.drawable.maogou_hat3,R.drawable.maogou_hat4,R.drawable.maogou_hat5)
    val maogouEyesArr: Array<Int> = arrayOf(R.drawable.maogou_eye1,R.drawable.maogou_eye2,R.drawable.maogou_eye3,R.drawable.maogou_eye4,R.drawable.maogou_eye5,R.drawable.maogou_eye6)
    val maogouSpecialArr: Array<Int> = arrayOf(R.drawable.maogou_blade,R.drawable.maogou_clara,R.drawable.maogou_dan_heng,R.drawable.maogou_guinaifen,R.drawable.maogou_herta,R.drawable.maogou_kafka,R.drawable.maogou_march_7th,R.drawable.maogou_qingque,R.drawable.maogou_ruan_mei,R.drawable.maogou_trailblazer)

    val maogouBodyUIArr: Array<Int> = arrayOf(R.drawable.maogou_body1_ui,R.drawable.maogou_body2_ui,R.drawable.maogou_body3_ui,R.drawable.maogou_body4_ui,R.drawable.maogou_body5_ui,R.drawable.maogou_body6_ui)
    val maogouHeadUIArr: Array<Int> = arrayOf(R.drawable.maogou_head1_ui,R.drawable.maogou_head2_ui,R.drawable.maogou_head3_ui,R.drawable.maogou_head4_ui,R.drawable.maogou_head5_ui,R.drawable.maogou_head6_ui,R.drawable.maogou_head7_ui)
    val maogouFurUIArr: Array<Int> = arrayOf(R.drawable.baseline_do_not_disturb_24,R.drawable.maogou_face1_ui,R.drawable.maogou_face2_ui,R.drawable.maogou_face3_ui,R.drawable.maogou_face4_ui,R.drawable.maogou_face5_ui)
    val maogouTailUIArr: Array<Int> = arrayOf(R.drawable.baseline_do_not_disturb_24,R.drawable.maogou_tail1_ui,R.drawable.maogou_tail2_ui,R.drawable.maogou_tail3_ui,R.drawable.maogou_tail4_ui,R.drawable.maogou_tail5_ui)
    val maogouStickerUIArr: Array<Int> = arrayOf(R.drawable.baseline_do_not_disturb_24,R.drawable.maogou_hat1_ui,R.drawable.maogou_hat2_ui,R.drawable.maogou_hat3_ui,R.drawable.maogou_hat4_ui,R.drawable.maogou_hat5_ui)
    val maogouEyesUIArr: Array<Int> = arrayOf(R.drawable.maogou_eye1_ui,R.drawable.maogou_eye2_ui,R.drawable.maogou_eye3_ui,R.drawable.maogou_eye4_ui,R.drawable.maogou_eye5_ui,R.drawable.maogou_eye6_ui)

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
    var maogouSelectionArray = arrayOf(5,2,0,4,0,0) //Body, Eyes, Fur, Head, Tail, Sticker
    val maogouArray = arrayOf(maogouBodyArr,maogouEyesArr,maogouFurArr,maogouHeadArr,maogouTailArr,maogouStickerArr) //Body, Eyes, Fur, Head, Tail, Sticker
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
        addMaogouInLL(maogou_body_ll,maogouBodyUIArr,maogouBodyArr,0,maogou_body_img)
        addMaogouInLL(maogou_eyes_ll,maogouEyesUIArr,maogouEyesArr,1,maogou_eyes_img)
        addMaogouInLL(maogou_fur_ll,maogouFurUIArr,maogouFurArr,2,maogou_fur_img)
        addMaogouInLL(maogou_head_ll,maogouHeadUIArr,maogouHeadArr,3,maogou_head_img)
        addMaogouInLL(maogou_tail_ll,maogouTailUIArr,maogouTailArr,4,maogou_tail_img)
        addMaogouInLL(maogou_sticker_ll,maogouStickerUIArr,maogouStickerArr,5,maogou_sticker_img)

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
                    child.alpha = if(linearlayout.indexOfChild(child) == maogouSelectionArray[maogouLLArray.indexOf(linearlayout)]){1f} else 0.2f
                }
            }

            //Check whether user got the 1% of showing rare maogou
            if(Random.nextDouble(0.0,1.0) >= 0.99){
                maogou_special.setImageResource(maogouSpecialArr[Random(System.currentTimeMillis()).nextInt(maogouSpecialArr.size)])
            }else{
                maogou_special.setImageResource(R.drawable.bg_transparent)
            }
            updateMaogouId()
            return //Done !
        };

        //Randomize and save the record of each maogou parts
        for (index in maogouSelectionArray.indices){
            maogouSelectionArray[index] = Random(System.currentTimeMillis()+index*1024).nextInt(maogouArray[index].size)
        }

        //Randomizing, the main ImageView must show each randomize result
        maogou_special.setImageResource(R.drawable.bg_transparent)
        maogou_body_img.setImageResource(maogouBodyArr[maogouSelectionArray[0]])
        maogou_eyes_img.setImageResource(maogouEyesArr[maogouSelectionArray[1]])
        maogou_fur_img.setImageResource(maogouFurArr[maogouSelectionArray[2]])
        maogou_head_img.setImageResource(maogouHeadArr[maogouSelectionArray[3]])
        maogou_tail_img.setImageResource(maogouTailArr[maogouSelectionArray[4]])
        maogou_sticker_img.setImageResource(maogouStickerArr[maogouSelectionArray[5]])
        Handler().postDelayed({
            randomMaoGou(loopTimes + 1, targetTimes)
        },20)
    }

    fun addMaogouInLL(maogouLL : LinearLayout, maogouUIArr : Array<Int>,maogouArr : Array<Int>, maogouPartIndex : Int, displayImage : ImageView){
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
            uiImage.alpha = 0.2f
            uiImage.setOnClickListener {
                displayImage.setImageDrawable(context.getDrawable(maogouArr[maogouUIArr.indexOf(maogouUI)]))
                maogouSelectionArray[maogouPartIndex] = maogouUIArr.indexOf(maogouUI)
                for (maogouView in maogouLL.children){
                    maogouView.alpha = 0.2f
                }
                uiImage.alpha = 1f

                updateMaogouId()
            }
            if(maogouSelectionArray[maogouPartIndex] == maogouUIArr.indexOf(maogouUI)){
                uiImage.alpha = 1f
            }

            maogouLL.addView(uiImage)
        }
        maogouLL.setVerticalGravity(Gravity.CENTER_VERTICAL)
        maogouLL.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
    }

    fun updateMaogouId(){
        var id : String = ""
        for (value in maogouSelectionArray){
            id += value.toString()
        }
        maogou_id_tv.setText(id);
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