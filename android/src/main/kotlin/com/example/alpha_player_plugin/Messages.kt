// Autogenerated from Pigeon (v9.2.5), do not edit directly.
// See also: https://pub.dev/packages/pigeon

package com.example.alpha_player_plugin

import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private fun wrapResult(result: Any?): List<Any?> {
  return listOf(result)
}

private fun wrapError(exception: Throwable): List<Any?> {
  if (exception is FlutterError) {
    return listOf(
      exception.code,
      exception.message,
      exception.details
    )
  } else {
    return listOf(
      exception.javaClass.simpleName,
      exception.toString(),
      "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  }
}

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

enum class DataSourceType(val raw: Int) {
  ASSETS(0),
  URL(1),
  FILE(2);

  companion object {
    fun ofRaw(raw: Int): DataSourceType? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

enum class AlphaPlayerScaleType(val raw: Int) {
  SCALETOFILL(0),
  SCALEASPECTFITCENTER(1),
  SCALEASPECTFILL(2),
  TOPFILL(3),
  BOTTOMFILL(4),
  LEFTFILL(5),
  RIGHTFILL(6),
  TOPFIT(7),
  BOTTOMFIT(8),
  LEFTFIT(9),
  RIGHTFIT(10);

  companion object {
    fun ofRaw(raw: Int): AlphaPlayerScaleType? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

enum class AlphaVideoEvent(val raw: Int) {
  INIT(0),
  PLAY(1),
  PAUSE(2),
  COMPLATE(3),
  ERROR(4);

  companion object {
    fun ofRaw(raw: Int): AlphaVideoEvent? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class TextureMessage (
  val textureId: Long

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): TextureMessage {
      val textureId = list[0].let { if (it is Int) it.toLong() else it as Long }
      return TextureMessage(textureId)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      textureId,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class LoopingMessage (
  val textureId: Long,
  val isLooping: Boolean

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): LoopingMessage {
      val textureId = list[0].let { if (it is Int) it.toLong() else it as Long }
      val isLooping = list[1] as Boolean
      return LoopingMessage(textureId, isLooping)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      textureId,
      isLooping,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class VolumeMessage (
  val textureId: Long,
  val volume: Double

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): VolumeMessage {
      val textureId = list[0].let { if (it is Int) it.toLong() else it as Long }
      val volume = list[1] as Double
      return VolumeMessage(textureId, volume)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      textureId,
      volume,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class PlaybackSpeedMessage (
  val textureId: Long,
  val speed: Double

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): PlaybackSpeedMessage {
      val textureId = list[0].let { if (it is Int) it.toLong() else it as Long }
      val speed = list[1] as Double
      return PlaybackSpeedMessage(textureId, speed)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      textureId,
      speed,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class PositionMessage (
  val textureId: Long,
  val position: Long

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): PositionMessage {
      val textureId = list[0].let { if (it is Int) it.toLong() else it as Long }
      val position = list[1].let { if (it is Int) it.toLong() else it as Long }
      return PositionMessage(textureId, position)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      textureId,
      position,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class CreateMessage (
  val viewID: Long,
  val asset: String? = null,
  val uri: String? = null,
  val packageName: String? = null,
  val formatHint: String? = null,
  val httpHeaders: Map<String?, String?>? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): CreateMessage {
      val viewID = list[0].let { if (it is Int) it.toLong() else it as Long }
      val asset = list[1] as String?
      val uri = list[2] as String?
      val packageName = list[3] as String?
      val formatHint = list[4] as String?
      val httpHeaders = list[5] as Map<String?, String?>?
      return CreateMessage(viewID, asset, uri, packageName, formatHint, httpHeaders)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      viewID,
      asset,
      uri,
      packageName,
      formatHint,
      httpHeaders,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class InitMessage (
  val viewId: Long,
  val dataSource: String,
  val dataSourceType: DataSourceType,
  val videoAlign: AlphaPlayerScaleType,
  val isLooping: Boolean

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): InitMessage {
      val viewId = list[0].let { if (it is Int) it.toLong() else it as Long }
      val dataSource = list[1] as String
      val dataSourceType = DataSourceType.ofRaw(list[2] as Int)!!
      val videoAlign = AlphaPlayerScaleType.ofRaw(list[3] as Int)!!
      val isLooping = list[4] as Boolean
      return InitMessage(viewId, dataSource, dataSourceType, videoAlign, isLooping)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      viewId,
      dataSource,
      dataSourceType.raw,
      videoAlign.raw,
      isLooping,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class AlphaVideoEventMessage (
  val viewId: Long,
  val event: AlphaVideoEvent,
  val duration: Long? = null,
  val width: Long? = null,
  val height: Long? = null

) {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromList(list: List<Any?>): AlphaVideoEventMessage {
      val viewId = list[0].let { if (it is Int) it.toLong() else it as Long }
      val event = AlphaVideoEvent.ofRaw(list[1] as Int)!!
      val duration = list[2].let { if (it is Int) it.toLong() else it as Long? }
      val width = list[3].let { if (it is Int) it.toLong() else it as Long? }
      val height = list[4].let { if (it is Int) it.toLong() else it as Long? }
      return AlphaVideoEventMessage(viewId, event, duration, width, height)
    }
  }
  fun toList(): List<Any?> {
    return listOf<Any?>(
      viewId,
      event.raw,
      duration,
      width,
      height,
    )
  }
}
@Suppress("UNCHECKED_CAST")
private object AndroidAlphaVideoPlayerApiCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      128.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          CreateMessage.fromList(it)
        }
      }
      129.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          InitMessage.fromList(it)
        }
      }
      130.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          LoopingMessage.fromList(it)
        }
      }
      131.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          PlaybackSpeedMessage.fromList(it)
        }
      }
      132.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          PositionMessage.fromList(it)
        }
      }
      133.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          TextureMessage.fromList(it)
        }
      }
      134.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          VolumeMessage.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is CreateMessage -> {
        stream.write(128)
        writeValue(stream, value.toList())
      }
      is InitMessage -> {
        stream.write(129)
        writeValue(stream, value.toList())
      }
      is LoopingMessage -> {
        stream.write(130)
        writeValue(stream, value.toList())
      }
      is PlaybackSpeedMessage -> {
        stream.write(131)
        writeValue(stream, value.toList())
      }
      is PositionMessage -> {
        stream.write(132)
        writeValue(stream, value.toList())
      }
      is TextureMessage -> {
        stream.write(133)
        writeValue(stream, value.toList())
      }
      is VolumeMessage -> {
        stream.write(134)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface AndroidAlphaVideoPlayerApi {
  fun initialize(msg: InitMessage)
  fun created(msg: CreateMessage)
  fun dispose(msg: TextureMessage)
  fun setLooping(msg: LoopingMessage)
  fun setVolume(msg: VolumeMessage)
  fun setPlaybackSpeed(msg: PlaybackSpeedMessage)
  fun play(msg: TextureMessage)
  fun position(msg: TextureMessage): PositionMessage
  fun seekTo(msg: PositionMessage)
  fun pause(msg: TextureMessage)

  companion object {
    /** The codec used by AndroidAlphaVideoPlayerApi. */
    val codec: MessageCodec<Any?> by lazy {
      AndroidAlphaVideoPlayerApiCodec
    }
    /** Sets up an instance of `AndroidAlphaVideoPlayerApi` to handle messages through the `binaryMessenger`. */
    @Suppress("UNCHECKED_CAST")
    fun setUp(binaryMessenger: BinaryMessenger, api: AndroidAlphaVideoPlayerApi?) {
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.initialize", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as InitMessage
            var wrapped: List<Any?>
            try {
              api.initialize(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.created", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as CreateMessage
            var wrapped: List<Any?>
            try {
              api.created(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.dispose", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as TextureMessage
            var wrapped: List<Any?>
            try {
              api.dispose(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.setLooping", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as LoopingMessage
            var wrapped: List<Any?>
            try {
              api.setLooping(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.setVolume", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as VolumeMessage
            var wrapped: List<Any?>
            try {
              api.setVolume(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.setPlaybackSpeed", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as PlaybackSpeedMessage
            var wrapped: List<Any?>
            try {
              api.setPlaybackSpeed(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.play", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as TextureMessage
            var wrapped: List<Any?>
            try {
              api.play(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.position", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as TextureMessage
            var wrapped: List<Any?>
            try {
              wrapped = listOf<Any?>(api.position(msgArg))
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.seekTo", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as PositionMessage
            var wrapped: List<Any?>
            try {
              api.seekTo(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.AndroidAlphaVideoPlayerApi.pause", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val msgArg = args[0] as TextureMessage
            var wrapped: List<Any?>
            try {
              api.pause(msgArg)
              wrapped = listOf<Any?>(null)
            } catch (exception: Throwable) {
              wrapped = wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
@Suppress("UNCHECKED_CAST")
private object FlutterAlphaVideoPlayerApiCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      128.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          AlphaVideoEventMessage.fromList(it)
        }
      }
      129.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          TextureMessage.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is AlphaVideoEventMessage -> {
        stream.write(128)
        writeValue(stream, value.toList())
      }
      is TextureMessage -> {
        stream.write(129)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated class from Pigeon that represents Flutter messages that can be called from Kotlin. */
@Suppress("UNCHECKED_CAST")
class FlutterAlphaVideoPlayerApi(private val binaryMessenger: BinaryMessenger) {
  companion object {
    /** The codec used by FlutterAlphaVideoPlayerApi. */
    val codec: MessageCodec<Any?> by lazy {
      FlutterAlphaVideoPlayerApiCodec
    }
  }
  fun onVideoError(msgArg: TextureMessage, errorMsgArg: String, callback: () -> Unit) {
    val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.FlutterAlphaVideoPlayerApi.onVideoError", codec)
    channel.send(listOf(msgArg, errorMsgArg)) {
      callback()
    }
  }
  fun onVideoEvent(msgArg: AlphaVideoEventMessage, callback: () -> Unit) {
    val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.FlutterAlphaVideoPlayerApi.onVideoEvent", codec)
    channel.send(listOf(msgArg)) {
      callback()
    }
  }
}
