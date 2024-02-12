package com.voc2048.maogou.data

import com.voc2048.maogou.R
//Body, Eyes, Fur, Head, Tail, Sticker
class MaoGou(
    var body: Int,
    var eyes: Int,
    var fur: Int,
    var head: Int,
    var tail: Int,
    var sticker: Int,
    var maoGouId: Int
) {

    enum class MaoGouType() {
        BODY,EYES,FUR,HEAD,TAIL,STICKER

    }
    companion object {
        private val maogouBodyArr: Array<Int> = arrayOf(
            R.drawable.maogou_body1,
            R.drawable.maogou_body2,
            R.drawable.maogou_body3,
            R.drawable.maogou_body4,
            R.drawable.maogou_body5,
            R.drawable.maogou_body6
        )
        private val maogouHeadArr: Array<Int> = arrayOf(
            R.drawable.maogou_head1,
            R.drawable.maogou_head2,
            R.drawable.maogou_head3,
            R.drawable.maogou_head4,
            R.drawable.maogou_head5,
            R.drawable.maogou_head6,
            R.drawable.maogou_head7
        )
        private val maogouFurArr: Array<Int> = arrayOf(
            R.drawable.bg_transparent,
            R.drawable.maogou_face1,
            R.drawable.maogou_face2,
            R.drawable.maogou_face3,
            R.drawable.maogou_face4,
            R.drawable.maogou_face5
        )
        private val maogouTailArr: Array<Int> = arrayOf(
            R.drawable.bg_transparent,
            R.drawable.maogou_tail1,
            R.drawable.maogou_tail2,
            R.drawable.maogou_tail3,
            R.drawable.maogou_tail4,
            R.drawable.maogou_tail5
        )
        private val maogouStickerArr: Array<Int> = arrayOf(
            R.drawable.bg_transparent,
            R.drawable.maogou_hat1,
            R.drawable.maogou_hat2,
            R.drawable.maogou_hat3,
            R.drawable.maogou_hat4,
            R.drawable.maogou_hat5
        )
        private val maogouEyesArr: Array<Int> = arrayOf(
            R.drawable.maogou_eye1,
            R.drawable.maogou_eye2,
            R.drawable.maogou_eye3,
            R.drawable.maogou_eye4,
            R.drawable.maogou_eye5,
            R.drawable.maogou_eye6
        )
        private val maogouSpecialArr: Array<Int> = arrayOf(
            R.drawable.maogou_blade,
            R.drawable.maogou_clara,
            R.drawable.maogou_dan_heng,
            R.drawable.maogou_guinaifen,
            R.drawable.maogou_herta,
            R.drawable.maogou_kafka,
            R.drawable.maogou_march_7th,
            R.drawable.maogou_qingque,
            R.drawable.maogou_ruan_mei,
            R.drawable.maogou_trailblazer
        )

        private val maogouBodyUIArr: Array<Int> = arrayOf(
            R.drawable.maogou_body1_ui,
            R.drawable.maogou_body2_ui,
            R.drawable.maogou_body3_ui,
            R.drawable.maogou_body4_ui,
            R.drawable.maogou_body5_ui,
            R.drawable.maogou_body6_ui
        )
        private val maogouHeadUIArr: Array<Int> = arrayOf(
            R.drawable.maogou_head1_ui,
            R.drawable.maogou_head2_ui,
            R.drawable.maogou_head3_ui,
            R.drawable.maogou_head4_ui,
            R.drawable.maogou_head5_ui,
            R.drawable.maogou_head6_ui,
            R.drawable.maogou_head7_ui
        )
        private val maogouFurUIArr: Array<Int> = arrayOf(
            R.drawable.baseline_do_not_disturb_24,
            R.drawable.maogou_face1_ui,
            R.drawable.maogou_face2_ui,
            R.drawable.maogou_face3_ui,
            R.drawable.maogou_face4_ui,
            R.drawable.maogou_face5_ui
        )
        private val maogouTailUIArr: Array<Int> = arrayOf(
            R.drawable.baseline_do_not_disturb_24,
            R.drawable.maogou_tail1_ui,
            R.drawable.maogou_tail2_ui,
            R.drawable.maogou_tail3_ui,
            R.drawable.maogou_tail4_ui,
            R.drawable.maogou_tail5_ui
        )
        private val maogouStickerUIArr: Array<Int> = arrayOf(
            R.drawable.baseline_do_not_disturb_24,
            R.drawable.maogou_hat1_ui,
            R.drawable.maogou_hat2_ui,
            R.drawable.maogou_hat3_ui,
            R.drawable.maogou_hat4_ui,
            R.drawable.maogou_hat5_ui
        )
        private val maogouEyesUIArr: Array<Int> = arrayOf(
            R.drawable.maogou_eye1_ui,
            R.drawable.maogou_eye2_ui,
            R.drawable.maogou_eye3_ui,
            R.drawable.maogou_eye4_ui,
            R.drawable.maogou_eye5_ui,
            R.drawable.maogou_eye6_ui
        )

     //Body, Eyes, Fur, Head, Tail, Sticker
        fun getMaoGouImageArray(): Array<Array<Int>> {
            return arrayOf(
                maogouBodyArr,
                maogouEyesArr,
                maogouFurArr,
                maogouHeadArr,
                maogouTailArr,
                maogouStickerArr
            )
        }

        fun getMaoGouUIArray(): Array<Array<Int>> {
            return arrayOf(
                maogouBodyUIArr,
                maogouEyesUIArr,
                maogouFurUIArr,
                maogouHeadUIArr,
                maogouTailUIArr,
                maogouStickerUIArr
            )
        }

        fun getMaoGouSpecialArray(): Array<Int> {
            return maogouSpecialArr
        }
        fun findMaoGouImageIDByType(type: MaoGouType): Array<Int> {
            return getMaoGouImageArray()[type.ordinal]
        }

        fun findMaoGouUIIDByType(type: MaoGouType): Array<Int> {
            return getMaoGouUIArray()[type.ordinal]
        }
    }

    fun updateMaoGouId(){
        maoGouId = body*100000 + eyes * 10000 + fur * 1000 + head * 100 + tail * 10 + sticker
    }

    fun getMaoGouPartValue(partId : Int) : Int {
        when(partId){
            MaoGouType.BODY.ordinal -> return body
            MaoGouType.EYES.ordinal -> return eyes
            MaoGouType.FUR.ordinal -> return fur
            MaoGouType.HEAD.ordinal -> return head
            MaoGouType.TAIL.ordinal -> return tail
            MaoGouType.STICKER.ordinal -> return sticker
        }
        return 0
    }
    fun setMaoGouPartValue(partId : Int, value : Int){
        when(partId){
            MaoGouType.BODY.ordinal -> body = value
            MaoGouType.EYES.ordinal -> eyes = value
            MaoGouType.FUR.ordinal -> fur = value
            MaoGouType.HEAD.ordinal -> head = value
            MaoGouType.TAIL.ordinal -> tail = value
            MaoGouType.STICKER.ordinal -> sticker = value
        }
    }
}