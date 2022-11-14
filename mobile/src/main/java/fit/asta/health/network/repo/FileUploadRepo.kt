package fit.asta.health.network.repo

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.data.FileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException


class FileUploadRepo(
    private val remoteApi: RemoteApis,
) {

    suspend fun uploadFiles(userId: String, list: List<FileInfo>): Boolean {

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