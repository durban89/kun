package com.gowhich.kun.player

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.gowhich.kun.data.model.Song

/**
 * 全局播放控制器（单例）。
 * 持有唯一的 ExoPlayer 实例与当前播放队列，供发现/搜索/音乐库等页面共享。
 */
object PlayerController {

    /** 应用上下文（记录后用于启动前台媒体服务） */
    private var appContext: Context? = null

    /** 是否已启动前台媒体服务（避免重复 startForegroundService） */
    private var serviceStarted = false

    var exoPlayer: ExoPlayer? = null
        private set

    /** 当前播放队列 */
    var queue by mutableStateOf<List<Song>>(emptyList())
        private set

    /** 当前正在播放的歌曲 */
    var currentSong by mutableStateOf<Song?>(null)
        private set

    /** 是否正在播放 */
    var isPlaying by mutableStateOf(false)
        private set

    var currentPosition by mutableStateOf(0L)
        private set

    var duration by mutableStateOf(0L)
        private set

    /** 循环模式：REPEAT_MODE_OFF / REPEAT_MODE_ALL(列表) / REPEAT_MODE_ONE(单曲) */
    var repeatMode by mutableStateOf(Player.REPEAT_MODE_OFF)
        private set

    /** 初始化 ExoPlayer（仅在首次播放时创建） */
    fun ensurePlayer(context: Context) {
        appContext = context.applicationContext
        if (exoPlayer != null) return
        val player = ExoPlayer.Builder(context).build()
        player.repeatMode = repeatMode
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                super.onIsPlayingChanged(playing)
                isPlaying = playing
            }

            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                if (state == Player.STATE_READY) {
                    duration = player.duration
                }
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                // 自动切到下一首时同步当前歌曲信息
                val idx = player.currentMediaItemIndex
                if (idx in queue.indices) {
                    currentSong = queue[idx]
                }
                currentPosition = 0L
                duration = player.duration
            }
        })
        exoPlayer = player
    }

    /** 播放指定队列中的一首歌 */
    fun play(songs: List<Song>, index: Int) {
        val player = exoPlayer ?: return
        if (songs.isEmpty() || index !in songs.indices) return
        queue = songs
        val target = songs[index]
        currentSong = target
        currentPosition = 0L
        duration = 0L
        // 给每个播放项挂上标题/歌手元数据，系统媒体通知才能显示"正在播放的内容"
        val mediaItems = songs.map {
            MediaItem.Builder()
                .setUri(it.uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(it.title)
                        .setArtist(it.artist)
                        .build()
                )
                .build()
        }
        player.setMediaItems(mediaItems, index, 0L)
        player.prepare()
        player.play()
        startPlaybackService()
    }

    /** 真正开始播放时才启动前台媒体服务（保证 media3 会进入前台，避免 5 秒超时崩溃） */
    private fun startPlaybackService() {
        if (serviceStarted) return
        val ctx = appContext ?: return
        serviceStarted = true
        val intent = Intent(ctx, PlaybackService::class.java)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ctx.startForegroundService(intent)
        } else {
            ctx.startService(intent)
        }
    }

    /** 播放单曲（无队列） */
    fun playSingle(song: Song) {
        play(listOf(song), 0)
    }

    fun playNext() {
        val player = exoPlayer ?: return
        val idx = queue.indexOfFirst { it.id == currentSong?.id }
        val next = (idx + 1) % queue.size
        play(queue, next)
    }

    fun playPrevious() {
        val player = exoPlayer ?: return
        val idx = queue.indexOfFirst { it.id == currentSong?.id }
        val prev = if (idx <= 0) queue.size - 1 else idx - 1
        play(queue, prev)
    }

    fun togglePlayPause() {
        val player = exoPlayer ?: return
        if (player.isPlaying) player.pause() else player.play()
    }

    fun seekTo(positionMs: Long) {
        exoPlayer?.seekTo(positionMs)
    }

    /** 循环模式循环切换：关闭 -> 列表循环 -> 单曲循环 -> 关闭 */
    fun cycleRepeatMode() {
        repeatMode = when (repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
        exoPlayer?.repeatMode = repeatMode
    }

    fun updateProgress(positionMs: Long, totalMs: Long) {
        currentPosition = positionMs
        duration = totalMs
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }
}
