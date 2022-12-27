package com.charlezz.mviexample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.charlezz.mviexample.ui.view.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.sideEffects
            .onEach { Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)

        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(
                        state = mainViewModel.state.collectAsState().value,
                        onFetchClick = { mainViewModel.fetchUser() }
                    )
                }
            }
        }
    }

}