# BiorhythmCalculator
A Java program with interactive GUI which calculates a user's biorhythm given their birthdate and the target date.

_**Uses JFreeChart library**_ to construct the graph, Java Swing and AWT libraries for the rest of the GUI

Theory behind Biorhythms:
The pseudoscience of Biorhythms was attributed to the famous psychiatrist Sigmund Freud and his associates Dr Wilhelm Fliess and psychologist Dr Hermann Swoboda. They hypothesized that Biorhythms might determine the highs and lows of our life based on our birth date. The Biorhythms comprises three cycles: a 23-day physical cycle, a 28-day emotional cycle and a 33-day intellectual cycle.

The cycles are responsible for the following human conditions:
* Physical - strength, agility, well-being
* Emotional - creativity, sensitivity, mood
* Intellectual - alertness, memory, analytical functioning

Two parameters are required to calculate the three cycles: your birth date and the date to start calculations. Each cycle is calculated as a sine wave using the following equations:
* Physical = sin (2Πt / 23)
* Emotional = sin (2Πt / 28)
* Intellectual = sin (2Πt /33)
Where t is a number of days since an individual's birth

The higher the biorhythm value on a particular date, the higher the positive influence on a particular area of life will be.
On the contrary, negative biorhythm values are theorized to hinder an individual's performance in a particular domain.

Sources:
MACKENZIE, B. (2013) Biorhythms [WWW] Available from: https://www.brianmac.co.uk/biorhythms.htm [Accessed 22/10/2022]
“Biorhythm (Pseudoscience).” Wikipedia, Wikimedia Foundation, 9 Sept. 2022, https://en.wikipedia.org/wiki/Biorhythm_(pseudoscience). 
