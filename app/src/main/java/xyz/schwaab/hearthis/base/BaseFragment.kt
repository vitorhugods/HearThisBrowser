package xyz.schwaab.hearthis.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import xyz.schwaab.hearthis.ui.formatter.UserJourneyErrorFormatter

abstract class BaseFragment : Fragment() {
    private val userJourneyErrorFormatter: UserJourneyErrorFormatter by inject()

    protected val defaultErrorListener = UserJourneyErrorListener { error ->
        val readableMessage = userJourneyErrorFormatter.getReadableMessageForError(error)
        showWarningMessage(readableMessage)
    }

    protected open fun showWarningMessage(string: String) {
        val view = view ?: return
        Snackbar.make(view, string, Snackbar.LENGTH_LONG).show()
    }

    protected fun observeErrors(viewModel: BaseViewModel) {
        viewModel.errorEvents.observe(viewLifecycleOwner, { error ->
            defaultErrorListener.onError(error)
        })
    }
}