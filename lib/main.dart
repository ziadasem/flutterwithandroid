import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    
    return MaterialApp(
      title: 'Flutter with android',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home: const ExampleScreen(),
    );
  }
}
class ExampleScreen extends StatefulWidget {
  const ExampleScreen({super.key});

  @override
  State<ExampleScreen> createState() => _ExampleScreenState();
}

class _ExampleScreenState extends State<ExampleScreen> {
  final TextEditingController basicInputCtrl = TextEditingController();
  static const platform = MethodChannel('com.example.app/example');

  @override
  void initState() {
     platform.setMethodCallHandler(_receiveFromAndroidProject); //Method Call Handler Method Name
    super.initState();
  }

  int count = 0 ;
  @override
  Widget build(BuildContext context) {
    
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: const Text("Flutter with android"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            const SizedBox(height: 10,),
            const Text(
              'Send Text Data to Native Components',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
            ),
            Container(
              margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
              child: TextField(
                 decoration: InputDecoration(
                  hintText: 'Enter text',
                  filled: true,
                  fillColor: Colors.grey[200],
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10.0),
                    borderSide: BorderSide.none,
                  ),
                  contentPadding: const EdgeInsets.symmetric(vertical: 15.0, horizontal: 20.0),
                  ),
                controller: basicInputCtrl,
              ),
          ),
          // TextButton to check that no new flutter activity is created
          TextButton(onPressed: (){ 
            setState(() {
              count++;
            });
          }, child: Text(count.toString())),
           TextButton(onPressed: ()=> navigateToAndroidRoute("java"),
             child: const Text("Pass the data to Java Class through native channel")),
            TextButton(onPressed: ()=> navigateToAndroidRoute("kotlin"),
             child: const Text("Pass the data to Kotlin Class through native channel"))

          ],
        ),
      ),
      
    );
  }

  void navigateToAndroidRoute(String route) async{
    try {
      String result = await platform.invokeMethod('sendData', 
        {
          "route": route,
          "data": basicInputCtrl.text
        }
      );
      log("result is $result");
    } catch (e) {
      log(e.toString());
    }
     
  }

   Future<void> _receiveFromAndroidProject(MethodCall call) async {
    try {
      if (call.method == "receiveData") {
        final String data = call.arguments;
        setState(() {
          basicInputCtrl.text = data ;
        });
      }
    } catch (e) {
      log(e.toString());
    }
  }
}
