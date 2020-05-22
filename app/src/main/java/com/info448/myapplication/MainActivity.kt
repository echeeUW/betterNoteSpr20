package com.info448.myapplication

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.info448.myapplication.adapter.NotesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity() {

    private val notesFileName = "my_awesome_notes3.txt"
    lateinit var adapter: NotesAdapter

    companion object {
        private val TAG = "echee"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        context.filesDir

        btnWriteFile.setOnClickListener {
            writeSomeFile2()
        }

        btnRead.setOnClickListener {
            readNoteFile3()
        }
        btnShowAllFiles.setOnClickListener {
            printAllFiles()
        }
        btnAdd.setOnClickListener {
            adapter.addNewNote()
        }

        btnWriteExternal.setOnClickListener {
            writeExternalFile()
        }

        btnReadMusic.setOnClickListener {
            readMusicFromAssets()
        }

        // To put an image
//        // val imageFilePath = file.absolutePath
//        ivAlbumArt.setImageDrawable( Drawable.createFromPath(imageFilePath))
    }

    private var isSongPlaying = false
    private var mediaPlayer: MediaPlayer? = null

    private fun readMusic() {
        if (!isSongPlaying) {
            val luckySongFile = File(filesDir, "lucky.mp3")
            val path = luckySongFile.absolutePath

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(path)
                    prepare()
                    start()
                }
            } else {
                mediaPlayer?.start()
            }
            isSongPlaying = true
        } else {
            mediaPlayer?.pause()
            isSongPlaying = false
        }
    }
    private fun readMusicFromAssets() {
        if (!isSongPlaying) {
            val luckySongFile = File(cacheDir, "lucky.mp3").also { cacheFile ->
                cacheFile.outputStream().use { cache -> assets.open("lucky.mp3").use { it.copyTo(cache)} }
            }
            val path = luckySongFile.absolutePath

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(path)
                    prepare()
                    start()
                }
            } else {
                mediaPlayer?.start()
            }
            isSongPlaying = true
        } else {
            mediaPlayer?.pause()
            isSongPlaying = false
        }
    }

    private fun writeSomeFile() {

        val fileContents = """
            Go huskies!
            Whose house!? DAWGS HOUSE!
            Be bounnnndddllessssss
            Go purple, be gold
            Boooooo cougars
        """.trimIndent()

        openFileOutput(notesFileName, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        Log.i(TAG, "All done writing")
    }

    private fun writeSomeFile2() {
        val fileContents = """
            Go huskies!!!!!!!!
            Whose house!? DAWGS HOUSE!
            Be bounnnndddllessssss
            Go purple, be gold
            Boooooo cougars sadfadfafawer awef awer 
        """.trimIndent()

        File(filesDir, notesFileName).writeText(fileContents)
    }

    private fun readNoteFile() {
        openFileInput(notesFileName).bufferedReader().useLines { lines ->
            lines.forEach { line ->
                Log.i(TAG, line)
            }
        }
    }

    private fun readNoteFile2() {
        File(filesDir, notesFileName).forEachLine { line ->
            Log.i(TAG, line)
        }
    }

    private fun readNoteFile3() {
        val listOfNotes = mutableListOf<String>()

        val scanner = Scanner(File(filesDir, notesFileName))
        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            Log.i(TAG, line)
            listOfNotes.add(line)
        }

        adapter = NotesAdapter(listOfNotes)
        rvListOfNotes.adapter = adapter
    }

    private fun printAllFiles() {
        val filenames = filesDir.list()

        filesDir.list()?.let { list ->
            adapter =  NotesAdapter(list.toList())
            rvListOfNotes.adapter = adapter
        }
    }

    override fun onPause() {
        super.onPause()

        val allNotes = StringBuilder()
        adapter.notes.forEach {
            allNotes.append(it).append("\n")
        }

        File(filesDir, notesFileName).writeText(allNotes.toString())
    }


    // Checks if a volume containing external storage is available
    // for read and write.
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    // Checks if a volume containing external storage is available to at least read.
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    private fun writeExternalFile() {
        val contents = """
            you looking at me?
            no im not lookin at you
            aight thought so punk
        """.trimIndent()
        val externalFileOfNotes = File(getExternalFilesDir(null), "externalNotes.txt")
        externalFileOfNotes.writeText(contents)

        readExternalFile()
    }

    private fun readExternalFile() {
        File(getExternalFilesDir(null), "externalNotes.txt").forEachLine {
            Log.i(TAG, it)
        }
    }







}
