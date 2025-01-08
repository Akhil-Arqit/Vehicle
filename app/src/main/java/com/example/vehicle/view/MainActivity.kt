package com.example.vehicle.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vehicle.R
import com.example.vehicle.common.DataViewModelFactory
import com.example.vehicle.common.UIState
import com.example.vehicle.model.Data
import com.example.vehicle.model.EvSalesGraphData
import com.example.vehicle.repository.DataRepository
import com.example.vehicle.ui.theme.VehicleTheme
import com.example.vehicle.viewmodel.DataViewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val dataRepository = DataRepository()
    private val viewModel: DataViewModel by viewModels {
        DataViewModelFactory(dataRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VehicleTheme {
                val uiState by viewModel.uiState.collectAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                fontFamily = FontFamily(
                                    Font(R.font.electrolize_regular, FontWeight.W900)
                                ),
                                fontSize = 28.sp,
                                textAlign = TextAlign.Center,
                                text = "Total EV Sales",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                fontFamily = FontFamily(
                                    Font(R.font.zcool, FontWeight.Medium)
                                ),
                                fontSize = 42.sp,
                                letterSpacing = 3.sp,
                                textAlign = TextAlign.Center,
                                text = "1287641",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                modifier = Modifier.padding(
                                    start = 15.dp,
                                ),
                                fontFamily = FontFamily(
                                    Font(R.font.electrolize_regular, FontWeight.Medium)
                                ),
                                fontSize = 14.sp,
                                text = "In Sep. '24",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        bottom = 20.dp
                                    ),
                                    fontFamily = FontFamily(
                                        Font(R.font.zcool, FontWeight.Medium)
                                    ),
                                    letterSpacing = 3.sp,
                                    fontSize = 36.sp,
                                    text = "396175",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    modifier = Modifier.padding(
                                        top = 12.dp,
                                        start = 5.dp,
                                    ).weight(1f),
                                    textAlign = TextAlign.Start,
                                    letterSpacing = 2.sp,
                                    fontSize = 14.sp,
                                    text = "EV's Sold",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Row(
                                    modifier = Modifier
                                        .wrapContentWidth().padding(end = 12.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(
                                                start = 5.dp,
                                            ),
                                        fontFamily = FontFamily(
                                            Font(R.font.zcool, FontWeight.Normal)
                                        ),
                                        textAlign = TextAlign.End,
                                        letterSpacing = 2.sp,
                                        fontSize = 28.sp,
                                        text = "8.99",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Image(
                                        modifier = Modifier.size(18.dp).padding(start = 5.dp),
                                        contentDescription = null,
                                        painter = painterResource(R.drawable.up_arrow)
                                    )
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                PieChartSample()
                                Image(
                                    modifier = Modifier.size(100.dp).offset(x= (-50).dp).scale(2.7f,2.7f).weight(1f),
                                    contentDescription = null,
                                    painter = painterResource(R.drawable.car)
                                )
                            }
                            Spacer(modifier = Modifier.height(70.dp))
                            when (uiState) {
                                is UIState.Loading -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "Loading...",
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                }
                                is UIState.Success -> {
                                    val vehicleData = (uiState as UIState.Success<EvSalesGraphData>).data
                                    LineChartSample(
                                        modifier = Modifier.padding(innerPadding),
                                        vehicleData.data.dropLast(1)
                                    )
                                    Spacer(modifier = Modifier.height(100.dp))
                                }
                                is UIState.Error -> {
                                    val errorMessage = (uiState as UIState.Error).message
                                    Text(text = "Error: $errorMessage", modifier = Modifier.padding(innerPadding))
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    @Composable
    fun PieChartSample() {
        var data by remember {
            mutableStateOf(
                listOf(
                    Pie(
                        label = "Android",
                        data = 20.0,
                        color = Color(0xFFFF6384).copy(alpha = 0.5f),
                        selectedColor = Color(0xFFFF6384)
                    ),
                    Pie(
                        label = "Windows",
                        data = 45.0,
                        color = Color(0xFF36A2EB).copy(alpha = 0.5f),
                        selectedColor = Color(0xFF36A2EB)
                    ),
                    Pie(
                        label = "Linux",
                        data = 35.0,
                        color = Color(0xFFFFCE56).copy(alpha = 0.5f),
                        selectedColor = Color(0xFFFFCE56)
                    ),
                    Pie(
                        label = "Windows",
                        data = 45.0,
                        color = Color(0xFF4BC0C0).copy(alpha = 0.5f),
                        selectedColor = Color(0xFF4BC0C0)
                    ),
                    Pie(
                        label = "Linux",
                        data = 35.0,
                        color = Color(0xFF9966FF).copy(alpha = 0.5f),
                        selectedColor = Color(0xFF9966FF)
                    ),
                )
            )
        }
        var selectedText = ""
        PieChart(
            modifier = Modifier.width(250.dp).height(150.dp).offset(x= (-30).dp),
            data = data,
            onPieClick = {
                selectedText = it.label.toString()
                println("${it.label} Clicked")
                val pieIndex = data.indexOf(it)
                data = data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.05f,
            spaceDegree = 1f,
            selectedPaddingDegree = 0f,
            style = Pie.Style.Stroke(width = 20.dp),
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
        )
        Text(
            modifier = Modifier.offset(x = (-180).dp).width(100.dp),
            fontFamily = FontFamily(
                Font(R.font.zcool, FontWeight.Normal)
            ),
            textAlign = TextAlign.Start,
            letterSpacing = 2.sp,
            fontSize = 28.sp,
            text = "8.99",
            style = MaterialTheme.typography.titleLarge
        )
    }


    @Composable
    fun LineChartSample(modifier: Modifier = Modifier, data: List<Data>) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 22.dp),
            data = remember {
                data.map { data ->
                    Line(
                        label = if (data.category == "3 Wheeler (Passenger)") "3 Wheeler" else data.category,
                        values = data.y.map { it.toDouble() / 1000},
                        color = SolidColor(Color(
                            red = Random.nextFloat(),
                            green = Random.nextFloat(),
                            blue = Random.nextFloat(),
                            alpha = 1.0f
                        )),
                        firstGradientFillColor = Color.Transparent,
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                    )
                }.toMutableList()
            },
            animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            }),
            labelHelperProperties = LabelHelperProperties(
                textStyle = TextStyle.Default.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp
                ),
            ),
            indicatorProperties = HorizontalIndicatorProperties(
                textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
                padding = 16.dp
            ),
            labelProperties = LabelProperties(
                enabled = true,
                padding = 10.dp,
                textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
                labels = data[0].x.map { it.toString() }),
        )
    }
}