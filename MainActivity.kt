package com.example.assignment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.example.assignment.model.QuoteViewModel
import com.example.assignment.model.UserDetails
import com.example.assignment.page.DashboardScreen
//import com.example.assignment.page.QuoteScreen
//import com.example.assignment.page.MainBottomBar
import com.example.assignment.ui.theme.AssignmentTheme
import com.example.assignment.utils.DataStoreManger
import com.example.assignment.utils.DataStoreManger.Companion.EMAIL
import com.example.assignment.utils.preferenceDataStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            splashScreen.setKeepOnScreenCondition { false }
        }
        setContent {
            AssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val dataStoreContext = LocalContext.current
                    val dataStoreManger = DataStoreManger(dataStoreContext)

                    AppContent(
                        this@MainActivity,
                        preferenceDataStore,
                        dataStoreManger
                    )

                }
            }
        }
    }
}


@Composable
fun AppContent(
    registerScreen: MainActivity,
    preferenceDataStore: DataStore<Preferences>,
    dataStoreManger: DataStoreManger
) {

    var isRegistered by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val onRegisterSuccess = { isRegistered = true }
    val onLogout = {
        isRegistered = false
        scope.launch {
            dataStoreManger.clearDataStore()
        }
    }

    //lets check if user is registered in when the app start
    LaunchedEffect(key1 = Unit) {
        checkRegisterState(preferenceDataStore) { it ->
            isRegistered = it
        }
    }

    if (isRegistered) {
        HomePage(
            onLogout = onLogout, onNavigateToProfile = {},
            onNavigateToSettings = {}, dataStoreManger)
    } else {
        RegisterPageUI(onRegisterSuccess, dataStoreManger)
    }

}

suspend fun checkRegisterState(
    preferenceDataStore: DataStore<Preferences>,
    onResult: (Boolean) -> Unit
) {
    val preferences = preferenceDataStore.data.first()
    val email = preferences[EMAIL]
    val isRegistered = email != null
    onResult(isRegistered)
}

@Composable
fun RegisterPageUI(onRegisterSuccess: () -> Unit, dataStoreManger: DataStoreManger) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()

    // Create focus requesters for each TextField
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4= remember { FocusRequester() }

    // Get the current focus manager
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .background(colorResource(R.color.mainColor))
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Main card Content for Register
        ElevatedCard(
            modifier = Modifier

                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Add verticalScroll modifier here

        ) {
            Box{
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(700.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp)

                ) {

                    // Heading Jetpack Compose
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp

                    )

                    // Register Logo
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp)
                            .padding(top = 8.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data = R.drawable.img2).crossfade(enable = true).scale(Scale.FILL)
                            .build(),
                        contentDescription = stringResource(id = R.string.app_name)
                    )


                    Text(
                        text = "Create Account",
                        fontSize = 25.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Please fill in your details below.",
                        fontSize = 16.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(top = 8.dp,start = 16.dp)
                    )

                    // Name Field
                    OutlinedTextField(
                        value = name, // Add a variable for name
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next // Set imeAction to Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequester2.requestFocus() // Move to next TextField
                            }
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester1)
                    )

                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        label = { Text("Mobile Number") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next // Set imeAction to Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequester3.requestFocus() // Move to next TextField
                            }
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester2)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next // Set imeAction to Done
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusRequester4.requestFocus() // Move to next TextField
                            }
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth().focusRequester(focusRequester3)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done // Set imeAction to Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus() // Hide the keyboard
                            }
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester4)
                    )
                    Button(
                        onClick = {
                            if (name.isEmpty()) {
                                Toast.makeText(mContext, "Name is Empty", Toast.LENGTH_SHORT).show()
                            } else if (mobileNumber.isEmpty()) {
                                Toast.makeText(mContext, "Mobile No. is Empty", Toast.LENGTH_SHORT).show()
                            } else if (email.isEmpty()) {
                                Toast.makeText(mContext, "Email is Empty", Toast.LENGTH_SHORT).show()
                            } else if (password.isEmpty()) {
                                Toast.makeText(mContext, "Password is Empty", Toast.LENGTH_SHORT).show()
                            } else {
                                //Submit you data
                                scope.launch {
                                    dataStoreManger.saveToDataStore(
                                        UserDetails(
                                            emailAddress = email,
                                            name = name,
                                            mobileNumber = mobileNumber
                                        )
                                    )
                                    onRegisterSuccess()
                                }
                            }

                        }, modifier = Modifier.padding(16.dp).fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.orange),contentColor = Color.White),

                        ) {
                        Text("Submit", fontSize = 20.sp)
                    }

                }
            }
        }


    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    onLogout: () -> Job, onNavigateToProfile: () -> Unit,
    onNavigateToSettings: () -> Unit, dataStoreManger: DataStoreManger
) {
    // Set system bar color to black
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Black,
            darkIcons = useDarkIcons
        )
    }

    var expanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.darkBlue)
            ),
            title = { Text("Quotes App", color = Color.White) },
            actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("Settings") },
                        onClick = {
                            expanded = false
                            //onNavigateToSettings()
                            showDialog = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            expanded = false
                            showLogoutDialog = true

                        }
                    )
                }
            }

        )

    }, bottomBar = {
       // MainBottomBar()
    }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.mainColor))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val userDetails by dataStoreManger.getFromDataStore().collectAsState(initial = null)
            AnimatedVisibility(visible = true, enter = fadeIn() + slideInVertically()) {}

//            Text(
//                text = "Hi, ${"\nWelcome to the Home Page "}",
//                style = MaterialTheme.typography.headlineMedium,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
//            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.padding(top = 8.dp))
             Row {
                 Text(
                     text = "Welcome Back , ",
                     color = Color.White,
                     fontSize = 15.sp,
                     textAlign = TextAlign.Center,
                     style = MaterialTheme.typography.headlineMedium,
                     lineHeight = 20.sp
                 )

                 Text(
                     text = userDetails?.name ?: "",
                     color = Color.White,
                     fontSize = 15.sp,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Center,
                     style = MaterialTheme.typography.headlineMedium,
                     lineHeight = 20.sp
                 )
             }

//                Text(
//                    text = "Mobile: ${userDetails?.mobileNumber ?: ""}",
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.headlineSmall
//                )
//
//                Text(
//                    text = "Email Id: ${userDetails?.emailAddress ?: ""}",
//                    style = MaterialTheme.typography.headlineSmall
//                )

                Spacer(modifier = Modifier.height(32.dp))

                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
//                        Image(
//                            painter = painterResource(R.drawable.img1),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .padding(horizontal = 16.dp)
//                                .fillMaxWidth()
//                        )


                        val quoteViewModel: QuoteViewModel = viewModel()
                        DashboardScreen(viewModel = quoteViewModel)
                    }
                }
            }
        }
    }


if (showLogoutDialog) {
    AlertDialog(
        onDismissRequest = { showLogoutDialog = false },
        title = { Text("Confirm Logout") },
        text = { Text("Are you sure you want to logout?") },
        confirmButton = {
            TextButton(onClick = {
                showLogoutDialog = false
                onLogout()
            }) {
                Text("Logout")
            }
        },
        dismissButton = {
            TextButton(onClick = { showLogoutDialog = false }) {
                Text("Cancel")
            }
        }
    )
}

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                   // Spacer(modifier = Modifier.height(24.dp))
                    Image(
                        painter = painterResource(id = R.drawable.robokalam),
                        contentDescription = "Robokalam Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    listOf(
                        "About Us" to "ROBOKALAM is a 21st Century Edutech company, focusing on gamified education and latest technologies like AI, IoT, Coding, Robotics.",
                        "Mission" to "To promote experiential gamified education through workshops, trainings and innovation challenges worldwide.",
                        "Vision" to "Act as a mediator between technology and students to empower young minds through quality education."
                    ).forEach { (title, content) ->
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 4.dp)
                        )
                        Text(
                            text = content,
                            maxLines = 15,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    androidx.compose.material3.Button(
                        onClick = { showDialog = false },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }

}









