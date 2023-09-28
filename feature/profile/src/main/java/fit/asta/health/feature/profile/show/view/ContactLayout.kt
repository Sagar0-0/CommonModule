package fit.asta.health.profile.feature.show.view

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.data.profile.remote.model.Address
import fit.asta.health.data.profile.remote.model.Contact
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDefServerImg
import fit.asta.health.designsystem.components.generic.AppDrawImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.extras.theme.ProfileBorder1
import fit.asta.health.designsystemx.extras.theme.ProfileBorder2
import fit.asta.health.designsystemx.extras.theme.ProfileBorder3
import fit.asta.health.designsystemx.AppTheme
import fit.asta.health.resources.strings.R
import fit.asta.health.resources.drawables.R as RDraw

@Composable
fun ContactLayout(
    basicDetails: Contact,
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
private fun UserProfileSection(basicDetails: Contact) {
    Column(
        modifier = Modifier
            .padding(top = AppTheme.appSpacing.medium)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserProfileImg(userProfilePic = basicDetails.url)
        Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
        UserDetails(
            basicDetails.name,
            basicDetails.phone,
            basicDetails.email,
            basicDetails.address,
        )
        Spacer(modifier = Modifier.height(AppTheme.appSpacing.medium))
    }
}

@Composable
private fun UserAchievementsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.appSpacing.medium),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small)
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
            .padding(AppTheme.appSpacing.medium),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small),
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
        getImgUrl(url = userProfilePic.url)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = AppTheme.appSpacing.small)
    ) {
        ProfileImageBorder(size = 188.dp, color = MaterialTheme.colorScheme.primary) {
            AppDefServerImg(
                model = imageUrl,
                contentDescription = "User Profile Pic",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(AppTheme.imageSize.picSize)
                    .border(
                        border = BorderStroke(
                            width = 2.dp, color = MaterialTheme.colorScheme.primary
                        ), shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
        }
        ProfileImageBorder(size = 218.dp, color = ProfileBorder1)
        ProfileImageBorder(size = 248.dp, color = ProfileBorder2)
        ProfileImageBorder(size = 278.dp, color = ProfileBorder3)
    }
}

@Composable
private fun ProfileImageBorder(size: Dp, color: Color, content: @Composable () -> Unit = {}) {
    Surface(
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
            modifier = Modifier.padding(AppTheme.appSpacing.medium)
        ) {
            AppDrawImg(
                painter = painterResource(id = imageID),
                contentDescription = "Leaderboard",
                modifier = Modifier.size(AppTheme.imageSize.largeMedium)
            )
            Spacer(modifier = Modifier.width(AppTheme.appSpacing.medium))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.minSmall)
            ) {
                AppTexts.BodyLarge(text = scoreBoard)
                AppTexts.LabelSmall(text = cardType)
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
    address: Address = Address(
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
            .padding(horizontal = AppTheme.appSpacing.large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.appSpacing.small)
    ) {
        AppTexts.BodyLarge(text = name)
        if (phoneNumber.isNotEmpty()) {
            AppTexts.BodyMedium(text = "+91 $phoneNumber")
        }
        AppTexts.BodyMedium(text = email)
        if (address.pin.isNotEmpty()) {
            AppTexts.BodySmall(text = buildString {
                append(address.address)
                append(" ")
                append(address.street)
                append(" ")
                append(address.city)
                append(" ")
                append(address.country)
                append(" - ")
                append(address.pin)
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
    AppCard(modifier = modifier, onClick = onCardClick) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.appSpacing.medium,
                    vertical = AppTheme.appSpacing.small
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppDrawImg(
                painter = painterResource(id = imageID),
                contentDescription = "Details Images",
                modifier = Modifier.size(AppTheme.imageSize.standard)
            )
            Spacer(modifier = Modifier.height(AppTheme.appSpacing.small))
            AppTexts.LabelMedium(text = profileType, textAlign = TextAlign.Center)
        }
    }
}