##GCM Sample Client and Server

sample code for [Google Cloud Messaging](https://developers.google.com/cloud-messaging/) for Android. The client is an Android app and the server is a [PostMan](https://www.getpostman.com/) Tool

##Setup Google Cloud Messaging
At first, you need go to [google developer console](https://console.developers.google.com) setup a project and enable Google Cloud Messaging API.
And then create a server key, so you have two data.

1. gcm sender ID (google developer console project ID)
2. gcm server key

##Client
The Android App can help you direct get push token.

1. put sender ID in editText
2. click `取得tokeb` button, and then token will show in the screen
3. click `mail` button send to your email

![Alt text](resources/gcmtool.png)

##Server
Use [PostMan](https://www.getpostman.com/) and import `gcm-push.json.postman_coll`. [Download here](https://drive.google.com/file/d/0B4lG-zFPhFZ0bG5tTE0ycXItRzg/view?usp=sharing)


######HTTP HEAD
```java
Content-Type:application/json
Authorization:key=your gcm server key
```

######HTTP BODY
```java
{
  "to": "your Android devive push token",
  "data": {
    "message": "your message",
   }
}
```


#####HTTP POST Request Sample

Use `Token` push
```java
https://gcm-http.googleapis.com/gcm/send
Content-Type:application/json
Authorization:key=AIzaSyC1bjpdS61qAQOAzDt281VZn8XTBdGQa3A

{
  "to": "cHni_q-c6FQ:APA91bFXeCryQYvToMCMyCAHZuQJTK-yEK5a81FNqwIc6t40RCa54HBdxi-5g5shcWNNUmIxF3HaDl6IEl33XL1GBG1EgQFhzAUEcI5VNPO6hB72UJwrJ_I7O9JrTz_uR8qugbp5NBpn",
  "data": {
    "message": "推播token",
   }
}
 ```

Use `Topics` push
```java
https://gcm-http.googleapis.com/gcm/send
Content-Type:application/json
Authorization:key=AIzaSyC1bjpdS61qAQOAzDt281VZn8XTBdGQa3A

{
  "to": "/topics/foo-bar",
  "data": {
    "message": "推播topics",
   }
}
 ```
