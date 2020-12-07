package alex.carcar.subtract4digits

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.subtract_fragment.*

class SubtractFragment : Fragment() {

    private var first = 0
    private var second = 0

    override fun onCreateView(i: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return i.inflate(R.layout.subtract_fragment, container, false)
    }

    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        createQuestion()
        button_second.setOnClickListener({
            finish()
        })
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

        a3.setOnClickListener({
            borrow(a3, _3, _4)
        })

        a2.setOnClickListener({
            borrow(a2, _2, _3)
        })

        a1.setOnClickListener({
            borrow(a1, _1, _2)
        })
    }

    private fun borrow(d: TextView, top: TextView, friend: TextView) {
        if (d.paintFlags == Paint.STRIKE_THRU_TEXT_FLAG) {
            d.paintFlags = 0x00
            top.text = "  "
            friend.text = " "
        } else {
            d.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            top.text = " "+ (numberValue(d.text) - 1).toString()
            friend.text = "1 "
        }
    }

    private fun numberValue(x: CharSequence?): Int {
        return x!![0].toInt() - '0'.toInt()
    }

    private fun finish() {
        findNavController().navigate(R.id.action_finish)
    }
}