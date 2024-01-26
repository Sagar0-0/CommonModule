package fit.asta.health.referral.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.copyTextToClipboard
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.referral.remote.model.PrimeUserTypes
import fit.asta.health.referral.remote.model.ReferralDataResponse
import fit.asta.health.referral.remote.model.UserDetails
import fit.asta.health.resources.drawables.R


@Preview(
    "Light Referral", heightDp = 1100
)
@Preview(
    name = "Dark Referral",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
@Composable
fun ReferralScreenPreview() {
    AppTheme {
        NewReferralDesign(referralData = ReferralDataResponse())
    }
}

/**
 * Composable function for the overall design of the new referral screen.
 * @param modifier Modifier for the Compose UI elements.
 * @param shareRefLink Callback to handle sharing the referral link.
 * @param refCode Referral code associated with the user.
 * @param referredUserList List of UserDetails representing users referred by the current user.
 */
@Composable
fun NewReferralDesign(
    modifier: Modifier = Modifier,
    shareRefLink: (String) -> Unit = {},
    referralData: ReferralDataResponse,
) {
    // Use AppSurface as the root layout
    AppSurface(
        modifier = modifier.fillMaxSize()
    ) {
        // Column layout to organize UI elements vertically
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            // Display referral image
            ReferralImage()

            // Button to share referral link
            ShareReferralButton(shareRefLink = { shareRefLink(referralData.referralDetails.refCode) })

            // Display OR text
            HeadingTexts.Level1(
                text = "OR",
                color = AppTheme.colors.onSurfaceVariant,
            )

            // Display referral code with a copy button
            CopyReferralCodeCard(refCode = referralData.referralDetails.refCode)

            // Display invitation report
            InvitationReport(
                referralData.referralStats.totalIncome.toString(),
                referralData.referralStats.nonPremiumUsers.toString(),
                referralData.referralStats.premiumUsers.toString()
            )

            referralData.referredByUsersDetails?.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                ) {
                    HeadingTexts.Level3(
                        modifier = Modifier
                            .align(Alignment.Start),
                        text = "Referred by:"
                    )
                    ReferralUserItem(userDetails = user)
                }
            }

            // Display the list of referred users, if available
            referralData.referredUsers?.let { list ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
                ) {
                    HeadingTexts.Level3(
                        modifier = Modifier
                            .align(Alignment.Start),
                        text = "You've invited..."
                    )
                    list.forEach { user ->
                        ReferralUserItem(user)
                    }
                }

            }

            Spacer(modifier = Modifier)
        }
    }
}

/**
 * Composable function to display the referral image.
 * @param refImg Resource ID of the referral image.
 * @param aspectRatio Aspect ratio of the image.
 */
@Composable
fun ReferralImage(
    refImg: Int = R.drawable.ref_ed_2,
    aspectRatio: Float = AppTheme.aspectRatio.wideScreen
) {
    // Use AppLocalImage to display the referral image
    AppLocalImage(
        painter = painterResource(id = refImg),
        modifier = Modifier.aspectRatio(aspectRatio),
        contentScale = ContentScale.Fit
    )
}

/**
 * Composable function to display the button for sharing the referral link.
 * @param modifier Modifier for the Compose UI elements.
 * @param shareRefLink Callback to handle sharing the referral link.
 */
@Composable
fun ShareReferralButton(modifier: Modifier = Modifier, shareRefLink: () -> Unit = {}) {
    // Row layout to organize UI elements horizontally
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        // Use AppFilledButton to create the share button
        AppFilledButton(
            textToShow = "Share your link",
            trailingIcon = Icons.Filled.Link, onClick = shareRefLink, modifier = modifier
        )
    }
}

/**
 * Composable function to display a card with the referral code and a copy button.
 * @param refCode Referral code to be displayed.
 * @param colors Card colors.
 */
@Composable
fun CopyReferralCodeCard(
    refCode: String = "",
    colors: CardColors = CardDefaults.cardColors(),
) {
    // Access the current context
    val context = LocalContext.current

    // Use AppCard to create a card containing the referral code and copy button
    AppCard(
        colors = colors,
        onClick = { context.copyTextToClipboard(refCode) }
    ) {
        // Row layout to organize UI elements horizontally
        Row(
            modifier = Modifier.padding(AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Display the referral code
            HeadingTexts.Level1(
                modifier = Modifier.padding(end = AppTheme.spacing.level1),
                text = refCode,
                textAlign = TextAlign.Center,
                color = AppTheme.colors.primary
            )

            // Row layout to organize UI elements horizontally
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Display copy icon
                AppIcon(imageVector = Icons.Filled.ContentCopy, tint = AppTheme.colors.primary)
                // Display copy text
                CaptionTexts.Level1(text = "Copy", color = AppTheme.colors.primary)
            }
        }
    }
}

/**
 * Composable function to display details of an invited user.
 * @param userDetails Details of the invited user.
 * @param addToCommunity Callback to handle adding the user to the community.
 */
@Composable
fun ReferralUserItem(
    userDetails: UserDetails,
    addToCommunity: () -> Unit = {}
) {
    // Use AppCard to create a card for each invited user
    AppCard {
        // Row layout to organize UI elements horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2, horizontal = AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Display user details including profile picture, name, and contact information
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
            ) {
                AppNetworkImage(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(AppTheme.imageSize.level4)
                        .clip(CircleShape),
                    model = userDetails.pic,
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0)
                ) {
                    TitleTexts.Level3(text = userDetails.name)
                    BodyTexts.Level3(
                        text = userDetails.phone.ifEmpty {
                            userDetails.mail
                        },
                        color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                    )
                }

                if (userDetails.prime == PrimeUserTypes.ACTIVE.code) {
                    AppIcon(
                        imageVector = Icons.Filled.WorkspacePremium,
                        tint = AppTheme.colors.primary
                    )
                } else {
                    AppIcon(imageVector = Icons.Default.VerifiedUser)
                }
            }
        }
    }
}

///**
// * Composable function to display a button for adding a user to the community.
// *
// * @param addToCommunity Callback to handle adding the user to the community.
// */
//@Composable
//fun AddToCommunityButton(addToCommunity: () -> Unit = {}) {
//    // Use AppIconButton to create a button for adding to the community
//    AppIconButton(imageVector = Icons.Filled.GroupAdd, onClick = addToCommunity)
//}

/**
 * Composable function to display the invitation report.
 */
@Composable
fun InvitationReport(totalIncome: String, nonPremiumUsers: String, premiumUsers: String) {
    // Column layout to organize UI elements vertically
    Column(
        modifier = Modifier
            .padding(horizontal = AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        // Display the heading for the invitation report
        HeadingTexts.Level2(text = "Invitation Report", maxLines = 1)

        // Row layout to organize UI elements horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display three invitation report cards
            InvitationReportCard(
                modifier = Modifier.weight(1f),
                title = "Total Earnings",
                text = totalIncome
            )
            InvitationReportCard(
                modifier = Modifier.weight(1f),
                title = "Referred Users",
                text = nonPremiumUsers
            )
            InvitationReportCard(
                modifier = Modifier.weight(1f),
                title = "Premium Users",
                text = premiumUsers
            )
        }
    }
}

/**
 * Composable function to display an invitation report card.
 *
 * @param modifier Modifier for the Compose UI elements.
 * @param title Title of the report card.
 * @param text Value of the report card.
 */
@Composable
fun InvitationReportCard(
    modifier: Modifier = Modifier,
    title: String = "Demo",
    text: String = "$500"
) {
    // Use AppCard to create a card for the invitation report
    AppCard(modifier = modifier) {
        // Column layout to organize UI elements vertically
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display the value of the report card
            LargeTexts.Level3(text = text)

            // Add spacing
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))

            // Display the title of the report card
            CaptionTexts.Level2(text = title, maxLines = 2)
        }
    }
}