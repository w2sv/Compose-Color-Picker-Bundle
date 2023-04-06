package com.smarttoolfactory.colorpicker.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.colorpicker.model.ColorModel
import com.smarttoolfactory.extendedcolors.util.*

@Composable
fun ColorDisplayExposedSelectionMenu() {
    var index by remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        ExposedSelectionMenu(
            index = index,
            options = ColorModel.values().map { it.name },
            onSelected = {
                index = it
            }
        )
    }
}

/**
 * Selection menu that displays Color's components in Hex, HSL, or HSL
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColorDisplayExposedSelectionMenu(
    color: Color,
    initialColorModel: ColorModel,
    backgroundColor: Color,
    textColor: Color
) {
    var colorModel by remember { mutableStateOf(initialColorModel) }

    var selectedIndex by remember {
        mutableStateOf(colorModel.ordinal)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        ExposedSelectionMenu(
            modifier = Modifier.width(100.dp),
            index = selectedIndex,
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                trailingIconColor = Color.Black,
                focusedTrailingIconColor = Color.Black,
                textColor = Color.Black,
            ),
            options = ColorModel.values().map { it.name },
            onSelected = {
                selectedIndex = it
                colorModel = ColorModel.values()[it]
            }
        )

        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            when (colorModel) {
                ColorModel.RGB -> {
                    ColorValueColumn(
                        label = "R",
                        value = color.red.fractionToRGBString(),
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "G",
                        value = color.green.fractionToRGBString(),
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "B",
                        value = color.blue.fractionToRGBString(),
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "A",
                        value = "${color.alpha.fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                }

                ColorModel.HSV -> {
                    val hsvArray = ColorUtil.colorToHSV(color)
                    ColorValueColumn(
                        label = "H",
                        value = "${hsvArray[0].round()}°",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "S",
                        value = "${hsvArray[1].fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "V",
                        value = "${hsvArray[2].fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "A",
                        value = "${color.alpha.fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                }

                ColorModel.HSL -> {
                    val hslArray = ColorUtil.colorToHSL(color)
                    ColorValueColumn(
                        label = "H",
                        value = "${hslArray[0].round()}°",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "S",
                        value = "${hslArray[1].fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "L",
                        value = "${hslArray[2].fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                    ColorValueColumn(
                        label = "A",
                        value = "${color.alpha.fractionToPercent()}%",
                        modifier = Modifier.weight(1f),
                        textColor = textColor
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorValueColumn(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    textColor: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = textColor, fontSize = 14.sp)
    }
}