package com.example.test_neurone.presentation.purchases_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.test_neurone.R
import com.example.test_neurone.core.components.ScreenStatus
import com.example.test_neurone.core.ui.BaseScreen
import com.example.test_neurone.core.ui.ErrorScreen
import com.example.test_neurone.core.ui.Header
import com.example.test_neurone.core.ui.LoadingScreen
import com.example.test_neurone.core.ui.UnauthorizedScreen
import com.example.test_neurone.domain.models.PurchaseGroup
import com.example.test_neurone.presentation.purchases_screen.state.PurchasesScreenState
import com.example.test_neurone.presentation.purchases_screen.view_model.PurchasesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PurchasesScreenContainer(
    viewModel: PurchasesViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val status = state.screenState

    when (status) {
        ScreenStatus.LOADING -> LoadingScreen()
        ScreenStatus.SUCCESS -> PurchasesScreen(state, navController)
        ScreenStatus.ERROR -> ErrorScreen()
        ScreenStatus.UNAUTHORIZED -> UnauthorizedScreen()
    }
}

@Composable
fun PurchasesScreen(
    state: PurchasesScreenState,
    navController: NavController
) {
    BaseScreen(useVerticalScroll = false) {

        // хедер
        Header { navController.popBackStack() }

        // отступ
        Spacer(modifier = Modifier.height(32.dp))

        // заголовок
        Text(
            text = stringResource(R.string.my_purchases_title),
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 26.sp,
        )

        // отступ
        Spacer(modifier = Modifier.height(24.dp))

        // список покупок
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.purchases) { purchaseGroup ->
                PurchaseGroupItem(purchaseGroup)
            }
        }
    }
}

// группа по дате
@Composable
fun PurchaseGroupItem(purchaseGroup: PurchaseGroup) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        // дата группы покупок
        Text(
            text = purchaseGroup.date,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // контейнер с покупками
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            purchaseGroup.items.forEach { item ->
                Text(
                    text = item,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
            }
        }
    }
}
