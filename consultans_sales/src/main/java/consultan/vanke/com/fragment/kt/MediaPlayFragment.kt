package consultan.vanke.com.fragment.kt

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import consultan.vanke.com.R
import consultan.vanke.com.fragment.BaseFragment
import consultan.vanke.com.utils.MediaplayerUtils
import consultan.vanke.com.utils.ToastUtils
import kotlinx.android.synthetic.main.media_play_fragment.*

class MediaPlayFragment : BaseFragment() {
    override fun initViews() {
    }

    override fun initDatas() {
    }

    override fun initEvents() {
        btn_sys.setOnClickListener {
            MediaplayerUtils.playSystem(_mActivity)
        }
        btn_inter.setOnClickListener {
            MediaplayerUtils.playInternal(_mActivity)
        }
    }

    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return View.inflate(_mActivity, R.layout.media_play_fragment, null)
    }
}