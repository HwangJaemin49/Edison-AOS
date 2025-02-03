package com.umc.edison.ui.components

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.umc.edison.ui.theme.Gray500
import com.umc.edison.ui.theme.Gray800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageGallery(
    onImageSelected: (List<Uri>) -> Unit,
    multiSelectMode: Boolean,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val images = remember { mutableStateListOf<Uri>() }
    val selectedImages = remember { mutableStateListOf<Uri>() }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            images.addAll(loadGalleryImages(context))
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    BottomSheet(onDismiss = { onClose() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            if (multiSelectMode) {
                Text(
                    text = "선택(${selectedImages.size})",
                    modifier = Modifier.clickable(
                        onClick = {
                            onImageSelected(selectedImages)
                            selectedImages.clear()
                            onClose()
                        },
                        enabled = selectedImages.isNotEmpty()
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selectedImages.isEmpty()) Gray500 else Gray800
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(count = images.size, key = { index -> images[index].hashCode() }) { index ->
                    val uri = images[index]
                    val isSelected = selectedImages.contains(uri)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                if (multiSelectMode) {
                                    if (isSelected) {
                                        selectedImages.remove(uri)
                                    } else {
                                        selectedImages.add(uri)
                                    }
                                } else {
                                    selectedImages.add(uri)
                                    onImageSelected(selectedImages)
                                    selectedImages.clear()
                                    onClose()
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                if (multiSelectMode) {
                                    if (isSelected) {
                                        selectedImages.remove(uri)
                                    } else {
                                        selectedImages.add(uri)
                                    }
                                } else {
                                    selectedImages.add(uri)
                                    onImageSelected(selectedImages)
                                    selectedImages.clear()
                                    onClose()
                                }
                            },
                            modifier = Modifier.align(Alignment.TopEnd).size(24.dp).padding(4.dp)
                        )

                        if (multiSelectMode) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = if (isSelected) Gray800.copy(alpha = 0.5f) else Color.Transparent
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun loadGalleryImages(context: Context): List<Uri> {
    val images = mutableListOf<Uri>()
    val projection = arrayOf(
        MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME
    )
    val uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val cursor = context.contentResolver.query(
        uriExternal, projection, null, null, "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
    )

    cursor?.use {
        val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

        while (it.moveToNext()) {
            val id = it.getLong(idColumn)
            val name = it.getString(nameColumn)
            val contentUri = ContentUris.withAppendedId(uriExternal, id)

            Log.d("GalleryImage", "Image: $name, URI: $contentUri")
            images.add(contentUri)
        }
    }
    return images
}
