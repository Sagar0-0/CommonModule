package fit.asta.health.navigation.home.model.dummy

import fit.asta.health.R

data class TestimonialsData(
    val clientDescription: String,
    val clientName: String,
    val clientJob: String,
    val clientImage:Int
)

val slideClientDataList =
    listOf(TestimonialsData(clientDescription = "I work in project management and joined this course because I get great courses for less.\n" +
            "The instructors are fantastic, interesting, and helpful. I plan to use this for a long time!",
        clientName = "Kristin Watson",
        clientJob = "CTO, EkoHunt", clientImage = R.drawable.testimonial_person1),
        TestimonialsData(clientDescription = "I work in project management and joined this course because I get great courses for less.\n" +
                "The instructors are fantastic, interesting, and helpful. I plan to use this for a long time!",
            clientName = "Kristin Watson",
            clientJob = "CTO, EkoHunt",clientImage = R.drawable.testimonial_person1),
        TestimonialsData(clientDescription = "I work in project management and joined this course because I get great courses for less.\n" +
                "The instructors are fantastic, interesting, and helpful. I plan to use this for a long time!",
            clientName = "Kristin Watson",
            clientJob = "CTO, EkoHunt",clientImage = R.drawable.testimonial_person1))
