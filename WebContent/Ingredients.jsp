<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Search By Ingredients</title>
<link rel="stylesheet" type="text/css" href="styles/styling.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<%@ page import="java.util.ArrayList" %>

	<div class="logoContainer">
		<a class="logo" href="index.jsp">Foodle</a>
	</div>

	<div class="searchBar">
	
		<!-- Maybe put a header here, or not -->
		<form method="POST" action="ingredientSearch">
			<input type="text" placeholder="Search Ingredients" name="q" class="ingredientSearchBox">
			<!-- <input type="submit" value="Add"> -->
			<button type="submit"><i class="fa fa-search"></i></button>
		</form>
	</div>
	
	<br>


	<div id="searchResults">
	
	<!-- should put each result inside its own div -->
	
	
<%
	ArrayList<String> foodTitle = new ArrayList<String>();
	foodTitle = (ArrayList<String>) request.getAttribute("foodTitle");
	
	ArrayList<String> imageUrl = new ArrayList<String>();
	imageUrl = (ArrayList<String>) request.getAttribute("imageUrl");
	
	ArrayList<String> foodLikes = new ArrayList<String>();
	foodLikes = (ArrayList<String>) request.getAttribute("foodLikes");
	
	ArrayList<String> foodId = new ArrayList<String>();
	foodId = (ArrayList<String>) request.getAttribute("foodId");
	
	
%>

	
	
	<form method="POST" action="ingredientToRecipe">
<%	
	int i = (Integer)request.getAttribute("start");
	int end = (Integer)request.getAttribute("end");
	
	if(foodTitle != null){
		
		//for(int i =0; i<10;i++){ 
			while(i < end){
			
%>			

		<!-- Creating a div for each value returned from servlet -->	
		
		
		<button class="ingredientRecipe" name="ingredientRecipe" value="<%=foodId.get(i) %>">
		
			
			<div class="foodResults">
			
				<div class="foodFlexContainer">
				
				
					<!-- Insert for img tag to put imageUrl as attribute for src -->
					<img src ="<%=imageUrl.get(i) %>" alt="<%=foodTitle.get(i) %>" >
			
			
			
					<div id="foodContext">
						<h3 class="foodData"> <%=foodTitle.get(i) %> </h3>
						<br>
						<h4 class="foodData"> rating: <%=foodLikes.get(i) %>  </h4>
					</div>
				</div>		

<% 

	//Can put the rest of data here for the loop....

	
	
	out.print("<br>");
	
%>
			</div>	
			
			</button>
			
			
<%	
System.out.println(i);
	i++;
		}
	}
	

%>
		
		</form>
	</div>
	
	
	<!-- Could also make another servlet to send the data to the Ingredient Search and if there is a value from other servlet, use that as the start index, else use 0 -->
	<div id="recipePages">
		
		<button id="prevPage">
		 << previous
		</button>
	
		<button id="nextPage">
			next >> 
		</button>
	</div>
	
	<script>
	var startIndex="<%=i%>";
	var endIndex = "<%=end%>";	
	</script>
	
	<script src="JavaScript/pages.js"></script>

</body>
</html>