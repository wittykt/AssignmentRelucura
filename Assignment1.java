
package com.relecura.assignments;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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

		System.out.println("--------------");
		System.out.println("Test Case1:( (java NEAR2 collection)  NEAR framework) OR  (java ADJ3 collection) AND (java NEAR3 multithreading)");
		String input1 = "( (java NEAR2 collection)  NEAR framework) OR  (java ADJ3 collection) AND (java NEAR3 multithreading)";
	    getOutputOngivenConditions(input1);
			
	    System.out.println("--------------");
		System.out.println("Test Case 3: (((a NEAR2 b) NEAR c) NEAR2 (d AND e))");
		String input0 = "(((a NEAR2 b) NEAR c) NEAR2 (d AND e))";
		getOutputOngivenConditions(input0);
		
		System.out.println("--------------");
		System.out.println("Test Case 4: (((a NEAR2 b) NEAR c) NEAR3 d)  ");
		String input7  = "(((a NEAR2 b) NEAR c) NEAR3 d)"; 
	    getOutputOngivenConditions(input7);
	    
	    System.out.println("--------------");
		System.out.println("Test Case 5: ((a NEAR2 b) NEAR c) ");
		String input8  = "((a NEAR2 b) NEAR c)"; 
	    getOutputOngivenConditions(input8);
	    
	   
	}
	 public static void getOutputOngivenConditions(String input1) {
		
		 String orgStr = input1;
		 if(input1.contains("NEAR") ||  input1.contains("ADJ") ) {
			 String replacedStr = replaceNEARandADJ(input1);
			 String reShuffled = reShufflingTheString(replacedStr);
			 String replacedBracketsStr = replacingStringWithSquareBraces(reShuffled);
			 System.out.println(replacedBracketsStr);
		 }else {
			 System.out.println(orgStr);
		 }
	 }
	
	 
	 public static String replacingStringWithSquareBraces(String str) {
		 Stack<String> stack = new Stack<String>();
		 boolean flag= false;
		
		 while(str.contains("(")) {
			 String chkOpenBraces = str; 
			 while(chkOpenBraces.contains("(")) {
				 stack.push("(");
				 chkOpenBraces =  chkOpenBraces.substring(chkOpenBraces.indexOf("(")+1,chkOpenBraces.contains(")")?chkOpenBraces.indexOf(")")+1:chkOpenBraces.length());
			 }
			  String tempBegin =str.substring(0,str.indexOf("("));
			 String tempStr1  = str.substring(str.indexOf(")")+1, str.length());
		  	 String  tempStr = str.substring(str.indexOf("(")+1, str.contains(")")?str.indexOf(")")+1:str.length());
			 String replaceStr = null;
			 
			 if(!stack.isEmpty() ) {
				 if(tempStr.contains(")")) {
					 flag = stack.pop().equalsIgnoreCase("(") ? true:false;
					 if(flag && !stack.isEmpty()) {
						 replaceStr =  tempStr.replace("(", "");			
						 replaceStr = replaceStr.replace(")", "");
						 replaceStr = "["+replaceStr+"]";
					 }else if(flag) {
						 replaceStr =  tempStr.replace("(", "");			
						 replaceStr = replaceStr.replace(")", "");
						 replaceStr = "\""+replaceStr+"\"";
					 }
				 }
			 }
			 String tempOpenBraces = "";
			 while(!stack.isEmpty()) {
				 tempOpenBraces = tempOpenBraces+stack.pop();
			 }
			 str = tempBegin+tempOpenBraces+(replaceStr!=null && !replaceStr.equalsIgnoreCase("")? replaceStr+tempStr1:tempStr1);
		 }
		 return  str;
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
	 

		public static String reShufflingTheStringWorks(String str) {	
			String[]  strArr = str.split("");
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
	
	public static String reShufflingTheString(String str) {		 
		 StringBuffer sbBuffer = new StringBuffer();
		 List<String> list = new LinkedList<String>();
		 
		 String arr[] = str.split("");
		 for (int i = arr.length-1; i>=0; i--) {
			if(str!=null && str.length()>0) {
				if(arr[i].equalsIgnoreCase(")")) {
					String temp1 = str.substring(i+1,str.length());
					str = str.substring(0, i+1);
					if(temp1!=null && !temp1.equalsIgnoreCase("")) {
						String restemp1 = reShufflingTheStringWorks(temp1);
						list.add(restemp1);
					}
				}else if(arr[i].equalsIgnoreCase("(")) {
					String temp1 = str.substring(i,str.length());
					str = str.substring(0, i);
					String restemp1 = reShufflingTheStringWorks(temp1);
					list.add(restemp1);
				}
			}
			
		 }

		 for (int i = list.size(); i-- > 0; ) {
			 sbBuffer.append(list.get(i));
		 }

		 return sbBuffer.toString();
	}
}