package fit.asta.health.sunlight.remote.model

import com.google.gson.annotations.SerializedName

data class HelpAndNutrition (

  @SerializedName("suppl" ) var suppl : ArrayList<Suppl> = arrayListOf(),
  @SerializedName("food"  ) var food  : ArrayList<Food>  = arrayListOf(),
  @SerializedName("bev"   ) var bev   : ArrayList<Bev>   = arrayListOf(),
  @SerializedName("msg"   ) var msg   : String?          = null

)

data class Nutrition (

  @SerializedName("name" ) var name : String? = null,
  @SerializedName("val"  ) var value  : Int?    = null,
  @SerializedName("unit" ) var unit : Int?    = null

)

data class Suppl (

  @SerializedName("id"     ) var id     : String?           = null,
  @SerializedName("name"   ) var name   : String?           = null,
  @SerializedName("brand"  ) var brand  : String?           = null,
  @SerializedName("dsc"    ) var dsc    : String?           = null,
  @SerializedName("type"   ) var type   : Int?              = null,
  @SerializedName("ingr"   ) var ingr   : ArrayList<String> = arrayListOf(),
  @SerializedName("nutr"   ) var nutr   : ArrayList<Nutrition>   = arrayListOf(),
  @SerializedName("serRcm" ) var serRcm : String?           = null,
  @SerializedName("warn"   ) var warn   : ArrayList<String> = arrayListOf(),
  @SerializedName("url"    ) var url    : String?           = null

)


data class Food (

  @SerializedName("id"    ) var id    : String? = null,
  @SerializedName("code"  ) var code  : String? = null,
  @SerializedName("type"  ) var type  : Int?    = null,
  @SerializedName("dsc"   ) var dsc   : String? = null,
  @SerializedName("name"  ) var name  : String? = null,
  @SerializedName("since" ) var since : Int?    = null,
  @SerializedName("url"   ) var url   : String? = null

)
data class Bev (

  @SerializedName("id"    ) var id    : String? = null,
  @SerializedName("code"  ) var code  : String? = null,
  @SerializedName("type"  ) var type  : Int?    = null,
  @SerializedName("dsc"   ) var dsc   : String? = null,
  @SerializedName("name"  ) var name  : String? = null,
  @SerializedName("since" ) var since : Int?    = null,
  @SerializedName("url"   ) var url   : String? = null

)