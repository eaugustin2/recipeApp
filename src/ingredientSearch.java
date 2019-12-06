

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
		String errorUrl = "/errorPage.jsp";
		
		
		//make api call using ingredients that customer will enter
		String ingredients = request.getParameter("q");
		request.setAttribute("userIngredients", ingredients);
		System.out.println("ingredients before: " + ingredients);
		ingredients = stringFormatter(ingredients);
		System.out.println("ingredients after: " + ingredients);
		
		//System.out.println(ingredients);
		//String apiUrl = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=chicken,+flour,+eggs&number=2&apiKey=feb10bfb8d704aaa82674a48746efabc";
		String apiUrl = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients + "&number=100&limitLicense=true&apiKey=feb10bfb8d704aaa82674a48746efabc";
		
		
		
		final HttpResponse<JsonNode> apiResult = Unirest.get(apiUrl)
				
				.asJson();
		
		int resultsFound = apiResult.getBody().getArray().length(); //returns all recipes found by ingredients
		System.out.println("there were: " + resultsFound + " results found");
		
		
		
		/*
		 * Want to remove number limiter in api call, however each page should be limited to a certain amount, then the user can scroll through the pages or click next page...
		 * 
		 */
		
		/*
		if(apiResult.getBody().getObject().get("code")!=null) {
			int httpCode = (Integer)apiResult.getBody().getObject().get("code");
			if(httpCode <200 || httpCode >=300) {
				System.out.println("error: " + apiResult.getBody().getObject().get("message"));
				getServletContext()
					.getRequestDispatcher(errorUrl)
					.forward(request, response);
			}
		}
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
				
			
			//check for value of request.getAttribute("..."); if null, assign startIndex 0, else assign value+=10
			
			//int startIndex = 0;
			//int endIndex = 13;
			
			request.setAttribute("foodTitle", foodTitle);
			request.setAttribute("foodId", foodId);
			request.setAttribute("imageUrl", imageUrl);
			request.setAttribute("foodLikes", foodLikes);
			//request.setAttribute("start", startIndex);
			//request.setAttribute("end", endIndex);
			
			
			
			getServletContext()
			.getRequestDispatcher(url)
			.include(request,response);
		}
		
		//have a button, if user presses to increase limiter of 10 per apge to 20, then so on...
		
		
	}
	
	
	
	/*
	 * Method to format users ingredients with correct url formatting for api call
	 * 
	 */
	
	protected String stringFormatter(String str) {
		
		str = str.replaceAll("[^a-zA-Z0-9\\,\\s]", "");
		System.out.println("after replace all: " + str);
		
		StringBuilder newStr = new StringBuilder();
		int start = 0;
		int end = str.length()-1;
		if(str.charAt(0)==32) {
			int i =0;
			while(str.charAt(i) == 32) {
				i++;
			}
			start = i;
		}
		
		if(str.charAt(str.length()-1)==32) {
			int j =str.length()-1;
			while(str.charAt(j)==32) {
				j--;
			}
			end = j;
		}
		
		//finish rest from notebook
		while(start<=end) {
			if(str.charAt(start) == 32) {
				newStr.append("-");
				while(str.charAt(start) == 32) {
					start++;
				}
				//newStr.append(str.charAt(start));
			}
			else if(str.charAt(start) == 44) {
				//comma
				newStr.append(",+");
				while(str.charAt(start) == 44 || str.charAt(start) == 32) {
					start++;
				}
			}
			else {
				newStr.append(str.charAt(start));
				System.out.println("word: " + newStr.toString());
				start++;
			}
		}
		return newStr.toString();
	}
	
	
	
	protected String foodTitleFormatter(String str) {
		str = str.replace("\\W", "");
		return str;
	}

}
