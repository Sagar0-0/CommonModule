package fit.asta.health.feature.profile.show.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.data.profile.remote.model.BasicDetail
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.UserProfileAddress
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.token.DefaultColorTokens
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.resources.strings.R
import fit.asta.health.resources.drawables.R as RDraw

@Composable
fun ContactLayout(
    basicDetails: BasicDetail,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        UserProfileSection(basicDetails)
        UserAchievementsSection()
        UserProfileDetailsSection()
    }
}

@Composable
private fun UserProfileSection(basicDetails: BasicDetail) {
    Column(
        modifier = Modifier
            .padding(top = AppTheme.spacing.level2)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserProfileImg(userProfilePic = basicDetails.media)
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        UserDetails(
            basicDetails.name,
            basicDetails.phoneNumber,
            basicDetails.email,
            basicDetails.userProfileAddress,
        )
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
    }
}

@Composable
private fun UserAchievementsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        UserAchievCard(
            scoreBoard = "24/346",
            cardType = stringResource(R.string.leaderboard),
            imageID = RDraw.drawable.leaderboard,
            modifier = Modifier.weight(1f)
        )
        UserAchievCard(
            scoreBoard = "12",
            cardType = stringResource(R.string.badges),
            imageID = RDraw.drawable.badgecompleted,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun UserProfileDetailsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileDetails(
            imageID = RDraw.drawable.ssidchart,
            profileType = stringResource(R.string.level),
            modifier = Modifier.weight(1f)
        )
        ProfileDetails(
            imageID = RDraw.drawable.description,
            profileType = stringResource(R.string.plan),
            modifier = Modifier.weight(1f)
        )
        ProfileDetails(
            imageID = RDraw.drawable.sportsscore,
            profileType = stringResource(R.string.goal),
            modifier = Modifier.weight(1f)
        )
        ProfileDetails(
            imageID = RDraw.drawable.healthandsafety,
            profileType = stringResource(R.string.details_health),
            modifier = Modifier.weight(1f)
        )
    }
}


// User's Profile Photo with Design Layout
@Composable
fun UserProfileImg(userProfilePic: ProfileMedia) {

    val imageUrl = if (userProfilePic.url.isEmpty()) {
        "https://img2.asta.fit/profile/Men_Default.png"
    } else {
        getImageUrl(url = userProfilePic.url)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = AppTheme.spacing.level1)
    ) {
        ProfileImageBorder(size = 188.dp, color = AppTheme.colors.primary) {
            AppNetworkImage(
                model = imageUrl,
                contentDescription = "User Profile Pic",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(AppTheme.imageSize.level12)
                    .border(
                        border = BorderStroke(
                            width = 2.dp, color = AppTheme.colors.primary
                        ), shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
        }
        ProfileImageBorder(size = 218.dp, color = DefaultColorTokens.ProfileBorder1)
        ProfileImageBorder(size = 248.dp, color = DefaultColorTokens.ProfileBorder2)
        ProfileImageBorder(size = 278.dp, color = DefaultColorTokens.ProfileBorder3)
    }
}

@Composable
private fun ProfileImageBorder(size: Dp, color: Color, content: @Composable () -> Unit = {}) {
    AppSurface(
        shape = CircleShape,
        modifier = Modifier.size(size),
        color = Color.Transparent,
        border = BorderStroke(1.dp, color)
    ) {
        content()
    }
}


// User's Achievement Card Layout
@Composable
fun UserAchievCard(
    scoreBoard: String,
    cardType: String,
    imageID: Int,
    modifier: Modifier = Modifier,
) {
    AppCard(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(AppTheme.spacing.level2)
        ) {
            AppLocalImage(
                painter = painterResource(id = imageID),
                contentDescription = "Leaderboard",
                modifier = Modifier.size(AppTheme.imageSize.level5)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0)
            ) {
                BodyTexts.Level1(text = scoreBoard)
                CaptionTexts.Level3(text = cardType)
            }
        }
    }
}


// User's Detail Layout
@Composable
private fun UserDetails(
    name: String = "Naman",
    phoneNumber: String = "9305243422",
    email: String = "namangr8y@gmail.com",
    userProfileAddress: UserProfileAddress = UserProfileAddress(
        country = "India",
        city = "Kanpur",
        pin = "208016",
        street = "Bamba Road",
        address = "1531 Gopalpuram"
    ),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level4),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        BodyTexts.Level1(text = name)
        if (phoneNumber.isNotEmpty()) {
            BodyTexts.Level2(text = "+91 $phoneNumber")
        }
        BodyTexts.Level2(text = email)
        if (userProfileAddress.pin.isNotEmpty()) {
            BodyTexts.Level3(text = buildString {
                append(userProfileAddress.address)
                append(" ")
                append(userProfileAddress.street)
                append(" ")
                append(userProfileAddress.city)
                append(" ")
                append(userProfileAddress.country)
                append(" - ")
                append(userProfileAddress.pin)
            }, textAlign = TextAlign.Center)
        }
    }
}


@Composable
fun ProfileDetails(
    imageID: Int,
    profileType: String,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    AppCard(
        modifier = modifier, onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.spacing.level2, vertical = AppTheme.spacing.level1
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppLocalImage(
                painter = painterResource(id = imageID),
                contentDescription = "Details Images",
                modifier = Modifier.size(AppTheme.imageSize.level3)
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            CaptionTexts.Level3(text = profileType, textAlign = TextAlign.Center)
        }
    }
}