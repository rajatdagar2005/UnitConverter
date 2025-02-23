package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.time.format.TextStyle
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {  //SPECIAL FUNCTION  ctrl+click to open
                // A surface container using the 'background' color from the theme
                Surface( // background of application
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

// creating our own composables
@Composable
fun UnitConverter(){
    var inputvalue by remember { mutableStateOf("") } //should be var here not val
    var outputvalue by remember { mutableStateOf("") }
    var inputunit by remember { mutableStateOf("meters") }
    var outputunit by remember { mutableStateOf("meters") }
    var iexpanded by remember { mutableStateOf(false) }
    var oexpanded by remember { mutableStateOf(false) }
    val conversionfactor = remember { mutableStateOf(1.00) }
    val oconversionfactor = remember { mutableStateOf(1.00) }

    // to add text style more precise and better you can make function for it
//    val customTextStyle = TextStyle(
//        fontFamily = FontFamily.Monospace,
//        fontSize = 32.sp,
//        color = color.Red
//    )

    fun convertunits(){
        val inputvaluedouble = inputvalue.toDoubleOrNull() ?:0.0 // converting this to a data type double
        // or give NULL
        // if input value is double then double else 0.0
        val result = (inputvaluedouble * conversionfactor.value*100/oconversionfactor.value).roundToInt() / 100.00
        outputvalue = result.toString()
    }
    // modifiers are added inside curly braces of column
    // it will help us change orientation of our app screen
    // alignment is changed using these things
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        // here all the UI elements will be stacked below each other
        Text("Unit Converter",style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp)) // adds space b/w unit converter and outlined text space horizontally as used in column
        OutlinedTextField(value = inputvalue, onValueChange ={   // basically taking text input from user
            inputvalue = it  // this it will get value you entered it's of type string you entered
            convertunits() // harbar kuch input par result change ho isliye
        }, label = {Text(text = "enter value")}) // label just like default text on this
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            // a box is a layout element that works as a row and column . it works diffrently in that it arranges
            // and stack composables on top of each other .it is commoly used to create custom and complex layouts
            // because drop down menu can't stand on its own as it requires a parent,which can help it position
            // itself on screen .
            // INPUT BOX AND INPUT BUTTON
            Box {
                Button(onClick = { iexpanded = true }) {
                    Text(text = inputunit) // what you want on button
                    Icon(Icons.Default.ArrowDropDown,contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = iexpanded, onDismissRequest = { iexpanded=false }) {
                    // make expanded = "true" if you want to see it
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        inputunit="Centimeters"
                        iexpanded = false
                        conversionfactor.value = 0.01
                        convertunits()
                    })
                    // text inside dropdown menu is itself a composable so you have entered datatype
                    // of composable you want
                    DropdownMenuItem(text = { Text(text = "meters") }, onClick = {
                        inputunit="meters"
                        iexpanded = false
                        conversionfactor.value = 1.0 // WE ARE TAKING METERE AS BASE
                        convertunits()
                    })
                    DropdownMenuItem(text = { Text(text = "feet") }, onClick = {
                        inputunit="feet"
                        iexpanded = false
                        conversionfactor.value = 0.3048 // WE ARE TAKING METERE AS BASE
                        convertunits()
                    })
                    DropdownMenuItem(text = { Text(text = "milimetres") }, onClick = {
                        inputunit="milimeters"
                        iexpanded = false
                        conversionfactor.value =  0.001 // WE ARE TAKING METERE AS BASE
                        convertunits()
                    })
                }
            }
            // to add space/horizontal space between 2 box 
            Spacer(modifier = Modifier.width(16.dp))
            // OUTPUT BOX AND OUTPUT BUTTON
            Box {
                Button(onClick = { oexpanded = true }) {
                    Text(text = outputunit)
                    Icon(Icons.Default.ArrowDropDown,contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oexpanded, onDismissRequest = { oexpanded=false }) {
                    // make expanded = "true" if you want to see it
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        outputunit="Centimeters"
                        oexpanded = false
                        oconversionfactor.value =  0.01
                        convertunits()
                    })
                    // text inside dropdown menu is itself a composable so you have entered datatype
                    // of composable you want
                    DropdownMenuItem(text = { Text(text = "meters") }, onClick = {
                        outputunit="meters"
                        oexpanded = false
                        oconversionfactor.value =  1.00
                        convertunits()
                    })
                    DropdownMenuItem(text = { Text(text = "feet") }, onClick = {
                        outputunit="feet"
                        oexpanded = false
                        oconversionfactor.value =  0.3048
                        convertunits()
                    })
                    DropdownMenuItem(text = { Text(text = "milimetres") }, onClick = {
                        outputunit="milimeters"
                        oexpanded = false
                        oconversionfactor.value =  0.001
                        convertunits()
                    })
                }
            }
            //val context = LocalContext.current //used in button for pop up
        // here all the UI elements will be stacked next to each other
            // in button we have used toast.maketext in order to show some text when we click and .show is used so that we can see it
            // length.long is used as how much duration a text should be displayed
            // its basically a pop up that appears because of this
//            Button(onClick = { Toast.makeText(context,"thanks for clicking!",Toast.LENGTH_LONG).show() })
//            {
//                Text(text = "click me!")
//            }
        }
        // used some style in text
        Text(text = "Result:$outputvalue $outputunit", style = MaterialTheme.typography.headlineMedium)
        // style = customtextstyles in text field to use desired function for text field
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
//creating our own preview
// it is like a user interface but we don't have to open app again again to see changes in app
@Preview(showBackground=true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}