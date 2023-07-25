<h2>---getAllMeals---</h2>
curl --location --request GET http://localhost:8080/topjava/rest/meals

<h2>---getMeal---</h2>
curl --location --request GET http://localhost:8080/topjava/rest/meals/100003

<h2>---updateMeal---</h2>
curl -H "Content-Type: application/json; charset=UTF-8" -X PUT --data-binary "{\"dateTime\": \"2023-07-23T00:51\",\"description\": \"Обед\",\"calories\": 1001}" http://localhost:8080/topjava/rest/meals/100008

<h2>---deleteMeal---</h2>
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

<h2>Get user with it meals</h2>#---createMeal---
curl -H "Content-Type: application/json; charset=UTF-8" -X POST --data-binary "{\"dateTime\": \"2023-06-23T01:51\",\"description\": \"Обед\",\"calories\": 1001}" http://localhost:8080/topjava/rest/meals

<h2>---filterMealList---</h2>
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter

<h2>---GetUserWithMeals---</h2>
curl --location --request GET "http://localhost:8080/topjava/rest/admin/users/100000/with-meals"