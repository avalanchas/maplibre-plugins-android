package com.mapbox.mapboxsdk.plugins.offline.model

import android.os.Parcelable
import com.mapbox.mapboxsdk.offline.OfflineRegionDefinition
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class OfflineDownloadOptions(
    val definition: OfflineRegionDefinition,
    val notificationOptions: NotificationOptions,
    val regionName: String,
    val metadata: ByteArray = byteArrayOf(),
    var progress: Int = 0,
    val uuid: Long = UUID.randomUUID().mostSignificantBits
) : Parcelable {

    @Deprecated(
        "Use proper field access via Kotlin 'definition' or Java 'getDefinition()'",
        replaceWith = ReplaceWith("getDefinition()")
    )
    fun definition(): OfflineRegionDefinition {
        return definition
    }

    @Deprecated(
        "Use proper field access via Kotlin 'notificationOptions' or Java 'getNotificationOptions()'",
        replaceWith = ReplaceWith("getNotificationOptions()")
    )
    fun notificationOptions(): NotificationOptions {
        return notificationOptions
    }

    @Deprecated(
        "Use proper field access via Kotlin 'regionName' or Java 'getRegionName()'",
        replaceWith = ReplaceWith("getRegionName()")
    )
    fun regionName(): String {
        return regionName
    }

    @Deprecated(
        "Use proper field access via Kotlin 'metadata' or Java 'getMetadata()'",
        replaceWith = ReplaceWith("getMetadata()")
    )
    fun metadata(): ByteArray {
        return metadata
    }

    @Deprecated(
        "Use proper field access via Kotlin 'progress' or Java 'getProgress()'",
        replaceWith = ReplaceWith("getProgress()")
    )
    fun progress(): Int {
        return progress
    }

    @Deprecated(
        "Use proper field access via Kotlin 'progress' or Java 'getProgress()'",
        replaceWith = ReplaceWith("getProgress()")
    )
    fun uuid(): Long {
        return uuid
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OfflineDownloadOptions
        if (definition != other.definition) return false
        if (notificationOptions != other.notificationOptions) return false
        if (regionName != other.regionName) return false
        if (!metadata.contentEquals(other.metadata)) return false
        if (progress != other.progress) return false
        return uuid == other.uuid
    }

    override fun hashCode(): Int {
        var result = definition.hashCode()
        result = 31 * result + notificationOptions.hashCode()
        result = 31 * result + regionName.hashCode()
        result = 31 * result + metadata.contentHashCode()
        result = 31 * result + progress
        result = 31 * result + uuid.hashCode()
        return result
    }

    // TODO remove this companion object and the builder after a few versions to avoid unnecessary instances, bad initializations and nullpointers
    companion object {

        @JvmStatic
        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions()")
        )
        fun builder(): Builder {
            return Builder()
        }
    }

    @Deprecated(
        "Use idiomatic Kotlin constructor with named properties",
        replaceWith = ReplaceWith("OfflineDownloadOptions()")
    )
    class Builder {
        private lateinit var definition: OfflineRegionDefinition
        private lateinit var notificationOptions: NotificationOptions
        private lateinit var regionName: String
        private var metadata: ByteArray = byteArrayOf()
        private var progress: Int = 0
        private var uuid: Long = UUID.randomUUID().mostSignificantBits

        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions(definition = definition)")
        )
        fun definition(definition: OfflineRegionDefinition) =
            apply { this.definition = definition }

        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions(notificationOptions = notificationOptions)")
        )
        fun notificationOptions(notificationOptions: NotificationOptions) =
            apply { this.notificationOptions = notificationOptions }
        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions(regionName = regionName)")
        )
        fun regionName(regionName: String) =
            apply { this.regionName = regionName }
        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions(metadata = metadata)")
        )
        fun metadata(metadata: ByteArray) =
            apply { this.metadata = metadata }
        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions(progress = progress)")
        )
        fun progress(progress: Int) =
            apply { this.progress = progress }
        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions(uuid = uuid)")
        )
        fun uuid(uuid: Long) =
            apply { this.uuid = uuid }

        @Deprecated(
            "Use idiomatic Kotlin constructor with named properties",
            replaceWith = ReplaceWith("OfflineDownloadOptions()")
        )
        fun build() = OfflineDownloadOptions(
            definition = definition,
            notificationOptions = notificationOptions,
            regionName = regionName,
            metadata = metadata,
            progress = progress,
            uuid = uuid
        )
    }
}