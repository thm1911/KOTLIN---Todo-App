package com.example.todoapp.UI.MainApp.Settings.Theme

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setMargins
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.UI.ShareViewModel
import com.example.todoapp.databinding.FragmentThemeBinding

class ThemeFragment : Fragment() {
    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ThemeViewModel by viewModels(){
        ThemeViewModel.ThemeViewModelFactory(shareViewModel, requireContext())
    }
    private val shareViewModel: ShareViewModel by activityViewModels()
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThemeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.theme.setOnClickListener {
            selectColor()
        }
    }

    private fun init(){
        viewModel.listUser.observe(viewLifecycleOwner){user ->
            val theme = user.themeTask
            username = user.username
            binding.color.setBackgroundColor(theme)
            binding.theme.setBackgroundColor(theme)
        }
    }

    private fun selectColor(){
        var theme = 0
        val listColor =  resources.getStringArray(R.array.color_list)
        val colorDialogView = layoutInflater.inflate(R.layout.dialog_color_picker, null)
        val colorDialog = AlertDialog.Builder(requireContext())
            .setTitle("Color")
            .setView(colorDialogView)
            .create()
        val gridLayout = colorDialogView.findViewById<GridLayout>(R.id.gridColor)
        listColor.forEach { color ->
            val colorButton = View(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 170
                    height = 170
                    setMargins(20, 20, 20, 20)
                }
                setBackgroundColor(Color.parseColor(color))
                setOnClickListener {
                    binding.theme.setBackgroundColor(Color.parseColor(color))
                    binding.color.setBackgroundColor(Color.parseColor(color))
                    theme = Color.parseColor(color)
                    colorDialog.cancel()
                }
            }
            gridLayout.addView(colorButton)
        }
        colorDialog.show()

        binding.save.setOnClickListener {
            viewModel.setTheme(username, theme)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}