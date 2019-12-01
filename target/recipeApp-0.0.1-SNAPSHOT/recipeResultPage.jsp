<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="styles/styling.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<%@ page import="java.util.ArrayList" %>


<!-- put title name of food in title -->
<%
	String imageUrl =  (String) request.getAttribute("imageUrl");
	
	String servingSize = (String) request.getAttribute("servingSize");
	
	String recipeTitle = (String) request.getAttribute("recipeTitle");
	
	
	String cookTime = (String) request.getAttribute("cookTime");
	
	ArrayList<String> ingredients = new ArrayList<String>();
	ingredients = (ArrayList<String>) request.getAttribute("ingredients");
	
	ArrayList<String> recipeSteps = new ArrayList<String>();
	recipeSteps = (ArrayList<String>) request.getAttribute("recipeSteps");

%>

<title> <%=recipeTitle %>  </title>
</head>
<body>



	<div id="recipeHeader">
		<div id="imgHolder">
		
		<img class ="ingredientPhoto"src="<%=imageUrl %>" alt = "<%=recipeTitle %>">
	</div>
	
		<div id="foodInfo">
			<h2><%=recipeTitle %></h2>
			
				
				<!-- Try putting these two in their own div and using flexbox -->
				<div id="foodLogistics">
					<i class='far fa-clock'> <%=cookTime %> minutes</i>	
				
					<i class='far fa-user-circle'> <%=servingSize %></i>
				</div>
				
						 
				
		</div>
	</div>

	
	
	
	<div id="ingredients">
	
	<h2>Ingredients needed: </h2>
	
	<ul>
	
		<%
			for(int i =0; i<ingredients.size(); i++){
		%>
			
			<li> <%=ingredients.get(i) %> </li>
		
		<%
			}
		%>
		
		
	</ul>
	</div>
	
	<div id="recipeSteps">
	
	<h2>Directions: </h2>
		
		<ol>
			
			<% 
			
			for(int j =0; j<recipeSteps.size(); j++){ 
			
			%>
			
			<li> <%=recipeSteps.get(j) %> </li>
			
			
			<%
			}
			%>
			
			
		</ol>
		
	</div>

</body>
</html>