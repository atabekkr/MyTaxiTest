package com.atabekdev.mytaxitest.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.atabekdev.mytaxitest.R

// Set of Material typography styles to start with
val LightTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(700),
        fontSize = 20.sp,
        lineHeight = 24.sp,
        color = MainTypography,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        color = NumberColor,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        color = MainTypography,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(400),
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        color = MainTypography,
        letterSpacing = 0.5.sp
    ),
)
// Set of Material typography styles to start with
val DarkTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(700),
        fontSize = 20.sp,
        lineHeight = 24.sp,
        color = MainTypography,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        color = NumberColorDark,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(600),
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        color = White,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.font_lato_regular)),
        fontWeight = FontWeight(400),
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
        color = White,
        letterSpacing = 0.5.sp
    ),
)