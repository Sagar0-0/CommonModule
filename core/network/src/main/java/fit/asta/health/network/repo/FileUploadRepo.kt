package fit.asta.health.network.repo

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import fit.asta.health.network.api.Api
import fit.asta.health.network.data.MultiFileUpload
import fit.asta.health.network.data.SingleFileUpload
import fit.asta.health.network.data.UploadInfo
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class FileUploadRepo(
    private val context: Context,
    private val remoteApi: Api,
) {

    suspend fun uploadFile(fileInfo: UploadInfo): Flow<SingleFileUpload> {

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

        val documentFile = DocumentFile.fromSingleUri(context, fileInfo.filePath)
        val contentPart = InputStreamRequestBody(context.contentResolver, fileInfo.filePath)
        val multiPart = MultipartBody.Part.createFormData(
            name = "file",
            filename = documentFile?.name,
            body = contentPart
        )

        return flow {
            withContext(Dispatchers.IO) {
                emit(
                    remoteApi.uploadFile(fileInfo, multiPart).singleFile
                )
            }
        }
    }

    suspend fun uploadFiles(list: List<UploadInfo>): Flow<MultiFileUpload> {

        val multipart = MultipartBody.Builder().setType(MultipartBody.FORM)

        /*val id = fileInfo.id.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
        val body: RequestBody = healthHisList[0].toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        multipart.addFormDataPart(name = "json", filename = "", healthHisList)*/

        list.forEach {

            val documentFile = DocumentFile.fromSingleUri(context, it.filePath)
            val contentPart = InputStreamRequestBody(context.contentResolver, it.filePath)
            multipart.addFormDataPart(name = "file", filename = documentFile?.name, contentPart)
        }

        return flow {
            withContext(Dispatchers.IO) {
                emit(
                    remoteApi.uploadFiles(null, multipart.build()).multiFile
                )
            }
        }
    }
}