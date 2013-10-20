Hangman's resurrection
======================

*	[Overview](#overview)
	*	[Hangmen](#hangman)
*	[Technical](#tech)
	*	[Libraries and Frameworks](#libraries)
	*   [Installation](#instal)
	*   [Final release](#final)
*	[Design](#design)

<a id="overview"></a>Overview
-----------------------------

The original Hangman game, but with a little twist.

Project by: Matthijs Klijn
Studentnumber: 10447822
Mail: matthijsthoolen@hotmail.com

<a id="hangman"></a>Hangman
---------------------------

Almost everyone knows the original Hangman game, but this is not the original Hangman game! 

There will be 2 different modes: The Original mode and the Resurrection mode. 

### Original mode

The original mode will be exactly the same as the Hangman with a paper and a pencil. Some of the features:
	* Random words from the dictionary
	* Local scorebore
	* Novice level: Short (and maybe easy) words and 12 steps to complete the gallow
	* Intermediate level: Normal words and 9 steps to complete the gallow
	* Advanced level: Long and difficult words and only 6 steps to complete the gallow
	* Expert level: A selection of the hardest words available for Hangman, and you only hang in 3 times...
	* Custom level: A custom level where you can select the length of the word and your lives/ tries.
	
### Arcade mode

The arcade mode will be a totally new experience for Hangman! Expect what you don't expect! Some of the features:

	* Every feature of the original mode is in the arcade mode! But there will be more:
	* Time limit, depending on the chosen level you will have a time limit for each guess. If you exceed the limit,
		you have a problem (depending on the level you will lose points, a body part will be drawed or it's game over...)
	* Random occurrences (this includes random people who will try to save you by destroying (parts) of the gallow, but you
		to help them first! For example you can help them by answering a question. There will be more according to 
		the time left before the deadline). 
	* There will be a Legendary level, when you guess a wrong letter or you exceed the time limit, the word will change!
	
<a id="tech"></a> Technical
===========================

<a id="libraries"></a> Libraries and Frameworks
-----------------------------------------------

I will use the [AndEngine] [1] android framework. This framework is free to use and the sample games look great for my Hangman project. 
And there is a book available with more samples and tutorials, so that will be a good starting point. At this moment I think I don't need more
frameworks or libraries, but maybe I will change my mind later on.

[1]: http://www.andengine.org/

<a id="instal"></a> Installation
-----------------------------------------------

1. clone https://github.com/Eragon666/hangman.git
2. Open project in your favorite IDE as a project with existing sources
3. Run application

Note for beta: I was struggling for to long with the xml parser to parse a file from the res path. So I couldn't finish the database in time for the beta.
But almost all features are in the app already, only the scoreboard isn't working and there is only one word (so its a pretty boring game...). But for the final release it
will be done ofcourse.

<a id="final"></a> Final release
-----------------------------------------------

In the final release I changed the design from the hangman app, I used photoshop to create nice states for the Gallow. And I made a new launcher background and a icon for the app.
And I totally rewrite the HangmanDraw class to support the new files and to make it faster.

But because I tested it on my mobile most of the time, I didn't noticed the huge bug on the emulator. The design works on all MDPI and HDPI devices, but on the emulator the CPU is to slow for the XmlPullParser
and the game to work at the same time (XmlPullParser is in a async task). I used the aSync task to add the words from the XML file in the SQlite database, and in the meantime it was still possible to play the game
and use the DB (I made a simple queue for the database). I already made all the resources much smaller, but that didn't solve the problem.

Because I discovered this problem to late, I couldn't find a proper fix. So I choose to only add words to the database while the user is at the launcher screen. But this means that not all the words are
added to the database. But you can still enable this feature, go to Launcher.java and disable row 185.

<a id="tech"></a> Design
===========================

The design images are very simple sketches from the final design. But the main idea will be clear from the sketches. There will be 3 important screens:

The main menu: <br>
![Main menu](/doc/main.jpg)

The level menu: <br>
![Level menu](/doc/level.jpg)

The game: <br>
![Game screen](/doc/game.jpg)

Scoreboard: <br>
![Scoreboard](/doc/scoreboard.jpg)

Gameover: <br>
![Gameover](/doc/gameover.jpg)

Won: <br>
![Gameover](/doc/won.jpg)

