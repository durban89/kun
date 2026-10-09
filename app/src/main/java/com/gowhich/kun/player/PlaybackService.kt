package com.gowhich.kun.player

import android.content.Intent
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * 前台媒体服务：把 PlayerController 的 ExoPlayer 暴露为系统 MediaSession，
 * 使通知栏/锁屏/控制中心的"正在播放"卡片生效，并允许后台持续播放。
 */
class PlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        // 复用全局播放器实例（UI 与系统会话共享同一播放器）
        PlayerController.ensurePlayer(this)
        mediaSession = MediaSession.Builder(this, PlayerController.exoPlayer!!)
            .setId("kun-music")
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = PlayerController.exoPlayer
        if (player == null || !player.playWhenReady || player.mediaItemCount == 0) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        // 仅释放会话，不释放全局播放器（由 PlayerController 统一管理生命周期）
        mediaSession?.release()
        mediaSession = null
        super.onDestroy()
    }
}