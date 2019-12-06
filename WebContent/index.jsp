<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodle - A recipe repository based on ingredients</title>

<link rel="stylesheet" type="text/css" href="styles/index.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>


<!-- Outer container to hold other divs, make direction column -->




<div id="outerContainer"> 

	<div id="navBar">
		<a id="logo" href="index.jsp">Foodle</a>
		<a id="recipeLink" href="#">Recipes</a>
	</div>

	

	<div id="searchDiv">
		<h1 class="header">Create New Recipes with the Same Ingredients!</h1>
		
		<!-- Have to make search bar bigger -->
		 
		 <div class="searchBar">
		 
		<!-- Maybe put a header here, or not -->
		<form method="POST" action="ingredientSearch" class="searchForm">
		
			<div id="buttonContainer">
				<input type="text" results="5" placeholder="Add all your favorite ingredients and search!" name="q" class="ingredientSearchBox" id="ingredientSearchBox">
				<!-- <input type="submit" value="Add"> -->
				<button type="submit"><i class="fa fa-search" ></i></button>
			</div>
			
		</form>
		<h4 id="disclaimer">*Please separate ingredients by commas</h4>
	</div>
		
	</div>
	<br>

</div> <!-- End of outer div -->

</body>
</html>