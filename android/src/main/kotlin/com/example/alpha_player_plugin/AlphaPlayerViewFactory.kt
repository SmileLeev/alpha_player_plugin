package com.example.alpha_player_plugin

import android.content.Context
import android.util.LongSparseArray
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

/**
 * Created by pengboboer.
 * Date: 2023/3/28
 */
class AlphaPlayerViewFactory(
    private val messenger: BinaryMessenger,
    private val videoPlayers: LongSparseArray<AlphaPlayerView>
) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val alphaPlayerView = AlphaPlayerView(context, viewId, messenger)
        videoPlayers.put(viewId.toLong(), alphaPlayerView)
        return alphaPlayerView
    }

}