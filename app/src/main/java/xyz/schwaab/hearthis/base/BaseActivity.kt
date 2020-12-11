package xyz.schwaab.hearthis.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import xyz.schwaab.hearthis.ui.formatter.UserJourneyErrorFormatter

abstract class BaseActivity : AppCompatActivity() {
    private val userJourneyErrorFormatter: UserJourneyErrorFormatter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
    }

    protected open fun showWarningMessage(string: String) {
        Snackbar.make(findViewById(android.R.id.content), string, Snackbar.LENGTH_LONG).show()
    }

    protected val defaultErrorListener = UserJourneyErrorListener { error ->
        val readableMessage = userJourneyErrorFormatter.getReadableMessageForError(error)
        showWarningMessage(readableMessage)
    }
}
