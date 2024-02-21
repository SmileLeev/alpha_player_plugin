import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

import 'messages.g.dart';

const String _viewType = "alpha_player_view_factory";
const String _channelName = "alpha_player_plugin_";

class AlphaPlayerView extends StatefulWidget {
  final double? width;
  final double? height;
  final AlphaPlayerController controller;
  final PlatformViewCreatedCallback? onCreated;

  /// 只有在looping为false的时候才有播放完成的回调
  final ValueChanged<String?>? onCompleted;

  const AlphaPlayerView({
    Key? key,
    required this.width,
    required this.height,
    required this.controller,
    this.onCreated,
    this.onCompleted,
  }) : super(key: key);

  @override
  State<AlphaPlayerView> createState() => _AlphaPlayerViewState();
}

class _AlphaPlayerViewState extends State<AlphaPlayerView> {
  var viewId = -1;

  @override
  void initState() {
    super.initState();
    FlutterAlphaVideoPlayerApi.setup(widget.controller);
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final Widget showWidget;
    if (Platform.isAndroid) {
      showWidget = AndroidView(
          viewType: _viewType,
          onPlatformViewCreated: _onPlatformViewCreated,
          creationParamsCodec: const StandardMessageCodec(),
          hitTestBehavior: PlatformViewHitTestBehavior.transparent);
    } else if (Platform.isIOS) {
      showWidget = UiKitView(
        viewType: _viewType,
        onPlatformViewCreated: _onPlatformViewCreated,
        creationParamsCodec: const StandardMessageCodec(),
        hitTestBehavior: PlatformViewHitTestBehavior.transparent,
        creationParams: {
          'width': widget.width,
          'height': widget.height,
        },
      );
    } else {
      showWidget = const Center(
        child: Text(
          "该平台暂不支持 platform View",
          style: TextStyle(color: Colors.red, fontWeight: FontWeight.bold),
        ),
      );
    }

    return SizedBox(
      width: widget.width,
      height: widget.height,
      child: showWidget,
    );
  }

  void _onPlatformViewCreated(int id) {
    widget.controller.createView(id);
    widget.onCreated?.call(id);
    // methodChannel = MethodChannel('$_channelName$id');
    // methodChannel?.setMethodCallHandler((call) async {
    //   switch (call.method) {
    //     case "playEnd":
    //       widget.onCompleted?.call(call.arguments?["filePath"]);
    //       break;
    //     default:
    //       break;
    //   }
    // });
  }
}

class AlphaPlayerController extends ValueNotifier<AlphaVideoValue> implements FlutterAlphaVideoPlayerApi {
  AlphaPlayerScaleType? videoAlign;
  bool? isLooping;
  AndroidAlphaVideoPlayerApi _api = AndroidAlphaVideoPlayerApi();

  AlphaPlayerController.file(String path):
        super(AlphaVideoValue(dataSourcePath: path, dataSourceType: DataSourceType.File));
  AlphaPlayerController.networkUrl(Uri url):
        super(AlphaVideoValue(dataSourcePath: url.toString(), dataSourceType: DataSourceType.Url));
  AlphaPlayerController.assets(String name):
        super(AlphaVideoValue(dataSourcePath: name, dataSourceType: DataSourceType.Assets));

  void onVideoError(TextureMessage msg, String errorMsg){
    if (value.viewId == msg.textureId) {
      value = value.copyWith(hasError: true, complete: false, isPlaying: false);
    }
  }

  void onVideoEvent(AlphaVideoEventMessage msg) {
    if (value.viewId == msg.viewId) {
      switch(msg.event) {
        case AlphaVideoEvent.Init:
          value = value.copyWith(hasError: false, complete: false, isPlaying: false);
          break;
        case AlphaVideoEvent.Play:
          value = value.copyWith(hasError: false, complete: false, isPlaying: true);
          break;
        case AlphaVideoEvent.Pause:
          value = value.copyWith(hasError: false, complete: false, isPlaying: false);
          break;
        case AlphaVideoEvent.Complate:
          value = value.copyWith(hasError: false, complete: true, isPlaying: false);
          break;
        case AlphaVideoEvent.Error:
          value = value.copyWith(hasError: true, complete: false, isPlaying: false);
          break;
      }
    }
  }

  void createView(int viewId) {
    value = value.copyWith(viewId: viewId);
  }

  void start({
    AlphaPlayerScaleType? align,
    bool? isLooping,
  }) {
    var viewId = value.viewId;
    if (viewId == null) {
      throw Exception("viewId is null");
    }
    var path = value.dataSourcePath;
    if (path?.isNotEmpty != true) {
      throw Exception("path is null");
    }
    value = value.copyWith(dataSourcePath: path!);
    videoAlign = align;
    this.isLooping = isLooping;
    _api.initialize(InitMessage(viewId: viewId,
        dataSource: path,
        dataSourceType: value.dataSourceType,
        videoAlign: videoAlign ?? AlphaPlayerScaleType.ScaleAspectFitCenter,
        isLooping: isLooping ?? true));
  }

  void pause() {
    var viewId = value.viewId;
    if (viewId == null) {
      throw Exception("viewId is null");
    }
    _api.pause(TextureMessage(textureId: viewId));
  }

  void resume() {
    var viewId = value.viewId;
    if (viewId == null) {
      throw Exception("viewId is null");
    }
    _api.play(TextureMessage(textureId: viewId));
  }

  @override
  void dispose() {
    var viewId = value.viewId;
    if (viewId != null) {
      _api.dispose(TextureMessage(textureId:viewId));
    }
    super.dispose();
  }
}

extension ScaleTypeExtension on AlphaPlayerScaleType {
  int getValue() {
    if (this == AlphaPlayerScaleType.ScaleToFill) return 0;
    if (this == AlphaPlayerScaleType.ScaleAspectFitCenter) return 1;
    if (this == AlphaPlayerScaleType.ScaleAspectFill) return 2;
    if (this == AlphaPlayerScaleType.TopFill) return 3;
    if (this == AlphaPlayerScaleType.BottomFill) return 4;
    if (this == AlphaPlayerScaleType.LeftFill) return 5;
    if (this == AlphaPlayerScaleType.RightFill) return 6;
    if (this == AlphaPlayerScaleType.TopFit) return 7;
    if (this == AlphaPlayerScaleType.BottomFit) return 8;
    if (this == AlphaPlayerScaleType.LeftFit) return 9;
    if (this == AlphaPlayerScaleType.RightFit) return 10;
    return 0;
  }
}

class AlphaVideoValue {
  int? viewId;
  String? dataSourcePath;
  DataSourceType dataSourceType;
  bool? hasError;
  bool? isPlaying;
  bool? complete;

  AlphaVideoValue({this.viewId, this.dataSourcePath, this.dataSourceType = DataSourceType.Url,
      this.hasError, this.isPlaying, this.complete});

  AlphaVideoValue copyWith({
    int? viewId,
    String? dataSourcePath,
    DataSourceType? dataSourceType,
    bool? hasError,
    bool? isPlaying,
    bool? complete,
  }) {
    return AlphaVideoValue()
      ..viewId = viewId ?? this.viewId
      ..dataSourcePath = dataSourcePath ?? this.dataSourcePath
      ..dataSourceType = dataSourceType ?? this.dataSourceType
      ..hasError = hasError ?? this.hasError
      ..isPlaying = isPlaying ?? this.isPlaying
      ..complete = complete ?? this.complete;
  }
}
