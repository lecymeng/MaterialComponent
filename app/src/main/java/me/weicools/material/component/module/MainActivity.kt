package me.weicools.material.component.module

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.weicools.common.preferences.RxPreferences
import me.weicools.common.preferences.edit
import me.weicools.material.component.R
import me.weicools.material.component.expansion.panel.ExpansionPanelSummaryActivity
import kotlin.math.abs

/**
 * @author Weicools Create on 2018.08.23
 *
 * desc:
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      addGridTopDividerVisibilityListener()
    } else {
      view_divider.visibility = View.VISIBLE
    }

    RxPreferences.create(this, "MainSP")
      .edit {
        putBoolean("Fuck", true)
        putLong("Date", System.currentTimeMillis())
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show() },
        { error -> Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show() }
      )

    val gridSpanCount = calculateGridSpanCount()
    recyclerView.layoutManager = GridLayoutManager(this, gridSpanCount)
    // recyclerView.addItemDecoration(
    //   GridDividerDecoration(
    //     resources.getDimensionPixelSize(R.dimen.cat_toc_grid_divider_size),
    //     ContextCompat.getColor(context, R.color.cat_toc_grid_divider_color),
    //     gridSpanCount
    //   )
    // )

    recyclerView.adapter = FeatureAdapter(this, FeatureDataManager.instance.featureDataList)
  }

  private fun addGridTopDividerVisibilityListener() {
    app_bar_layout.setOnClickListener { startActivity(Intent(this, ExpansionPanelSummaryActivity::class.java)) }
    app_bar_layout.addOnOffsetChangedListener(OnOffsetChangedListener { _, verticalOffset ->
      if (abs(verticalOffset) == app_bar_layout.totalScrollRange) {
        // CTL is collapsed, hide top divider
        view_divider.visibility = View.GONE
      } else {
        // CTL is expanded or expanding, show top divider
        view_divider.visibility = View.VISIBLE
      }
    })
  }

  private fun calculateGridSpanCount(): Int {
    val displayMetrics = resources.displayMetrics
    val displayWidth = displayMetrics.widthPixels
    val itemSize = resources.getDimensionPixelSize(R.dimen.toc_item_size)
    val gridSpanCount = displayWidth / itemSize
    return MathUtils.clamp(gridSpanCount, GRID_SPAN_COUNT_MIN, GRID_SPAN_COUNT_MAX)
  }

  companion object {
    private const val GRID_SPAN_COUNT_MIN = 1
    private const val GRID_SPAN_COUNT_MAX = 4
  }
}
