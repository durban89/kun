package com.gowhich.kun.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gowhich.kun.data.model.Song
import com.gowhich.kun.data.model.SongSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "kun_music")

/**
 * 本地数据仓库（单例）。
 * 通过 DataStore 将歌曲列表序列化为 JSON 持久化到本地，无需任何服务端。
 * 负责歌曲的增删、分类、喜欢标记。使用前需调用 [init] 注入 Application Context。
 */
object MusicRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    private val songListSerializer = ListSerializer(Song.serializer())

    private val KEY_SONGS = stringPreferencesKey("songs")

    @Volatile
    private var appContext: Context? = null

    /** 在 Application/MainActivity 启动时调用一次 */
    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun dataStore() = checkNotNull(appContext) {
        "MusicRepository.init(context) 必须在使用前调用"
    }.dataStore

    /** 所有歌曲的实时流 */
    val songs: Flow<List<Song>> by lazy {
        dataStore().data.map { prefs ->
            val raw = prefs[KEY_SONGS] ?: return@map emptyList()
            runCatching { json.decodeFromString(songListSerializer, raw) }
                .getOrDefault(emptyList())
        }
    }

    /** 喜欢的歌曲 */
    val favoriteSongs: Flow<List<Song>> by lazy { songs.map { list -> list.filter { it.isFavorite } } }

    /** 所有分类 (去重) */
    val genres: Flow<List<String>> by lazy { songs.map { list -> list.map { it.genre }.filter { it.isNotBlank() }.distinct() } }

    suspend fun addSong(song: Song) {
        dataStore().edit { prefs ->
            val current = readSongs(prefs[KEY_SONGS])
            prefs[KEY_SONGS] = json.encodeToString(songListSerializer, current + song)
        }
    }

    suspend fun removeSong(id: String) {
        dataStore().edit { prefs ->
            val current = readSongs(prefs[KEY_SONGS])
            prefs[KEY_SONGS] = json.encodeToString(
                songListSerializer, current.filterNot { it.id == id }
            )
        }
    }

    suspend fun toggleFavorite(id: String) {
        dataStore().edit { prefs ->
            val current = readSongs(prefs[KEY_SONGS])
            prefs[KEY_SONGS] = json.encodeToString(
                songListSerializer, current.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
            )
        }
    }

    private fun readSongs(raw: String?): List<Song> {
        if (raw == null) return emptyList()
        return runCatching { json.decodeFromString(songListSerializer, raw) }.getOrDefault(emptyList())
    }

    /** 生成唯一 ID */
    fun genId(): String = "song_${System.currentTimeMillis()}_${(Math.random() * 10000).toInt()}"
}
