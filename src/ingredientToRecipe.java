

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;


@WebServlet("/ingredientToRecipe")
public class ingredientToRecipe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ingredientToRecipe() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		String recipe = request.getParameter("ingredientRecipe");
		
		recipe = recipe.replace(" ", "+");
		
		String apiUrl = "https://api.spoonacular.com/recipes/search?query="+recipe+"&number=2&apiKey=feb10bfb8d704aaa82674a48746efabc";
		
		
		//Gets regular information about the recipe, pics etc...
		HttpResponse<JsonNode> apiResult = Unirest.get(apiUrl)
				
				.asJson();
		
		
		//HAve to make another API call using ID gotten from previous call to get the recipe steps
		
		
		
		//System.out.println("ID value: " + apiResult.getBody().getArray().getJSONObject(0));
		//System.out.println("ID value: " + apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results"));
		System.out.println("ID value: " + apiResult.getBody().getArray()); //using this to parse the correct values for search by recipe
		
		//if recipe id is not found redirect saying no steps... or bring to specific website
		
		/*
		 * Title, Image, duration(have to divide by 60),servings
		 */
		String recipeID =  apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("id").toString();
		String imageUrl = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("image").toString();
		String servingSize = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("servings").toString();
		String recipeTitle = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("title").toString();
		String cookTime = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("readyInMinutes").toString();
		
		/*
		 * Want to get missing ingredients and display next to inputted ingredients
		 */
		String recipeIngredientsUrl = "https://api.spoonacular.com/recipes/"+recipeID+"/ingredientWidget.json?apiKey=feb10bfb8d704aaa82674a48746efabc";
		HttpResponse<JsonNode> recipeIngredients = Unirest.get(recipeIngredientsUrl).asJson();
		
		int numOfIngredients = recipeIngredients.getBody().getObject().getJSONArray("ingredients").length();
		for(int j=0;j<numOfIngredients;j++) {
			//Need to get the metric for each ingredient
			System.out.println("Recipe Ingredients: " + recipeIngredients.getBody().getObject().getJSONArray("ingredients").getJSONObject(j).get("name"));
		}
		
		//System.out.println("Recipe Ingredients: " + recipeIngredients.getBody().getObject().getJSONArray("ingredients").getJSONObject(0).get("name"));
		
		/*
		 * Want to get Recipe steps and such...
		 */
		
		String recipeStepsUrl = "https://api.spoonacular.com/recipes/"+recipeID+"/analyzedInstructions?apiKey=feb10bfb8d704aaa82674a48746efabc";
		
		HttpResponse<JsonNode> recipeStepsApi = Unirest.get(recipeStepsUrl).asJson();


		ArrayList<String> recipeSteps = new ArrayList<String>(); //Recipe Steps
 
		int numOfSteps = recipeStepsApi.getBody().getArray().getJSONObject(0).getJSONArray("steps").length();
		
		for(int i =0; i<numOfSteps;i++) {
			//System.out.println("Step " + (i+1) + ": "+recipeStepsApi.getBody().getArray().getJSONObject(0).getJSONArray("steps").getJSONObject(i).get("step"));
			recipeSteps.add(recipeStepsApi.getBody().getArray().getJSONObject(0).getJSONArray("steps").getJSONObject(i).get("step").toString());
		}


	}
	

}
