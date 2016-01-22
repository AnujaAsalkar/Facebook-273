

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.TypeInfoProvider;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.TypeParameter;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import net.sourceforge.plantuml.sequencediagram.graphic.Constraint;

public class Parse_java{
	
	static File wr=new File("output.txt");
	
	static BufferedWriter bw=null;
	
	static List<String> class_name=new ArrayList<String>();
	
	//static ClassStructure[] cs =new ClassStructure[10];
	//static  int i=0;
	
	//List<String> class_names=new ArrayList<String>();
	
	public static void main(String[] args) throws ParseException, IOException{
		
		
		//List<ClassStructure> c=new ArrayList<ClassStructure>();
		
			if(!(wr.exists()))
			{
					wr.createNewFile();	
			}
			FileWriter fw=new FileWriter(wr.getAbsolutePath());
			 bw=new BufferedWriter(fw);
			 bw.write("just after initialize");
			 bw.newLine();
			 
			 FileInputStream in=null;	 //C:/Users/Anuja Asalkar/Desktop/SJSU/202-Paul/Personal_Project/Personal_Project/
			
		String dir="Test1";
		File directory=new File(dir);
		File[] files=directory.listFiles();
		
		for(File f:files)
		{
			if(f.getName().endsWith(".java")||f.getName().endsWith(".JAVA"))
			{
			System.out.println("-----------------------------------------------------");
			bw.newLine();
			bw.write("--------------------------------------------------");
			bw.newLine();
			System.out.println("file names:"+f.getName());
			
			// creates an input stream for the file to be parsed
			
			try {
				/*if(f.getName().endsWith(".java"))
				{*/
					String java_file=f.getName();
					String file=dir+"/"+java_file;  //f.getName().endsWith(".java");
					in = new FileInputStream(file);
				//}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CompilationUnit cu;
			try {
				// parse the file
				cu = JavaParser.parse(in);
			} finally {
					try {
						in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			}	
        
			//cs[i]=new ClassStructure();
			
			//cs[i].setClass_name(f.getName());
			//cs[i].class_name=f.getName();
      
        //class or interface names and details
        System.out.println("Class details:-----------------------");
        new ClassInterface().visit(cu,null);
        
        //imports inside class
        System.out.println("imports are:"+cu.getImports());
        
        new Const().visit(cu,null);
        
        //class variables visitor
        System.out.println("Class variables details:------------------------");
        new VariableVisitor().visit(cu, null);
              
        // visit and print the methods names
        System.out.println("Class methods details:---------------------------");
        new MethodVisitor().visit(cu, null);
        
		}

		}//all files are read from folder end of File loop
		
		bw.close();
		/*for(String s: class_name)
	    {
	        	System.out.println("class_name:"+s);
	    }*/
		
		//ReadIntermediate ri=new ReadIntermediate();
		//ri.read(wr,class_name);
		
		GenerateStruct gg=new GenerateStruct();
		gg.grammar(wr,class_name);
    }
	
	private static class Const extends VoidVisitorAdapter{
		public void visit(ConstructorDeclaration n,Object arg) 
		{
			System.out.println("Constructor:"+n.getName());
			System.out.println("Constructor Modifier:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
			System.out.println("Constructor Parameters:"+n.getParameters());
			try {
				bw.write("Constructor Modifier:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
				bw.newLine();
				bw.write("Constructor:"+n.getName());
				bw.newLine();
				bw.write("Constructor Parameters:"+n.getParameters());
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private static class ClassInterface extends VoidVisitorAdapter{
		public void visit(ClassOrInterfaceDeclaration n,Object arg) 
		{
			
			System.out.println("Classes");
			
			//ClassStruct c=new ClassStruct();
			//c.setClass_name(n.getName());
			//c.setModifier((ModifierSet.getAccessSpecifier(n.getModifiers())).toString());
			//cs.c.setClass_name(n.getName());
			//cs.c.class_name=n.getName();
			//cs.c.modifier=(ModifierSet.getAccessSpecifier(n.getModifiers())).toString();
			//List<ClassOrInterfaceType> l=n.getExtends();
			//List<String> new_l = null;
			/*for(ClassOrInterfaceType temp:l)
			{
				new_l.add(c.toString());
			}
			//cs.c.ext=new_l;
			c.setExt(new_l);*/
			
			/*List<ClassOrInterfaceType> l_i=n.getImplements();
			List<String> new_li = null;
			for(ClassOrInterfaceType temp:l_i)
			{
				new_li.add(c.toString());
			}
			
			c.setImpl(new_li);
			c.setIntfce(n.isInterface());
			
			cs[i].setC(c);*/
			//cs.c.impl=new_l;
			//cs.c.intfce=n.isInterface();
			
			
			/*System.out.println("******************************************************");
			
			System.out.println(c.getClass_name());
			
			
			System.out.println(cs[i].getC());
			
			System.out.println("******************************************************");*/
			
			
			System.out.println("Modifier:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
			System.out.println("Class :"+n.getName());
			
			//class_name.add(n.getName());
			
			class_name.add(n.getName());
			
			System.out.println("Extends:"+n.getExtends());
			System.out.println("Implements:"+n.getImplements());
			//String class_name=n.getName();
			System.out.println("Interface:"+n.isInterface());
				
			
			
			try {
				bw.write("Interface:"+n.isInterface());
				bw.newLine();
				bw.write("Modifier:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
				bw.newLine();
				bw.write("Class:"+n.getName());
				bw.newLine();
				bw.write("Extends:"+n.getExtends());
				bw.newLine();
				bw.write("Implements:"+n.getImplements());
				bw.newLine();	
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
			//System.out.println("members are:"+n.getMembers());
			
		}
	}
	
	 
	 private static class MethodVisitor extends VoidVisitorAdapter {

	        @Override
	        public void visit(MethodDeclaration n, Object arg) {
	            // here you can access the attributes of the method.
	            // this method will be called for all methods in this 
	            // CompilationUnit, including inner class methods
	        	
	        	
	        	
	           System.out.println("Method:"+n.getName());
	           System.out.println("Body:"+n.getBody());
	           System.out.println("Parameters:"+n.getParameters());
	           System.out.println("Type:"+n.getType());
	           //System.out.println("check data:"+n.getData());
	           System.out.println("Modifiers:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
	           
	         try 
	         {
	        	bw.write("Method Modifiers:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
	        	bw.newLine();
	        	bw.write("Type:"+n.getType());
			    bw.newLine();
				bw.write("Method:"+n.getName());
				bw.newLine();
				bw.write("Parameters:"+n.getParameters());
		        bw.newLine();
				bw.write("Body:"+n.getBody());
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           
	           
	        }
	    }

	 public static class VariableVisitor extends VoidVisitorAdapter{
	
		 //Override
	 public void visit(FieldDeclaration n, Object arg)
	 {
		 /*List<VariableStruct> var_list=new ArrayList<VariableStruct>();
		 int count=0;
		 VariableStruct var = new VariableStruct();
		 
		 var.setModifier((ModifierSet.getAccessSpecifier(n.getModifiers())).toString());
		 var.setType(n.getType().toString());*/
		 //cs.v[i].modifier=(ModifierSet.getAccessSpecifier(n.getModifiers())).toString();
		 //cs.v[i].type=n.getType().toString();
	     List <VariableDeclarator> myVars = n.getVariables();
	     System.out.println("Access specifier:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
	     System.out.println("Type:"+n.getType());
	         for (VariableDeclarator vars: myVars)
	         {
	        	
	        	 //System.out.println(count);
	             System.out.println("Variable Name: "+vars.getId().getName());
	             //var.setVar_name(vars.getId().getName());
	         }
	     try {
					bw.write("Access specifier:"+ModifierSet.getAccessSpecifier(n.getModifiers()));
					bw.newLine();
					bw.write("Type:"+n.getType());
					bw.newLine();
					for (VariableDeclarator vars: myVars)
					{
			             bw.write("Variable Name: "+vars.getId().getName());
			             bw.newLine();
			        }	
			}
	     	catch (IOException e) 
	     	{
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
	               
	    // System.out.println("End of var");
	     
	     /*var_list.add(var);
	     for(VariableStruct v:var_list)
	     {
	    	 System.out.println("v:"+v.toString());
	     }*/
	     
	     /*if(var != null)
	     {
	     //cs[i].v.add(var);
	     }
	     else
	     {
	    	 System.out.println("var is null");
	     }
	     System.out.println("***********");
	     for(VariableStruct temp:cs[i].v)
	     {
	    	 System.out.println("temp is:"+temp);
	     }
	     */
	     //cs[i].setV(var_list);
	 }
	}
}
	 
	 
	 

