package cuong.dev.dotymovie.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class NavigationViewModel: ViewModel() {
    private val _selectedItem = mutableIntStateOf(1);
    val selectedItem: State<Int> get() = _selectedItem;

    fun setSelectedItem(index: Int) {
        _selectedItem.intValue = index
    }

    fun updateSelectedItem(route: String) {
        _selectedItem.intValue = when (route) {
            "favorites" -> 0
            "home" -> 1
            "tickets" -> 2
            else -> 1
        }
    }
}