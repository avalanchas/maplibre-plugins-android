package com.mapbox.mapboxsdk.plugins.offline.offline

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.mapbox.mapboxsdk.offline.OfflineRegion
import com.mapbox.mapboxsdk.plugins.offline.model.OfflineDownloadOptions

/**
 * OfflinePlugin is the main entry point for integrating the offline plugin into your app.
 *
 *
 * To start downloading a region call [.startDownload]
 *
 *
 * @since 0.1.0
 */
class OfflinePlugin
/**
 * Private no-args constructor for singleton
 */
private constructor(private val context: Context) {

    private val stateChangeDispatcher = OfflineDownloadChangeDispatcher()
    private val offlineDownloads: MutableList<OfflineDownloadOptions> = ArrayList()

    /**
     * Returns an immutable list of the currently active offline downloads
     */
    fun getActiveDownloads(): List<OfflineDownloadOptions> {
        return offlineDownloads
    }

    /**
     * Start downloading an offline download by providing an options object.
     *
     * You can listen to the actual creation of the download with [OfflineDownloadChangeListener].
     *
     * @param options the offline download builder
     * @since 0.1.0
     */
    fun startDownload(options: OfflineDownloadOptions?) {
        val intent = Intent(context, OfflineDownloadService::class.java)
        intent.setAction(OfflineConstants.ACTION_START_DOWNLOAD)
        intent.putExtra(OfflineConstants.KEY_BUNDLE, options)
        context.startService(intent)
    }

    /**
     * Cancel an ongoing download.
     *
     * @param offlineDownload the offline download
     * @since 0.1.0
     */
    fun cancelDownload(offlineDownload: OfflineDownloadOptions?) {
        val intent = Intent(context, OfflineDownloadService::class.java)
        intent.setAction(OfflineConstants.ACTION_CANCEL_DOWNLOAD)
        intent.putExtra(OfflineConstants.KEY_BUNDLE, offlineDownload)
        context.startService(intent)
    }

    /**
     * Get the OfflineDownloadOptions for an offline region, returns null if no download is active for region.
     *
     * @param offlineRegion the offline region to get related offline download for
     * @return the active offline download, null if not downloading the region.
     * @since 0.1.0
     */
    fun getActiveDownloadForOfflineRegion(offlineRegion: OfflineRegion): OfflineDownloadOptions? {
        var offlineDownload: OfflineDownloadOptions? = null
        if (!offlineDownloads.isEmpty()) {
            for (download in offlineDownloads) {
                if (download.uuid == offlineRegion.id) {
                    offlineDownload = download
                }
            }
        }
        return offlineDownload
    }

    /**
     * Add a callback that is invoked when the offline download state changes.
     *
     *
     * In normal cases this method will be invoked as part of [Activity.onStart]
     *
     *
     * @param listener the callback that will be invoked
     * @since 0.1.0
     */
    fun addOfflineDownloadStateChangeListener(listener: OfflineDownloadChangeListener?) {
        stateChangeDispatcher.addListener(listener)
    }

    /**
     * remove a callback that is invoked when the offline download state changes.
     *
     *
     * In normal cases this method will be invoked as part of [Activity.onStop]
     *
     *
     * @param listener the callback that will be removed
     * @since 0.1.0
     */
    fun removeOfflineDownloadStateChangeListener(listener: OfflineDownloadChangeListener?) {
        stateChangeDispatcher.removeListener(listener)
    }
    //
    // internal API
    //
    /**
     * Called when the OfflineDownloadService has created an offline region for an offlineDownload and
     * has assigned a region and service id.
     *
     * @param offlineDownload the offline download to track
     * @since 0.1.0
     */
    fun addDownload(offlineDownload: OfflineDownloadOptions) {
        offlineDownloads.add(offlineDownload)
        stateChangeDispatcher.onCreate(offlineDownload)
    }

    /**
     * Called when the OfflineDownloadService has finished downloading.
     *
     * @param offlineDownload the offline download to stop tracking
     * @since 0.1.0
     */
    fun removeDownload(offlineDownload: OfflineDownloadOptions, canceled: Boolean) {
        if (canceled) {
            stateChangeDispatcher.onCancel(offlineDownload)
        } else {
            stateChangeDispatcher.onSuccess(offlineDownload)
        }
        offlineDownloads.remove(offlineDownload)
    }

    /**
     * Called when the OfflineDownloadService produced an error while downloading
     *
     * @param offlineDownload the offline download that produced an error
     * @param error           short description of the error
     * @param errorMessage    full description of the error
     * @since 0.1.0
     */
    fun errorDownload(
        offlineDownload: OfflineDownloadOptions,
        error: String?,
        errorMessage: String?
    ) {
        stateChangeDispatcher.onError(offlineDownload, error, errorMessage)
        offlineDownloads.remove(offlineDownload)
    }

    /**
     * Called when the offline download service has made progress downloading an offline download.
     *
     * @param offlineDownload the offline download for which progress was made
     * @param progress        the amount of progress
     * @since 0.1.0
     */
    fun onProgressChanged(offlineDownload: OfflineDownloadOptions?, progress: Int) {
        stateChangeDispatcher.onProgress(offlineDownload, progress)
    }

    companion object {
        // Suppress warning about context being possibly leaked, we immediately get the application
        // context which removes this risk.
        @Volatile
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: OfflinePlugin? = null

        /**
         * Get the unique instance of the OfflinePlugin
         *
         * @param context The current context used to create intents and services later. This method
         * will look for the application context on the parameter you pass, so you don't need to do
         * that beforehand
         * @return the single instance of OfflinePlugin
         * @since 0.1.0
         */
        @JvmStatic
        fun getInstance(context: Context): OfflinePlugin = INSTANCE ?: synchronized(this) {
            INSTANCE ?: OfflinePlugin(context.applicationContext).also { INSTANCE = it }
        }

        /**
         * Initializer method, which can be called before or after any call of [getInstance]. Will
         * set all the desired fields for the offline handling and remember them on this unique
         * instance for the lifetime of the process. Therefore, you can configure the service to
         * your implementation's liking. Note that if you call this method after the first call to
         * [startDownload], it will have no effect anymore, as - currently - the service will not be
         * recreated.
         */
        @JvmStatic
        @JvmOverloads
        fun initialize(context: Context, channelName: String?, useGrouping: Boolean = true) {
            // This method may have many other effects in the future. Remember, the instance can
            // also be accessed and set here, e.g.: `getInstance(context).myField = myValue`
            OfflineDownloadService.config =
                OfflineServiceConfiguration(
                    channelName = channelName,
                    useGrouping = useGrouping,
                )
        }
    }
}