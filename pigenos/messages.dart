import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/messages.g.dart',
  kotlinOut: 'android/src/main/kotlin/com/example/alpha_player_plugin/Messages.kt',
  kotlinOptions: KotlinOptions(
    package: 'com.example.alpha_player_plugin'
  ),
))
class TextureMessage {
  TextureMessage(this.textureId);
  int textureId;
}

class LoopingMessage {
  LoopingMessage(this.textureId, this.isLooping);
  int textureId;
  bool isLooping;
}

class VolumeMessage {
  VolumeMessage(this.textureId, this.volume);
  int textureId;
  double volume;
}

class PlaybackSpeedMessage {
  PlaybackSpeedMessage(this.textureId, this.speed);
  int textureId;
  double speed;
}

class PositionMessage {
  PositionMessage(this.textureId, this.position);
  int textureId;
  int position;
}

class CreateMessage {
  CreateMessage({required this.viewID});
  int viewID;
  String? asset;
  String? uri;
  String? packageName;
  String? formatHint;
  Map<String?, String?>? httpHeaders;
}

class InitMessage {
  InitMessage(this.viewId,this.dataSource, this.dataSourceType, this.videoAlign, this.isLooping);
  final int viewId;
  final String dataSource;
  final DataSourceType dataSourceType;
  final AlphaPlayerScaleType videoAlign;
  final bool isLooping;
}

class AlphaVideoEventMessage {
  final int viewId;
  final AlphaVideoEvent event;
  final int? duration;
  final int? width;
  final int? height;

  AlphaVideoEventMessage(this.viewId, this.event, this.duration, this.width, this.height);
}

enum DataSourceType {
  Assets,Url,File
}

enum AlphaPlayerScaleType {
  ScaleToFill, //  拉伸铺满全屏
  ScaleAspectFitCenter, //  等比例缩放对齐全屏，居中，屏幕多余留空
  ScaleAspectFill, //  等比例缩放铺满全屏，裁剪视频多余部分
  TopFill, //  等比例缩放铺满全屏，顶部对齐
  BottomFill, //  等比例缩放铺满全屏，底部对齐
  LeftFill, //  等比例缩放铺满全屏，左边对齐
  RightFill, //  等比例缩放铺满全屏，右边对齐
  TopFit, //  等比例缩放至屏幕宽度，顶部对齐，底部留空
  BottomFit, //  等比例缩放至屏幕宽度，底部对齐，顶部留空
  LeftFit, //  等比例缩放至屏幕高度，左边对齐，右边留空
  RightFit //  等比例缩放至屏幕高度，右边对齐，左边留空
}

enum AlphaVideoEvent {
  Init,Play,Pause,Complate,Error
}

@HostApi(dartHostTestHandler: 'TestHostVideoPlayerApi')
abstract class AndroidAlphaVideoPlayerApi {
  void initialize(InitMessage msg);
  void created(CreateMessage msg);
  void dispose(TextureMessage msg);
  void setLooping(LoopingMessage msg);
  void setVolume(VolumeMessage msg);
  void setPlaybackSpeed(PlaybackSpeedMessage msg);
  void play(TextureMessage msg);
  PositionMessage position(TextureMessage msg);
  void seekTo(PositionMessage msg);
  void pause(TextureMessage msg);
}

@FlutterApi()
abstract class FlutterAlphaVideoPlayerApi {
  void onVideoError(TextureMessage msg, String errorMsg);
  void onVideoEvent(AlphaVideoEventMessage msg);
}
