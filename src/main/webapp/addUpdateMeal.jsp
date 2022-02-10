<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<h3><a href="index.html">Home</a></h3>
<hr>
<head>
    <title>Meals</title>
</head>
<h2>Add/update meal</h2>
<body>
<form method="POST" action='meals' name="formAddMeal">
    <table>
        <tr>
            <%--<td>ID</td>--%>
            <td><input
                    type="hidden" type="number" name="id"
                    value="<c:out value="${meal.id}" />"/>
            </td>
        </tr>
        <tr>
            <td>DateTime</td>
            <td><input
                    type="datetime-local" name="dateTime" required
                    value="<c:out value="${meal.dateTime}" />"/>
            </td>
        </tr>
        <tr>
            <td>Description</td>
            <td><input
                    type="text" name="description"
                    value="<c:out value="${meal.description}" />"/></td>
        </tr>
        <tr>
            <td>Calories</td>
            <td><input
                    type="number" name="calories" required
                    value="<c:out value="${meal.calories}" />"/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>