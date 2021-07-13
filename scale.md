To enable handling more requests per second we can implement a server side load balancing mechanism(Zuul from netflix cloud).
We have to figure out the performance of current application using some performances test tools like jmeter and figure 
out the number of instances required to handle the forecasted requests. Spin up that many instances of the app and place 
a load balancer infront of all the instances. This will prevent the application from failing during high load and also will rule out the single point of failure as an application
since we have many isntances. ( We need to have multiple instances of load balancers as well for preventing 
any single point of failure.)

Also if there are limitations to server scaling we can always choose a PAAS service like Pivotal cloud foundary to 
maintain the scalability of our application under high load.
