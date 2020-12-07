package alex.carcar.subtract4digits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.title_fragment.*

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.title_fragment, group, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_first.setOnClickListener {
            findNavController().navigate(R.id.action_start_game)
        }
    }
}