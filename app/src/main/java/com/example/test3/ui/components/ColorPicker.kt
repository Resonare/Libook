package com.example.test3.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun ColorPicker(handleColorChange: (Color) -> Unit) {
    val controller = rememberColorPickerController()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp)
    ) {
        BrightnessSlider(
            modifier = Modifier
                .width(160.dp)
                .height(35.dp)
                .rotate(90f)
                .offset(x = 60.dp),
            controller = controller,
        )
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            controller = controller,
            onColorChanged = { colorEnv ->
                handleColorChange(Color(colorEnv.color.red, colorEnv.color.green, colorEnv.color.blue))
            }
        )
    }
}