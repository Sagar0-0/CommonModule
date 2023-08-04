package fit.asta.health.payments.referral.view

sealed class ReferralScreen(val route: String) {
    object Share : ReferralScreen("rs_share")
}
                                    Row(
                                        Modifier
                                            .padding(spacing.medium)
                                            .fillMaxWidth()
                                    ) {
                                        var code by rememberSaveable {
                                            mutableStateOf("")
                                        }
                                        TextField(
                                            modifier = Modifier.weight(1f),
                                            value = code,
                                            onValueChange = {
                                                if (it.length <= 8) code = it
                                            }
                                        )
                                        Button(
                                            enabled = code.length == 8 && enabled.value,
                                            onClick = {
                                                onCheckRefereeData(code)
                                                showLoading.value = true
                                                enabled.value = false
                                            }
                                        ) {
                                            Text(text = "Check")
                                        }
                                    }
                                }
                            }
                        } else {
                            ReferralCustomCard(title = "Referred by:") {
                                ReferredUserItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(spacing.medium),
                                    user = referralDataState.data.data.referredByUsersDetails
                                )
                            }
                        }
                    }

                    val referredUsers = referralDataState.data.data.referredUsers
                    referredUsers?.let {
                        items(it) { user ->
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(spacing.medium)
                                    .height(1.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            )
                            ReferredUserItem(
                                user = user
                            )
                        }
                    }
                }
            }

            else -> {}
        }

    }
}

@Composable
fun ReferralCustomCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
            .background(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Text(
            modifier = Modifier
                .padding(top = spacing.medium, start = spacing.medium),
            text = title,
            textAlign = TextAlign.Start
        )

        content()
    }
}

@Composable
private fun ReferralCard(code: String) {
    val context = LocalContext.current
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
                .clickable {
                    context.shareReferralCode(code)
                }
                .paint(
                    painterResource(id = R.drawable.error_404)
                ),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(spacing.large),
                text = stringResource(id = R.string.refer_and_earn),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = spacing.large, start = spacing.large, end = spacing.large),
                text = stringResource(id = R.string.refer_invite_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(spacing.medium)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Default.Share,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondaryContainer
            )

        }
        Box(
            modifier = Modifier
                .padding(spacing.medium)
                .align(Alignment.CenterHorizontally)
                .dashedBorder(
                    width = 1.dp,
                    radius = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                .clickable {
                    context.copyTextToClipboard(
                        code
                    )
                }
        ) {
            Text(
                modifier = Modifier.padding(spacing.medium),
                text = stringResource(id = R.string.code_side_text) + code,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }


}

@Composable
private fun ReferredUserItem(
    modifier: Modifier = Modifier,
    user: UserDetails
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(if (user.prime) Color.Green else Color.Red),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(spacing.small)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(
                model = getImgUrl(url = user.pic),
                placeholder = painterResource(
                    id = R.drawable.ic_person
                )
            ), contentDescription = "Profile"
        )
        Text(text = user.name)
    }
}