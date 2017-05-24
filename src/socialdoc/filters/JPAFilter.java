package socialdoc.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class JPAFilter
 */
@WebFilter(servletNames = { "Faces Servlet" })
public class JPAFilter implements Filter {

	private EntityManagerFactory factory;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		factory.close();
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		EntityManager manager = factory.createEntityManager();
		request.setAttribute("EntityManager", manager);
		manager.getTransaction().begin();
		
		chain.doFilter(request, response);
		
		try {
			manager.getTransaction().commit();
		} catch(Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		factory = Persistence.createEntityManagerFactory("socialdoc");
	}

}
