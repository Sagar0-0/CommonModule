package fit.asta.health.utils

interface DomainMapper<NetworkModel, DomainModel> {

    fun mapToDomainModel(networkModel: NetworkModel): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): NetworkModel
}