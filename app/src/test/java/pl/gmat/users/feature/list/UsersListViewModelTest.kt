package pl.gmat.users.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var effectObserverMock: Observer<UsersListEffect>

    private val viewModel = UsersListViewModel()

    @Before
    fun setup() {
        viewModel.effect.observeForever(effectObserverMock)
    }

    @Test
    fun `on add clicked`() {
        viewModel.onAddClicked()

        verify(effectObserverMock).onChanged(UsersListEffect.ShowAddUser)
    }
}