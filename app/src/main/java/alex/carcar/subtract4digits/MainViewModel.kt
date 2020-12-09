package alex.carcar.subtract4digits

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var points = 0
    fun setPoints(p: Int) {
        points = p
    }

    fun getPoints(): Int {
        return points
    }
}