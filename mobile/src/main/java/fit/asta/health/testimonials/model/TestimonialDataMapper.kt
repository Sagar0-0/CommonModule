package fit.asta.health.testimonials.model

import androidx.compose.runtime.toMutableStateList
import fit.asta.health.testimonials.model.domain.Media
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.model.domain.TestimonialUser
import fit.asta.health.testimonials.model.network.NetMedia
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialUser


class TestimonialDataMapper {

    fun mapToDomainModel(networkModel: List<NetTestimonial>): List<Testimonial> {
        return networkModel.map { mapToDomainModel(it) }
    }

    fun mapToDomainModel(networkModel: NetTestimonial) =
        Testimonial(
            id = networkModel.id,
            type = TestimonialType.fromInt(networkModel.type),
            rank = networkModel.rank,
            title = networkModel.title,
            testimonial = networkModel.testimonial,
            userId = networkModel.userId,
            user = TestimonialUser(
                name = networkModel.user.name,
                org = networkModel.user.org,
                role = networkModel.user.role,
                url = networkModel.user.url
            ),
            media = mapToMedia(networkModel.media).toMutableStateList()
        )

    private fun mapToMedia(mediaList: List<NetMedia>): List<Media> {
        return mediaList.map {
            Media(
                name = it.name,
                title = it.title,
                url = it.url
            )
        }
    }

    fun mapToNetworkModel(domainModel: Testimonial): NetTestimonial {
        return NetTestimonial(
            id = domainModel.id,
            type = domainModel.type.value,
            rank = -1,
            apv = false,
            title = domainModel.title,
            testimonial = domainModel.testimonial,
            userId = domainModel.userId,
            user = NetTestimonialUser(
                name = domainModel.user.name,
                org = domainModel.user.org,
                role = domainModel.user.role,
                url = domainModel.user.url
            ),
            media = mapToNetMedia(domainModel.media)
        )
    }

    private fun mapToNetMedia(mediaList: List<Media>): List<NetMedia> {
        return mediaList.map {
            NetMedia(
                name = it.name,
                title = it.title,
                url = it.url
            )
        }
    }
}