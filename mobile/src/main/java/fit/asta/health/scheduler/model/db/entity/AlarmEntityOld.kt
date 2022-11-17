package fit.asta.health.scheduler.model.db.entity


//@Entity(tableName = "ALARM_TABLE")
//@Parcelize
//data class AlarmEntityOld(
//    @ColumnInfo(name = "alarm_status") var alarmStatus: Boolean?,
//    @ColumnInfo(name = "alarm_week") var alarmWeek: Wk?,
//    @ColumnInfo(name = "alarm_tag") var alarmTag: String?,
//    @ColumnInfo(name = "alarm_hour") var alarmHour: String?,
//    @ColumnInfo(name = "alarm_minute") var alarmMinute: String?,
//    @ColumnInfo(name = "alarm_midday") var alarmMidDay: Boolean?,
//    @ColumnInfo(name = "alarm_name") var alarmName: String?,
//    @ColumnInfo(name = "alarm_description") var alarmDescription: String?,
//    @ColumnInfo(name = "alarm_interval") var alarmInterval: IntervalItem?,
//    @ColumnInfo(name = "alarm_mode") var alarmMode: String?,
//    @ColumnInfo(name = "alarm_important") var alarmImportant: Boolean?,
//    @ColumnInfo(name = "alarm_vibrate") var alarmVibrate: VibrationItem?,
//    @ColumnInfo(name = "alarm_tone") var alarmTone: RingtoneItem?,
//    @PrimaryKey(autoGenerate = true) val alarmId: Int = 0
//) : Serializable, Parcelable {
//
//    private fun schedulerPreNotification(
//        context: Context,
//        isInterval: Boolean,
//        interval: AlarmTimeItem?,
//        id: Int
//    ) {
//        val preNotificationManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val preNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        val hour = if (isInterval) {
//            if (interval!!.alarmMidDay) {
//                interval.alarmHour.toInt() + 12
//            } else {
//                interval.alarmHour.toInt()
//            }
//        } else {
//            if (this.alarmMidDay!!) {
//                this.alarmHour!!.toInt() + 12
//            } else {
//                this.alarmHour!!.toInt()
//            }
//        }
//        val millie = if (isInterval) {
//            (hour * 60 * 60000) + (interval!!.alarmMinute.toInt() * 60000) - (this.alarmInterval!!.advancedReminder.advancedRemainderTime * 60000)
//        } else {
//            (hour * 60 * 60000) + (this.alarmMinute!!.toInt() * 60000) - (this.alarmInterval!!.advancedReminder.advancedRemainderTime * 60000)
//        }
//
//        val millieToHour = ((millie / (1000 * 60 * 60)) % 24)
//        val millieToMinute = ((millie / (1000 * 60)) % 60)
//        val preNotificationAlarmEntity = this.copy(
//            alarmHour = (if (millieToHour >= 12) millieToHour - 12 else millieToHour).toString(),
//            alarmMinute = millieToMinute.toString(),
//            alarmMidDay = millieToHour >= 12,
//        )
//        Toast.makeText(
//            context,
//            ": ${((millie / (1000 * 60 * 60)) % 24) >= 12}",
//            Toast.LENGTH_SHORT
//        ).show()
//        bundle.putSerializable(Constants.ARG_PRE_NOTIFICATION_OBJET, preNotificationAlarmEntity)
//        bundle.putInt("id", (id + 1))
//        preNotificationIntent.putExtra(Constants.BUNDLE_PRE_NOTIFICATION_OBJECT, bundle)
//        preNotificationIntent.putExtra("id", (id + 1))
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(
//                context,
//                (id + 1),
//                preNotificationIntent,
//                FLAG_UPDATE_CURRENT
//            )
//
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        if (preNotificationAlarmEntity.alarmMidDay!!) {
//            calendar[Calendar.HOUR_OF_DAY] = preNotificationAlarmEntity.alarmHour!!.toInt() + 12
//        } else {
//            calendar[Calendar.HOUR_OF_DAY] = preNotificationAlarmEntity.alarmHour!!.toInt()
//        }
//        calendar[Calendar.MINUTE] = preNotificationAlarmEntity.alarmMinute!!.toInt()
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//
//        val toastText =
//            "Pre Notification at ${preNotificationAlarmEntity.alarmHour}:${preNotificationAlarmEntity.alarmMinute}"
//
//        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//        preNotificationManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            alarmPendingIntent
//        )
//        preNotificationAlarmEntity.alarmStatus = true
//
//    }
//
//    fun schedulerPostNotification(
//        context: Context,
//        isInterval: Boolean,
//        interval: AlarmTimeItem?,
//        id: Int
//    ) {
//        val postNotificationManager =
//            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val postNotificationIntent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        val hour = if (isInterval) {
//            if (interval!!.alarmMidDay) {
//                interval.alarmHour.toInt() + 12
//            } else {
//                interval.alarmHour.toInt()
//            }
//        } else {
//            if (this.alarmMidDay!!) {
//                this.alarmHour!!.toInt() + 12
//            } else {
//                this.alarmHour!!.toInt()
//            }
//        }
//        val millie = if (isInterval) {
//            (hour * 60 * 60000) + (interval!!.alarmMinute.toInt() * 60000) + (this.alarmInterval!!.duration * 60000)
//        } else {
//            (hour * 60 * 60000) + (this.alarmMinute!!.toInt() * 60000) + (this.alarmInterval!!.duration * 60000)
//        }
//
//        val millieToHour = ((millie / (1000 * 60 * 60)) % 24)
//        val millieToMinute = ((millie / (1000 * 60)) % 60)
//        val postNotificationAlarmEntity = this.copy(
//            alarmHour = (if (millieToHour >= 12) millieToHour - 12 else millieToHour).toString(),
//            alarmMinute = millieToMinute.toString(),
//            alarmMidDay = millieToHour >= 12,
//        )
//        Toast.makeText(
//            context,
//            ": ${((millie / (1000 * 60 * 60)) % 24) >= 12}",
//            Toast.LENGTH_SHORT
//        ).show()
//        bundle.putSerializable(Constants.ARG_POST_NOTIFICATION_OBJET, postNotificationAlarmEntity)
//        bundle.putInt("id", (id + 4))
//        postNotificationIntent.putExtra(Constants.BUNDLE_POST_NOTIFICATION_OBJECT, bundle)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(
//                context,
//                (id + 4),
//                postNotificationIntent,
//                FLAG_UPDATE_CURRENT
//            )
//
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        if (postNotificationAlarmEntity.alarmMidDay!!) {
//            calendar[Calendar.HOUR_OF_DAY] = postNotificationAlarmEntity.alarmHour!!.toInt() + 12
//        } else {
//            calendar[Calendar.HOUR_OF_DAY] = postNotificationAlarmEntity.alarmHour!!.toInt()
//        }
//        calendar[Calendar.MINUTE] = postNotificationAlarmEntity.alarmMinute!!.toInt()
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//
//        val toastText =
//            "Post Notification at ${postNotificationAlarmEntity.alarmHour}:${postNotificationAlarmEntity.alarmMinute}"
//
//        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//        postNotificationManager.setExact(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            alarmPendingIntent
//        )
//        postNotificationAlarmEntity.alarmStatus = true
//
//    }
//
//    fun schedule(context: Context) {
//        var isAlarmHasAlreadyPassed = false
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        bundle.putSerializable(Constants.ARG_ALARM_OBJET, this)
//        intent.putExtra(Constants.BUNDLE_ALARM_OBJECT, bundle)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(context, this.alarmId, intent, FLAG_UPDATE_CURRENT)
//
//        val calendar = Calendar.getInstance()
//        calendar.timeInMillis = System.currentTimeMillis()
//        if (this.alarmMidDay!!) {
//            calendar[Calendar.HOUR_OF_DAY] = this.alarmHour!!.toInt() + 12
//        } else {
//            calendar[Calendar.HOUR_OF_DAY] = this.alarmHour!!.toInt()
//        }
//        calendar[Calendar.MINUTE] = this.alarmMinute!!.toInt()
//        calendar[Calendar.SECOND] = 0
//        calendar[Calendar.MILLISECOND] = 0
//
//        // if alarm time has already passed, increment day by 1
//        if (calendar.timeInMillis <= System.currentTimeMillis()) {
//            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
//            isAlarmHasAlreadyPassed = true
//        }
//
//        Log.d("TAGTAGTAG", "schedule: $isAlarmHasAlreadyPassed")
//
//        if (!alarmWeek!!.recurring) {
//            var toastText: String? = null
//            try {
//                toastText = java.lang.String.format(
//                    Locale.getDefault(),
//                    "One Time Alarm %s scheduled for %s at %02d:%02d", alarmName, DayUtil.toDay(
//                        calendar[Calendar.DAY_OF_WEEK]
//                    ), alarmHour!!.toInt(), alarmMinute!!.toInt()
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//            alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                alarmPendingIntent
//            )
//        } else {
//            val toastText = java.lang.String.format(
//                Locale.getDefault(),
//                "Recurring Alarm %s scheduled for %s at %02d:%02d",
//                alarmName,
//                getRecurringDaysText(alarmWeek!!),
//                alarmHour!!.toInt(), alarmMinute!!.toInt()
//            )
//            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//            val runDaily = (24 * 60 * 60 * 1000).toLong()
//            alarmManager.setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                runDaily,
//                alarmPendingIntent
//            )
//        }
//
//        if (this.alarmInterval!!.isIntervalBeingUsed) {
//            if (this.alarmInterval!!.isVariantInterval) {
//                if (this.alarmInterval!!.staticIntervalTimes.isNotEmpty()) {
//                    this.alarmInterval!!.staticIntervalTimes.forEach {
//                        cancelInterval(context, it)
//                    }
//                }
//                scheduleInterval(
//                    context,
//                    this.alarmInterval!!.variantIntervalTimes,
//                    isAlarmHasAlreadyPassed
//                )
//            } else {
//                if (!this.alarmInterval!!.variantIntervalTimes.isNullOrEmpty()) {
//                    this.alarmInterval!!.variantIntervalTimes!!.forEach {
//                        cancelInterval(context, it)
//                    }
//                }
//                scheduleInterval(
//                    context,
//                    this.alarmInterval!!.staticIntervalTimes,
//                    isAlarmHasAlreadyPassed
//                )
//            }
//        } else {
//            if (this.alarmInterval!!.isVariantInterval) {
//                if (!this.alarmInterval!!.variantIntervalTimes.isNullOrEmpty()) {
//                    this.alarmInterval!!.variantIntervalTimes!!.forEach {
//                        cancelInterval(context, it)
//                    }
//                }
//            } else {
//                if (this.alarmInterval!!.staticIntervalTimes.isNotEmpty()) {
//                    this.alarmInterval!!.staticIntervalTimes.forEach {
//                        cancelInterval(context, it)
//                    }
//                }
//            }
//        }
//
//        if (this.alarmInterval!!.advancedReminder.isAdvancedReminderEnabled) {
//            schedulerPreNotification(context, false, null, this.alarmId)
//        }
////        if (this.alarmInterval!!.isRemainderAtTheEnd) {
////            schedulerPostNotification(context, false, null, this.alarmId)
////        }
//        this.alarmStatus = true
//    }
//
//    fun scheduleInterval(
//        context: Context,
//        listOfVariantIntervals: List<AlarmTimeItem>?,
//        isAlarmHasAlreadyPassed: Boolean
//    ) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val bundle = Bundle()
//        listOfVariantIntervals?.forEach { variantInterval ->
//            bundle.putSerializable(
//                Constants.ARG_VARIANT_INTERVAL_ALARM_OBJECT, this.copy(
//                    alarmHour = variantInterval.alarmHour,
//                    alarmMinute = variantInterval.alarmMinute,
//                    alarmMidDay = variantInterval.alarmMidDay
//                )
//            )
//            bundle.putParcelable(Constants.ARG_VARIANT_INTERVAL_OBJECT, variantInterval)
//            intent.putExtra(Constants.BUNDLE_VARIANT_INTERVAL_OBJECT, bundle)
//            val alarmPendingIntent =
//                PendingIntent.getBroadcast(
//                    context,
//                    variantInterval.alarmTimeId,
//                    intent,
//                    FLAG_UPDATE_CURRENT
//                )
//
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = System.currentTimeMillis()
//            if (variantInterval.alarmMidDay) {
//                calendar[Calendar.HOUR_OF_DAY] = variantInterval.alarmHour.toInt() + 12
//            } else {
//                calendar[Calendar.HOUR_OF_DAY] = variantInterval.alarmHour.toInt()
//            }
//            calendar[Calendar.MINUTE] = variantInterval.alarmMinute.toInt()
//            calendar[Calendar.SECOND] = 0
//            calendar[Calendar.MILLISECOND] = 0
//
//
//            // if alarm time has already passed, increment day by 1
//            if (isAlarmHasAlreadyPassed) {
//                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
//            }
//
//            if (!alarmWeek!!.recurring) {
//                var toastText: String? = null
//                try {
//                    toastText = java.lang.String.format(
//                        Locale.getDefault(),
//                        "One Time Alarm %s scheduled for %s at %02d:%02d",
//                        variantInterval.alarmLabel,
//                        DayUtil.toDay(
//                            calendar[Calendar.DAY_OF_WEEK]
//                        ),
//                        variantInterval.alarmHour.toInt(),
//                        variantInterval.alarmMinute.toInt()
//                    )
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//                alarmManager.setExact(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.timeInMillis,
//                    alarmPendingIntent
//                )
//            } else {
//                val toastText = java.lang.String.format(
//                    Locale.getDefault(),
//                    "Recurring Alarm %s scheduled for %s at %02d:%02d",
//                    variantInterval.alarmLabel,
//                    getRecurringDaysText(alarmWeek!!),
//                    variantInterval.alarmHour.toInt(), variantInterval.alarmMinute.toInt()
//                )
//                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
//                val runDaily = (24 * 60 * 60 * 1000).toLong()
//                alarmManager.setInexactRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    calendar.timeInMillis,
//                    runDaily,
//                    alarmPendingIntent
//                )
//            }
//            if (this.alarmInterval!!.advancedReminder.isAdvancedReminderEnabled) {
//                schedulerPreNotification(
//                    context,
//                    true,
//                    variantInterval,
//                    variantInterval.alarmTimeId
//                )
//            }
////            if (this.alarmInterval!!.isRemainderAtTheEnd) {
////                schedulerPostNotification(
////                    context,
////                    true,
////                    variantInterval,
////                    variantInterval.alarmTimeId
////                )
////            }
//        }
//
////        this.alarmStatus = true
//    }
//
//    fun cancelAlarm(context: Context, id: Int, cancelAllIntervals: Boolean) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(context, id, intent, 0)
//        alarmManager.cancel(alarmPendingIntent)
//        this.alarmStatus = false
//        @SuppressLint("DefaultLocale") val toastText =
//            String.format(
//                "Alarm cancelled for %02d:%02d",
//                this.alarmHour!!.toInt(),
//                this.alarmMinute!!.toInt()
//            )
//        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
//        if (cancelAllIntervals) {
//            if (this.alarmInterval?.isVariantInterval!!) {
//                this.alarmInterval?.variantIntervalTimes?.forEach {
//                    cancelNotification(context, it.alarmTimeId)
//                    cancelInterval(context, it)
//                }
//            }
//            if (!this.alarmInterval?.staticIntervalTimes.isNullOrEmpty()) {
//                this.alarmInterval?.staticIntervalTimes?.forEach {
//                    cancelNotification(context, it.alarmTimeId)
//                    cancelInterval(context, it)
//                }
//            }
//        }
//        Log.i("cancel", toastText)
//    }
//
//    fun cancelInterval(context: Context, variantInterval: AlarmTimeItem) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(context, variantInterval.alarmTimeId, intent, 0)
//        alarmManager.cancel(alarmPendingIntent)
////        this.alarmStatus = false
//        @SuppressLint("DefaultLocale") val toastText =
//            String.format(
//                "Alarm cancelled for %02d:%02d",
//                variantInterval.alarmHour.toInt(),
//                variantInterval.alarmMinute.toInt()
//            )
//        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
//        Log.i("cancel", toastText)
//    }
//
//    fun cancelNotification(context: Context, id: Int) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
//        val alarmPendingIntent =
//            PendingIntent.getBroadcast(context, id + 4, intent, 0)
//        alarmManager.cancel(alarmPendingIntent)
////        this.alarmStatus = false
//        val toastText = "Notification Canceled"
//        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
//        Log.i("cancel", toastText)
//    }
//}