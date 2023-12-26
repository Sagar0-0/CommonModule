package fit.asta.health.meditation.viewmodel

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.Value
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.getVideoUrl
import fit.asta.health.datastore.PrefManager
import fit.asta.health.meditation.db.MeditationData
import fit.asta.health.meditation.domain.mapper.getMeditationTool
import fit.asta.health.meditation.domain.mapper.getMusicTool
import fit.asta.health.meditation.remote.network.PostRes
import fit.asta.health.meditation.remote.network.PutData
import fit.asta.health.meditation.repo.LocalRepo
import fit.asta.health.meditation.repo.MeditationRepo
import fit.asta.health.meditation.view.home.MEvent
import fit.asta.health.meditation.view.home.ToolUiState
import fit.asta.health.network.utils.toValue
import fit.asta.health.player.audio.MusicServiceConnection
import fit.asta.health.player.domain.mapper.asMediaItem
import fit.asta.health.player.domain.model.Song
import fit.asta.health.player.domain.utils.MediaConstants
import fit.asta.health.player.domain.utils.convertToPosition
import fit.asta.health.player.presentation.screens.player.PlayerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
@androidx.annotation.OptIn(UnstableApi::class)
class MeditationViewModel @Inject constructor(
    private val meditationRepo: MeditationRepo,
    private val musicServiceConnection: MusicServiceConnection,
    private val localRepo: LocalRepo,
    private val player: Player,
    private val prefManager: PrefManager,
    @UID private val uId: String,
    private val notificationManager: NotificationManager
) : ViewModel() {
    fun getPlayer() = player
    private var backgroundPlayer: Player? = null
    private val date = LocalDate.now().dayOfMonth

    private val _uiState = mutableStateOf(ToolUiState())
    val uiState: State<ToolUiState> = _uiState
    private val _mutableState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val state = _mutableState.asStateFlow()

    private val _selectedData = mutableStateListOf<Prc>()
    val selectedData = MutableStateFlow(_selectedData)
    private val _sheetDataList = mutableStateListOf<NetSheetData>()
    val sheetDataList = MutableStateFlow(_sheetDataList)


    private val music = MutableStateFlow<Song?>(null)
    private val list = mutableStateListOf<Song>()
    val musicList = MutableStateFlow(list)
    private var rememberedState: Triple<String, Int, Long>? = null
    private val window: Timeline.Window = Timeline.Window()
    private val _trackList = mutableStateListOf("")
    val trackList = MutableStateFlow(_trackList)
    val track = prefManager.userData
        .map { it.trackLanguage }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "hi",
        )

    private val listener = object : Player.Listener {
        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            // recover the remembered state if media id matched
            Log.d("TAG", "onTimelineChanged: change")
            rememberedState
                ?.let { (id, index, position) ->
                    if (!timeline.isEmpty
                        && timeline.windowCount > index
                        && id == timeline.getWindow(index, window).mediaItem.mediaId
                    ) {
                        player.seekTo(index, position)
                    }
                }
                ?.also { rememberedState = null }
        }
    }

    init {
        player.apply {
            addListener(listener)
        }
        loadData()
        loadLocalData()
//        loadMusicData()
    }

    private fun getSheetItemValue(code: String) {
        viewModelScope.launch {
            when (val result = meditationRepo.getSheetData(code)) {
                is ResponseState.Success -> {
                    _sheetDataList.clear()
                    _sheetDataList.addAll(result.data)
                }

                else -> {}
            }
        }
    }

    fun setMultiple(index: Int, itemIndex: Int) {
        val data = _selectedData[index]
        val item = _sheetDataList[itemIndex]
        _sheetDataList[itemIndex] = item.copy(isSelected = !item.isSelected)
        data.values = _sheetDataList.filter { it.isSelected }.map { it.toValue() }.toList()
        _selectedData[index] = data
    }

    fun setSingle(index: Int, itemIndex: Int) {
        val data = _selectedData[index]
        _sheetDataList.map { it.isSelected = false }
        val item = _sheetDataList[itemIndex].copy(isSelected = true)
        _sheetDataList[itemIndex] = item
        data.values = listOf(item.toValue())
        _selectedData[index] = data
    }

    private fun saveState() {
        player.currentMediaItem?.let { mediaItem ->
            rememberedState = Triple(
                mediaItem.mediaId,
                player.currentMediaItemIndex,
                player.currentPosition
            )
        }
    }

    fun event(event: MEvent) {
        when (event) {
            is MEvent.SetTarget -> {
                _uiState.value = _uiState.value.copy(targetValue = event.target)
            }

            is MEvent.SetTargetAngle -> {
                _uiState.value = _uiState.value.copy(targetAngle = event.angle)
            }

            is MEvent.SetDNDMode -> {
                _uiState.value = _uiState.value.copy(dndMode = event.value)
            }

            is MEvent.GetSheetData -> {
                getSheetItemValue(event.code)
            }

            is MEvent.Start -> {
                if (_uiState.value.targetValue < 1f) {
                    Toast.makeText(event.context, "select target", Toast.LENGTH_SHORT).show()
                } else {
                    putMeditationData()
                    setBackgroundSound(context = event.context)
                    if (_uiState.value.dndMode) {
                        setDNDModeON()
                    }
                }
            }

            is MEvent.End -> {
                postData()
                if (_uiState.value.dndMode) {
                    setDNDModeOff()
                }
                release(context = event.context)
            }
        }
    }

    fun checkDNDStatus() = notificationManager.isNotificationPolicyAccessGranted
    private fun setDNDModeON() =
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS)

    private fun setDNDModeOff() =
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)


    private fun loadData() {
        viewModelScope.launch {
            _mutableState.value = UiState.Loading
            val result = meditationRepo.getMeditationTool(
                uid = "6309a9379af54f142c65fbfe",
                date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
                    .toString()//"2023-03-27"
            )
            _mutableState.value = when (result) {
                is ResponseState.Success -> {
                    val data = result.data.getMeditationTool()
                    _selectedData.clear()
                    _selectedData.addAll(data.bottomSheetPrc)
                    _uiState.value = _uiState.value.copy(
                        target = data.target,
                        achieved = data.achieved,
                        recommended = data.recommended,
                        remaining = data.remaining,
                    )
                    UiState.Success(Unit)
                }

                is ResponseState.ErrorMessage -> {
                    UiState.ErrorMessage(result.resId)
                }

                is ResponseState.ErrorRetry -> {
                    UiState.ErrorRetry(result.resId)
                }

                else -> {
                    UiState.NoInternet
                }
            }
        }
    }

    private fun loadLocalData() {
        viewModelScope.launch {
            val data = localRepo.getWaterData(date = date)
            if (data != null) {
                _uiState.value = _uiState.value.copy(
                    start = data.start,
                    targetAngle = data.appliedAngleDistance,
                    progressAngle = data.appliedAngleDistance,
                    consume = data.time.toFloat()
                )
            } else {// val time = ((System.nanoTime() - data.time) / 1_000_000_000L / 60L).toInt()
                localRepo.insert(
                    MeditationData(
                        date = date,
                        appliedAngleDistance = 0f, start = false, time = 0
                    )
                )
            }
            musicServiceConnection.currentProgress.collect { progress ->
                if (data != null) {
                    var pro: Long = 0
                    pro = if (progress >= data.time) progress else data.time + 1
                    localRepo.updateTime(date = date, time = pro)
                    _uiState.value = _uiState.value.copy(
                        consume = pro.toFloat()
                    )
                }
            }
        }
    }

    private fun putMeditationData() {

        _selectedData[0] = _selectedData[0].copy(
            values = listOf(
                Value(
                    dsc = "",
                    id = "",
                    ttl = _uiState.value.targetValue.toInt().toString(),
                    code = _uiState.value.targetValue.toInt().toString(),
                    url = ""
                )
            )
        )

        viewModelScope.launch {
            val result = meditationRepo.putMeditationData(
                PutData(
                    code = "meditation",
                    id = "",
                    prc = _selectedData.toList(),
                    type = 3,
                    uid = "6309a9379af54f142c65fbfe",
                    wea = true
                )
            )
            when (result) {
                is ResponseState.Success -> {
                    localRepo.updateAngle(
                        date = LocalDate.now().dayOfMonth,
                        appliedAngleDistance = _uiState.value.targetAngle
                    )
                    localRepo.updateState(date = LocalDate.now().dayOfMonth, start = true)
                    localRepo.updateTime(date = LocalDate.now().dayOfMonth, time = 0)
                    _uiState.value = _uiState.value.copy(start = true)
                    loadData()
                    loadMusicData()
                    UiState.Success(result.data)
                }

                is ResponseState.ErrorMessage -> {
                    UiState.ErrorMessage(result.resId)
                }

                is ResponseState.ErrorRetry -> {
                    UiState.ErrorRetry(result.resId)
                }

                else -> {
                    UiState.NoInternet
                }
            }
        }
    }

    private fun postData() {
        viewModelScope.launch {
            val result = meditationRepo.postMeditationData(
                PostRes(
                    id = "",
                    uid = "6309a9379af54f142c65fbfe",
                    duration = _uiState.value.consume.toInt(),
                    mode = "indoor",
                    exp = 40
                )
            )
            when (result) {
                is ResponseState.Success -> {
                    localRepo.updateState(date = LocalDate.now().dayOfMonth, start = false)
                    _uiState.value = _uiState.value.copy(start = false)
                    UiState.Success(result.data)
                }

                is ResponseState.ErrorMessage -> {
                    UiState.ErrorMessage(result.resId)
                }

                is ResponseState.ErrorRetry -> {
                    UiState.ErrorRetry(result.resId)
                }

                else -> {
                    UiState.NoInternet
                }
            }
        }
    }


    private fun loadMusicData() {
        viewModelScope.launch {
            when (val result = meditationRepo.getMusicTool(uid = "6309a9379af54f142c65fbfe")) {
                is ResponseState.Success -> {
                    val data = result.data.getMusicTool()
                    music.value = Song(
                        id = 55,
                        artist = data.music.artist_name,
                        artworkUri = getImgUrl(data.music.imgUrl).toUri(),
                        duration = 4,
                        mediaUri = getVideoUrl(data.music.music_url).toUri(),
                        title = data.music.music_name,
                        audioList = data.music.language
                    )
                    val listIterator = mutableListOf<Song>()
                    data.instructor.forEachIndexed { index, it ->
                        val subUrl = it.music_url.split(".")
                        val url = subUrl[0] + "_hi." + subUrl[1]
                        Log.d("TAG", "loadMusicData: $url")
                        listIterator.add(
                            Song(
                                id = index,
                                artist = it.artist_name,
                                artworkUri = getImgUrl(it.imgUrl).toUri(),
                                duration = duration(it.duration),
                                mediaUri = getVideoUrl(if (index > 17) url else it.music_url).toUri(),
                                title = "Day $index",
                                audioList = it.language
                            )
                        )
                    }
                    listIterator.reverse()
                    list.clear()
                    list.addAll(listIterator)
                    startPlayer(list)
                    UiState.Success(data)
                }

                is ResponseState.ErrorMessage -> {
                    UiState.ErrorMessage(result.resId)
                }

                is ResponseState.ErrorRetry -> {
                    UiState.ErrorRetry(result.resId)
                }

                else -> {
                    UiState.NoInternet
                }
            }
        }
    }


    private fun startPlayer(
        list: List<Song>,
        startIndex: Int = MediaConstants.DEFAULT_INDEX,
    ) {
        musicServiceConnection.playSongs(
            songs = list,
            startIndex = startIndex
        )
        music.value?.let {
            backgroundPlayer?.run {
                setMediaItem(MediaItem.fromUri(it.mediaUri))
                prepare()
                volume = 0.3f
                repeatMode = Player.REPEAT_MODE_ONE
                play()
            }
        }
        _trackList.clear()
        _trackList.addAll(list[startIndex].audioList)
    }


    val musicState = musicServiceConnection.musicState

    private val _visibility = MutableStateFlow(false)
    val visibility: StateFlow<Boolean> = _visibility

    fun setVisibility() {
        _visibility.value = !_visibility.value
    }

    fun onAudioEvent(event: PlayerEvent) {
        when (event) {
            is PlayerEvent.PlayIndex -> playIndex(event.index)
            is PlayerEvent.Play -> play()
            is PlayerEvent.Pause -> pause()
            is PlayerEvent.SkipNext -> skipNext()
            is PlayerEvent.SkipPrevious -> skipPrevious()
            is PlayerEvent.SkipTo -> skipTo(event.value)
            is PlayerEvent.SkipForward -> forward()
            is PlayerEvent.SkipBack -> backward()
        }
    }

    fun setBackgroundSound(context: Context) {
        if (backgroundPlayer != null) return
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(USAGE_MEDIA)
            .build()

        backgroundPlayer = ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, false)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    fun release(context: Context) {
        musicServiceConnection.release(context)
        backgroundPlayer?.release()
    }

    private fun skipPrevious() {
        musicServiceConnection.skipPrevious()
        _trackList.clear()
        _trackList.addAll(list[player.previousMediaItemIndex].audioList)
    }

    private fun playIndex(index: Int) {
        musicServiceConnection.playIndex(index)
    }

    fun play() {
        musicServiceConnection.play()
        backgroundPlayer?.play()
    }

    private fun pause() {
        musicServiceConnection.pause()
        backgroundPlayer?.pause()
    }

    private fun skipNext() {
        musicServiceConnection.skipNext()
        _trackList.clear()
        _trackList.addAll(list[player.nextMediaItemIndex].audioList)
    }

    private fun skipTo(position: Float) =
        musicServiceConnection.skipTo(convertToPosition(position, musicState.value.duration))

    private fun forward() = musicServiceConnection.forward()
    private fun backward() = musicServiceConnection.backward()

    fun duration(value: String = "00:15:33"): Int {
        val data = value.split(":")
        return data[1].toInt() + (data[2].toInt() / 60)
    }

    fun onTrackChange(language: String) {
        saveState()
        viewModelScope.launch { prefManager.setTrackLanguage(language) }
        val subUrl = list[player.currentMediaItemIndex].mediaUri.toString().split("_")
        val url = subUrl.first() + "_" + language + "." + subUrl.last().split(".").last()
        Log.d("TAG", "onTrackChange: $url")
        musicServiceConnection.changeTrack(
            list[player.currentMediaItemIndex]
                .copy(mediaUri = url.toUri()).asMediaItem()
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(listener)
        player.release()
        backgroundPlayer?.release()
    }
}