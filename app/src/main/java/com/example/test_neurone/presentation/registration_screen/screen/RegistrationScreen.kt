package com.example.test_neurone.presentation.registration_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.core.ui.BaseScreen
import com.example.test_neurone.core.ui.ErrorScreen
import com.example.test_neurone.core.ui.Header
import com.example.test_neurone.core.ui.LoadingScreen
import com.example.test_neurone.core.ui.UnauthorizedScreen

import com.example.test_neurone.presentation.registration_screen.state.RegistrationScreenState
import com.example.test_neurone.presentation.registration_screen.view_model.RegistrationViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreenContainer(
    viewModel: RegistrationViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val status = state.screenState

    when (status) {
        ScreenStatus.LOADING -> LoadingScreen()
        ScreenStatus.SUCCESS -> RegistrationScreen(state, navController, viewModel)
        ScreenStatus.ERROR -> ErrorScreen()
        ScreenStatus.UNAUTHORIZED -> UnauthorizedScreen()
    }
}


@Composable
fun RegistrationScreen(
    state: RegistrationScreenState,
    navController: NavController,
    viewModel: RegistrationViewModel
) {

    //базовый экран с отступами фоном и т.д.
    BaseScreen {
        // хедер
        Header { navController.popBackStack() }

        // отступ
        Spacer(modifier = Modifier.height(32.dp))

        // текст регистрация для клиентов банка
        Text(
            text = "Регистрация для\nклиентов банка",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 26.sp,
            lineHeight = 30.sp
        )

        // отступ
        Spacer(modifier = Modifier.height(24.dp))

        //поле номер участника
        BasicTextField(
            placeholder = "Номер участника",
            underFieldText = "Номер из 16 цифр, который вы получили от банка",
            value = state.user.userId,
            onValueChange = { viewModel.onUserIdChange(it) },
            keyboardType = KeyboardType.Number,
            onlyDigits = true,
            isCorrectField = !state.userIdValidation.isError,
            errorMessage = state.userIdValidation.errorMessage
        )
        // отступ
        Spacer(modifier = Modifier.height(12.dp))

        //поле код
        BasicTextField(
            placeholder = "Код",
            underFieldText = "Код, который вы получили от банка",
            value = state.user.code,
            onValueChange = { viewModel.onCodeChange(it) },

            keyboardType = KeyboardType.Number,
            onlyDigits = true,
            isCorrectField = !state.codeValidation.isError,
            errorMessage = state.codeValidation.errorMessage
        )


        // отступ
        Spacer(modifier = Modifier.height(8.dp))

        //поле имя участника
        BasicTextField(
            placeholder = "Имя",
            underFieldText = "Имя (на латинице, как в загранпаспорте)",
            value = state.user.name,
            onValueChange = { viewModel.onNameChange(it) },
            isCorrectField = !state.nameValidation.isError,
            errorMessage = state.nameValidation.errorMessage
        )


        // отступ
        Spacer(modifier = Modifier.height(8.dp))

        //поле фамилия участника
        BasicTextField(
            placeholder = "Фамилия",
            underFieldText = "Фамилия (на латинице, как в загранпаспорте)",
            value = state.user.surname,
            onValueChange = { viewModel.onSurnameChange(it) },
            isCorrectField = !state.surnameValidation.isError,
            errorMessage = state.surnameValidation.errorMessage
        )


        // отступ
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )


        //кнопка регистрации
        RegistrationButton(
            state.isFormValid,
            {
                viewModel.submitForm {
                    navController.navigate("home_screen") {
                        popUpTo("registration_screen") { inclusive = true }
                    }
                }
            })

        // отступ
        Spacer(modifier = Modifier.height(12.dp))


    }
}


// поле ввода (контейнер)
@Composable
fun BasicTextField(
    isCorrectField: Boolean,
    errorMessage: String,
    placeholder: String,
    underFieldText: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    onlyDigits: Boolean = false
) {
    val colors = MaterialTheme.colorScheme

    Column(modifier = modifier) {

        TextField(
            value = value,
            onValueChange = { newValue ->
                val processed =
                    if (onlyDigits) newValue.filter { it.isDigit() }
                    else newValue
                onValueChange(processed)
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = colors.onSecondary,
                    fontSize = 16.sp
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors.onBackground,
                unfocusedContainerColor = colors.onBackground,
                errorContainerColor = colors.onBackground,

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,

                cursorColor = colors.onPrimary,
                focusedTextColor = colors.onPrimary,
                unfocusedTextColor = colors.onPrimary,
                errorTextColor = colors.onErrorContainer
            ),
            isError = !isCorrectField,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = if (!isCorrectField) colors.onErrorContainer else Color.Transparent,
                    shape = RoundedCornerShape(20.dp)
                )
        )

        Spacer(modifier = Modifier.height(2.dp))

        if (isCorrectField) {
            Text(
                text = underFieldText,
                color = colors.onSecondary,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 2.dp)
            )
        } else {
            Text(
                text = errorMessage,
                color = colors.onErrorContainer,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 2.dp)
            )
        }

    }
}

//кнопка с текстом снизу
@Composable
fun RegistrationButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme


    // текст с соглашением


    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Нажимая кнопку продолжить,",
            color = colors.onPrimary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "вы соглашаетесь с ",
                color = colors.onPrimary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "условиями участия",
                color = colors.onPrimary,
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { /* туду */ }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // кнопка
        Button(
            onClick = onClick,
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.onPrimaryContainer,
                disabledContainerColor = colors.onSecondaryContainer,
                contentColor = colors.onPrimary,
                disabledContentColor = colors.onPrimary
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "Продолжить",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

