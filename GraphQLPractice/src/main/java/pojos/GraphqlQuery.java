package pojos;

public class GraphqlQuery {
	private String query;
	private QueryVariable variables;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public QueryVariable getVariables() {
		return variables;
	}
	public void setVariables(QueryVariable variable) {
		this.variables = variable;
	}
	
	@Override
	public String toString() {
		return "query=" + query + ", variable=" + variables;
	}
}
