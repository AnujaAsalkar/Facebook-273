

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateStruct {
	
	public void grammar(File f,List<String> l)
	{
		List<StoreClass> sc=new ArrayList<StoreClass>();
		StoreClass s=new StoreClass();
		
		String[] parent=null;
		String[] inter=null;
		String[] var=null;
		String[] mthd=null;
		String[] m=null;
		String[] impl_list=null;
		boolean main=false;
		int bracet;
		
		FileInputStream fis=null;
		BufferedReader br=null;
		try {
			fis=  new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(fis));
		//	System.out.println("@startuml");
			String str = null;
			while((str=br.readLine())!=null)
			{
				//System.out.println("inside while");
				if(str.contains("Interface"))
				{
					sc.add(s);
					s=new StoreClass();
					inter=str.split(":");
					if(inter[1].equalsIgnoreCase("true"))
					{
						s.itf=true;
					}	
					System.out.println("inside interface");
					
				}
				if(str.contains("Class"))
				{
					System.out.println("substring is:"+str.substring(0, 5));
					if((str.substring(0, 5)).equals("Class"))
					{
					System.out.println("inside class");
					//print_class=str.split(":")
					str=str.replace(":"," ");
					if(s.itf==true)
					{
						s.class_name=str.replace("Class", "Interface");
						System.out.println("###########Printing class name:"+s.class_name);
					}
					else
					{
						s.class_name=str;
					}
					System.out.println(s.class_name);
					}
				}
				if(str.contains("Extends"))
				{
					bracet=str.indexOf("[");
					if(str.charAt(bracet+1)==']')
					{
						//System.out.println("empty");
					}
					else
					{
						parent=str.split("\\[");
						String e=parent[1].replace("]", "");
						//System.out.println("parent is:"+e);
						
							s.ext.add(e);
						
					}			
				}
				if(str.contains("Implements"))
				{
					bracet=str.indexOf("[");
					if(str.charAt(bracet+1)==']')
					{
						//System.out.println("empty");
					}
					else
					{
						parent=str.split("\\[");
						String e=parent[1].replace("]", "");
						//System.out.println("parent is:"+e);
						impl_list=e.split(",");
						s.setImp((Arrays.asList(impl_list)));
							
					}			
				}
				
				if(str.contains("Access specifier"))
				{
					StoreVariable strv=new StoreVariable();
					var=null;
					var=str.split(":");
					strv.setAccess(var[1]);
					System.out.println(var[1]);
					
					var=null;
					str=br.readLine();
					var=str.split(":");
					strv.setTy(var[1]);
					System.out.println(var[1]);
					for(String st:l)
					{
						if(var[1].contains(st))
						{
							s.assoct.add(st);
						}
					}
					//for(String st:l)
					{
						if(var[1].contains("<"))
						{
						
							m=var[1].split("<");
							s.mul.add(m[1].replace(">", " "));
						}
					}
					
					var=null;
					str=br.readLine();
					var=str.split(":");
					strv.setVar_name(var[1]);
					System.out.println(var[1]);
			
					s.sv.add(strv);				
				}
				if(str.contains("Constructor Modifier:"))
				{
					StoreMethod strm=new StoreMethod();
					mthd=null;
					mthd=str.split(":");
					strm.setMethod_modifier(mthd[1]);
					System.out.println(mthd[1]);
					
					mthd=null;
					str=br.readLine();
					mthd=str.split(":");
					strm.setMethod_name(mthd[1]);
					System.out.println(mthd[1]);
					
					mthd=null;
					str=br.readLine();
					mthd=str.split(":");
					strm.setParams(mthd[1]);
					System.out.println(mthd[1]);
					
					s.sm.add(strm);	
					
				}
				if(str.contains("Method Modifiers"))
				{
					StoreMethod strm=new StoreMethod();
					mthd=null;
					mthd=str.split(":");
					strm.setMethod_modifier(mthd[1]);
					System.out.println(mthd[1]);
					
					mthd=null;
					str=br.readLine();
					mthd=str.split(":");
					strm.setReturn_type(mthd[1]);
					System.out.println(mthd[1]);
					
					mthd=null;
					str=br.readLine();
					mthd=str.split(":");
					strm.setMethod_name(mthd[1]);
					if(strm.getMethod_name().equals("main"))
					{
						main=true;
					}
					System.out.println(mthd[1]);
					
					
					mthd=null;
					str=br.readLine();
					mthd=str.split(":");
					strm.setParams(mthd[1]);
					System.out.println(mthd[1]);
					
					mthd=null;
					str=br.readLine();
					mthd=str.split(":");
					StringBuilder sb=new StringBuilder();
					sb.append(mthd[1]);
					strm.setBody(sb);
					System.out.println(mthd[1]);
					if(main)
					{
						while(!((str=br.readLine()).equals("}")))
						{
							if(str!=null)
							{
								sb.append(str);
								strm.setBody(sb);
								System.out.println(sb);
							}
						}
					}
			
					s.sm.add(strm);	
				}
				//sc.add(s);
				//System.out.println("the details of s are:"+s);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.add(s);
		
		try {
			br.close();
			//System.out.println("@enduml");
			
			for(StoreClass st:sc)
			{
				System.out.println(st);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> class_interface=new ArrayList<String>();
		
		for(StoreClass strcs:sc)
		{
			class_interface.add(strcs.class_name);
		
		}
		
		
		GenerateOutput go=new GenerateOutput();
		go.generate(sc,l);
		
	}

}
