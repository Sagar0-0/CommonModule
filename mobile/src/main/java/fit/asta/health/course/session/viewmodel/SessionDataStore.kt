package fit.asta.health.course.session.viewmodel

import fit.asta.health.course.session.data.Exercise
import fit.asta.health.course.session.data.SessionData

interface SessionDataStore {
    fun updateSession(sessionData: SessionData)
    fun getExercises(): List<Exercise>
    fun getExercise(position: Int): Exercise
}