package com.example.testcomposemvvm.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable()
fun defaultSnckBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier =Modifier,
    onDismiss :() -> Unit,
){
    SnackbarHost(hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        action = {
                            data.actionLabel?.let { action_label ->
                                TextButton(onClick = onDismiss) {
                                    Text(text = action_label,
                                        style = MaterialTheme.typography.body2,
                                        color = Color.White)
                                }
                            }
                        }
                    ){
                         Text(text = data.message,
                             style = MaterialTheme.typography.body2,
                             color = Color.White)
                    }

                    },
        modifier = modifier

        )
}