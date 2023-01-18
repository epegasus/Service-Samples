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
	-> Bound Service 	(Binded with an application component)
	
	Bound service can be binded with multiple components. It will automatically be get killed when all components unbind it.
	A bound service runs only as long as another application component is bound to it.
	It can be binded with already running service. (music player {current song}) and stopSelf won't work, untill unbind.
	
    IntentService 	
    	-> Extent to base class
	-> It will not work well starting with Android 8 Oreo, due to the introduction of Background execution limits.
	-> This class was deprecated in API level 30.
	-> Use `JobIntentService` instead
	
    JobIntentService
    	-> Also deprecated, use work Manager.
				
	
#### Choosing b/w thread or service?	
	
#### NOTE (Work Manager)
	In many cases, using WorkManager is preferable to using foreground services directly.	
	
### Steps
 
#### Step # 1 -> Add <service> tag in Manifest
	
	<manifest ... >
  		...
 	 	<application ... >
			<service android:name=".ExampleService" />
			...
		</application>
	</manifest>
 
#### Step # 2 -> Extend class with Service
	
	class HelloService : Service() {
		...
	}
	
#### Step # 3 -> Life Cycles
	
 	1) onStartCommand() 
		This method will be triggered when 'startService()' is called. To top a service, call `stopSelf()` or `stopService()`
	2) onBind()
		This method will be triggered when 'bindService()' is called. It will stop service, once all components are unbouned. 
	 	You must always implement this method `IBinder`, however, if you don't want to allow binding, you should return null.
	3) onCreate()
		This method will be triggered when the service is initially created (before it calls either onStartCommand() or onBind()).
		If the service is already running, this method is not called.
	4) onDestroy()
		The system invokes this method when the service is no longer used and is being destroyed. Your service should implement 
		this to clean up any resources such as threads, registered listeners, or receivers. This is the last call that the service receives.
	
### Service get auto-stops 
	-> Low memory
	-> Cannot get killed if user is interacting to it's binded component
	-> Foreground services are rarely get killed.
	
#### How to save?
	There are 3 return types in `onStartCommand()`
	1) START_STICKY:		(Will restart with null intent)
	2) START_NOT_STICKY:		(Will not restart)   e.g. for alarm closing
	3) START_REDELIVER_INTENT:	(Will restart with previous intent)
	
### WARNING
	After you publish your application, leave this name unchanged to avoid the risk of 
	breaking code due to dependence on explicit intents to start or bind the service.
		
`startForeground()`	
		This method creates a background service, but the method signals to the system 
		that the service will promote itself to the foreground. Once the service has been created, 
		the service must call its startForeground() method within five seconds. (OTHERWISE ANR)

`startService()`	
		The startService() method returns immediately, and the Android system 
		calls the service's onStartCommand() method. If the service isn't already running, 
		the system first calls onCreate(), and then it calls onStartCommand().
	
Result from Service
		Client that starts the service can create a PendingIntent for a broadcast (with getBroadcast()) 
		and deliver it to the service in the Intent that starts the service.
			
### LifeCycle
	![image](https://user-images.githubusercontent.com/100923337/213107630-204ba81a-98b4-45de-9d25-3fbc36e51084.png)

