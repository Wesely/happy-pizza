package tw.wesley.uiassignment.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import tw.wesley.uiassignment.R
import tw.wesley.uiassignment.databinding.ActivityMainBinding
import tw.wesley.uiassignment.viewmodel.AirQualityViewModel

@AndroidEntryPoint // For HiltViewModel is used in our AirQualityViewModel
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Use this delegate to auto create/destroy on correct lifecycle
    private val viewModel: AirQualityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(toolbar)
        }

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setTitle(R.string.home_title)

        // Ask to start getting the data
        lifecycleScope.launch {
            viewModel.fetchAirData()
        }

        viewModel.verticalAirLiveData.observe(this) { data ->
            // only print on debug build
            Timber.d("collect/vertical/dataSize=${data.size}")
            binding.verticalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = VerticalAirDataAdapter(data) {
                    // Example: 沙鹿的空氣不良好
                    Toast.makeText(context, getString(R.string.home_item_toast, it.siteName), Toast.LENGTH_SHORT).show()
                }
            }

        }
        viewModel.horizontalAirLiveData.observe(this) { data ->
            // only print on debug build
            Timber.d("collect/horizontal/dataSize=${data.size}")
            binding.horizontalRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = HorizontalAirDataAdapter(data)
            }
        }
    }

    // https://developer.android.com/develop/ui/views/search/training/setup
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_item_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_item_search -> {
            // User chose the "Search" item
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}