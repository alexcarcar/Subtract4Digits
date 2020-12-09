package alex.carcar.subtract4digits

import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.subtract_fragment.*


class SubtractFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var first = 0
    private var second = 0
    private lateinit var _view: View

    override fun onCreateView(i: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return i.inflate(R.layout.subtract_fragment, container, false)
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        viewModel = ViewModelProvider(this.requireActivity()).get(MainViewModel::class.java)
        _view = view
        super.onViewCreated(view, state)
        createQuestion()
        button_second.setOnClickListener {
            check()
        }
    }

    private fun createQuestion() {
        first = (999..10000).random()
        second = (999..10000).random()
        if (first < second) {
            val temp = first
            first = second
            second = temp
        }
        val f = first.toString()
        val s = second.toString()
        _0.text = "      "
        _1.text = "  "
        _2.text = "  "
        _3.text = "  "
        _4.text = "  "
        a0.text = "   "
        a1.text = f[0].toString()
        a2.text = f[1].toString()
        a3.text = f[2].toString()
        a4.text = f[3].toString()
        b0.text = "  -"
        b1.text = s[0].toString()
        b2.text = s[1].toString()
        b3.text = s[2].toString()
        b4.text = s[3].toString()
        c0.text = "   "
        b1.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        b2.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        b3.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        b4.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        a3.setOnClickListener { borrow(a3, _3, _4) }
        a2.setOnClickListener { borrow(a2, _2, _3) }
        a1.setOnClickListener { borrow(a1, _1, _2) }
    }

    private fun borrow(d: TextView, top: TextView, nextTop: TextView) {
        val digit = myInt(d)
        val nt = myInt(nextTop)
        val t = myVal(top)
        if (digit == 0 && t == 0) return // can't borrow from zero
        if (d.paintFlags == Paint.STRIKE_THRU_TEXT_FLAG) {
            d.paintFlags = 0x00
            top.text = "  "
            nextTop.text = "  "
        } else {
            d.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            top.text = " " + ((t * 10 + digit) - 1).toString()
            if (nt == -1) {
                nextTop.text = " 1"
            } else {
                nextTop.text = " " + (10 + nt).toString()
            }
        }
    }

    private fun myInt(textView: TextView): Int {
        val s = textView.text.toString().trim()
        if (s == "") return -1
        return Integer.parseInt(s)
    }

    private fun myVal(textView: TextView): Int {
        val x = myInt(textView)
        if (x < 0) return 0
        return x
    }

    private fun check() {
        val answer = first - second
        val response = myVal(c1) * 1000 + myVal(c2) * 100 + myVal(c3) * 10 + myVal(c4)
        if (answer == response) {
            val finishedListener: View.OnClickListener = View.OnClickListener {
                findNavController().navigate(R.id.action_finish)
            }
            Snackbar.make(_view, "Good job!", Snackbar.LENGTH_LONG)
                .setAction("Close", finishedListener).show()
            questionGrid.visibility = View.INVISIBLE
            button_second.visibility = View.INVISIBLE
            successImage.visibility = View.VISIBLE
            val imageIDs = arrayOf(
                R.drawable.dance0,
                R.drawable.dance1,
                R.drawable.dance2,
                R.drawable.dance3,
                R.drawable.dance4
            )
            var i = 0
            object : CountDownTimer(6000, 250) {
                override fun onTick(millisUntilFinished: Long) {
                    successImage.setImageResource(imageIDs[i % 5])
                    i++
                }

                override fun onFinish() {
                    viewModel.setPoints(viewModel.getPoints() + 50)
                    findNavController().navigate(R.id.action_finish)
                }
            }.start()
        } else {
            Snackbar.make(_view, "Keep trying!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}