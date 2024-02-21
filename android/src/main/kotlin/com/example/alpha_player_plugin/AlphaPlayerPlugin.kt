package com.example.alpha_player_plugin

import android.util.LongSparseArray
import androidx.annotation.NonNull
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger

/**
 * Created by pengboboer.
 * Date: 2023/3/28
 */
class AlphaPlayerPlugin : FlutterPlugin, AndroidAlphaVideoPlayerApi {

    private val videoPlayers = LongSparseArray<AlphaPlayerView>()

    private fun getVideoPlayer(id:Long):AlphaPlayerView? {
        if (videoPlayers.indexOfKey(id) < 0) {
            return null
        }
        return videoPlayers[id]
    }

    private fun getAssetsPath(name:String, packageName:String?):String {
        return if (packageName?.isNotEmpty() == true) {
            FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(name, packageName)
        } else {
            FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(name)
        }
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        val messenger: BinaryMessenger = flutterPluginBinding.binaryMessenger
        flutterPluginBinding
                .platformViewRegistry
                .registerViewFactory(
                        "alpha_player_view_factory", AlphaPlayerViewFactory(messenger, videoPlayers)
                )
        AndroidAlphaVideoPlayerApi.setUp(messenger, this)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        AndroidAlphaVideoPlayerApi.setUp(binding.binaryMessenger, null)
    }

    override fun initialize(msg: InitMessage) {
        var dataSource = msg.dataSource
        val videoAlign = msg.videoAlign
        val looping = msg.isLooping
        if (msg.dataSourceType == DataSourceType.ASSETS) {
           dataSource = "asset:///${getAssetsPath(dataSource, packageName = null)}"
        }
        getVideoPlayer(msg.viewId)?.startVideo(dataSource, videoAlign.raw, looping)
    }

    override fun created(msg: CreateMessage) {
        TODO("Not yet implemented")
    }

    override fun dispose(msg: TextureMessage) {
        val viewId = msg.textureId
        getVideoPlayer(viewId)?.dispose()
        videoPlayers.remove(viewId)
    }

    override fun setLooping(msg: LoopingMessage) {
        TODO("Not yet implemented")
    }

    override fun setVolume(msg: VolumeMessage) {
        TODO("Not yet implemented")
    }

    override fun setPlaybackSpeed(msg: PlaybackSpeedMessage) {
        TODO("Not yet implemented")
    }

    override fun play(msg: TextureMessage) {
        getVideoPlayer(msg.textureId)?.play()
    }

    override fun position(msg: TextureMessage): PositionMessage {
        TODO("Not yet implemented")
    }

    override fun seekTo(msg: PositionMessage) {
        TODO("Not yet implemented")
    }

    override fun pause(msg: TextureMessage) {
        getVideoPlayer(msg.textureId)?.pause()
    }
}
