# LendLeasePoc
I have tried to demo the following feature.

1. List of all phases you deem to fit kick starting an IT project. ( React UI has the option to add as many Items you want to add and retrieve them ).
2. TDD approach is encouraged ( Integrated Test cases are written )
3. Restful APIs ( both sync and Queue based are implemented )
4. In-memory cache database or Json objects can be used to store data. ( REDIS is used to store the data ).
5. Swagger is also implemented running on http://localhost:8080/swagger-ui.html.

NOTE: 

1. During my interview, I thought you asked me a question on caching and I did not realise you were looking for Distributed type cache, thats why I tried to demo my understaing of REDIS.

2. I have also added the REDIS distribution with it, just run the redis server please before doing mvn package or mvn spring-boot:run

STEP to RUN: 
RUN THE REDIS -- Run the redis\64bit\ redis server
RUN the Backend project: mvn package then mvn spring-boot:run
RUN the Fronend project --> Please do npm install and the npm start.


