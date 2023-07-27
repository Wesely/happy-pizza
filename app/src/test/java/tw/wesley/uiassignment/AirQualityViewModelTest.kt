package tw.wesley.uiassignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.repo.AirDataRepository
import tw.wesley.uiassignment.viewmodel.AirQualityViewModel

@ExperimentalCoroutinesApi
class AirQualityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: AirQualityViewModel
    private val airDataRepository = mockk<AirDataRepository>()

    private val testScope = TestScope()

    @Before
    fun setup() {
        val mockData = List(60) { index ->
            AirData(
                siteId = index.toString(),
                siteName = "siteName",
                county = "county",
                pm25 = index,
                status = "良好"
            )
        }
        every { airDataRepository.getListAirDataFlow() } returns flowOf(mockData)
        coEvery { airDataRepository.fetchAndStoreAirQualityData() } just Runs

        // 使用fake的Repo來創立ViewModel
        viewModel = AirQualityViewModel(airDataRepository)
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }

    /**
     * Everytime we called viewModel.fetchAirData()
     * Make sure the horizontalAirLiveData and verticalAirLiveData are updated
     */
    @Test
    fun fetchAirData_ShouldUpdateLiveData() = runTest {
        var uiState: AirQualityViewModel.UiState? = null
        val job = launch { // launch a new coroutine to collect from the flow
            viewModel.uiState.collect { uiState = it }
        }

        viewModel.fetchAirData()

        // verify fetchAirData calls to getListAirDataFlow()
        coVerify { airDataRepository.getListAirDataFlow() }

        // small delay to wait for flow emit
        delay(500)

        // verify it's collected
        assert(uiState?.horizontalAirDataList?.isNotEmpty() ?: false)
        assert(uiState?.verticalAirDataList?.isNotEmpty() ?: false)

        job.cancel() // cancel the collect
    }

}
