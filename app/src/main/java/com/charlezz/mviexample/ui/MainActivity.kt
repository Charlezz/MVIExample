package com.charlezz.mviexample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.charlezz.mviexample.ui.intent.MainIntent
import com.charlezz.mviexample.ui.view.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    MainScreen(
                        mainState = mainViewModel.state.collectAsState().value,
                        onFetchClick = {
                            lifecycleScope.launch {
                                mainViewModel.userIntent.send(MainIntent.FetchUsers)
                            }
                        }
                    )
                }
            }
        }
    }

}