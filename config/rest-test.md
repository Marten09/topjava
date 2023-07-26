<h3>Get all meals</h3>
curl --location --request GET http://localhost:8080/topjava/rest/meals

<h3>Get meal by id</h3>
curl --location --request GET http://localhost:8080/topjava/rest/meals/100003

<h3>Update meal</h3>
curl -H "Content-Type: application/json; charset=UTF-8" -X PUT --data-binary "{\"dateTime\": \"2023-07-23T00:51\",\"description\": \"Обед\",\"calories\": 1001}" http://localhost:8080/topjava/rest/meals/100008

<h3>Delete meal by id</h3>
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

<h3>Create new meal</h3>
curl -H "Content-Type: application/json; charset=UTF-8" -X POST --data-binary "{\"dateTime\": \"2023-06-23T01:51\",\"description\": \"Обед\",\"calories\": 1001}" http://localhost:8080/topjava/rest/meals

<h3>Get meals filtered by date and time</h3>
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter

<h3>Get user with his meals</h3>
curl --location --request GET "http://localhost:8080/topjava/rest/admin/users/100000/with-meals"