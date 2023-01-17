# Service-Samples

### DATE: 17-JAN-2023

#### Defination:
    -> A Service is an `application component` that can perform long-running operations in the background. 
    -> Additionally, a component can bind to a service to interact with it and even perform interprocess communication (IPC). 
    
    Inter-Process Communication or IPC in short, is a mechanism that allows multiple independent processes to communicate and exchange data.

#### Examples:
	1) Network Transactions
	2) Play Music
	3) Perform I/O operations
	4) Interact with content providers

#### NOTE:	
	Service runs on 'Main Thread', avoid thread-blocking operations else it will produce ANR's.

### ANRs
	ANR's are a problem because the app's main thread, can't process user input events or draw.
	-> Input dispatching timed out:
			* 5-sec time out
	-> Executing service
			* If a service declared by your app cannot finish executing Service.onCreate() 
	   		  and Service.onStartCommand()/Service.onBind() within a few seconds.
	-> Service.startForeground() not called
			* If your app uses Context.startForegroundService() to start a new service in the foreground, 
			  but the service then does not call startForeground() within 5 seconds.
	-> Broadcast of intent
			* If a BroadcastReceiver hasn't finished executing within a set amount of time.
			  If the app has any activity in the foreground, this timeout is 5 seconds.
			  
### Enable background ANR dialogs
	Android shows ANR dialogs for apps that take too long to process the broadcast message only 
	if Show all ANRs is enabled in the device’s Developer options. For this reason, background ANR 
	dialogs are not always displayed to the user, but the app could still be experiencing performance issues.
	
	
### Types of Services
	-> Foreground Service	(Noticeable to the user) e.g. Audio Player
	-> Background Service	(Hidden) e.g. Cleaning Storage
	-> Bound Service 		(Binded with an application component)
	
	Bound service can be binded with multiple components. It will automatically be get killed when all components unbind it.
	A bound service runs only as long as another application component is bound to it.
	
#### Choosing b/w thread or service?	
	
#### NOTE (Work Manager)
	In many cases, using WorkManager is preferable to using foreground services directly.	
	
### Steps
 
#### Step # 1
	Add <service> tag in Manifest
	
	```
	<manifest ... >
  		...
 	 	<application ... >
			<service android:name=".ExampleService" />
			...
		</application>
	</manifest>
	```
 
#### Step # 2
	Extend class with Service
	
	```
	class HelloService : Service() {
		...
	}
	```
	
 	1) onStartCommand()
	2) onBind()
	3) onCreate()
	4) onDestroy()
	
	
	
	
	
	
