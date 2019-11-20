<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ page import="java.util.ArrayList" %>


<!-- put title name of food in title -->
<%
	ArrayList<String> imageUrl = new ArrayList<String>();
	imageUrl = (ArrayList<String>) request.getAttribute("imageUrl");
	
	ArrayList<String> servingSize = new ArrayList<String>();
	servingSize = (ArrayList<String>) request.getAttribute("servingSize");
	
	ArrayList<String> recipeTitle = new ArrayList<String>();
	recipeTitle = (ArrayList<String>) request.getAttribute("recipeTitle");
	
	
	ArrayList<String> cookTime = new ArrayList<String>();
	cookTime = (ArrayList<String>) request.getAttribute("cookTime");
	
	ArrayList<String> ingredients = new ArrayList<String>();
	ingredients = (ArrayList<String>) request.getAttribute("ingredients");
	
	ArrayList<String> recipeSteps = new ArrayList<String>();
	recipeSteps = (ArrayList<String>) request.getAttribute("recipeSteps");

%>

<title>Insert title her</title>
</head>
<body>

</body>
</html>