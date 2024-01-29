package com.chirag047.tuber.screen

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.chirag047.tuber.Common.ResponseType
import com.chirag047.tuber.R
import com.chirag047.tuber.components.FilledCustomButton
import com.chirag047.tuber.components.progressBar
import com.chirag047.tuber.components.topBar
import com.chirag047.tuber.models.YoutubeModel
import com.chirag047.tuber.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(context: Context) {

    val homeViewModel: HomeViewModel = viewModel()

    val progressVisible = remember {
        mutableStateOf(false)
    }

    val contentVisible = remember {
        mutableStateOf(false)
    }

    val thumbnail = remember {
        mutableStateOf("")
    }

    Column {
        topBar("Home")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            var search = remember {
                mutableStateOf("")
            }

            OutlinedTextField(value = search.value,
                onValueChange = {
                    search.value = it
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 0.dp, 15.dp, 0.dp),
                placeholder = {
                    Text(
                        text = "Paste link here...",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp, 2.dp, 0.dp, 0.dp)
                    )
                })

            FilledCustomButton(imageIcon = R.drawable.searchicon) {

                progressVisible.value = true
                contentVisible.value = false

                val tempUrl1 = search.value
                val tempUrl2 = tempUrl1.substringAfterLast("/")
                val finalUrl = tempUrl2.substringBefore("?")

                homeViewModel.getResponse(finalUrl)

            }
        }

        val scrollState = rememberScrollState()

        val responseData = remember {
            mutableStateOf(YoutubeModel())
        }

        LaunchedEffect(key1 = "dm") {
            CoroutineScope(Dispatchers.IO).launch {
                val response = homeViewModel.data.onStart {

                }.onCompletion {
                    progressVisible.value = false
                    contentVisible.value = true
                }.collect {
                    when (it) {
                        is ResponseType.Sucess -> {
                            progressVisible.value = false
                            contentVisible.value = true
                            responseData.value = it.data!!
                        }

                        is ResponseType.Error -> {

                        }

                        is ResponseType.Loading -> {
                            //progressVisible.value = true
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = contentVisible.value) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 5.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = rememberImagePainter(thumbnail.value),
                        modifier = Modifier
                            .size(100.dp)
                            .padding(15.dp, 5.dp, 15.dp, 0.dp),
                        contentDescription = ""
                    )

                    Text(
                        text = responseData.value.videoDetails.title,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(0.dp, 15.dp, 15.dp, 15.dp)
                    )
                }

                responseData.value.videoDetails.thumbnail.thumbnails.forEach {
                    thumbnail.value = it.url
                }

                responseData.value.streamingData.formats.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val fileSize = remember {
                            mutableStateOf("...")
                        }
                        LaunchedEffect(key1 = "calcu") {
                            CoroutineScope(Dispatchers.IO).launch {
                                val url = URL(it.url)
                                val connection = url.openConnection()
                                connection.connect()
                                fileSize.value = android.text.format.Formatter.formatFileSize(
                                    context,
                                    connection.contentLengthLong
                                )
                            }
                        }

                        Text(
                            text = it.qualityLabel + " (" + fileSize.value + ")",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(15.dp)
                        )

                        Button(
                            modifier = Modifier
                                .padding(20.dp),
                            content = {
                                Text(
                                    text = "Download",
                                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(8.dp)
                                )
                            },
                            onClick = {
                                downloadVideo(
                                    it.url,
                                    responseData.value!!.videoDetails.title,
                                    context
                                )
                            }
                        )
                    }
                }
                responseData.value.streamingData.adaptiveFormats.forEach {
                    if (it.qualityLabel.isEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, 5.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Audio" + " (" + android.text.format.Formatter.formatFileSize(
                                    context,
                                    it.contentLength.toLong()
                                ) + ")",
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(15.dp)
                            )

                            Button(
                                modifier = Modifier
                                    .padding(20.dp),
                                content = {
                                    Text(
                                        text = "Download",
                                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                },
                                onClick = {
                                    downloadVideo(
                                        it.url,
                                        responseData.value.videoDetails.title,
                                        context
                                    )
                                }
                            )
                        }
                    }
                }
            }

        }

    }

    progressBar(progressVisible.value)

}

fun downloadVideo(url: String, title: String, context: Context) {

    CoroutineScope(Dispatchers.IO).launch {

        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle("Tuber")
                .setShowRunningNotification(true)
                .setDescription(title)
                .setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_MOBILE
                            or DownloadManager.Request.NETWORK_WIFI
                )
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + ".mp4")
                .setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE
                            or DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                )

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}
