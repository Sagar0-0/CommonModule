package fit.asta.health.old.course.session.viewmodel

import fit.asta.health.old.course.session.data.Exercise
import fit.asta.health.old.course.session.data.SessionData

interface SessionDataStore {
    fun updateSession(sessionData: SessionData)
    fun getExercises(): List<Exercise>
    fun getExercise(position: Int): Exercise
}