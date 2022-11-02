package com.example.testcomposemvvm.presentation.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import kotlinx.coroutines.NonDisposableHandle.parent


@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed:Boolean
) {
    if (isDisplayed)
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val constraints = if (minWidth < 600.dp) {
                myDecoupledConstraints(.3f)
            } else {
                myDecoupledConstraints(.7f)
            }
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),
                constraintSet = constraints

            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("progressBar"),

                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "Loading",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    ),
                    modifier = Modifier.layoutId("loadingText"))

            }

        }
}
private fun myDecoupledConstraints(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideLine = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor("progressBar")
        val text = createRefFor("loadingText")

        constrain(progressBar) {
            top.linkTo(guideLine)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(text) {
            top.linkTo(progressBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

