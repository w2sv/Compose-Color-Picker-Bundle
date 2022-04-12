package com.smarttoolfactory.colorpicker.picker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.colorpicker.model.ColorModel
import com.smarttoolfactory.colorpicker.selector.SelectorRectSaturationLightnessHSL
import com.smarttoolfactory.colorpicker.slider.SliderCircleColorDisplayHueHSL
import com.smarttoolfactory.colorpicker.util.colorToHSL
import com.smarttoolfactory.colorpicker.util.colorToHex
import com.smarttoolfactory.colorpicker.widget.HexDisplay

@Composable
fun ColorPickerRectSaturationLightnessHSL(
    modifier: Modifier = Modifier,
    selectionRadius: Dp = 8.dp,
    initialColor: Color,
    onColorChange: (Color, String) -> Unit
) {

    var colorModel by remember { mutableStateOf(ColorModel.HSL) }

    val hslArray = colorToHSL(initialColor)

    var hue by remember { mutableStateOf(hslArray[0]) }
    var saturation by remember { mutableStateOf(hslArray[1]) }
    var lightness by remember { mutableStateOf(hslArray[2]) }
    var alpha by remember { mutableStateOf(initialColor.alpha) }

    val currentColor =
        Color.hsl(hue = hue, saturation = saturation, lightness = lightness, alpha = alpha)

    onColorChange(currentColor, colorToHex(currentColor))

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectorRectSaturationLightnessHSL(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4 / 3f),
            hue = hue,
            saturation = saturation,
            lightness = lightness,
            selectionRadius = selectionRadius
        ) { s, l ->
            saturation = s
            lightness = l
        }

        Column(modifier = Modifier.padding(8.dp)) {
            SliderCircleColorDisplayHueHSL(
                modifier = Modifier.padding(8.dp),
                hue = hue,
                saturation = saturation,
                lightness = lightness,
                alpha = alpha,
                onHueChange = {
                    hue = it
                },
                onAlphaChange = {
                    alpha = it
                }
            )

            HexDisplay(
                color = currentColor,
                colorModel = colorModel,
                onColorModelChange = {
                    colorModel = it
                }
            )
        }
    }
}