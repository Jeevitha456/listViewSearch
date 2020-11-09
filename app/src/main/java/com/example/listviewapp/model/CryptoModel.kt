package com.example.listviewapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoModel (


        @Expose
        @SerializedName("symbol")
        val symbol: String,

        @Expose
        @SerializedName("display_symbol")
        val display_symbol: String,

@Expose
@SerializedName("name")
val name: String,

@Expose
@SerializedName("aliases")
val aliases: String,

@Expose
@SerializedName("shortname")
val shortname: String,

@Expose
@SerializedName("last_price_usd")
val last_price_usd: Float,

@Expose
@SerializedName("market_cap_rank")
val market_cap_rank: Integer,

@Expose
@SerializedName("volume_rank")
val volume_rank: Integer,

@Expose
@SerializedName("price_change_1H_percent")
val price_change_1H_percent: Float,

@Expose
@SerializedName("price_change_1D_percent")
val price_change_1D_percent: Float,


@Expose
@SerializedName("price_change_7D_percent")
val price_change_7D_percent: Float,

@Expose
@SerializedName("price_change_30D_percent")
val price_change_30D_percent: Float,

@Expose
@SerializedName("price_change_90D_percent")
val price_change_90D_percent: Float,

@Expose
@SerializedName("price_change_180D_percent")
val price_change_180D_percent: Float,

@Expose
@SerializedName("price_change_365D_percent")
val price_change_365D_percent: Float,

@Expose
@SerializedName("price_change_YTD_percent")
val price_change_YTD_percent: Float,


@Expose
@SerializedName("volume_24_usd")
val volume_24_usd: Double,



@Expose
@SerializedName("display")
val display: String,


@Expose
@SerializedName("trading_since")
val trading_since: String,


@Expose
@SerializedName("supply")
val supply: Double,


@Expose
@SerializedName("last_update")
val last_update: String,


@Expose
@SerializedName("ico_end")
val ico_end: String,


@Expose
@SerializedName("include_supply")
val include_supply: String,

@Expose
@SerializedName("use_volume")
val use_volume: String,

@Expose
@SerializedName("market_cap_usd")
val market_cap_usd: Double
){

}