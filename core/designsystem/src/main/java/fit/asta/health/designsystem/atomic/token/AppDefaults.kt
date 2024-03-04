package fit.asta.health.designsystem.atomic.token

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun appRememberTopAppBarState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f
) : TopAppBarState{
    return rememberTopAppBarState(
        initialHeightOffsetLimit = initialHeightOffsetLimit,
        initialHeightOffset = initialHeightOffset,
        initialContentOffset = initialContentOffset
    )
}


object ScrollBehaviors{
    @Composable
    fun appTopAppBarEnterAlwaysScrollBehavior(
        state: TopAppBarState = rememberTopAppBarState(),
        canScroll: () -> Boolean = { true },
        snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay()
    ) : TopAppBarScrollBehavior{
        return TopAppBarDefaults.enterAlwaysScrollBehavior(
            state = state,
            canScroll = canScroll,
            snapAnimationSpec = snapAnimationSpec,
            flingAnimationSpec = flingAnimationSpec
        )
    }

    @Composable
    fun appTopAppBarPinnedScrollBehavior(
        state: TopAppBarState = rememberTopAppBarState(),
        canScroll: () -> Boolean = { true }
    ) : TopAppBarScrollBehavior{
        return TopAppBarDefaults.pinnedScrollBehavior(
            state = state,
            canScroll = canScroll
        )
    }

    @Composable
    fun appTopAppBarScroll(
        state: TopAppBarState = rememberTopAppBarState(),
        canScroll: () -> Boolean = { true },
        snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay()
    ) : TopAppBarScrollBehavior{
        return TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = state,
            canScroll = canScroll,
            snapAnimationSpec = snapAnimationSpec,
            flingAnimationSpec = flingAnimationSpec
        )
    }

}

@Composable
fun provideLocalTextStyle() : TextStyle{
    return LocalTextStyle.current
}