package com.example.test_neurone.presentation.home_screen.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.test_neurone.R
import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.core.ui.*
import com.example.test_neurone.presentation.home_screen.state.HomeScreenState
import com.example.test_neurone.presentation.home_screen.view_model.HomeViewModel
import org.koin.androidx.compose.koinViewModel

// обертка
@Composable
fun HomeScreenContainer(viewModel: HomeViewModel = koinViewModel(), navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState() //при каждом заходе на экран тянуть данные

    LaunchedEffect(navBackStackEntry) {
        viewModel.onScreenResume()
    }

    val state by viewModel.state.collectAsState()
    val status = state.screenState // статус экрана через енам класс

    when (status) {
        ScreenStatus.LOADING -> LoadingScreen()
        ScreenStatus.SUCCESS -> HomeScreen(state, navController, viewModel)
        ScreenStatus.ERROR -> ErrorScreen()
        ScreenStatus.UNAUTHORIZED -> UnauthorizedScreen()
    }
}

// основной экран
@Composable
fun HomeScreen(state: HomeScreenState, navController: NavController, viewModel: HomeViewModel) {

    //базовый экран с отступами фоном и т.д.
    BaseScreen {

        // хедер
        Header { navController.popBackStack() }

        // отступ
        Spacer(modifier = Modifier.height(32.dp))

        // фамилия имя
        UserNameBlock(state.user.name, state.user.surname)

        // отступ
        Spacer(modifier = Modifier.height(16.dp))

        // номер телефона
        Text(
            text = state.user.phoneNumber,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 12.sp
        )

        // отступ
        Spacer(modifier = Modifier.height(16.dp))

        // надпись мои покупки
        Text(
            text = stringResource(R.string.my_purchases),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 10.sp
        )

        // блок с покупками
        UserContentContainerRow(
            { navController.navigate("purchases_screen") },
            { UserPurchases() }
        )

        // отступ
        Spacer(modifier = Modifier.height(16.dp))

        // надпись настройки
        Text(
            text = stringResource(R.string.settings),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 10.sp
        )

        val emailConfirmed by remember(state) { derivedStateOf { state.isEmailConfirmed } }

        // блок с почтой
        UserContentContainerRow(
            { },
            { UserEmail(emailConfirmed, state.user.email) }
        )

        // отступ
        Spacer(modifier = Modifier.height(8.dp))

        // биометрия
        UserBiometry(state.isActivatedBiometryEntry) { viewModel.changeBiometryAccess() }

        // отступ
        Spacer(modifier = Modifier.height(8.dp))

        // сменить 4-х значный код
        UserContentContainerRow(
            { },
            { TextForContainer(stringResource(R.string.change_4_digit_code)) }
        )

        // отступ
        Spacer(modifier = Modifier.height(8.dp))

        // регистрация для клиентов банка
        UserContentContainerRow(
            { navController.navigate("singup_screen")},
            { TextForContainer(stringResource(R.string.bank_clients_registration)) }
        )

        // отступ
        Spacer(modifier = Modifier.height(8.dp))

        // смена языка
        UserContentContainerRow(
            { },
            { ChangeLanguage("Русский") }
        )
    }
}

// имя фамилия и иконка карандаша
@Composable
fun UserNameBlock(name: String, surname: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {

        Text(
            text = name.ifEmpty { "Ivan" },
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = surname.ifEmpty { "Ivanov" },
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.width(8.dp))

            // иконка редактирования
            Icon(
                painter = painterResource(R.drawable.edit),
                tint = MaterialTheme.colorScheme.onSecondary,
                contentDescription = stringResource(R.string.cd_edit_button),
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

// карточка-контейнтер
@Composable
fun UserContentContainerRow(
    onClick: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onBackground)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 8.dp)
        ) {
            content(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
        }

        // стрелка справа
        Icon(
            painter = painterResource(R.drawable.small_arrow_right),
            contentDescription = stringResource(R.string.cd_more),
            modifier = Modifier
                .size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.width(8.dp))
    }
}

// значки покупок (вставляем в контейнер выше)
@Composable
fun UserPurchases() {
    val colors = remember {
        listOf(
            Color(0xFF1565C0),
            Color(0xFF2E7D32),
            Color(0xFF6A1B9A),
            Color(0xFFF57F17),
            Color(0xFFD84315),
            Color(0xFF00897B),
            Color(0xFF5D4037),
            Color(0xFF455A64),
            Color(0xFF7B1FA2),
            Color(0xFF3949AB)
        ) //мок покупок
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(colors) { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

// почта (тоже вставляем в контейнер)
@Composable
fun UserEmail(confirmedEmail: Boolean, email: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = stringResource(R.string.email),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = email,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            if (!confirmedEmail) {
                Text(
                    text = stringResource(R.string.email_must_confirm),
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }

}

// вход по биометрии
@Composable
fun UserBiometry(
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onBackground),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(R.string.biometry_login),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .weight(1f))


        val trackColor by animateColorAsState(
            targetValue = if (checked)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.background,
            animationSpec = tween(300)
        )

        Box(
            modifier = Modifier.height(8.dp)
        ) {
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = trackColor,
                    uncheckedTrackColor = trackColor,
                    uncheckedBorderColor = Color.Transparent,
                    checkedBorderColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))
    }
}

// контейнер для текста, для блоков клиентов банка и смены кода (тоже вставляем в контейнер)
@Composable
fun TextForContainer(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }

}

// смена языка, тоже вставляем в контейнер
@Composable
fun ChangeLanguage(lang: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = stringResource(R.string.language),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Text(
            text = lang,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}
