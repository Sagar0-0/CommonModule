package fit.asta.health.thirdparty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.SpotifyMusicHomeActivityBinding
import fit.asta.health.thirdparty.spotify.view.adapters.ViewPagerAdapter

@AndroidEntryPoint
class MusicHomeActivity : AppCompatActivity() {

    private lateinit var binding: SpotifyMusicHomeActivityBinding
    private var tag = this::class.java.simpleName

    val fragmentNames = arrayOf(
        "Asta Music",
        "Favorites",
        "Third Party"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpotifyMusicHomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = fragmentNames[position]
        }.attach()
    }
}