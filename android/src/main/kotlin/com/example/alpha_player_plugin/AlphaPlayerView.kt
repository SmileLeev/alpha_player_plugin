package com.example.alpha_player_plugin

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.ss.ugc.android.alpha_player.IMonitor
import com.ss.ugc.android.alpha_player.IPlayerAction
import com.ss.ugc.android.alpha_player.controller.IPlayerController
import com.ss.ugc.android.alpha_player.controller.PlayerController
import com.ss.ugc.android.alpha_player.model.AlphaVideoDirection
import com.ss.ugc.android.alpha_player.model.AlphaVideoViewType
import com.ss.ugc.android.alpha_player.model.Configuration
import com.ss.ugc.android.alpha_player.model.DataSource
import com.ss.ugc.android.alpha_player.model.ScaleType
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView

/**
 * Created by pengboboer.
 * Date: 2023/3/28
 */
class AlphaPlayerView(context: Context, private val viewId: Int, private val messenger: BinaryMessenger) :
        PlatformView, RelativeLayout(context), LifecycleOwner {
    private val TAG = "AlphaPlayerView"


    private var mRegistry = LifecycleRegistry(this)

    private var mPlayerController: IPlayerController? = null

    private var dataSourcePath: String? = null

    private val flutterApi = FlutterAlphaVideoPlayerApi(messenger)

    private val playerAction = object : IPlayerAction {
        override fun onVideoSizeChanged(videoWidth: Int, videoHeight: Int, scaleType: ScaleType) {
            Log.i(TAG,
                    "call onVideoSizeChanged(), videoWidth = $videoWidth, videoHeight = $videoHeight, scaleType = $scaleType"
            )
        }

        override fun startAction() {
            Log.i(TAG, "call startAction()")
            callbackFlutter(AlphaVideoEvent.PLAY)
        }

        override fun endAction() {
            Log.i(TAG, "call endAction")
            callbackFlutter(AlphaVideoEvent.COMPLATE)
        }
    }

    private fun callbackFlutter(event: AlphaVideoEvent) {
        post {
            flutterApi.onVideoEvent(
                AlphaVideoEventMessage(
                    viewId = viewId.toLong(),
                    event = event
                )
            ) {
                Log.w(TAG, "call onVideoEvent error, event=$event")
            }
        }
    }

    private fun callbackFlutterError(msg: String) {
        post {
            flutterApi.onVideoError(
                TextureMessage(viewId.toLong()),
                msg
            ) {
                Log.w(TAG, "call onVideoError error, msg=$msg")
            }
        }
    }

    private val monitor = object : IMonitor {
        override fun monitor(state: Boolean, playType: String, what: Int, extra: Int, errorInfo: String) {
            Log.i(TAG,
                    "call monitor(), state: $state, playType = $playType, what = $what, extra = $extra, errorInfo = $errorInfo"
            )
            if (state == false) {
                callbackFlutterError(msg = errorInfo)
            }
        }
    }

    init {
        val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
        )
        setLayoutParams(layoutParams)
        initPlayerController(context, this)
    }

    override fun getView(): View = this

    override fun dispose() {
        releasePlayerController()
        detachView()
    }

    override fun getLifecycle(): Lifecycle = mRegistry

    private fun initPlayerController(context: Context, owner: LifecycleOwner) {
        val configuration = Configuration(context, owner)
        //  GLTextureView supports custom display layer, but GLSurfaceView has better performance, and the GLSurfaceView is default.
        configuration.alphaVideoViewType = AlphaVideoViewType.GL_TEXTURE_VIEW
        configuration.alphaVideoDirection = AlphaVideoDirection.BOTTOM
        //  You can implement your IMediaPlayer, here we use ExoPlayerImpl that implemented by ExoPlayer, and
        //  we support DefaultSystemPlayer as default player.
        mPlayerController = PlayerController.get(configuration, ExoPlayerImpl(context))
        mPlayerController?.let {
            it.setPlayerAction(playerAction)
            it.setMonitor(monitor)
        }

        attachView()
    }

    fun startVideo(dataSourcePath: String, align: Int?, looping: Boolean?) {
        if (TextUtils.isEmpty(dataSourcePath)) {
            return
        }
        this.dataSourcePath = dataSourcePath

        val scaleType = align ?: 2
        val isLooping = looping ?: false

        val dataSource = DataSource().setPortraitPath(dataSourcePath, scaleType)
                .setLandscapePath(dataSourcePath, scaleType).setLooping(isLooping)
        if (dataSource.isValid()) {
            mPlayerController?.start(dataSource)
        }
    }

    private fun attachView() {
        mPlayerController?.attachAlphaView(this)
    }

    fun detachView() {
        mPlayerController?.detachAlphaView(this)
    }

    fun releasePlayerController() {
        mPlayerController?.let {
            it.detachAlphaView(this)
            it.release()
        }
    }

    fun play() {
        mPlayerController?.play()
    }

    fun pause(){
        mPlayerController?.pause()
    }

    fun resume(){
        mPlayerController?.resume()
    }


}