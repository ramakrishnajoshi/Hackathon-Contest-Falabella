package com.example.dependencyinjection.di

import com.example.dependencyinjection.*
import com.example.dependencyinjection.app.WeatherApplication
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//This is dependency provider class. @Provider would have been more sensible than @Module
//@Provider - the class that provides dependencies
//@Provides - the methods in @Provider(@Module) class that provides actual dependencies.
@Module
abstract class WeatherModule {

    /**
     * Convey Dagger to generate code to make WeatherActivity injectable.
     * This method is never used at runtime; Dagger only looks at the return type and generates code.
     * The method name does not matter; it is for your sanity.
     **/
    @ContributesAndroidInjector
    abstract fun contributeWeatherActivity() : WeatherActivity

    /**
     * @Binds tells system that Whenever someone injects the ApiWeatherRepository interface, go and give them a WeatherRepository instance.
     * Dagger doesn’t know how to instantiate the ApiWeatherRepository to pass to the view model’s constructor. Since it’s an interface we can’t simply
     * add a @Inject -annotated constructor. What we can do, however, is add another abstract method to our WeatherModule which “binds” the repository
     * implementation to the interface:
     * */
    @Binds
    abstract fun bindApiWeatherRepositoryToWeatherRepository(weatherRepository: WeatherRepository) : ApiWeatherRepository

    //Can't execute below code because @Binds methods must be abstract
//    @Binds
//    fun bindWeatherServiceToRetrofitInstance() : WeatherService{
//        return Retrofit
//            .Builder()
//            .baseUrl("https://www.google.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(WeatherService::class.java)
//    }

    //Can't execute below code because A @Module may not contain both non-static and abstract binding methods
//    @Provides
//    fun bindWeatherServiceToRetrofitInstance() : WeatherService{
//        return Retrofit
//            .Builder()
//            .baseUrl("https://www.google.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(WeatherService::class.java)
//    }

    @Module //Add this annotation otherwise you would get error - "@Provides methods can only be present within a @Module or @ProducerModule"
    companion object {
        /**
         * @Binds and @ContributesAndroidInjector methods must be abstract, because they don't have method bodies. That means that they must go on an
         * interface or abstract class. @Provides methods may be static, which means they can go on abstract classes and Java-8-compiled interfaces,
         * but non-static ("instance") @Provides methods don't work on abstract classes. This is explicitly listed in the Dagger FAQ, under the
         * sections "Why can’t @Binds and instance @Provides methods go in the same module?" and "What do I do instead?".
         * If your @Provides method doesn't use instance state, you can mark it static, and it can go onto an abstract class adjacent to your
         * @Binds methods. If not, consider putting the bindings like @Binds and @ContributesAndroidInjector into a separate class--possibly a static
         * nested class--and including that using the includes attribute on Dagger's @Module annotation.
         * */
        @JvmStatic //Add this annotation otherwise you would get error - "WeatherService cannot be provided without an @Provides-annotated method"
        @Provides
        fun getWeatherService(): WeatherService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

            val okHttpClientBuilder = OkHttpClient
                .Builder()
                .readTimeout(45, TimeUnit.SECONDS)
                .connectTimeout(45, TimeUnit.SECONDS)
            okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)

            return Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .client(okHttpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }

        @JvmStatic
        @Provides
        fun getWeatherDataViewStateConverter() : WeatherDataViewStateConverter {
            return WeatherDataViewStateConverter()
        }
    }
}
