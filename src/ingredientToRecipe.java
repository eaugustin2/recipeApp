

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

import java.io.PrintWriter;


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
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String noId = "/Ingredients.jsp";
		String resultPage = "/recipeResultPage.jsp";
		
		
		String recipeId = request.getParameter("ingredientRecipe");
		System.out.println("recipeId is : " + recipeId);
		
		String complexInfoUrl = "https://api.spoonacular.com/recipes/"+recipeId+"/information?includeNutrition=false&apiKey=feb10bfb8d704aaa82674a48746efabc";
		
		HttpResponse<JsonNode> complexInfo = Unirest.get(complexInfoUrl).asJson();
		
		/*
		 * 
		 * Need to get image url, serving size, cook time, title, etc...
		 */
		
		System.out.println(complexInfo.getBody().getObject());
		
		
		String imageUrl = complexInfo.getBody().getObject().get("image").toString();
		String servingSize = complexInfo.getBody().getObject().get("servings").toString();
		String recipeTitle = complexInfo.getBody().getObject().get("title").toString();
		String cookTime = complexInfo.getBody().getObject().get("readyInMinutes").toString();
		
		//recipe = recipe.replace(" ", "+");
		//System.out.println("recipe after: " + recipe);
		
		/*
		 * Searches recipe by recipe name
		 */
		//String apiUrl = "https://api.spoonacular.com/recipes/search?query="+recipe+"&number=10&instructionsRequired=true&apiKey=feb10bfb8d704aaa82674a48746efabc";
		
		
		//Gets regular information about the recipe, pics etc...
		//HttpResponse<JsonNode> apiResult = Unirest.get(apiUrl).asJson(); //returns list of recipes searched by recipe name, to look for recipe id
		
		
		
		//System.out.println("ID value: " + apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results")); //using this to parse the correct values for search by recipe
		
		//if recipe id is not found redirect saying no steps... or bring to specific website
		
		/*
		 * Title, Image, duration(have to divide by 60),servings
		 */
		
		
		
		/*
		if(apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").length() == 0) {
			out.println("This recipe does not contain an ID, refer to original recipe page...");
			getServletContext()
			.getRequestDispatcher(noId)
			.include(request,response);
			
		}
		*/
		//else {
			//recipeID is already accounted for
			//String recipeID =  apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("id").toString();
			//System.out.println("recipe id is: " + recipeID);
			
			/*
			 * Needed values
			 */
			/*
			String imageUrl = "https://spoonacular.com/recipeImages/" + apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("image").toString();
			System.out.println("api Result " + apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0));
			String servingSize = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("servings").toString();
			String recipeTitle = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("title").toString();
			String cookTime = apiResult.getBody().getArray().getJSONObject(0).getJSONArray("results").getJSONObject(0).get("readyInMinutes").toString();
			*/
			/*
			 * Want to get missing ingredients and display next to inputted ingredients
			 */
		
		/*
		 * 
		 * Got missing id, need to mesh with everything else
		 * 
		 * below is ingredients
		 */
			String recipeIngredientsUrl = "https://api.spoonacular.com/recipes/"+recipeId+"/ingredientWidget.json?apiKey=feb10bfb8d704aaa82674a48746efabc";
			HttpResponse<JsonNode> recipeIngredients = Unirest.get(recipeIngredientsUrl).asJson();
			
			/*
			 * Needed value
			 */
			ArrayList<String> ingredients = new ArrayList<String>();
			
			int numOfIngredients = recipeIngredients.getBody().getObject().getJSONArray("ingredients").length();
			for(int j=0;j<numOfIngredients;j++) {
				//Need to get the metric for each ingredient
				ingredients.add(recipeIngredients.getBody().getObject().getJSONArray("ingredients").getJSONObject(j).get("name").toString());
				System.out.println("Recipe Ingredients: " + recipeIngredients.getBody().getObject().getJSONArray("ingredients").getJSONObject(j).get("name"));
			}
			
			
			
			String recipeStepsUrl = "https://api.spoonacular.com/recipes/"+recipeId+"/analyzedInstructions?apiKey=feb10bfb8d704aaa82674a48746efabc";
			
			HttpResponse<JsonNode> recipeStepsApi = Unirest.get(recipeStepsUrl).asJson();
			
			
			
			if(recipeStepsApi.getBody().getArray().length() == 0) {
				out.println("There are no steps for this recipe, refer to original recipe page...");
				
				getServletContext()
					.getRequestDispatcher(noId)
					.include(request, response);
			}
			
			else {
				
				/*
				 * Needed value
				 */
				ArrayList<String> recipeSteps = new ArrayList<String>(); //Recipe Steps
				
		 
				int numOfSteps = recipeStepsApi.getBody().getArray().getJSONObject(0).getJSONArray("steps").length();
				
				
				for(int i =0; i<numOfSteps;i++) {
					//System.out.println("Step " + (i+1) + ": "+recipeStepsApi.getBody().getArray().getJSONObject(0).getJSONArray("steps").getJSONObject(i).get("step"));
					recipeSteps.add(recipeStepsApi.getBody().getArray().getJSONObject(0).getJSONArray("steps").getJSONObject(i).get("step").toString());
				}
				
				/*
				 * setting attributes
				 */
				
				request.setAttribute("imageUrl",imageUrl);
				request.setAttribute("servingSize", servingSize);
				request.setAttribute("recipeTitle", recipeTitle);
				request.setAttribute("cookTime", cookTime);
				request.setAttribute("ingredients", ingredients);
				request.setAttribute("recipeSteps", recipeSteps);
			
				
				getServletContext()
					.getRequestDispatcher(resultPage)
					.include(request, response);
				
			}

			
			
			
			
		
		//}//end of outter else
		
		
		
		


	}
	

}
