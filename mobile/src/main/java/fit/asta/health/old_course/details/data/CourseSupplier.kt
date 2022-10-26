package fit.asta.health.old_course.details.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import fit.asta.health.old_course.details.networkdata.CourseDetailsNetData
import fit.asta.health.utils.ONE_MEGABYTE
import fit.asta.health.utils.loadJSONFromAsset


class CourseSupplier {

    private val mStorageRef: StorageReference? = FirebaseStorage.getInstance().reference

    fun get(url: String): LiveData<CourseDetailsNetData> {

        val mCourse = MutableLiveData<CourseDetailsNetData>()
        val storageRef: StorageReference = mStorageRef!!.child(url)
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->

            parse(bytes.toString(Charsets.UTF_8), mCourse)
        }

        return mCourse
    }

    private fun parse(json: String, mCourseDetailsData: MutableLiveData<CourseDetailsNetData>) {

        try {

            //val turnsType = object : TypeToken<List<Category>>() {}.type
            val gson = Gson()
            val course = gson.fromJson(json, CourseDetailsNetData::class.java)
            mCourseDetailsData.value = course

        } catch (e: JsonSyntaxException) {

            e.printStackTrace()
        }
    }

    fun get(context: Context, url: String): LiveData<CourseDetailsNetData> {

        val mCourse = MutableLiveData<CourseDetailsNetData>()
        val jsonCourse = context.loadJSONFromAsset(url)!!
        parse(jsonCourse, mCourse)

        return mCourse
    }
}