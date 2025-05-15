package com.example.assignment.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment.R
import com.example.assignment.model.QuoteViewModel

@Composable
fun DashboardScreen(viewModel: QuoteViewModel = viewModel()) {
    val quote = viewModel.quote
    val author = viewModel.author

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {


        Spacer(Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) { Text("Today's Quote.", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 95.dp))
                    Spacer(Modifier.height(16.dp))

                    Text(

                        text = "❝ $quote ❞",
                        fontStyle = FontStyle.Italic,
                        fontSize = 18.sp,
                        color = Color.White,
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.5f)).padding(8.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "- $author",
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(8.dp),
                        color = Color.White
                    )
                }
            }
        }

    }
}

//@Composable
//fun NavigationCard(label: String, onClick: () -> Unit) {
//    Card(
//        shape = RoundedCornerShape(10.dp),
//        elevation = CardDefaults.cardElevation(6.dp),
//        modifier = Modifier
//            .padding(4.dp)
//            .width(100.dp)
//            .clickable { onClick() }
//    ) {
//        Box(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = label, textAlign = TextAlign.Center)
//        }
//    }
//}
