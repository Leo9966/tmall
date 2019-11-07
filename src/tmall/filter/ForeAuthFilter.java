package tmall.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.OrderItemDAO;

public class ForeAuthFilter implements Filter{
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String contextPath=request.getServletContext().getContextPath();
 
        String[] noNeedAuthPage = new String[]{
                "homepage",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
         
        String uri = request.getRequestURI();
        uri =StringUtils.remove(uri, contextPath);
        System.out.println("ForeAuthFilter的context:"+contextPath+" uri:"+request.getRequestURI()+" after:"+uri);
        if(uri.startsWith("/fore")&&!uri.startsWith("/foreServlet")){
            String method = StringUtils.substringAfterLast(uri,"/fore" );//取url中/fore后面的字符
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user =(User) request.getSession().getAttribute("user");
                if(null==user){
                    response.sendRedirect("login.jsp");
                    return;
                }
            }
        }
         
        chain.doFilter(request, response);
    }
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
