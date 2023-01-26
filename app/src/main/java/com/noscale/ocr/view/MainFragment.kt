package com.noscale.ocr.view

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.noscale.core.ViewBindingFragment
import com.noscale.core.util.spacing
import com.noscale.core.util.showSnackbar
import com.noscale.ocr.databinding.FragmentMainBinding
import com.noscale.ocr.OCRProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : ViewBindingFragment<FragmentMainBinding>(), OnSuccessListener<Text>, OnFailureListener {

    @Inject lateinit var mProvider: OCRProvider

    private val viewModel by viewModels<MainViewModel>()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val uri = mProvider.getResult(data)

            read(uri)
            return@registerForActivityResult
        }
        showProgress(false)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun setup() = with(binding) {
        lifecycleOwner = viewLifecycleOwner
        vm = viewModel

        val adapter = MainAdapter()
        rvMain.adapter = adapter
        rvMain.spacing()

        viewModel.eventFlow.onEach {
            when(it) {
                is Event.Start -> {
                    showProgress(true)
                    val intent = mProvider.intent
                    launcher.launch(intent)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.expressions.observe(viewLifecycleOwner) {
            isEmpty = it.isNullOrEmpty()

            adapter.submitList(it)

            showProgress(false)
            rvMain.smoothScrollToPosition(0)
        }
    }

    override fun onSuccess(p0: Text?) {
        try {
            for (block in requireNotNull(p0?.textBlocks)) {
                val text = block.text
                val expression = ExpressionBuilder(text).build()
                val result = expression.evaluate()

                viewModel.insert(text, result)
                showProgress(false)
                break
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            onFailure(e)
        }
    }

    override fun onFailure(p0: Exception) {
        showProgress(false)
        binding.root.showSnackbar(
            requireNotNull(p0.message), Snackbar.LENGTH_LONG
        )
    }

    private fun read(uri: Uri?) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        if (null != uri) {
            val image = InputImage.fromFilePath(requireContext(), uri)
            recognizer.process(image)
                .addOnSuccessListener(this)
                .addOnFailureListener(this)
        } else onFailure(IllegalArgumentException("Unknown Result"))
    }

    private fun showProgress(isVisible: Boolean) = with(binding) {
        vLoading.root.visibility = if (isVisible) View.VISIBLE else View.GONE
        vContainer.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
}