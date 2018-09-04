# Producer/ Listener/ Viewer 

The goal of this mini application is to display the average of the last X prices, with X an input parameter. The user input/output takes place on the web application 1/. Data producer 3/ publishes a continuous series of prices which is received by consumer 2/, which stores them. The consumer 2/ is also a "calculation engine" which processes the user requests.
 
1/ Web App = User Front-End
- takes in user input X: the number of last prices
- requests the back-end to run the calculation: what is the average value of the last X prices?
- receives the result and displays it
 
2/ Data Consumer = Back-End
- receives the published numbers
- stores them
- responds to user requests with a result
 
3/ Data Producer = 3rd party source
- publishes an arbitrary price time-series (any number with a timestamp) at regular intervals
 
Those 3 components are to be implemented as separate executables/processes.
