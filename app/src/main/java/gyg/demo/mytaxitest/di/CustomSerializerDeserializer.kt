package gyg.demo.mytaxitest.di

import com.google.gson.*
import gyg.demo.mytaxitest.data.model.TaxiType
import java.lang.reflect.Type

class CustomSerializerDeserializer
    : JsonSerializer<TaxiType>, JsonDeserializer<TaxiType> {

    override fun serialize(src: TaxiType?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement? {
        return if (src == null) {
            null
        } else {
            JsonObject()
                .apply {
                    add(FLEET_TYPE_KEY, context.serialize(src, src.javaClass))
                }
        }
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): TaxiType {
        val type = json.asJsonPrimitive
        return getFleetType(type.asString) ?: throw RuntimeException("Unknown class: $type")
    }

    private fun getFleetType(type: String): TaxiType? {
        return when (type) {
            FLEET_TYPE_TAXI -> TaxiType.NormalTaxi
            FLEET_TYPE_POOLING -> TaxiType.PoolingTaxi
            else -> null
        }
    }

    companion object {
        const val FLEET_TYPE_KEY = "fleetType"
        const val FLEET_TYPE_POOLING = "POOLING"
        const val FLEET_TYPE_TAXI = "TAXI"
    }
}
