package pl.gmat.users.feature.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.times
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditUserViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var stateObserverMock: Observer<EditUserState>

    private lateinit var viewModel: EditUserViewModel

    @Before
    fun setup() {
        viewModel = EditUserViewModel()
        viewModel.state.observeForever(stateObserverMock)
    }

    @Test
    fun `on choose existing address clicked`() {
        viewModel.onChooseExistingAddressClicked()

        inOrder(stateObserverMock) {
            verify(stateObserverMock).onChanged(EditUserState())
            verify(stateObserverMock).onChanged(EditUserState(isAddNewAddressChecked = false))
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `on add new address clicked`() {
        viewModel.onAddNewAddressClicked()

        inOrder(stateObserverMock) {
            verify(stateObserverMock, times(2)).onChanged(EditUserState())
            verifyNoMoreInteractions()
        }
    }
}