import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coin.R
import com.example.coin.data.PriceResponse
import com.example.coin.presentation.SpecificCryptocurrencyScreen.roundNumber

@SuppressLint("ResourceAsColor")
@Composable
fun CryptoPriceChart(priceResponse: PriceResponse, selectedTimeFrame: String) {

    val timeFrameInMillis = when (selectedTimeFrame) {
        "1D" -> 23L * 60L * 60L * 1000L
        "7D" -> 7L * 24L * 60L * 60L * 1000L
        "1M" -> 30L * 24L * 60L * 60L * 1000L
        "3M" -> 85L * 24L * 60L * 60L * 1000L
        "1Y" -> 365L * 24L * 60L * 60L * 1000L
        "MAX" -> Long.MAX_VALUE
        else -> 24L * 60L * 60L * 1000L
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {


        val currentTime = System.currentTimeMillis()
        val filteredPrices = filterPricesByTimeFrame(priceResponse.prices, timeFrameInMillis)
        val prices = filteredPrices

        val maxPrice = filteredPrices.maxOf { it[1] }
        val minPrice = filteredPrices.minOf { it[1] }
        val maxTime = filteredPrices.maxOf { it[0] }
        val minTime = filteredPrices.minOf { it[0] }

        val xPadding = 30f // отступы слева и справа для графика
        val yPadding = 50f // отступы сверху и снизу для графика

        val xRange =
            size.width - xPadding * 2 // доступное пространство по горизонтали для графика
        val yRange =
            size.height - yPadding * 2 // доступное пространство по вертикали для графика

        val timeRange = maxTime - minTime
        val priceRange = maxPrice - minPrice


        val paint = Paint().apply {
            color = Color(0xFF0063F5)
            style = PaintingStyle.Stroke // Использование PaintingStyle.Stroke вместо Stroke
            strokeWidth = 5f // Ширина линии
        }


        for (i in 0 until filteredPrices.size - 1) {
            val x1 = ((prices[i][0] - minTime) / timeRange) * xRange + xPadding
            val y1 = size.height - ((prices[i][1] - minPrice) / priceRange) * yRange - yPadding

            val x2 = ((prices[i + 1][0] - minTime) / timeRange) * xRange + xPadding
            val y2 =
                size.height - ((prices[i + 1][1] - minPrice) / priceRange) * yRange - yPadding

            drawLine(
                color = Color.Blue, // Замените Color.Blue на нужный цвет
                start = Offset(x1.toFloat(), y1.toFloat()),
                end = Offset(x2.toFloat(), y2.toFloat()),
                strokeWidth = 2f, // Ширина линии
                cap = StrokeCap.Round // Опционально: настройка конца линии
            )

        }

        val maxPriceY = size.height - ((maxPrice - minPrice) / priceRange) * yRange - yPadding
        val minPriceY = size.height - ((minPrice - minPrice) / priceRange) * yRange - yPadding

        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        drawLine(
            color = Color(color = R.color.color_primary_text), // Цвет максимальной цены
            start = Offset(size.width - xPadding, maxPriceY.toFloat()),
            end = Offset(xPadding, maxPriceY.toFloat()),
            strokeWidth = 2f,
            pathEffect = pathEffect // Пунктирный эффект для линии
        )

        drawLine(
            color = Color(color = R.color.color_primary_text), // Цвет минимальной цены
            start = Offset(size.width - xPadding, minPriceY.toFloat()),
            end = Offset(xPadding, minPriceY.toFloat()),
            strokeWidth = 2f,
            pathEffect = pathEffect // Пунктирный эффект для линии
        )

        drawIntoCanvas { canvas ->
            val paintText = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                color = R.color.color_primary_text // Цвет текста
                textSize = 42f // Размер текста
            }


            val maxPrice2 = roundNumber(maxPrice)
            val minPrice2 = roundNumber(minPrice)

            val maxPriceText = "$maxPrice2 $" // Текст для максимальной цены
            val minPriceText = "$minPrice2 $" // Текст для минимальной цены

            val textWidth = paintText.measureText(maxPriceText)

            // Вычисление координат для текста
            val maxPriceTextX = size.width - xPadding + 10f - textWidth
            val maxPriceTextY = maxPriceY + 5f // Небольшой отступ сверху
            val minPriceTextX = size.width - xPadding + 10f - textWidth
            val minPriceTextY = minPriceY + 5f // Небольшой отступ сверху

            // Рисование текста на холсте
            canvas.nativeCanvas.drawText(maxPriceText, maxPriceTextX, maxPriceTextY.toFloat(), paintText)
            canvas.nativeCanvas.drawText(minPriceText, minPriceTextX, minPriceTextY.toFloat(), paintText)
        }

        val path = Path().apply {
            moveTo(xPadding, size.height - yPadding) // Начинаем отрисовку с левого нижнего угла

            // Проводим линии до точек графика
            for (i in 0 until prices.size - 1) {
                val x = ((prices[i][0] - minTime) / timeRange) * xRange + xPadding
                val y = size.height - ((prices[i][1] - minPrice) / priceRange) * yRange - yPadding
                lineTo(x.toFloat(), y.toFloat())
            }

            lineTo(size.width - xPadding, size.height - yPadding) // Закрываем фигуру
            close() // Замыкаем путь
        }

        // Рисуем фигуру под графиком
        drawPath(
            path = path,
            color = Color.Blue.copy(alpha = 0.1f) // Цвет с прозрачностью
        )
    }
}

private fun filterPricesByTimeFrame(prices: List<List<Double>>, timeFrameInMillis: Long): List<List<Double>> {
    val currentTime = System.currentTimeMillis()
    val filteredPrices = prices.filter { it[0] >= currentTime - timeFrameInMillis }

    return if (timeFrameInMillis > 7L * 24L * 60L * 60L * 1000L) {
        filteredPrices.filterIndexed { index, _ ->
            index % 5 == 0
        }
    } else if (timeFrameInMillis > 84L * 24L * 60L * 60L * 1000L) {
        filteredPrices.filterIndexed { index, _ ->
            index % 15 == 0
        }
    } else {
        filteredPrices
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewCryptoPriceChart() {
    val testData = PriceResponse(
        prices = listOf(
            listOf(1702905628269.0, 41062.35792800487),
            listOf(1702905925784.0, 41086.62748275064),
            listOf(1702906273389.0, 41149.07771941036)
        ),
        marketCaps = emptyList(),
        totalVolumes = emptyList()
    )
    CryptoPriceChart(testData, "1 day")
}

