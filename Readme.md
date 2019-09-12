### O projeto ORMLite_EXEMPLO é de autoria de Edvaldo Martins

#### ORMLite_EXEMPLO

##### [Android Kotlin - Trabalhando com base de dados usando ormlite (parte 1)](https://medium.com/@edvaldonuniomartins/android-kotlin-trabalhando-com-base-de-dados-usando-o-ormlite-parte-1-3fb30c7ae670)<br/>

##### [Android Kotlin e ORM Lite Exemplo](https://github.com/EdvaldoMartins/AndroidORMLiteExemplo)<br/>

O link acima teve apenas o seu arquivo gradle atualizado para que fosse executado adequadamente na versão 3.4 do Android Studio.

#### Os projetos CAPTUREPHOTO, KOTLINCONTACTLISTVIEW, KOTLINSELECTIMAGEGALLERY, são de autoria de Demonuts.

#### KOTLIN SELECT IMAGEM GALLERY
##### [Pick Image From Gallery Or Camera In Android Kotlin Programmatically](https://demonuts.com/pick-image-gallery-camera-android-kotlin/)<br/>

#### KOTLIN  CURRENT LOCATION
##### [Current Location Android Kotlin With Latitude Longitude Programmatically](https://demonuts.com/current-location-kotlin/)<br/>

#### KOTLIN CONTACT LIST VIEW
##### [Get Contact List In Android Kotlin And Show in Custom ListView Programmatically](https://demonuts.com/contact-list-kotlin/)<br/>

#### O projeto GETLOCATION é de autoria de kmvignesh.
##### [Location Example](https://github.com/kmvignesh/LocationExample/)<br/>

#### O projeto GETACCELEROMETERSENSOR é de autoria de Android Codility.
##### [Accelerometer Sensor](https://github.com/AndroidCodility/AccelerometerSensor/)<br/>

#### O projeto GET COMPASS é de autoria de Andreas Mausch e foi alterado para funcionar na API 15.
##### [Get Compass](https://andreas-mausch.de/blog/2017/05/14/compass-app-in-kotlin/)<br/>
##### [Andres Maush Github - GetCompass](https://github.com/andreas-mausch/compass/blob/master/app/src/main/java/de/neonew/compass/MainActivity.kt/)<br/>

#### O projeto ANDROID ROOM é de autoria de Luiz Marcus e foi alterado para funcionar na API 15 e com uma tentativa de inclusão de herança em data class.
##### [Persistindo Dados no Android com a Room](https://luizmarcus.com/android/persistindo-dados-no-android-com-a-room/)<br/>
##### [Luiz marcus Github - ExemploRoom](https://github.com/luizmarcus/Android/tree/master/ExemploRoom)<br/>

## Links Auxiliares

#### GET CURRENT LOCATION
##### [Get Current location android with kotlin - Android Teachers](https://androidteachers.com/kotlin-for-android/get-location-in-android-with-kotlin/)<br/>

#### GET CURRENT LOCATION
##### [Getting Current Location in Kotlin - Medium.com](https://medium.com/@manuaravindpta/getting-current-location-in-kotlin-30b437891781)<br/>

#### GET LOCATION ANDROID
##### [Get location android Kotlin - Stackoverflow](https://stackoverflow.com/questions/45958226/get-location-android-kotlin)<br/>

#### RETRIEVE A LIST OF CONTACTS
##### [Retrieve a list of contacts - Developer Google](https://developer.android.com/training/contacts-provider/retrieve-names)<br/>

#### ANDROID ACCELEROMETER
##### [Let’s play with the Android accelerometer + Kotlin](https://medium.com/@enzoftware/lets-play-with-the-android-accelerometer-kotlin-%EF%B8%8F-ed92981b0a6c)<br/>

#### ANDROID SENSORS
##### [Handling Sensors in Android Kotlin](https://medium.com/@nhkarthick/handling-sensors-in-android-kotlin-d728ddc20394)<br/>

#### ANDROID MOTION SENSORS
##### [How to Implement Motion Sensor in a Kotlin App](https://expertise.jetruby.com/how-to-implement-motion-sensor-in-a-kotlin-app-b70db1b5b8e5)<br/>

#### ANDROID SENSOR ACTIVITY
##### [Sensor Activity](https://gist.github.com/andriyadi/3960718e411c4dab449d3cba27615cea)<br/>

#### ANDROID KOTLIN COMPASS
##### [Android Kotlin Compass](https://github.com/catalinghita8/android-kotlin-compass)<br/>

#### ANDROID KOTLIN USAGE TUTORIAL - COMPASS WITH GPS LOCATION
##### [Android Kotlin Usage Tutorial #085 - Compass with GPS location, part 1](https://www.youtube.com/watch?v=CXgELb2gWI0)<br/>
##### [Android Kotlin Usage Tutorial #086 - Compass with GPS location, part 2](https://www.youtube.com/watch?v=972tRIzQ5iI)<br/>

#### NET CONNECT PHP (Referências)
##### [How to HTTP request by POST method with Kotlin](https://stackoverflow.com/questions/49188722/how-to-http-request-by-post-method-with-kotlin)<br/>
##### [Making a JSON POST Request With HttpURLConnection](https://www.baeldung.com/httpurlconnection-post)<br/>

## Example

	[Checkout the Net Connect PHP](https://github.com/phoenixproject/kotlindeveloper/tree/master/_NETCONNECTPHP) Basic example:

```php
	public function __construct() {
        
       $post = file_get_contents('php://input');
        
       if(isset($_POST['dados']))
       {
           $valor = filter_input(INPUT_POST, 'dados', FILTER_DEFAULT, FILTER_REQUIRE_ARRAY);           
           $this->retornaObjetoJson();
       }
       else{    
           $json = file_get_contents('http://www.codelogic.com.br/presidentslist.json');
           echo $json;
       }      
    }    
    
    public function retornaObjetoJson()
    {
        $animal = "Lion";
        echo json_encode($animal);
    }
```

#### GET MAC ADDRESS AND PHONE MODEL (Referências)
##### [How to finda MAC address of an Android device](https://stackoverflow.com/questions/10831578/how-to-find-mac-address-of-an-android-device-programmatically)<br/>
##### [How to get device model number](http://android--kotlin.blogspot.com/2019/02/how-to-get-get-device-model-number-android.html)<br/>