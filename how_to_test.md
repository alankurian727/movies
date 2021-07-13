1.Open any rest client like postman or ARC
2.Create a post request for authenticate end point http://localhost:8080/authenticate 
    Request body : {"userName":"backbase","password":"backbase123"}
3.Response will contain a JWT token ex :
{
"jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWNrYmFzZSIsImV4cCI6MTYyNjIyMjg0OCwiaWF0IjoxNjI2MTg2ODQ4fQ.Ir1U0HKC7LXO3lfT7DC1AuKLo9wnIFD-Y8tlZSpyxv4"
}
Copy the token for further requests.

4.For all other request follow this step
    Add a new header "Authorization" : "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWNrYmFzZSIsImV4cCI6MTYyNjIyMjg0OCwiaWF0IjoxNjI2MTg2ODQ4fQ.Ir1U0HKC7LXO3lfT7DC1AuKLo9wnIFD-Y8tlZSpyxv4"
    Make sure "Bearer " is added before appending jwt token recieved from authentication end point.
    If Authorization header is not present or if the jwt token doesn't match the response will be a 403 forbidden error.

5. Create a get request for find movie by name http://localhost:8080/movies/{name}
    Follow step 3 
   eg :-  http://localhost:8080/movies/Titanic
   Copy the movie ID for updating the movie rating
   Response will have all movie details with a boolean field isBestPicture to determine if the movie has won the best picture award
   eg : {
   "id": 80,
   "year": "1997 (70th)",
   "name": "Titanic",
   "isBestPicture": true,
   "rating": 9,
   "boxOfficeCollection": null
   }
   
6. Create a put request for updating movie rating by id http://localhost:8080/movies/{id}?rating={rating}
   Follow step 3
    eg :- http://localhost:8080/movies/80?rating=9.5
   Response will have updated movie details
   eg : 
   {
   "id": 80,
   "year": "1997 (70th)",
   "name": "Titanic",
   "isBestPicture": true,
   "rating": 9.5,
   "boxOfficeCollection": null
   }
   
7. To access the in memory h2 database use  http://localhost:8080/h2-console/
   username=sa
   password=password 