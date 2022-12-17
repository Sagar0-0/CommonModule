package fit.asta.health.network.uploader

/*import fit.asta.health.android.client.api2.mapping.toUploadedFile
import fit.asta.health.android.client.utils.map
import fit.asta.health.android.client.utils.toUnitResult*/


/*
internal class StreamFileUploader(
    private val retrofitCdnApi: RemoteApis,
) : FileUploader {

    override fun sendFile(
        channelType: String,
        channelId: String,
        userId: String,
        file: File,
        callback: ProgressCallback,
    ): Result<UploadedFile> {
        val body = file.asRequestBody(file.getMediaType())
        val part = MultipartBody.Part.createFormData("file", file.name, body)

        return retrofitCdnApi.sendFile(
            channelType = channelType,
            channelId = channelId,
            file = part,
            progressCallback = callback,
        ).execute().map {
            it.toUploadedFile()
        }
    }

    override fun sendFile(
        channelType: String,
        channelId: String,
        userId: String,
        file: File,
    ): Result<UploadedFile> {
        val body = file.asRequestBody(file.getMediaType())
        val part = MultipartBody.Part.createFormData("file", file.name, body)

        return retrofitCdnApi.sendFile(
            channelType = channelType,
            channelId = channelId,
            file = part,
            progressCallback = null,
        ).execute().map {
            it.toUploadedFile()
        }
    }

    override fun deleteFile(
        channelType: String,
        channelId: String,
        userId: String,
        url: String,
    ): Result<Unit> {
        return retrofitCdnApi.deleteFile(
            channelType = channelType,
            channelId = channelId,
            url = url
        ).execute().toUnitResult()
    }
}
*/
