package fit.asta.health.player.data.remote

import fit.asta.health.player.data.entity.Song


class MusicDatabase {
//    private val db = FirebaseFirestore.getInstance()
//    private val songCollection = db.collection(SONG_COLLECTION)

//    suspend fun getAllSongs(): List<Song> {
//        return try {
//            songCollection.get().await().toObjects(Song::class.java)
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }

    suspend fun getAllSongs(): List<Song> {
        return listOf(
            Song(
                "1",
                "On & On",
                "Cartoon",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Cartoon%20%20On%20%20On%20feat%20Daniel%20Levi%20NCS%20Release.mp3?alt=media&token=c1beb536-1937-4da6-aa78-8586ca973abf",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:28"
            ),
            Song(
                "2",
                "Blank",
                "Disfigure",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Disfigure%20%20Blank%20NCS%20Release.mp3?alt=media&token=dcfb6001-70fa-47ac-8672-cf8ec01a16d5",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:29"
            ),
            Song(
                "3",
                "Heroes Tonight",
                "Janji",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Janji%20%20Heroes%20Tonight%20feat%20Johnning%20NCS%20Release.mp3?alt=media&token=72ac15ce-2868-4daa-89d0-ba0f0caff890",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:28"
            ),
            Song(
                "4",
                "On & On",
                "Cartoon",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Cartoon%20%20On%20%20On%20feat%20Daniel%20Levi%20NCS%20Release.mp3?alt=media&token=c1beb536-1937-4da6-aa78-8586ca973abf",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:28"
            ),
            Song(
                "5",
                "Blank",
                "Disfigure",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Disfigure%20%20Blank%20NCS%20Release.mp3?alt=media&token=dcfb6001-70fa-47ac-8672-cf8ec01a16d5",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:29"
            ),
            Song(
                "6",
                "Heroes Tonight",
                "Janji",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Janji%20%20Heroes%20Tonight%20feat%20Johnning%20NCS%20Release.mp3?alt=media&token=72ac15ce-2868-4daa-89d0-ba0f0caff890",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:28"
            ),
            Song(
                "7",
                "On & On",
                "Cartoon",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Cartoon%20%20On%20%20On%20feat%20Daniel%20Levi%20NCS%20Release.mp3?alt=media&token=c1beb536-1937-4da6-aa78-8586ca973abf",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:28"
            ),
            Song(
                "8",
                "Blank",
                "Disfigure",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Disfigure%20%20Blank%20NCS%20Release.mp3?alt=media&token=dcfb6001-70fa-47ac-8672-cf8ec01a16d5",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:29"
            ),
            Song(
                "9",
                "Heroes Tonight",
                "Janji",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/y2mate.com%20-%20Janji%20%20Heroes%20Tonight%20feat%20Johnning%20NCS%20Release.mp3?alt=media&token=72ac15ce-2868-4daa-89d0-ba0f0caff890",
                "https://firebasestorage.googleapis.com/v0/b/spotifycloneyt-fd819.appspot.com/o/1200px-NCS_logo.svg.png?alt=media&token=9a9abcd8-2ae7-45a8-acb5-bca8e3f94b2a",
                "3:28"
            )
        )
    }
}