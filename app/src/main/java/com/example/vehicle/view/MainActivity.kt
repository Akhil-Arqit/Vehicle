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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie

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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(50.dp))
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
                                        vehicleData.data
                                    )
                                    Spacer(modifier = Modifier.height(100.dp))
                                    PieChartSample(
                                        modifier = Modifier.padding(innerPadding),
                                    )
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
    fun PieChartSample(modifier: Modifier = Modifier) {
        var data by remember {
            mutableStateOf(
                listOf(
                    Pie(
                        label = "Android",
                        data = 20.0,
                        color = Color.Red,
                        selectedColor = Color.Green
                    ),
                    Pie(
                        label = "Windows",
                        data = 45.0,
                        color = Color.Cyan,
                        selectedColor = Color.Blue
                    ),
                    Pie(
                        label = "Linux",
                        data = 35.0,
                        color = Color.Gray,
                        selectedColor = Color.Yellow
                    ),
                )
            )
        }

        PieChart(
            modifier = Modifier
                .size(400.dp)
                .padding(50.dp),
            data = data,
            onPieClick = {
                println("${it.label} Clicked")
                val pieIndex = data.indexOf(it)
                data =
                    data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.05f,
            spaceDegree = 1f,
            selectedPaddingDegree = 0f,
            style = Pie.Style.Stroke(width = 50.dp),
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
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
                listOf(
                    Line(
                        label = "EV Sales",
                        values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                        color = SolidColor(Color(0xFF23af92)),
                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                    )
                )
            },
            animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            }),
            labelHelperProperties = LabelHelperProperties(
                textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
            ),
            indicatorProperties = HorizontalIndicatorProperties(
                textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
                padding = 16.dp
            )
        )
    }
}