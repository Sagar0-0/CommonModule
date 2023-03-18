package fit.asta.health.tools.walking.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import fit.asta.health.tools.walking.sensor.AndroidSensor

class LightSensor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT,
    sensorType = Sensor.TYPE_LIGHT
)
class StepsSensor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_STEP_COUNTER,
    sensorType = Sensor.TYPE_STEP_COUNTER
)