package com.bertosoft.horoscapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.bertosoft.horoscapp.R
import com.bertosoft.horoscapp.databinding.ActivityHoroscopeDetailBinding
import com.bertosoft.horoscapp.domain.model.HoroscopeModel
import com.bertosoft.horoscapp.domain.model.HoroscopeModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHoroscopeDetailBinding
    private val horoscopeDetailViewModel: HoroscopeDetailViewModel by viewModels()

    private val args: HoroscopeDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoroscopeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //
        // Recuperamos el detalle del signo horoscope, enviado del fragment Horoscope por type
        //
        val type = args.type
        horoscopeDetailViewModel.getHoroscope(args.type)
        initUi()
    }

    private fun initUi() {
        initListener()
        initUiState()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopeDetailViewModel.state.collect {
                    when (it) {
                        HoroscopeDetailState.Loading -> loadingState()
                        is HoroscopeDetailState.Error -> errorState()
                        is HoroscopeDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun successState(state: HoroscopeDetailState.Success) {
        binding.pbCargando.isVisible = false
        binding.tvTitle.text = state.sign
        binding.tvBody.text = state.prediction

        val img = when(state.horoscopeModel){
            aries -> R.drawable.detail_aries
            taurus -> R.drawable.detail_taurus
            gemini -> R.drawable.detail_gemini
            cancer -> R.drawable.detail_cancer
            leo -> R.drawable.detail_leo
            virgo -> R.drawable.detail_virgo
            libra -> R.drawable.detail_libra
            scorpio -> R.drawable.detail_scorpio
            sagittarius -> R.drawable.detail_sagittarius
            capricorn -> R.drawable.detail_capricorn
            aquarius -> R.drawable.detail_aquarius
            pisces -> R.drawable.detail_pisces
        }

        binding.ivDetail.setImageResource(img)
    }

    private fun errorState() {
        binding.pbCargando.isVisible = false
    }

    private fun loadingState() {
        binding.pbCargando.isVisible = true

    }
}