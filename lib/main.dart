import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter with android',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.indigo),
        useMaterial3: true,
      ),
      home:  ExampleScreen(),
    );
  }
}
class ExampleScreen extends StatelessWidget {
  ExampleScreen({super.key});
  final TextEditingController basicInputCtrl = TextEditingController();
   static const platform = MethodChannel('com.example.app/example');

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
      await platform.invokeMethod('sendData', 
        {
          "route": route,
          "data": basicInputCtrl.text
        }
      );
    } catch (e) {
      log(e.toString());
    }
  }

}
