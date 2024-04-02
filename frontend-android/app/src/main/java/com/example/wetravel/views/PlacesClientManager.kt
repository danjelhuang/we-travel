import android.content.Context
import com.example.wetravel.BuildConfig
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

object PlacesClientManager {
    private var placesClient: PlacesClient? = null

    fun initialize(context: Context) {
        if (placesClient == null) {
            Places.initialize(context.applicationContext, BuildConfig.MAPS_API_KEY)
            placesClient = Places.createClient(context.applicationContext)
        }
    }

    fun getPlacesClient(): PlacesClient {
        return placesClient!!
    }
}
