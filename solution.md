A spring boot app to authenticate user and allow user to search and update movie details using in memory h2 databse.
Reading and persisting the data from csv is done while application is starting to run using @postconstruct initialisation. So 
once the application has started all the movies with best picture nominations are available in database to perform further 
operations. JWT token is created for static user "backbase" havig password "backbase123" for authorization. USed controller advice to 
handle all exceptions thrown. SpringData JPA is used for database operations.