package fit.asta.health.network.repo

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.FileInfo
import fit.asta.health.network.data.UploadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import okio.source
import retrofit2.HttpException
import java.io.IOException


class FileUploadRepo(
    private val context: Context,
    private val remoteApi: RemoteApis,
) {

    suspend fun uploadFile(info: UploadInfo, filePath: Uri): Boolean {

        /*val body = file.asRequestBody(file.getMediaType())
        val part = MultipartBody.Part.createFormData("file", file.name, body)*/

        /*context.contentResolver.query(filePath, null, null, null, null)?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            it.moveToFirst()
            val fileName = it.getString(nameIndex)
            val fileSize = it.getLong(sizeIndex).toString()

            val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)
            multipart.addFormDataPart(
                name = "file",
                filename = documentFile?.name,
                body = contentPart
            )
        }*/

        val documentFile = DocumentFile.fromSingleUri(context, filePath)
        val contentPart = InputStreamRequestBody(context.contentResolver, filePath)

        val multiPart = MultipartBody.Part.createFormData(
            name = "file",
            filename = documentFile?.name,
            body = contentPart
        )

        return withContext(Dispatchers.IO) {

            return@withContext try {
                remoteApi.uploadFile(
                    info.id.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                    info.uid.toRequestBody("multipart/form-data".toMediaType()),
                    info.feature.toRequestBody("multipart/form-data".toMediaType()),
                    multiPart
                )
                true
            } catch (e: HttpException) {
                false
            } catch (e: IOException) {
                false
            }
        }
    }

    suspend fun uploadFiles(info: UploadInfo, list: List<FileInfo>): Boolean {

        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)

        list.forEach {
            multipart.addFormDataPart(
                name = it.name,
                filename = it.file.name,
                body = it.file.asRequestBody(it.mediaType.toMediaType())
            )
        }

        return withContext(Dispatchers.IO) {

            return@withContext try {
                remoteApi.uploadFiles(
                    null,
                    multipart.build()
                )
                true
            } catch (e: HttpException) {
                false
            } catch (e: IOException) {
                false
            }
        }
    }
}

class InputStreamRequestBody(
    private val contentResolver: ContentResolver,
    private val uri: Uri
) : RequestBody() {

    override fun contentType() = contentResolver.getType(uri)?.toMediaTypeOrNull()

    override fun contentLength(): Long = -1

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {

        val input = contentResolver.openInputStream(uri)
        input?.use { sink.writeAll(it.source()) }
            ?: throw IOException("Could not open $uri")
    }
}