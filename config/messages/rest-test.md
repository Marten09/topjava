---getAllMeals---
curl --location --request GET http://localhost:8080/topjava/rest/meals

---getMeal---
curl --location --request GET http://localhost:8080/topjava/rest/meals/100003

---updateMeal---
curl -H "Content-Type: application/json; charset=UTF-8" -X PUT --data-binary "{\"dateTime\": \"2023-07-23T00:51\",\"description\": \"Обед\",\"calories\": 1001}" http://localhost:8080/topjava/rest/meals/100008

---deleteMeal---
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

---createMeal---
curl -H "Content-Type: application/json; charset=UTF-8" -X POST --data-binary "{\"dateTime\": \"2023-06-23T01:51\",\"description\": \"Обед\",\"calories\": 1001}" http://localhost:8080/topjava/rest/meals

---filterMealList---
curl -H "Accept: application/json" http://localhost:8080/topjava/rest/meals/filter

---GetUserWithMeals---
curl --location --request GET "http://localhost:8080/topjava/rest/admin/users/100000/with-meals"