package consultan.vanke.com.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import consultan.vanke.com.R

object MediaplayerUtils {
    /**
     * 播放系统自带的提示音(是属于铃声类型)
     *
     * @param context
     */
    fun playSystem(context: Context?) {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context, notification)
        r.play()
    }

    /**
     * 播放当前项目中所指定的音频(属于媒体类型)
     *
     * @param context
     */
    @JvmStatic
    fun playInternal(context: Context?) {
        val mMediaPlayer = MediaPlayer.create(context, R.raw.notice)
        mMediaPlayer.start()
    }
}