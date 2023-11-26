package fit.asta.health.referral.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Link
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
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
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
        NewReferralDesign(refCode = "0000")
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
    refCode: String = "",
    referralStats: ReferralDataResponse.ReferralStats = ReferralDataResponse.ReferralStats(),
    referredUserList: List<UserDetails>? = null,
) {
    // Use AppSurface as the root layout
    AppSurface(modifier = modifier.fillMaxSize()) {
        // Column layout to organize UI elements vertically
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Add spacing
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            // Display referral image
            ReferralImage()

            // Add spacing
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            // Button to share referral link
            ShareReferralButton(shareRefLink = { shareRefLink(refCode) })

            // Display OR text
            LargeTexts.Level2(
                text = "OR",
                color = AppTheme.colors.onSurfaceVariant,
            )

            // Display referral code with a copy button
            CopyReferralCodeCard(refCode = refCode)

            // Add spacing
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            // Display invitation report
            InvitationReport(
                referralStats.totalIncome.toString(),
                referralStats.nonPremiumUsers.toString(),
                referralStats.premiumUsers.toString()
            )

            // Add spacing
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

            // Display the list of referred users, if available
            referredUserList?.let {
                HeadingTexts.Level2(text = "You've invited...")
                Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
                referredUserList.forEach { user ->
                    InvitedUserList(user)
                }
            }
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

    // Row layout to organize UI elements horizontally
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.spacing.level2),
        horizontalArrangement = Arrangement.Center
    ) {
        // Use AppCard to create a card containing the referral code and copy button
        AppCard(colors = colors, onClick = { context.copyTextToClipboard(refCode) }) {
            // Row layout to organize UI elements horizontally
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    PaddingValues(
                        start = AppTheme.spacing.level3,
                        top = AppTheme.spacing.level1,
                        end = AppTheme.spacing.level3,
                        bottom = AppTheme.spacing.level1
                    )
                )
            ) {
                // Display the referral code
                HeadingTexts.Level1(
                    text = refCode,
                    modifier = Modifier.padding(AppTheme.spacing.level2),
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.primary
                )

                // Row layout to organize UI elements horizontally
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Display copy icon
                    AppIcon(imageVector = Icons.Filled.ContentCopy, tint = AppTheme.colors.primary)
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level0))

                    // Display copy text
                    CaptionTexts.Level1(text = "Copy", color = AppTheme.colors.primary)
                }
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
fun InvitedUserList(
    userDetails: UserDetails,
    addToCommunity: () -> Unit = {}
) {
    // Use AppCard to create a card for each invited user
    AppCard(modifier = Modifier.padding(horizontal = AppTheme.spacing.level2)) {
        // Row layout to organize UI elements horizontally
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2, horizontal = AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Display user details including profile picture, name, and contact information
            Row {
                AppNetworkImage(
                    model = userDetails.pic,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(AppTheme.boxSize.level6)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
                Column {
                    HeadingTexts.Level3(text = userDetails.name)
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                    BodyTexts.Level3(
                        text = userDetails.phone.ifEmpty {
                            userDetails.mail
                        },
                        color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                    )
                }
            }

            // Column layout to organize UI elements vertically
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
                // Display prime icon if the user is prime
                if (userDetails.prime) {
                    AppIcon(imageVector = Icons.Filled.Diamond, tint = AppTheme.colors.primary)
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))

                // Display button to add the user to the community
                AddToCommunityButton(addToCommunity = addToCommunity)
            }
        }
    }

    // Add spacing
    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
}

/**
 * Composable function to display a button for adding a user to the community.
 *
 * @param addToCommunity Callback to handle adding the user to the community.
 */
@Composable
fun AddToCommunityButton(addToCommunity: () -> Unit = {}) {
    // Use AppIconButton to create a button for adding to the community
    AppIconButton(imageVector = Icons.Filled.GroupAdd, onClick = addToCommunity)
}

/**
 * Composable function to display the invitation report.
 */
@Composable
fun InvitationReport(totalIncome: String, nonPremiumUsers: String, premiumUsers: String) {
    // Column layout to organize UI elements vertically
    Column(Modifier.padding(horizontal = AppTheme.spacing.level2)) {
        // Display the heading for the invitation report
        HeadingTexts.Level2(text = "Invitation Report")

        // Row layout to organize UI elements horizontally
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2),
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
            CaptionTexts.Level2(text = title)
        }
    }
}