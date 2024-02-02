package fit.asta.health.feature.testimonials.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import fit.asta.health.common.utils.getVideoUrlTools
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppErrorMsgCard
import fit.asta.health.designsystem.molecular.AppInternetErrorDialog
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFloatingActionButton
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.testimonials.components.TestimonialArtistCard
import fit.asta.health.feature.testimonials.components.TestimonialCardImage
import fit.asta.health.feature.testimonials.components.TestimonialsVideoView
import fit.asta.health.feature.testimonials.components.UserTestimonialUI

/**
 * This function controls the Testimonial Feature UI and it decides which UI it needs to show for
 * what type of states.
 *
 * @param testimonials This is the list of testimonials data which would be shown in the UI according
 * to the states they are in.
 * @param navigateToCreate This function navigates the user to the Create Screen of the testimonials
 * @param onBack This function navigates the current Testimonials Home Screen back to the Tools Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestimonialHomeScreenControl(
    testimonials: LazyPagingItems<Testimonial>,
    navigateToCreate: () -> Unit,
    onBack: () -> Unit
) {

    // Scroll Behaviour variable used to get the scroll height and the top app bar visibility states
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Scaffold which serves as a Base Parent UI for this Screen
    AppScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

            // TopAppBar
            AppTopBar(
                title = "Testimonials",
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {

            // Floating Action Button to navigate to the Create Testimonial Screen
            AppFloatingActionButton(
                imageVector = Icons.Filled.Edit,
                onClick = navigateToCreate
            )
        }
    ) { paddingValues ->

        // We contain these boxes so that we can show the Loading UI in the middle when needed
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // This function shows the Testimonial's List
            TestimonialListUI(testimonialList = testimonials)

            when {

                // Refresh
                testimonials.loadState.refresh is LoadState.Loading -> {
                    AppDotTypingAnimation(Modifier.align(Alignment.Center))
                }

                // Append
                testimonials.loadState.append is LoadState.Loading -> {
                    AppDotTypingAnimation(Modifier.align(Alignment.Center))
                }

                // Refresh error
                testimonials.loadState.refresh is LoadState.Error -> {
                    AppInternetErrorDialog {
                        testimonials.refresh()
                    }
                }

                // Append error
                testimonials.loadState.append is LoadState.Error -> {

                    // TODO :- Check if we can remove this parent composable function and implement something different
                    AppErrorMsgCard(
                        message = "Some error occurred",
                        imageVector = Icons.Filled.Help
                    )
                }
            }
        }
    }
}

@Composable
private fun TestimonialListUI(testimonialList: LazyPagingItems<Testimonial>) {

    // Testimonial list is being shown by this composable
    LazyColumn(
        modifier = Modifier.padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {
        items(testimonialList.itemCount) { index ->

            // Checking if we have a testimonial in the Current Testimonial
            testimonialList[index]?.let { testimonial ->

                AppElevatedCard {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.spacing.level2)
                    ) {
                        when (TestimonialType.from(testimonial.type)) {

                            // Testimonial Type is of Text Type
                            is TestimonialType.TEXT -> {

                                // Title of the Testimonial
                                TitleTexts.Level2(text = testimonial.title)

                                Spacer(modifier = Modifier.height(16.dp))
                                UserTestimonialUI(userTestimonial = testimonial.testimonial)
                            }

                            // Testimonial Api which is of type image.
                            is TestimonialType.IMAGE -> {

                                // App Horizontal Pager to show all the images of the User
                                TestimonialCardImage(
                                    listOf(
                                        testimonial.beforeImage,
                                        testimonial.afterImage
                                    )
                                )

                                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                                UserTestimonialUI(userTestimonial = testimonial.testimonial)
                            }

                            // Testimonial Api which is of type video
                            is TestimonialType.VIDEO -> {

                                if (testimonial.videoMedia != null) {
                                    AppSurface(modifier = Modifier.fillMaxWidth()) {
                                        TestimonialsVideoView(
                                            videoUri = getVideoUrlTools(
                                                url = testimonial.videoMedia!!.url
                                            )
                                        )
                                    }
                                } else
                                    BodyTexts.Level1(
                                        text = "MEDIA FILE NOT FOUND",
                                        color = AppTheme.colors.error
                                    )
                            }
                        }

                        TestimonialArtistCard(
                            userId = testimonial.user.userId,
                            name = testimonial.user.name,
                            organization = testimonial.user.org,
                            role = testimonial.user.role
                        )
                    }
                }
            }
        }
    }
}