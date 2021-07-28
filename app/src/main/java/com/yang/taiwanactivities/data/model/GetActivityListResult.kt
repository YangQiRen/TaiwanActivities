package com.yang.taiwanactivities.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetActivityListResult(
    @SerializedName("XML_Head") var XMLHead: XMLHead
)

data class XMLHead(

    @SerializedName("Listname") var Listname: String,
    @SerializedName("Language") var Language: String,
    @SerializedName("Orgname") var Orgname: String,
    @SerializedName("Updatetime") var Updatetime: String,
    @SerializedName("Infos") var Infos: Infos

)

data class Infos(

    @SerializedName("Info") var Info: List<Info>

)

@Parcelize
data class Info(
    @SerializedName("Id") var Id: String,
    @SerializedName("Name") var Name: String,
    @SerializedName("Description") var Description: String,
    @SerializedName("Participation") var Participation: String,
    @SerializedName("Location") var Location: String,
    @SerializedName("Add") var Add: String,
    @SerializedName("Region") var Region: String,
    @SerializedName("Town") var Town: String,
    @SerializedName("Tel") var Tel: String,
    @SerializedName("Org") var Org: String,
    @SerializedName("Start") var Start: String,
    @SerializedName("End") var End: String,
    @SerializedName("Cycle") var Cycle: String,
    @SerializedName("Noncycle") var Noncycle: String,
    @SerializedName("Website") var Website: String,
    @SerializedName("Picture1") var Picture1: String,
    @SerializedName("Picdescribe1") var Picdescribe1: String,
    @SerializedName("Picture2") var Picture2: String,
    @SerializedName("Picdescribe2") var Picdescribe2: String,
    @SerializedName("Picture3") var Picture3: String,
    @SerializedName("Picdescribe3") var Picdescribe3: String,
    @SerializedName("Px") var Px: Double,
    @SerializedName("Py") var Py: Double,
    @SerializedName("Class1") var Class1: String,
    @SerializedName("Class2") var Class2: String,
    @SerializedName("Map") var Map: String,
    @SerializedName("Travellinginfo") var Travellinginfo: String,
    @SerializedName("Parkinginfo") var Parkinginfo: String,
    @SerializedName("Charge") var Charge: String,
    @SerializedName("Remarks") var Remarks: String

) : Parcelable