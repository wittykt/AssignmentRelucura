
package com.relecura.assignments;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * @author Khyati
 * For any given string as follows you need to transfer it in the given format.
 * Input Query => ((java NEAR2 collection NEAR framework) OR (java ADJ3 collection) AND (java NEAR3 multithreading))
 * Output => (("[java collection]~2 framework"~1) OR "java collection"~>3 AND "java multithreading"~3)
 * The solution should be able to handle any kind of input queries.
 * NEAR/ADJ are two operators which will be changed to ~/~> respectively followed by the number given. e.g. ~2/~>1  -done
 * The AND/OR operator should be unchanged. -done
 * The open and close parentheses should be handled and checked for proper opening and closing.
 * Few more input and output examples for reference.
 * Input => (((a NEAR2 b) NEAR c) NEAR2 (d AND e))
 * Output => "[[a b]~2 c]~1 (d e)"~2
 * Input => (((a AND b) OR c) OR (d AND e))
 * Output => (((a AND b) OR c) OR (d AND e)).   
 * Note that the output is unchanged as it does not contains NEAR/ADJ  -done
 * The structure and hierarchy of the query can vary as per user input.
 * 
 * Input Query => ((java NEAR2 collection ) NEAR framework) OR (java ADJ3 collection) AND (java NEAR3 multithreading))
 * Output => (("[java collection]~2 framework"~1) OR "java collection"~>3 AND "java multithreading"~3)
 * 
 * Some transformations : (a NEAR2 b) =>  "a b"~2
                          ((a NEAR2 b) NEAR c) => "[a b]~2 c"~1
                          (((a NEAR2 b) NEAR c) NEAR2 d) => "[[a b]~2 c]~1 d"~2
 */
public class Assignment1 {
	final static String constantNEAR = "NEAR";
	final static String constantADJ = "ADJ";
	final static String constantNEAR_SYMBOL = "~";
	final static String constantADJ_SYMBOL = "~>";
	public static void main(String[] args) {

//		System.out.println("--------------");
//		System.out.println("Test Case1:");
//		String input1 = "( (java NEAR2 collection)  NEAR framework) OR  (java ADJ3 collection) AND (java NEAR3 multithreading)";
//	   getOutputOngivenConditions(input1);
		
//		System.out.println("--------------");
//		System.out.println("Test Case 5:");
//		String input1 = "( (java NEAR2 collection)  NEAR framework) ";
//	   getOutputOngivenConditions(input1);
//		
//	   System.out.println("--------------");
//		System.out.println("Test Case2:");
//		String input0 = "(a NEAR3 b)";
//		getOutputOngivenConditions(input0);
		
//		System.out.println("--------------");
//		System.out.println("Test Case3:Unchanged Output");
//		String input2 = "(((a AND b) OR c) OR (d AND e))";
//		getOutputOngivenConditions(input2);
	   
		System.out.println("--------------");
		System.out.println("Test Case 6:");
		String input6 = "((a NEAR2 b) NEAR c)";
	    getOutputOngivenConditions(input6);
	   
	}
	 public static void getOutputOngivenConditions(String input1) {
		
		 String orgStr = input1;
		 if(input1.contains("NEAR") ||  input1.contains("ADJ") ) {
			 StringBuffer sb = new StringBuffer();
			 String replacedStr = replaceNEARandADJ(input1);
			 
			 String res  = assignBracketsRecursively(replacedStr);
			 System.out.println("Result:"+res);
//		Working Code	
//			 Map<String, Object> mapObjTemp = getStringBetweenBraces(replacedStr);
//			 for (Entry<String, Object> entry : mapObjTemp.entrySet()) {
//				 if(entry.getValue().toString().contains(constantNEAR_SYMBOL) || entry.getValue().toString().contains(constantADJ_SYMBOL) ) {
//					 String shuffledStr  = reShufflingTheString(entry.getValue().toString());
//					 sb.append(shuffledStr);
//				 }else {
//					 sb.append(entry.getValue().toString());
//				 }
//				
//			}
			 System.out.println(sb.toString());

		 }else {
			 System.out.println(orgStr);
		 }
	 }
	 
	 public static String replaceNEARandADJ(String st) {
		 StringBuffer sb = new StringBuffer();
		 String tempSplit[] = st.split(" ");
		 for (String string : tempSplit) {
			if(string.contains(constantNEAR)) {
				String splitNear[] = string.split(constantNEAR);
				String count = splitNear.length==0? "1":"";
			
				String replaceNEAR  = string.replace(constantNEAR, "~"); 
				sb.append(replaceNEAR+count+" ");
			}else if(string.contains(constantADJ)) {
				String splitNear[] = string.split(constantADJ);
				String count = splitNear.length==0? "1":"";
			
				String replaceNEAR  = string.replace(constantADJ, "~>"); 
				sb.append(replaceNEAR+count+" ");
			}else {
				sb.append(string+" ");
			}
		}
		 return sb.toString();
	 }
	 
	public static String checkTheBalanceBracket(String st) {
		
		 String shuffledStr  = reShufflingTheString(st);
		 System.out.println("ReShuffled Str:"+shuffledStr);
		 
		 String[] st1 = shuffledStr.split("");
		 Stack<String> stack = new Stack<String>();
		 boolean flag = false;
		 
		// StringBuffer sbTemp = new StringBuffer();
		 for (int i = 0; i < st1.length; i++) {
			
			 if(st1[i].equalsIgnoreCase("(")) {
				 stack.push(st1[i]);
			 }
			 if(!stack.isEmpty() ) {
				 if(st1[i].equalsIgnoreCase(")")) {
						flag = stack.pop().equalsIgnoreCase("(") ? true:false;
						
				}
			 }
			 if(flag && stack.isEmpty()) {
				 shuffledStr =  shuffledStr.replace("(", "\"");			
				 shuffledStr = shuffledStr.replace(")", "\"");
			 }else {
				 shuffledStr =  shuffledStr.replace("(", "[");			
				 shuffledStr = shuffledStr.replace(")", "]");
			 }
			// sbTemp.append(st1[i]);
		 }
				 
		 return shuffledStr;
	 }
	
	public static String assignBracketsRecursively(String str) {
		if(str.length()==0) return str;
		else {
			//String reshuffled = null;
			 String answer = str;
			 String temp = null,temp1=null;
			boolean flag = false;
			 if(str.contains("(")) {
				 Stack<String> stack = new Stack<String>(); 
				 if(answer.contains("(")) {
					 stack.push("(");
				 }
				 if(!stack.isEmpty() ) {
					if(answer.contains(")")) {
						flag = stack.pop().equalsIgnoreCase("(") ? true:false;
						if(flag) { 
								 answer = str.substring(str.indexOf("(")+1,str.indexOf(")"));
								// answer = str.substring(str.indexOf("(")+1,str.length());
								 if(answer.contains("(")) {
									 temp = assignBracketsRecursively(answer);
								 }else {
									 answer = reShufflingTheString(answer);
								 }
								 temp1 = str.substring(str.indexOf(")")+1,str.length());
								 temp1 = assignBracketsRecursively(temp1);
								 
								 answer = "\""+temp+"\""+temp1;
						}
							
				    }else {
				      answer = str.substring(str.indexOf("(")+1,str.length());
				     // answer = str.substring(str.indexOf("(")+1,str.indexOf(")"));
				      answer = "["+answer+"]";
				      answer = reShufflingTheString(answer);
				    }
					if(answer.contains("(")) {
						 assignBracketsRecursively(answer);
					}
				}
				
			}
			return answer;
				

		}
 	}
	

	public static  Map<String, Object> getStringBetweenBraces(String str) {
		 String[] st1 = str.split("");
		 Stack<String> stack = new Stack<String>();
		 Map<String, Object> mapObjTemp =new LinkedHashMap<String,Object>();

		 Stack<Integer> stackTemp = new Stack<Integer>();
		 boolean flag = false;
		 
		 for (int i = 0, j=0; i < st1.length; i++,j++) {
			 if(st1[i].equalsIgnoreCase("(")) {
				 stack.push(st1[i]);
				 stackTemp.push(i);
			 }
			 if(!stack.isEmpty() ) {
				 if(st1[i].equalsIgnoreCase(")")) {
						flag = stack.pop().equalsIgnoreCase("(") ? true:false;
						if(flag) {
							Integer temp = stackTemp.pop();
							 if(!stack.isEmpty()) {
								 String answer = str.substring(temp+1,i);
								 mapObjTemp.put(""+j, "["+answer+"]");
								 
							 }else {
								 String answer = str.substring(temp+1,i);
								 if(answer.contains("(")) {
									 String temp1 =getSubstringWithBraces(answer);
									 mapObjTemp.put(""+j, "\""+temp1+"\"");  
								 }
								 mapObjTemp.put(""+j, "\""+answer+"\"");
							 }
						}
						
				}
			 }else {
				 mapObjTemp.put(""+j, st1[i]);
			 }
			
		 }
		return mapObjTemp;
	}
	
	public static String getSubstringWithBraces(String str) {
		String answer = str.substring(str.indexOf(")")+1,str.length());
		String temp = reShufflingTheString(answer);
		return temp;
	}
	
	
	public static String reShufflingTheString(String st) {		 
		String[]  strArr = st.split("");
		 Stack<String> stackTemp = new Stack<String>();
		 StringBuffer sbBuffer = new StringBuffer();
		 for (int i = 0; i < strArr.length; i++) {
			 if (strArr[i].equalsIgnoreCase("~") &&  strArr[i+1].equalsIgnoreCase(">") ) {
				 stackTemp.push(strArr[i+2]);
				 stackTemp.push(strArr[i+1]); 
				 stackTemp.push(strArr[i]); 
				i+=2;
			}else  if(strArr[i].equalsIgnoreCase("~") ) { 
				 stackTemp.push(strArr[i+1]);
				 stackTemp.push(strArr[i]); 
				i++;
			 } else {
				 sbBuffer.append(strArr[i]);
			 }
		 }
		 if(!stackTemp.isEmpty()) {
			 sbBuffer.toString().trim();
			 while(!stackTemp.isEmpty()) {
				 sbBuffer.append(stackTemp.pop());
			 }
		 }
		 return sbBuffer.toString();
	}
}