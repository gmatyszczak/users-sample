package pl.gmat.users.feature.edit.ui.widget

import android.content.Context
import android.widget.ArrayAdapter
import pl.gmat.users.common.model.Address

class AddressAdapter(
    context: Context
) : ArrayAdapter<Address>(context, android.R.layout.simple_spinner_item) {

    private var items = emptyArray<Address>()

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    fun submit(newItems: Array<Address>) {
        if (!newItems.contentEquals(items)) {
            clear()
            addAll(newItems.toList())
            items = newItems
        }
    }
}