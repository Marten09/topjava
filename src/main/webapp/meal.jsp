<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post" action="meals" name="frmAddMeal">
    ID: <input type="number" name="id" value="<c:out value="${meal.id}"/>"/> <br />
    Date: <input type="datetime-local" name="date" value="<c:out value="${meal.dateTime}"/>"/> <br />
    Description: <input type="text" name="description" value="<c:out value="${meal.description}"/>"/> <br />
    Calories:  <input type="number" name="calories" value="<c:out value="${meal.calories}"/>"/> <br />
    <input type="submit" value="Save"/>
</form>
</body>
<button onclick="window.history.back()" type="button">Cancel</button>
</html>