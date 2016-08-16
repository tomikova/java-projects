package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.getters.*;
import hr.fer.zemris.java.tecaj.hw5.db.operators.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class implements IFilter interface and its using conditional expressions 
 * in query for filtering StudentRecord objects which satisfies given query.
 * @author Tomislav
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * String representation of query.
	 */
	private String queryString;
	
	/**
	 * List of conditional expressions found in query.
	 */
	private List<ConditionalExpression> conditionalExpressions;
	
	/**
	 * Optional jmbag field used for fast index retrieving.
	 */
	private Optional<String> jmbag;  
	
	/**
	 * Constructor with one parameter.
	 * @param queryString String representation of query.
	 */
	public QueryFilter(String queryString) {
		this.queryString = queryString;
		jmbag = Optional.empty();
		queryParse();
	}
	
	/**
	 * Method tests if StudentRecord object satisfies conditional expressions in query.
	 * @param record StudentRecord object which will be tested.
	 * @return Value true/false depending on if StudentRecord object satisfies conditional expressions in query
	 */
	@Override
	public boolean accepts(StudentRecord record){
		boolean satisfies = true;
		for (ConditionalExpression expression : conditionalExpressions){
			if (!expression.getComparisonOperator().satisfied(
				expression.getFieldGetter().get(record), expression.getStringLiteral())){
				satisfies = false;
				break;
			}
		}
		return satisfies;
	}
	
	/**
	 * Method retrieves jmbag field value if field can be used for index retrieving.
	 * @return Value of jmbag field.
	 */
	public Optional<String> getJMBAG(){
		return jmbag;
	}
	
	/**
	 * Method parses given query into list of conditional expressions and
	 * determines if usage of index for jmbag field is possible.
	 * @throws IllegalArgumentException 
	 * If provided field doesn't exist in student records.
	 * If given comparison operator is not supported.
	 * If right side of conditional expression is not string literal.
	 * If string literal contains more than one wild cards.
	 */
	private void queryParse(){
		
		StringBuilder query = new StringBuilder(queryString.length());
		boolean quoteFound = false;
		for (int i = 0, length = queryString.length(); i < length; i++){
			char character = queryString.charAt(i);
			if (character == ' ' || character == '\t'){
				if (quoteFound){
					query.append(character);
				}
				else if (i+5 <= length){
					String subString = queryString.substring(i, i+5);
					if (subString.equalsIgnoreCase(" and ")){
						query.append("~"+subString+"~");
						i+=4;
					}
				}
			}
			else{
				query.append(character);
				if (character == '"'){
					quoteFound = !quoteFound;
				}
			}
		}
		
		conditionalExpressions = new LinkedList<ConditionalExpression>();
		HashMap<String, IFieldValueGetter> getters = new HashMap<String, IFieldValueGetter>();
		HashMap<String, IComparisonOperator> operators = new HashMap<String, IComparisonOperator>();
		getters.put("jmbag", new JmbagGetter());
		getters.put("lastname", new LastNameGetter());
		getters.put("firstname", new FirstNameGetter());
		operators.put("<", new LessThanOperator());
		operators.put("<=", new LessOrEqualOperator());
		operators.put(">", new GreaterThanOperator());
		operators.put(">=", new GreaterOrEqualOperator());
		operators.put("=", new EqualOperator());
		operators.put("!=", new NotEqualOperator());
		
		String[] conditions = query.toString().split("(?i)~ and ~");
		Pattern pattern = Pattern.compile("<=|<|>=|>|=|!=");
		Matcher matcher;
		String fieldName, operator, stringLiteral, studentJmbag;
		fieldName = operator = stringLiteral = studentJmbag = "";
		boolean indexUsagePossible = true;
		
		for (String condition : conditions){
			matcher = pattern.matcher(condition);
			if(matcher.find()){
				fieldName = condition.substring(0, matcher.start());
				operator = condition.substring(matcher.start(), matcher.end());
				stringLiteral = condition.substring(matcher.end(), condition.length());
			}
			
			IFieldValueGetter fieldGetter = getters.get(fieldName.toLowerCase());
			IComparisonOperator comparisonOperator = operators.get(operator);
			
			if (fieldGetter == null || comparisonOperator == null){
				throw new IllegalArgumentException("Invalid query");
			}
			
			if (stringLiteral.charAt(0) == '"' && stringLiteral.charAt(stringLiteral.length()-1) == '"'){
				boolean wildCardExist = false;
				for (int i = 1; i < stringLiteral.length()-1; i++){
					if (stringLiteral.charAt(i) == '*'){
						if (!wildCardExist){
							wildCardExist = true;
						}
						else{
							throw new IllegalArgumentException("Invalid query");
						}
					}
				}
				stringLiteral = stringLiteral.substring(1, stringLiteral.length()-1);
				if (wildCardExist){
					stringLiteral = stringLiteral.replace("*", ".*");
					if(fieldName.equals("jmbag")){
						indexUsagePossible = false;
					}
				}
				else{
					if(fieldName.equals("jmbag") && operator.equals("=") && (studentJmbag.equals("") || studentJmbag.equals(stringLiteral))){
						studentJmbag = stringLiteral;
					}
				}
			}
			else{
				throw new IllegalArgumentException("Invalid query");
			}
			
			ConditionalExpression expr = new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator);
			conditionalExpressions.add(expr);		
		}
		
		if (indexUsagePossible && !studentJmbag.equals("")){
			jmbag = Optional.of(studentJmbag);
		}
	}
}
