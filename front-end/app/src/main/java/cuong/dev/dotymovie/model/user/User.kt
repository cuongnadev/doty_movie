package cuong.dev.dotymovie.model.user

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("name")
    val name: String
)