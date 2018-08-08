package com.ahndwon.gethub.utils

import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.ahndwon.gethub.R.id.repoReadMe
import kotlinx.android.synthetic.main.fragment_code_info.*
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.net.URL


class DownloadFileFromURL : AsyncTask<String, String, String>() {
    /**
     * Before starting background thread Show Progress Bar Dialog
     */
    override fun onPreExecute() {
        super.onPreExecute()
    }

    /**
     * Downloading file in background thread
     */
    override fun doInBackground(vararg file_url: String): String? {
        try {
            val url = URL(file_url[0])
            val connection = url.openConnection()
            connection.connect()

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            val fileLength = connection.contentLength

            // download the file
            val input = BufferedInputStream(url.openStream(),
                    8192)

//            val file = File()
            // Output stream
            val output = FileOutputStream(Environment
                    .getDownloadCacheDirectory().toString() + "/readme.md")




            val data = ByteArray(1024)

            var total: Long = 0

            while (true) {
                val count = input.read(data)
                        if(count == -1)
                            break

                total += count.toLong()
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (total * 100 / fileLength).toInt())

                // writing data to file
                output.write(data, 0, count)
            }
            // flushing output
            output.flush()

            // closing streams
            output.close()
            input.close()
            Log.i("DownloadFileFromURL", "file successfully downloaded")

        } catch (e: Exception) {
            Log.e("DownloadFileFromURL: ", e.message)
        }

        return null
    }

    /**
     * Updating progress bar
     */
    override fun onProgressUpdate(vararg progress: String) {
        // setting progress percentage
    }

    /**
     * After completing background task Dismiss the progress dialog
     */
    override fun onPostExecute(file_url: String) {
        // dismiss the dialog after the file was downloaded

    }

}