Hangman's resurrection Design Doc
=================================

*	[Technical specifications](#tech)
	*	[Database](#db)
	*	[Classes and functions](#classes)
*	[Style Guide](#style)
	*	[Java code](#java)
	*   [SQL (lite)](#sql)
*	[Design](#design)

<a id="tech"></a>Technical specifications
=========================================

<a id="db"></a>Databases
------------------------
There will be three different databases. There will be a database for the scorebord, a database for special words and a database with the X last used words. 

<table>
	<tr>
		<th>Scoreboard</th>
	</tr>
	<tr>
		<th>Column name</th>	<th>Column type</th>		<th>Description</th>
	<tr>
		<td>score_id</td>		<td>int(8)</td>			<td></td>
	</tr>
	<tr>
		<td>user</td>			<td>varchar(256)</td>		<td>Name of the user</td>
	</tr>
	<tr>
		<td>score</td>			<td>smallint(5)</td>		<td>Score of the user.</td>
	</tr>
	<tr>
		<td>level</td>			<td>tinyint(1)</td>			<td>Int depening on the difficulty and arcade or original</td>
	</tr>
	<tr>
		<td>lives</td>			<td>tinyint(2)</td>			<td>Lives left (tries)</td>
	</tr>
	<tr>
		<td>date</td>			<td>datetime</td>			<td>Date of game at finish</td>
	</tr>
</table>

The special words table will be used for a special mode where I only want to use a few very difficult words.

<table>
	<tr>
		<th>Special words</th>
	</tr>
	<tr>
		<th>Column name</th>	<th>Column type</th>		<th>Description</th>
	</tr>
	<tr>
		<td>word_id</td>		<td>int(8)</td>				<td></td>
	</tr>
	<tr>
		<td>word</td>			<td>varchar(64)</td>		<td>The word</td>
	</tr>
	<tr>
		<td>length</td>			<td>int(2)</td>				<td>Length of the word</td>
	</tr>
	<tr>
		<td>difficulty</td>		<td>int(1)</td>				<td>The difficulty of the word</td>
	</tr>
</table>

Although there are thousands of words available in the dictionary, it is always possible that the random generator chooses the same word as used in
one of the last turns. To prevent that I have a database with the last used words, if the choosen word is already in the database we will search for
another word until we have a new word.

<table>
	<tr>
		<th>History</th>
	</tr>
	<tr>
		<th>Column name</th>	<th>Column type</th>		<th>Description</th>
	</tr>
	<tr>
		<td>history_id</td>		<td>int(8)</td>				<td></td>
	</tr>
	<tr>
		<td>word</td>			<td>int(64)</td>			<td>The used word</td>
	</tr>
	<tr>
		<td>last_used</td>		<td>datetime</td>			<td></td>
	</tr>
</table>	


<a id="classes"></a>Classes and functions
-----------------------------------------

For Hangman I will use classes and a few (smaller) functions to assist those classes. I will only describe the most 
important classes and functions, some small functions (for example a function to change the date from US to EU format) 
wont be described here. 

Class: Hangman
--------------
<i>Input: difficulty, length, lifes</i>
The hangman class will keep track of the game and keep all the variables like score, lifes etc. This will be one of the only
classes I will 'speak' to directly. The other classes will be started from within this class. 

<b>Method: Hangman</b>
<i>Private method</i>
Set the static vars which we will use in the hangman game. These vars are given in the initialization of the class or can be 
derived from the given variables. The word will be generated with the special class for generating. 

<b>Method: checkLetterGuess</b>
<i>Input: letter
Output: boolean</i>
Check the users guess if its correct and call all the other necessary methods for handling the guess

<b>Method: checkWordGuess</b>
<i>Input: word
Output: Boolean</i>
Check if the user guessed the correct word and start all the other necessary methods.

<b>Method: getLetterLocations</b>
<i>Private method
Input: letter
Output: array with letter places</i>
Get the location of the guessed letters.

<b>Method: drawScreen</b>
<i>Private method</i>
Send all the necessary information to the screen drawing class.

<b>Method: resetGame</b>
<i>Input: none
Output: none</b>
Restart the game

<b>Method: EndGame</b>
<i>Private method</i>
If the game ends of the user resets the game, this class will handle it. It will save the score and redirect the user to the end 
screen. 

Class: Screen drawing
---------------------
<i>Input: almost all vars from Hangman</i>

<b>Method: ScreenDrawing</i>
Set the screen on the start position

<b>Method: wrongGuess</b>
<i>Input: Array with information</i>
Draw some more of the gallow, and change all the screen information.

<b>Method: correctGuess</b>
<i>Input: array with information</i>
Add a letter to the word and update all other information on the screen.

Class: generate word
--------------------
<i>Input: difficulty, length.</i>
This class can generate the word depending on the difficulty it will get a word from the database or from the dictionary. 

<b>Method: generate word</b>
<i>Private method</i>
The initializer for the class. This class will set the variables used by the other methods in a static var. It will set these
vars depending on the difficulty, and if the difficulty is custom it will use the length. When thats done it will call the dictionary
method or the database method depending on the difficulty.

<b>Method: dictionaryGenerate</b>
<i>Private method</i>
Generate a word with the dictionary. And check if its used already recently with the checkHistory method, try until we found a new word. 

<b>Method: databaseGenerate</b>
<i>Private method</i>
Generate a word from the database and check if it was used recently.

<b>Method: CheckHistory</b>
<i>Private method</i>
Check in the database if the word has been used recently.

<b>Method: setHistory</b>
<i>Private method</i>
Set the word in the history so it wont be used in the upcoming matches.


Class: scorebord
-----------------
<i>Input: none</i>
This class will do the math for the scorebord. 

<b>Method: Scorebord</b>
Do all the math for the scorebord and put them in vars so the scorebord can request them

<a id="style"></a>Style Guides
==============================
Because I will work alone, the style guide will be important, but I am not going to write down every single possibility with an example
and a description but I will just give a short example for each coding language.

<a id="java"></a>Java code
--------------------------

/*
* If there is a multi line comment I will use this comment declaration
* and for single lines I will use a different one.
*/

function camelCase
{

	if (a == b) 
	{
		//Count a and b
		a + b = c;
	}
	else
	{
		a - c = d;
	}
}

<a id="sql"></a>SQL (lite)
--------------------------

/*
* The comment system will be the same as in Java (and like in almost all other languages
* I use).
*/

// When the query doesnt fit on a single line I will break in into different pieces.
$stmt = "SELECT
			a, b, c, d, e, f, h
		FROM theFirstTable
		Where a = b";

<a id="tech"></a> Design
===========================

I added some more sketches from the UI, and changed the others a little bit based on the input on the proposal. 

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