package fit.asta.health.utils

interface DomainMapper<NetworkModel, DomainModel> {

    fun mapToDomainModel(model: NetworkModel): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): NetworkModel
}