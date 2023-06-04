<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add/Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${param.action=="update" ? "Edit meal" : "Add meal"}</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="meals" name="frmAddMeal">
    <input type="hidden" name="id" value="${meal.id}">
    Date: <input type="datetime-local" name="date" value="${meal.dateTime}"/> <br />
    Description: <input type="text" name="description" value="${meal.description}"/> <br />
    Calories:  <input type="number" name="calories" value="${meal.calories}"/> <br />
    <input type="submit" value="Save"/>
</form>
</body>
<button onclick="window.history.back()" type="button">Cancel</button>
</html>