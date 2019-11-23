

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kong.unirest.Unirest;
import kong.unirest.JsonNode;
import kong.unirest.HttpResponse;
import java.io.PrintWriter;
import java.util.ArrayList;



@WebServlet("/ingredientSearch")
public class ingredientSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ingredientSearch() {
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
		String url ="/Ingredients.jsp";
		
		
		//make api call using ingredients that customer will enter
		String ingredients = request.getParameter("q");
		System.out.println("ingredients before: " + ingredients);
		ingredients = stringFormatter(ingredients);
		System.out.println("ingredients after: " + ingredients);
		
		//System.out.println(ingredients);
		//String apiUrl = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=chicken,+flour,+eggs&number=2&apiKey=feb10bfb8d704aaa82674a48746efabc";
		String apiUrl = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients + "&number=100&limitLicense=true&apiKey=feb10bfb8d704aaa82674a48746efabc";
		
		
		
		final HttpResponse<JsonNode> apiResult = Unirest.get(apiUrl)
				
				.asJson();
		
		int resultsFound = apiResult.getBody().getArray().length(); //returns all recipes found by ingredients
		
		
		
		
		/*
		 * Want to remove number limiter in api call, however each page should be limited to a certain amount, then the user can scroll through the pages or click next page...
		 * 
		 */
		
		if(resultsFound == 0 || apiResult == null) {
			System.out.println("This is null");
			//output under search "Sorry this search yieled 0 results..."
			out.println("<h2>Sorry this search yielded 0 results...</h2>");
			getServletContext()
			.getRequestDispatcher(url)
			.include(request,response);
		}
		
		else {
			//System.out.println("There were " + resultsFound + " results found");
			//can put results found: resultsFound in left side right under search bar
			//out.println("<h3>This search has returned" + resultsFound + "+ result(s)</h3>");
			
			ArrayList<String> foodTitle = new ArrayList<String>(); //fill with all the titles
			ArrayList<String> foodId = new ArrayList<String>();
			ArrayList<String> imageUrl = new ArrayList<String>(); //fill with all image url's
			ArrayList<String> foodLikes = new ArrayList<String>(); //fill with all likes and ratings of food
			
			System.out.println("object of ingredeint list: "+apiResult.getBody().getArray().getJSONObject(0));
			for(int i =0; i<resultsFound; i++) {
				
				foodTitle.add(foodTitleFormatter(apiResult.getBody().getArray().getJSONObject(i).get("title").toString()));
				foodId.add(apiResult.getBody().getArray().getJSONObject(i).get("id").toString());
				imageUrl.add(apiResult.getBody().getArray().getJSONObject(i).get("image").toString());
				System.out.println("foodId at: " + i + foodId.get(i));
				foodLikes.add(apiResult.getBody().getArray().getJSONObject(i).get("likes").toString());
			}
			
			//to prevent issues further, only add titles that have result array != 0
			
			/*
			 * after getting titles, create a recipe search api request using title name
			 * only add titles that follow above rule
			 */
					
			
			
			request.setAttribute("foodTitle", foodTitle);
			request.setAttribute("foodId", foodId);
			request.setAttribute("imageUrl", imageUrl);
			request.setAttribute("foodLikes", foodLikes);
			
			
			
			getServletContext()
			.getRequestDispatcher(url)
			.include(request,response);
		}
		
		//have a button, if user presses to increase limiter of 10 per apge to 20, then so on...
		
		/*
		for(int i =0; i<resultsFound; i++) {
			//System.out.println("result is: " + apiResult.getBody().getArray().get(i));
			//System.out.println("Food title name: " + apiResult.getBody().getArray().getJSONObject(i).get("title"));
			//System.out.println("image url: " + apiResult.getBody().getArray().getJSONObject(i).get("image"));
			//System.out.println("Food likes: " + apiResult.getBody().getArray().getJSONObject(i).get("likes").toString());
		}
		*/
		
		//Api string to get original name, now have to figure out how to put all results on regular page
		//System.out.println("one of the ingredients: " + apiResult.getBody().getArray().getJSONObject(0).getJSONArray(" usedIngredients").getJSONObject(0).getString("originalName"));
		//System.out.println("title name: " + apiResult.getBody().getArray().getJSONObject(0).get("title"));
		
		
		
		
		
	}
	
	
	
	/*
	 * Method to format users ingredients with correct url formatting for api call
	 * 
	 */
	
	protected String stringFormatter(String str) {
		
		str = str.replace("and", " ");
		str = str.replace(",", " ");
		str = str.replace("\\W", " ");
		
		//could possibly replace everything below with str = str.replace(" ", ",+");
		
		
		//have to account if last variable is a space
		int stringSize;
		int counter =0;
		
		if(str.charAt(str.length()-1) == 32) {
			System.out.println("Value at end is a space");
			int k = str.length()-1;
			while(str.charAt(k) == 32) {
				k--;
				counter++;
			}
		}
		
		if(counter != 0) {
			stringSize = str.length()-counter;
		}
		else {
			stringSize = str.length();
		}
		
		
		
		System.out.println("Value of String size: " + stringSize + ", value of original length: " + str.length());
		
		
		int i =0;
	    StringBuilder strResult = new StringBuilder();

	    while(i < stringSize){
	      if(str.charAt(i)== 32){
	        int j =i;
	        while(j<stringSize && str.charAt(j) == 32){
	          j++;
	        }
	        if(j<stringSize){
	          strResult.append(",+");
	          i =j;
	        }
	      
	      }
	      strResult.append(str.charAt(i));
	      i++;
	    }
	    String result = new String(strResult);
	    return result;
	}
	
	protected String foodTitleFormatter(String str) {
		str = str.replace("\\W", "");
		return str;
	}

}
