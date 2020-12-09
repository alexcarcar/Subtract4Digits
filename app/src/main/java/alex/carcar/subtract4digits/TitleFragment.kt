package alex.carcar.subtract4digits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.title_fragment.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class TitleFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    private val fileName = "goldPoints.txt"

    private fun save(points: Int) {
        try {
            val out = OutputStreamWriter(this.requireActivity().openFileOutput(fileName, 0))
            out.write(points.toString())
            out.close()
        } catch (t: Throwable) {
        }
    }

    private fun read(): Int {
        try {
            val inputStream: InputStream? = this.requireActivity().openFileInput(fileName)
            if (inputStream != null) {
                val tmp = InputStreamReader(inputStream)
                val reader = BufferedReader(tmp)
                var str: String
                reader.readLine().also { str = it }
                inputStream.close()
                return Integer.parseInt(str)
            }
        } catch (e: Exception) {
        }
        return 0
    }

    override fun onCreateView(inflater: LayoutInflater, group: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.title_fragment, group, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startButton.setOnClickListener {
            findNavController().navigate(R.id.action_start_game)
        }
        viewModel = ViewModelProvider(this.requireActivity()).get(MainViewModel::class.java)
        if (viewModel.getPoints() == 0) {
            viewModel.setPoints(read())
        }
        goldPoints.text = viewModel.getPoints().toString()
        save(viewModel.getPoints())
    }
}