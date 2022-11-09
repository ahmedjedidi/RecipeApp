package com.example.testcomposemvvm.presentation.utils

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SnackBarController(
    private val scope:CoroutineScope
){
 private var snackBarJob:Job?=null
 fun getScope() = scope


    init {
        cancelSnackbarJob()
    }
 fun showSnackBar(
     scaffoldState: ScaffoldState,
     message:String,
     actionLabel:String
 ){
     if(snackBarJob == null){
         snackBarJob= scope.launch {
             scaffoldState.snackbarHostState.showSnackbar(
                 message = message,
                 actionLabel=actionLabel
             )
             cancelSnackbarJob()
         }
     }
     else{
         cancelSnackbarJob()
         snackBarJob= scope.launch {
             scaffoldState.snackbarHostState.showSnackbar(
                 message = message,
                 actionLabel=actionLabel
             )
             cancelSnackbarJob()
         }
     }
 }

    fun cancelSnackbarJob(){
        snackBarJob?.let {job ->
            job.cancel()
            snackBarJob = Job()
        }
    }
}