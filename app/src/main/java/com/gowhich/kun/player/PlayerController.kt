package com.gowhich.kun.player

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.gowhich.kun.data.model.Song

/**
 * 全局播放控制器（单例）。
 * 持有唯一的 ExoPlayer 实例与当前播放队列，供发现/搜索/音乐库等页面共享。
 */
object PlayerController {

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

    /** 初始化 ExoPlayer（仅在首次播放时创建） */
    fun ensurePlayer(context: Context) {
        if (exoPlayer != null) return
        val player = ExoPlayer.Builder(context).build()
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
        player.setMediaItems(songs.map { MediaItem.fromUri(it.uri) }, index, 0L)
        player.prepare()
        player.play()
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

    fun updateProgress(positionMs: Long, totalMs: Long) {
        currentPosition = positionMs
        duration = totalMs
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }
}
