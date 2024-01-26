package com.advanced.base.components

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.advanced.base.R

@Composable
fun FilledCustomButton(imageIcon: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(50.dp)),  //avoid the oval shape
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            painterResource(id = imageIcon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(50.dp)
                .padding(15.dp)
        )
    }
}

fun downlaodMeme(url: String, context: Context) {
    val request = DownloadManager.Request(Uri.parse(url))
        .setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE
                    or DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        )
        .setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_MOBILE
                    or DownloadManager.Request.NETWORK_WIFI
        )
        .setTitle("Meme")
        .setDescription("Donwloading meme")
        .setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            System.currentTimeMillis().toString() + ".jpg"
        )

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)


}


fun share(url: String, context: Context) {

}