package com.maduo.redcarpet.android.di

import android.app.Application
import com.maduo.redcarpet.data.data_source.*
import com.maduo.redcarpet.data.local.DatabaseDriverFactory
import com.maduo.redcarpet.domain.firebase.FirebasePhoneAuthManager
import com.maduo.redcarpet.domain.repositories.*
import com.maduo.redcarpet.domain.usecase.GetOrderWithDesignUseCase
import com.maduo.redcarpet.domain.usecase.GetOrdersUseCase
import com.maduo.redcarpet.kmm.shared.database.AppDatabase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesClientRepository(): ClientRepository {
        return ClientRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuthManager(): FirebasePhoneAuthManager {
        return FirebasePhoneAuthManager()
    }

    @Provides
    @Singleton
    fun providesCustomOrderRepository(): CustomOrderRepository {
        return CustomOrderRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesDesignRepository(): DesignRepository {
        return DesignRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesNormalOrderRepository(): NormalOrderRepository {
        return NormalOrderRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesCourseRepository(): CourseRepository {
        return CourseRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesRegistrationRepository(): RegistrationRepository {
        return RegistrationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesPaymentRepository(): PaymentRepository {
        return PaymentRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesImageRepository(): ImageRepository {
        return ImageRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesCollectionRepository(): CollectionRepository {
        return CollectionRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesOrderUseCase(): GetOrdersUseCase {
        return GetOrdersUseCase()
    }

    @Provides
    @Singleton
    fun providesRegularOrderUseCase(): GetOrderWithDesignUseCase {
        return GetOrderWithDesignUseCase()
    }

    @Provides
    @Singleton
    fun providesClientCacheRepository(driver: SqlDriver): ClientCacheRepository {
        return ClientCacheRepositoryImpl(AppDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

}