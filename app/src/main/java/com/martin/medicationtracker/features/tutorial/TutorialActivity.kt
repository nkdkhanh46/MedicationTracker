package com.martin.medicationtracker.features.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.martin.medicationtracker.R
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.databinding.ActivityTutorialBinding
import com.martin.medicationtracker.customviews.SingleSnapHelper
import com.martin.medicationtracker.features.home.HomeActivity
import kotlinx.android.synthetic.main.activity_tutorial.*


class TutorialActivity : BaseActivity() {

    private lateinit var binding: ActivityTutorialBinding
    private lateinit var viewModel: TutorialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
        setupViewEvents()
        observeChanges()
    }

    private fun setupBinding() {
        (application as MainApplication).appComponent.inject(this)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_tutorial
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[TutorialViewModel::class.java]
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun initViews() {
        setupImagesPager()
        setupPageIndicator()
    }

    private fun setupImagesPager() {
        val screenWidth = this.resources.displayMetrics.widthPixels
        val imagesAdapter = ImageAdapter(
            screenWidth,
            viewModel.tutorials
        )

        val snapHelper = SingleSnapHelper()
        snapHelper.attachToRecyclerView(rvImages)
        val imagesLayoutManager = LinearLayoutManager(this, HORIZONTAL, false)

        rvImages?.apply {
            layoutManager = imagesLayoutManager
            adapter = imagesAdapter
            smoothScrollToPosition(1)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState != RecyclerView.SCROLL_STATE_IDLE) return

                    var currentPosition = imagesLayoutManager.findFirstCompletelyVisibleItemPosition()
                    currentPosition = if (currentPosition < 1) 0 else currentPosition-1
                    viewModel.changeCurrentPage(currentPosition)
                    vIndicator.setSelected(currentPosition)
                }
            })
        }
    }

    private fun setupPageIndicator() {
        vIndicator.count = viewModel.tutorials.size
        vIndicator.setSelected(0)
    }

    private fun setupViewEvents() {
        btnStart.setOnClickListener {
            completeTutorial()
        }
        btnSkip.setOnClickListener {
            completeTutorial()
        }
    }

    private fun completeTutorial() {
        viewModel.completeTutorial()
        openHomePage()
    }

    private fun openHomePage() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun observeChanges() {
        viewModel.tutorialCompleted.observe(this, Observer {
            it?.let { completed ->
                if (completed) {
                    openHomePage()
                }
            }
        })
    }
}
