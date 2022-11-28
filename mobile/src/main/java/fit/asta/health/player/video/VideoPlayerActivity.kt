package fit.asta.health.player.video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fit.asta.health.R
import fit.asta.health.old_course.session.SessionRepo
import fit.asta.health.old_course.session.data.Exercise
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class VideoPlayerActivity : AppCompatActivity(), Player.Listener {

    @Inject
    lateinit var sessionRepo: SessionRepo
    private val scope = MainScope()

    companion object {

        private const val VIDEO_LIST = "VideoList"
        private const val VIDEO_LIST_UID = "VideoListUid"
        private const val CURRENT_STATE = "CurrentState"
        private const val ARG_COURSE_ID = "courseId"
        private const val ARG_SESSION_ID = "sessionId"

        fun launch(context: Context, videosList: ArrayList<Exercise>?) {

            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putParcelableArrayListExtra(VIDEO_LIST, videosList)
            context.startActivity(intent)
        }

        fun launch(context: Context, courseId: String, sessionId: String) {

            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(ARG_COURSE_ID, courseId)
            intent.putExtra(ARG_SESSION_ID, sessionId)
            context.startActivity(intent)
        }
    }

    private val tag = VideoPlayerActivity::class.java.name
    private var playerView: PlayerView? = null
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var statePlayedAll = false
    private var currentIndex = 0
    private var playbackPosition: Long = 0
    private lateinit var videoList: ArrayList<Video>
    private var videoListUid: String? = null
    //private var fullscreen = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

        findViewById<AppCompatImageButton>(R.id.btn_close_video)?.setOnClickListener {

            onBackPressed()
        }

        //Remove notification bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        playerView = findViewById(R.id.video_view)
        videoListUid = intent.getStringExtra(VIDEO_LIST_UID)

        /*exo_fullscreen_icon.setOnClickListener {

            videoFullScreen(exo_fullscreen_icon)
        }*/
    }

    /*private fun videoFullScreen(view: ImageView) {

        if (fullscreen) {

            view.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_fullscreen_open
                )
            )

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            if (supportActionBar != null) {

                supportActionBar!!.show()
            }

            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            val params = playerView!!.layoutParams as RelativeLayout.LayoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = (220 * applicationContext.resources.displayMetrics.density).toInt()
            playerView!!.layoutParams = params
            fullscreen = false

        } else {

            view.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_fullscreen_exit
                )
            )

            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            if (supportActionBar != null) {

                supportActionBar!!.hide()
            }

            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            val params = playerView!!.layoutParams as RelativeLayout.LayoutParams
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            playerView!!.layoutParams = params
            fullscreen = true
        }
    }*/

    override fun onStart() {
        super.onStart()

        if (Util.SDK_INT >= 24) {

            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()

        hideSystemUi()
        if (Util.SDK_INT < 24 || player == null) {

            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()

        if (Util.SDK_INT < 24) {

            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()

        if (Util.SDK_INT >= 24) {

            releasePlayer()
        }
    }

    private fun initializePlayer() {

        if (player == null) {

            val trackSelector = DefaultTrackSelector(this)
            trackSelector.setParameters(
                trackSelector
                    .buildUponParameters()
                    .setMaxVideoSizeSd()
                    .setPreferredTextLanguage("en")
                    .setPreferredAudioLanguage("en")
            )

            player = ExoPlayer.Builder(this)
                .setSeekBackIncrementMs(30000)
                .setSeekForwardIncrementMs(30000)
                .setTrackSelector(trackSelector).build()
            player?.addListener(this)
        }

        playerView?.player = player

        val exerciseList = intent.getParcelableArrayListExtra<Exercise>(VIDEO_LIST)
        if (exerciseList == null) {

            scope.launch {
                sessionRepo.fetchSession(
                    "",
                    intent.getStringExtra(ARG_COURSE_ID)!!,
                    intent.getStringExtra(ARG_SESSION_ID)!!
                ).collect {
                    initializePlayList(ArrayList(it.exerciseList))
                }
            }
        } else {
            initializePlayList(exerciseList)
        }
    }

    private fun initializePlayList(exerciseList: ArrayList<Exercise>) {
        videosFromExercises(exerciseList).observe(this, Observer { videos ->
            findViewById<ContentLoadingProgressBar>(R.id.progressPlayer).hide()
            videoList = videos
            val mediaSource = MediaSourceAdapter(this, videoList).build()
            player?.playWhenReady = playWhenReady
            player?.seekTo(currentIndex, playbackPosition)
            player?.prepare(mediaSource, false, false)
        })
    }

    private fun hideSystemUi() {

        playerView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun releasePlayer() {

        if (player != null) {

            playWhenReady = false
            player!!.playWhenReady = playWhenReady
            playbackPosition = player!!.currentPosition
            currentIndex = player!!.currentWindowIndex
            player!!.removeListener(this)
            player!!.release()
            player = null
        }

        sendCurrentState()
    }

    private fun sendCurrentState() {

        val state = VideoListState(
            videoListUid,
            statePlayedAll,
            videoList.size,
            currentIndex,
            playbackPosition
        )
        val data = Intent()
        data.putExtra(CURRENT_STATE, state)
        setResult(Activity.RESULT_OK, data)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

        val stateString: String
        when (playbackState) {

            Player.STATE_IDLE -> {

                stateString = "ExoPlayer.STATE_IDLE      -"
                playerView?.keepScreenOn = false
            }
            Player.STATE_BUFFERING -> {

                stateString = "ExoPlayer.STATE_BUFFERING -"
            }
            Player.STATE_READY -> {

                stateString = "ExoPlayer.STATE_READY     -"
                playerView?.keepScreenOn = true
            }
            Player.STATE_ENDED -> {

                stateString = "ExoPlayer.STATE_ENDED     -"
                statePlayedAll = true
                playerView?.keepScreenOn = false

                if (currentIndex == videoList.size - 1)
                    finish()
            }
            else -> {

                stateString = "UNKNOWN_STATE             -"
            }
        }

        Log.d(tag, "Changed state to $stateString $playWhenReady: $playbackState")
    }

    override fun onTracksChanged(tracks: Tracks) {
        super.onTracksChanged(tracks)

        val video = videoList[player!!.currentWindowIndex]
        findViewById<TextView>(R.id.video_title).text = video.title
        findViewById<TextView>(R.id.video_sub_title).text = video.subTitle
    }

    override fun onPositionDiscontinuity(reason: Int) {
        super.onPositionDiscontinuity(reason)

        val newIndex = player!!.currentWindowIndex
        if (newIndex != currentIndex) {
            // The index has changed ==> last media has ended and next media playback is started.
            currentIndex = newIndex
        }
    }

    fun videosFromExercises(exList: List<Exercise>): LiveData<ArrayList<Video>> {

        val videoUrlTasks: MutableList<Task<Uri>> = ArrayList(exList.size)
        val videos = MutableLiveData<ArrayList<Video>>()
        val videoList = ArrayList<Video>()

        val mStorageRef: StorageReference = FirebaseStorage.getInstance().reference

        for (exercise in exList) {

            val reference: StorageReference = mStorageRef.child(exercise.url)
            val currentUrlTask: Task<Uri> = reference.downloadUrl
            videoUrlTasks.add(currentUrlTask)
            currentUrlTask.addOnSuccessListener { uri ->

                videoList.add(
                    Video(
                        exercise.uid,
                        exercise.title,
                        exercise.subTitle,
                        uri
                    )
                )
            }
        }

        Tasks.whenAllComplete(videoUrlTasks)
            .addOnCompleteListener {

                videoList.sortBy { video -> video.uid.toInt() }
                videos.value = videoList
            }

        return videos
    }
}