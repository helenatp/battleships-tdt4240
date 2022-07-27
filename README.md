# Battleships<br/>

This project was created inline with the cource TDT4240 - Software Architecture, where we develop a mulitplayer game focusing on using methods and patterns from the course. 

<img width="749" alt="Skjermbilde 2021-04-26 kl  15 26 05" src="https://user-images.githubusercontent.com/69898083/116090023-ba469500-a6a3-11eb-9284-13ed0016c325.png">

## 1 Installing project <br/>
### 1.1 Getting started <br/>
To run the application you need to install [Android Studio](https://developer.android.com/studio/?gclid=CjwKCAjwv4_1BRAhEiwAtMDLsozFvTbQdD1gDDKtKmX58Udj2DvLeoF2hyhyzdcBArlbnsQCdAUq6RoCfMYQAvD_BwE&gclsrc=aw.ds).
The project is andorid speciefied so you need two android emulators to run the multiplayer game, and one emulator for single player. If you don't have an android device Android Studio comes with the possibility to run the game on an emulator (see section 1.3) <br/>

### 1.2 Cloning the project </br>

 After downloading Android Studio you can clone the project. This is done by entering the termnial and then navigate into the folder of your choosing.  Clone gitLab repo with SSH/HTTP key:
 ```bash
  git clone https://gitlab.stud.idi.ntnu.no/progark_25/battleships.git
```

Now you can open Andorid studios -  Open an existing Anroid studio Project - Choose the cloned project.

<div class="text-blue mb-2">
 Wait for Gradle Build to finish.
</div>

### 1.3 Running the project <br>

You can either run on an andoid device or on the emulator in Andorid Studio. You can set up two emulators on your computer, or have one on the computer and run the game simontaniously on your android phone. The app runs faster on your phone, so we reccomend this if you have an android phone. To play multiplayer and see the highscore list, which is online, you need to be connected to internet. To play the multiplayer mode you have to use two devices or emulators. <br/>

#### Andorid Device

Running on Android device: Enable needed permissions for your phone, see the documentation [here](https://developer.android.com/training/basics/firstapp/running-app). Connect your device to your computer with USB, and select your android device in “Connected devices”.

#### Andorid Emulator
Click on “Tools” in Android Studio and open the AVD manager to add a new virtual device. We recommend using Pixel 3a. When selecting system image make sure ABI is set to x86. When verifying configurations make sure startup orientation is set to landscape and set graphics to “Software - GLES 2.0”.

You should have the latest Android Emulator version to make your emulator run faster, we reccommend version 30.5.4

We reccomend to set up this virtual device: <br/>
```bash
Name: Pixel 3a 
Size: 5,6 
Resolution: 1080x2220 
Density: 440dpi  
```
Settings:

<img width="500" alt="Skjermbilde 2021-04-26 kl  14 56 53" src="https://user-images.githubusercontent.com/69898083/116086086-bd3f8680-a69f-11eb-908d-f26367c43645.png">



# 2 Technology and Frameworks
* [Android Studio](https://developer.android.com/studio/?gclid=CjwKCAjwv4_1BRAhEiwAtMDLsozFvTbQdD1gDDKtKmX58Udj2DvLeoF2hyhyzdcBArlbnsQCdAUq6RoCfMYQAvD_BwE&gclsrc=aw.ds)
* [LibGDX](https://libgdx.com/)
* [Firebase](https://firebase.google.com/)

# 3 Demo / user manual
Battleships is one of the most famous games of all time! The game is originally played by two people, and the goal is to destroy the opponent’s fleet of ships.

The user will have the opportunity to play either a turn based multiplayer or single player. Each player has a ruled grid board that contains 10 x 10 squares. They also have six different ships occupying a different number of squares. In the singleplayer the goal is to have as few trys as possible to sink all the ships. When playing against an opponent you must sink all their ships before they sink yours! 

Good luck on getting on the Scoreboard!

<img width="546" alt="Skjermbilde 2021-04-26 kl  16 20 31" src="https://user-images.githubusercontent.com/69898083/116098392-545e0b80-a6ab-11eb-8315-0edeb0de901f.png">

Here you can choose by watching the tutorial or start playing the game. 

Play game will take you here:

<img width="540" alt="Skjermbilde 2021-04-26 kl  16 21 11" src="https://user-images.githubusercontent.com/69898083/116098498-6c358f80-a6ab-11eb-8c07-86ad9b479b57.png">

After choosing mulitplayer or single player you will have to wirte in a username. 

Before the game begins each player arranges their ships on their board. 

<img width="535" alt="Skjermbilde 2021-04-26 kl  16 21 48" src="https://user-images.githubusercontent.com/69898083/116098618-82435000-a6ab-11eb-824b-bccd327a0499.png">

Here you are playing against an opponent. 

The view shows the opponent’s turn and they are trying to hit your ships. 

<img width="546" alt="Skjermbilde 2021-04-26 kl  16 22 20" src="https://user-images.githubusercontent.com/69898083/116098723-95eeb680-a6ab-11eb-9fea-09ef04a0a321.png">

The scoreboard:

<img width="546" alt="Skjermbilde 2021-04-26 kl  16 22 52" src="https://user-images.githubusercontent.com/69898083/116098830-a99a1d00-a6ab-11eb-8c32-2a227cac25fa.png">



# Developers
* Ane Dyveke Blaauw
* Anne Skjærseth
* Benedicte Hansen
* Helena Phan
* Isabel Slorer
* Live Melkild







