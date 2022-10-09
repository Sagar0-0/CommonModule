package fit.asta.health.navigation.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import fit.asta.health.navigation.home.intent.HomeState
import fit.asta.health.navigation.home.view.component.*
import fit.asta.health.navigation.home.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: HomeViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Content(viewModel.state.collectAsState().value)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Content(state: HomeState) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        when (state) {
            is HomeState.Loading -> LoadingAnimation()
            is HomeState.Success -> ReadyScreen()
            is HomeState.Error -> NoInternetLayout()
        }
    }
}

@Preview
@Composable
@OptIn(ExperimentalPagerApi::class)
fun ReadyScreen() {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.background)
        .clip(RoundedCornerShape(16.dp))) {
        Column(Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())) {
            NameAndMoodHomeScreenHeader()
            Spacer(modifier = Modifier.height(24.dp))
            WeatherCardImage()
            Spacer(modifier = Modifier.height(24.dp))
            BannerAutoSlider()
            MyToolsAndViewAll()
            VerticalImageCards()
            Testimonials()
            Spacer(modifier = Modifier.height(24.dp))
            RateUsCard()
            Spacer(modifier = Modifier.height(24.dp))
            ReferAndEarn()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}