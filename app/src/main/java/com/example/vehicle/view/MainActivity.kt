package com.example.vehicle.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vehicle.R
import com.example.vehicle.common.DataViewModelFactory
import com.example.vehicle.common.UIState
import com.example.vehicle.model.avergaePowerSupplyTraiff.AverageSupplyTariff
import com.example.vehicle.model.evGraph.Data
import com.example.vehicle.model.evGraph.EvSalesGraphData
import com.example.vehicle.model.lastMonthSales.LastMonthSalesData
import com.example.vehicle.model.salesManufacturers.Manufacturer
import com.example.vehicle.model.salesManufacturers.SalesManufacturers
import com.example.vehicle.model.totalChargeStations.TotalChargeStations
import com.example.vehicle.model.totalLastMonthSales.TotalLastMonthSalesData
import com.example.vehicle.model.totalSales.TotalSalesData
import com.example.vehicle.repository.DataRepository
import com.example.vehicle.ui.theme.VehicleTheme
import com.example.vehicle.viewmodel.MainActivityViewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie
import java.text.DecimalFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val dataRepository = DataRepository()
    private val viewModel: MainActivityViewModel by viewModels {
        DataViewModelFactory(dataRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VehicleTheme {
                val evSalesGraphDataUIState by viewModel.evGraphData.collectAsState()
                val evTotalSalesDataUIState by viewModel.evTotalSalesData.collectAsState()
                val evTotalLastMonthSalesDataUIState by viewModel.evTotalLastMonthSalesData.collectAsState()
                val evLastMonthSalesDataUIState by viewModel.evLastMonthSalesData.collectAsState()
                val evTotalChargeStationsDataUIState by viewModel.evTotalChargeStationsData.collectAsState()
                val evAverageSupplyTariffDataUIState by viewModel.evAverageSupplyTariffData.collectAsState()
                val evManufacturersSalesDataUIState by viewModel.evManufacturersSalesData.collectAsState()
                var evAdoptionRate by remember { mutableStateOf("0.0") }
                var clickedBoxText by remember { mutableStateOf("") }
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
                        }
                        item {
                            when (evTotalSalesDataUIState) {
                                is UIState.Error -> {}
                                is UIState.Loading -> {}
                                is UIState.Success -> {
                                    val totalSalesData =
                                        (evTotalSalesDataUIState as UIState.Success<TotalSalesData>).data
                                    when (evTotalChargeStationsDataUIState) {
                                        is UIState.Error -> {}
                                        is UIState.Loading -> {}
                                        is UIState.Success -> {
                                            val totalChargeStationsData =
                                                (evTotalChargeStationsDataUIState as UIState.Success<TotalChargeStations>).data
                                            when (evAverageSupplyTariffDataUIState) {
                                                is UIState.Error -> {}
                                                is UIState.Loading -> {}
                                                is UIState.Success -> {
                                                    val averageSupplyTariffData =
                                                        (evAverageSupplyTariffDataUIState as UIState.Success<AverageSupplyTariff>).data
                                                    when (evManufacturersSalesDataUIState) {
                                                        is UIState.Error -> {}
                                                        is UIState.Loading -> {}
                                                        is UIState.Success -> {
                                                            val manufacturersSalesData =
                                                                (evManufacturersSalesDataUIState as UIState.Success<SalesManufacturers>).data
                                                            manufacturersSalesData.data
                                                            val topManufacturer =
                                                                manufacturersSalesData.data.manufacturer.maxByOrNull { it.sales }
                                                            when (evTotalLastMonthSalesDataUIState) {
                                                                is UIState.Error -> {}
                                                                is UIState.Loading -> {}
                                                                is UIState.Success -> {
                                                                    val lastMonthSalesData =
                                                                        (evTotalLastMonthSalesDataUIState as UIState.Success<TotalLastMonthSalesData>).data
                                                                    TotalEvSales(lastMonthSalesData)
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    BoxLayoutExample(
                                                                        totalSalesData,
                                                                        clickedText = {
                                                                            clickedBoxText = it
                                                                        })
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    TotalEvAdoption(evAdoptionRate)
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    DottedLine()
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    HighestEvAdoption(
                                                                        lastMonthSalesData
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    DottedLine()
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    TotalPublicEvStations(
                                                                        totalChargeStationsData
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    DottedLine()
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    AverageSupplyTariff(
                                                                        averageSupplyTariffData
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    DottedLine()
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    TopEvManufacturer(
                                                                        topManufacturer
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    DottedLine()
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    LastMonthSalesText(
                                                                        lastMonthSalesData
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            val selectedText = remember { mutableStateOf("") }
                            val selectedPieValueText = remember { mutableStateOf("") }
                            when (clickedBoxText) {
                                "2 Wheeler's" -> {
                                    selectedText.value = "2 Wheelers"
                                }
                                "3 Wheeler's" -> {
                                    selectedText.value = "3 Wheelers"
                                }
                                "Car/SUV" -> {
                                    selectedText.value = "Cars"
                                }
                                else -> {
                                    selectedText.value = "Buses"
                                }
                            }
                            when (evLastMonthSalesDataUIState) {
                                is UIState.Error -> {}
                                is UIState.Loading -> {}
                                is UIState.Success -> {
                                    val lastMonthSalesData =
                                        (evLastMonthSalesDataUIState as UIState.Success<LastMonthSalesData>).data
                                    evAdoptionRate =
                                        lastMonthSalesData.data.total_ev_adoption.toString()
                                    LastMonthSalesPieChat(
                                        lastMonthSalesData,
                                        selectedText,
                                        selectedPieValueText
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            DottedLine()
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            Spacer(modifier = Modifier.height(18.dp))
                            when (evSalesGraphDataUIState) {
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
                                    val vehicleData =
                                        (evSalesGraphDataUIState as UIState.Success<EvSalesGraphData>).data
                                    LineChartSample(
                                        vehicleData.data.dropLast(1)
                                    )
                                    Spacer(modifier = Modifier.height(50.dp))
                                }
                                is UIState.Error -> {
                                    val errorMessage =
                                        (evSalesGraphDataUIState as UIState.Error).message
                                    Text(text = "Error: $errorMessage", modifier = Modifier.padding(innerPadding))
                                }
                            }
                        }
                        item {
                            DottedLine()
                            Spacer(modifier = Modifier.height(8.dp))
                            Column {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Source : EvReadyIndia.org",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.electrolize_regular,
                                            FontWeight.Normal
                                        )
                                    )
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BoxLayoutExample(totalSalesData: TotalSalesData, clickedText: (String) -> Unit = {}) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    DottedBorderBox(
                        text = "2 Wheeler's",
                        value = totalSalesData.data.two_wheeler.sales_count,
                        clickedText = { clickedText(it) }
                    )
                    DottedBorderBox(
                        text = "3 Wheeler's",
                        value = totalSalesData.data.three_wheeler_p.sales_count + totalSalesData.data.three_wheeler_g.sales_count,
                        clickedText = { clickedText(it) }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    DottedBorderBox(
                        text = "Car/SUV",
                        value = totalSalesData.data.car_suv.sales_count,
                        clickedText = { clickedText(it) }
                    )
                    DottedBorderBox(
                        text = "Buses",
                        value = totalSalesData.data.bus.sales_count,
                        clickedText = { clickedText(it) }
                    )
                }
            }
        }
    }

    @Composable
    fun DottedBorderBox(text: String, value: Int, clickedText: (String) -> Unit = {}) {
        val hapticFeedback = LocalHapticFeedback.current
        val lineColor = MaterialTheme.colorScheme.onSurface
        Box(
            modifier = Modifier
                .size(
                    width = (LocalConfiguration.current.screenWidthDp.dp) * 0.5f,
                    height = 100.dp
                )
                .clickable {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    clickedText(text)
                }
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRect(
                    color = lineColor,
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    ),
                    topLeft = Offset.Zero,
                    size = this.size
                )
            }

            Box(
                modifier = Modifier
                    .matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 4.dp,
                            ),
                        fontFamily = FontFamily(
                            Font(
                                R.font.electrolize_regular,
                                FontWeight.Bold
                            )
                        ),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.8.sp,
                        fontSize = 22.sp,
                        text = text,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    AnimatedTextValueInt(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 4.dp,
                            ),
                        targetValue = value,
                        textSize = 26.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    @Composable
    private fun LastMonthSalesPieChat(
        lastMonthSalesData: LastMonthSalesData,
        selectedText: MutableState<String>,
        selectedPieValueText: MutableState<String>
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PieChartSample(
                    lastMonthSalesData.data,
                    selected = { selectedText.value = it },
                    selectedValue = {
                        selectedPieValueText.value =
                            it.toString()
                    },
                    selectedText = selectedText,
                )
                SlidingImage(selectedText)
            }
        }
    }

    @Composable
    private fun LastMonthSalesText(salesData: TotalLastMonthSalesData) {
        Text(
            modifier = Modifier.padding(
                start = 15.dp,
            ),
            fontFamily = FontFamily(
                Font(
                    R.font.electrolize_regular,
                    FontWeight.Medium
                )
            ),
            fontSize = 14.sp,
            text = "Sales In Sep. '24",
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            AnimatedTextValueInt(
                modifier = Modifier.padding(
                    start = 24.dp,
                    bottom = 18.dp,
                ),
                targetValue = salesData.data.total_ev_sales_by_month.total_count,
                textSize = 36.sp,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        start = 5.dp,
                        end = 15.dp
                    ),
                textAlign = TextAlign.Start,
                letterSpacing = 2.sp,
                fontSize = 14.sp,
                text = "EV's Sold",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .offset(y = (-10).dp)
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 1.sp,
                    fontSize = 18.sp,
                    text = "+",
                    style = MaterialTheme.typography.titleLarge
                )
                AnimatedTextValueFloat(
                    modifier = Modifier
                        .padding(
                            start = 1.dp,
                        ),
                    targetValue = salesData.data.total_ev_sales_by_month.month_on_month.toFloat(),
                    textSize = 24.sp,
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 1.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 2.sp,
                    fontSize = 18.sp,
                    text = "%",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 1.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 2.sp,
                    fontSize = 12.sp,
                    text = "MoM",
                    style = MaterialTheme.typography.titleLarge
                )
                Image(
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 5.dp),
                    contentDescription = null,
                    painter = painterResource(R.drawable.up_arrow)
                )
            }
        }
    }

    @Composable
    private fun TopEvManufacturer(topManufacturer: Manufacturer?) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                ),
                fontFamily = FontFamily(
                    Font(
                        R.font.electrolize_regular,
                        FontWeight.Medium
                    )
                ),
                fontSize = 18.sp,
                text = "Top EV Manufacturer Seller:",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.zcool,
                            FontWeight.Thin
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 1.sp,
                    fontSize = 22.sp,
                    text = if (topManufacturer?.manufacturer == "OLA ELECTRIC TE") {
                        "OLA ELECTRIC"
                    } else {
                        topManufacturer?.manufacturer.orEmpty()
                    },
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    @Composable
    private fun AverageSupplyTariff(averageSupplyTariffData: AverageSupplyTariff) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                ),
                fontFamily = FontFamily(
                    Font(
                        R.font.electrolize_regular,
                        FontWeight.Medium
                    )
                ),
                fontSize = 18.sp,
                text = "Average Supply Tariff :",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = 4.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 1.sp,
                    fontSize = 22.sp,
                    text = "₹",
                    style = MaterialTheme.typography.titleLarge
                )
                AnimatedTextValueFloat(
                    modifier = Modifier
                        .padding(
                            start = 1.dp,
                        ),
                    targetValue = averageSupplyTariffData.data.average_supply_tariff.average_tariff.toFloat(),
                    textSize = 24.sp,
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 1.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 1.sp,
                    fontSize = 18.sp,
                    text = " per kWh",
                    style = MaterialTheme.typography.titleLarge
                )
                Image(
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 5.dp),
                    contentDescription = null,
                    painter = painterResource(R.drawable.up_arrow)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    @Composable
    private fun HighestEvAdoption(salesData: TotalLastMonthSalesData) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                ),
                fontFamily = FontFamily(
                    Font(
                        R.font.electrolize_regular,
                        FontWeight.Medium
                    )
                ),
                fontSize = 18.sp,
                text = "Highest EV Adoption :",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                modifier = Modifier.padding(
                    start = 5.dp,
                    end = 1.dp
                ),
                fontFamily = FontFamily(
                    Font(
                        R.font.zcool,
                        FontWeight.ExtraBold
                    )
                ),
                letterSpacing = 1.sp,
                fontSize = 24.sp,
                text = "${
                    salesData.data.highest_ev_adoption_by_month.state.uppercase()
                } -",
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedTextValueFloat(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                        ),
                    targetValue = salesData.data.highest_ev_adoption_by_month.total_percentage_count.toFloat(),
                    textSize = 24.sp,
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 1.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 2.sp,
                    fontSize = 18.sp,
                    text = "%",
                    style = MaterialTheme.typography.titleLarge
                )
                Image(
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 5.dp),
                    contentDescription = null,
                    painter = painterResource(R.drawable.up_arrow)
                )
            }
        }
    }

    @Composable
    private fun TotalEvAdoption(evAdoptionRate: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                ),
                fontFamily = FontFamily(
                    Font(
                        R.font.electrolize_regular,
                        FontWeight.Medium
                    )
                ),
                fontSize = 18.sp,
                text = "EV Adoption :",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedTextValueFloat(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                        ),
                    targetValue = evAdoptionRate.toFloat(),
                    textSize = 24.sp,
                    textAlign = TextAlign.End
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 1.dp,
                        ),
                    fontFamily = FontFamily(
                        Font(
                            R.font.electrolize_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.End,
                    letterSpacing = 2.sp,
                    fontSize = 18.sp,
                    text = "%",
                    style = MaterialTheme.typography.titleLarge
                )
                Image(
                    modifier = Modifier
                        .size(18.dp)
                        .padding(start = 5.dp),
                    contentDescription = null,
                    painter = painterResource(R.drawable.up_arrow)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    @Composable
    private fun TotalPublicEvStations(totalChargeStationsData: TotalChargeStations) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    start = 15.dp,
                ),
                fontFamily = FontFamily(
                    Font(
                        R.font.electrolize_regular,
                        FontWeight.Medium
                    )
                ),
                fontSize = 18.sp,
                text = "Total Public EV Charge Stations :",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedTextValueInt(
                    modifier = Modifier
                        .padding(
                            start = 5.dp,
                        ),
                    targetValue = totalChargeStationsData.data.total_public_charging_stations.total_count,
                    textSize = 24.sp,
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    @Composable
    private fun MainActivity.TotalEvSales(salesData: TotalLastMonthSalesData) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontFamily = FontFamily(
                Font(
                    R.font.electrolize_regular,
                    FontWeight.W900
                )
            ),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            text = "Total EV Sales",
            style = MaterialTheme.typography.titleLarge
        )
        AnimatedTextValueInt(
            modifier = Modifier.fillMaxWidth(),
            targetValue = salesData.data.total_ev_sales_by_till_date.total_count,
            textSize = 42.sp,
            textAlign = TextAlign.Center,
        )
    }

    @Composable
    fun AnimatedTextValueFloat(
        modifier: Modifier = Modifier,
        textAlign: TextAlign = TextAlign.Start,
        targetValue: Float,
        textSize: TextUnit = 24.sp,
        animationDuration: Int = 2000
    ) {
        var animatedValue by remember { mutableFloatStateOf(0f) }

        LaunchedEffect(targetValue) {
            animate(
                initialValue = 0f,
                targetValue = targetValue,
                animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
            ) { value, _ ->
                animatedValue = value
            }
        }

        val formattedText = if (animatedValue % 1 == 0f) {
            animatedValue.toInt().toString()
        } else {
            String.format("%.2f", animatedValue)
        }

        Text(
            modifier = modifier,
            fontFamily = FontFamily(Font(R.font.zcool, FontWeight.ExtraBold)),
            textAlign = textAlign,
            letterSpacing = 2.sp,
            fontSize = textSize,
            text = formattedText, // Use the formatted text
            style = MaterialTheme.typography.titleLarge
        )
    }

    @Composable
    fun AnimatedTextValueInt(
        modifier: Modifier = Modifier,
        textAlign: TextAlign = TextAlign.Start,
        targetValue: Int,
        textSize: TextUnit = 24.sp,
        animationDuration: Int = 2000
    ) {
        var animatedValue by remember { mutableIntStateOf(0) }
        val formatter = DecimalFormat("#,##,###")

        LaunchedEffect(targetValue) {
            animate(
                initialValue = 0f,
                targetValue = targetValue.toFloat(),
                animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
            ) { value, _ ->
                animatedValue = value.toInt()
            }
        }

        Text(
            modifier = modifier,
            fontFamily = FontFamily(Font(R.font.zcool, FontWeight.ExtraBold)),
            textAlign = textAlign,
            letterSpacing = 2.sp,
            fontSize = textSize,
            text = formatter.format(animatedValue),
            style = MaterialTheme.typography.titleLarge
        )
    }

    @Composable
    fun DottedLine(offset: Offset = Offset(0f, 0f)) {
        val lineColor = MaterialTheme.colorScheme.onSurface
        Canvas(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .offset(x = offset.x.dp, y = offset.y.dp)
                .fillMaxWidth()
                .height(1.dp)
        ) {
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            drawLine(
                color = lineColor,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 2f,
                pathEffect = pathEffect
            )
        }
    }

    @Composable
    fun SlidingImage(selectedText: MutableState<String>) {
        val offsetX = remember { Animatable(0f) }
        val currentImage = remember { mutableStateOf(selectedText.value) }

        LaunchedEffect(selectedText.value) {
            offsetX.animateTo(-0f, animationSpec = tween(700))
            currentImage.value = selectedText.value
            offsetX.snapTo(9f)
            offsetX.animateTo(0.3f, animationSpec = tween(700))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset((offsetX.value * 150).toInt(), 0) }
        ) {
            val painter = when (currentImage.value) {
                "3 Wheelers" -> painterResource(R.drawable.auto)
                "Cars" -> painterResource(R.drawable.car)
                "2 Wheelers" -> painterResource(R.drawable.bike)
                else -> painterResource(R.drawable.bus)
            }
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .offset(y = 10.dp)
                    .scale(2.4f, 2.4f),
                contentDescription = null,
                painter = painter
            )
        }
    }


    @Composable
    fun PieChartSample(
        salesData: com.example.vehicle.model.lastMonthSales.Data,
        selected: (String) -> Unit = {},
        selectedValue: (Double) -> Unit = {},
        selectedText: MutableState<String>
    ) {
        var selectedPieValue by remember { mutableDoubleStateOf(((salesData.car_suv.sales_count.toDouble() / salesData.total_ev_sold.toDouble()) * 100) + 10) }
        var data by remember {
            mutableStateOf(
                listOf(
                    Pie(
                        label = "Cars",
                        data = ((salesData.car_suv.sales_count.toDouble() / salesData.total_ev_sold.toDouble()) * 100) + 8,
                        color = Color(0xFFFFCE56).copy(alpha = 0.5f),
                        selectedColor = Color(0xFFFFCE56),
                        selected = true
                    ),
                    Pie(
                        label = "2 Wheelers",
                        data = ((salesData.two_wheeler.sales_count.toDouble() / salesData.total_ev_sold.toDouble()) * 100) - 12,
                        color = Color(0xFFFF6384).copy(alpha = 0.5f),
                        selectedColor = Color(0xFFFF6384)
                    ),
                    Pie(
                        label = "3 Wheelers",
                        data = (((salesData.three_wheeler_g.sales_count + salesData.three_wheeler_p.sales_count).toDouble() / salesData.total_ev_sold.toDouble()) * 100) - 7,
                        color = Color(0xFF36A2EB).copy(alpha = 0.5f),
                        selectedColor = Color(0xFF36A2EB)
                    ),
                    Pie(
                        label = "Buses",
                        data = ((salesData.bus.sales_count.toDouble() / salesData.total_ev_sold.toDouble()) * 100) + 6,
                        color = Color(0xFF4BC0C0).copy(alpha = 0.5f),
                        selectedColor = Color(0xFF4BC0C0)
                    ),
                )
            )
        }

        LaunchedEffect(selectedText.value) {
            data = data.map { pie ->
                if (pie.label == selectedText.value) {
                    selectedPieValue = pie.data
                    pie.copy(selected = true)
                } else {
                    pie.copy(selected = false)
                }
            }
        }

        Box(
            modifier = Modifier
                .width(250.dp)
                .height(160.dp)
                .offset(x = (-35).dp),
            contentAlignment = Alignment.Center
        ) {
            val hapticFeedback = LocalHapticFeedback.current
            PieChart(
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp),
                data = data,
                onPieClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    selected(it.label.toString())
                    selectedPieValue = it.data
                    selectedValue(it.data)
                    println("${it.label} Clicked")
                    val pieIndex = data.indexOf(it)
                    data =
                        data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
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
                fontFamily = FontFamily(
                    Font(R.font.electrolize_regular, FontWeight.Normal)
                ),
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                fontSize = 16.sp,
                lineHeight = 25.sp,
                text = "${selectedText.value} \n ${String.format(Locale.US, "%.2f", selectedPieValue)}%",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(start = 3.dp)
                    .wrapContentSize()
                    .align(Alignment.Center)
            )
        }
    }


    @Composable
    fun LineChartSample(data: List<Data>) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 22.dp),
            data = remember {
                data.map { data ->
                    Line(
                        label = if (data.category == "3 Wheeler (Passenger)") "3 Wheeler" else data.category,
                        values = data.y.map { it.toDouble() / 1000 },
                        color = when (data.category) {
                            "2 Wheeler" -> SolidColor(Color(0xFFFF6384))
                            "3 Wheeler (Passenger)" -> SolidColor(Color(0xFF36A2EB))
                            "Bus" -> SolidColor(Color(0xFF4BC0C0))
                            "Car/SUV" -> SolidColor(Color(0xFFFFCE56))
                            else -> SolidColor(Color(0xFF9E9E9E))
                        },
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