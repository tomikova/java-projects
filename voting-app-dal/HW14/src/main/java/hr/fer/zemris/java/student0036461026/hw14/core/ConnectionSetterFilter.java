package hr.fer.zemris.java.student0036461026.hw14.core;

import hr.fer.zemris.java.student0036461026.hw14.sql.SQLConnectionProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 * Filter čija je odgovornost dohvaćanje veze iz bazena veza
 * i predaja {@link SQLConnectionProvider} razredu koji će njome opsluživati
 * trenutni zahtjev.
 * @author Tomislav
 *
 */
@WebFilter(filterName="f1",urlPatterns={"/*"})
public class ConnectionSetterFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void destroy() {
	}
	
	/**
	 * Metoda dohvaća vezu iz bazena veza i 
	 * predaje ju {@link SQLConnectionProvider} razredu.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		DataSource ds = (DataSource)request.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Database not available", e);
		}
		SQLConnectionProvider.setConnection(con);
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try { con.close(); } catch(SQLException ignorable) {}
		}
	}
	
}
