package tw.wesley.uiassignment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import tw.wesley.uiassignment.R
import tw.wesley.uiassignment.data.local.AirData
import tw.wesley.uiassignment.data.local.AirData.Companion.INVALID_INT
import tw.wesley.uiassignment.databinding.ItemVerticalAirDataBinding

class VerticalAirDataAdapter(
    private val airDataList: List<AirData>,
    private val onBadStatusClickedCallback: ((AirData) -> Unit),
) : RecyclerView.Adapter<VerticalAirDataAdapter.VerticalAirDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAirDataViewHolder {
        val binding = ItemVerticalAirDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalAirDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalAirDataViewHolder, position: Int) {
        val data = airDataList[position]
        holder.binding.apply {
            tvSiteId.text = data.siteId
            tvSiteName.text = data.siteName
            tvCounty.text = data.county

            // Handle exception: sometimes PM2.5 is empty
            if (data.pm25 == INVALID_INT) {
                tvPM25.setText(R.string.no_data)
            } else {
                tvPM25.text = data.pm25.toString()
            }

            // Handle Requirement: show when quality is good
            if (data.status == GOOD_STATUS_STRING) {
                root.setOnClickListener(null)
                ivArrow.isVisible = false
                tvStatus.setText(R.string.status_good_quality)
            } else {
                root.setOnClickListener { onBadStatusClickedCallback.invoke(data) }
                ivArrow.isVisible = true
                // handle exception if it's empty
                if (data.status.isEmpty()) {
                    tvStatus.setText(R.string.no_data)
                } else {
                    tvStatus.text = data.status
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount/airDataList.size=${airDataList.size}")
        return airDataList.size
    }

    class VerticalAirDataViewHolder(val binding: ItemVerticalAirDataBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val GOOD_STATUS_STRING = "良好"
    }
}
