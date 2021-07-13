1. Implement a proper logger like log4j or logback
2. Implement the omdbapi call to retrieve the boxoffice collection. Since the are no straight forward way for this ,
    we have to trigegr a rest call to omdapi for each line we read from csv and fetch the details and add it into the list to persist to 
    databse. Since current db data population is done using post construct this will substantially increase the application start time.
   We can use a thread pool and completeable future to reduce start time but again will bring more complexity to the solution.
   
3. Implement junit for utility classes.
