

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/pageNumbers")
public class pageNumbers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public pageNumbers() {
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
		String url = "/Ingredients.jsp";
		int start = (Integer)request.getAttribute("start");
		int end = (Integer)request.getAttribute("end");
		
		String page = request.getParameter("page");
		if(page == null) {
			//do nothing
			System.out.println("no button was pressed");
		}
		else if(page.equals("increment")){
			start+=10;
			request.setAttribute("start", start);
		}
		else if(page.equals("decrement")) {
			end-=10;
			request.setAttribute("end", end);
		}
		else {
			//do nothing
		}
		
		getServletContext()
		.getRequestDispatcher(url)
		.include(request,response);
	}

}
