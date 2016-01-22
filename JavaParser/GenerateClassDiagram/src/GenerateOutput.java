
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateOutput {
	
	void generate(List<StoreClass> sc, List<String> l)
	{
		
		File wr=new File("grammar.txt");
		BufferedWriter bw=null;
		FileWriter fw=null;
		if(!(wr.exists()))
		{
				try {
					wr.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
		 try {
			fw=new FileWriter(wr.getAbsolutePath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 bw=new BufferedWriter(fw);
		
		try {
			bw.write("@startuml");
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("@startuml");
		int impl_length;
		String[] params=null;
		int flag=0;
		int flag1=0;
		Set<String> uses=new HashSet<String>();
		List<String> intersection=new ArrayList<String>();
		List<String> const_assoc=new ArrayList<String>();
	
		try
		{
		for(StoreClass s:sc)
		{
			//don't consider first entry as it is null
			if(!(s.class_name==null))
			{
				//extends
				if(!(s.ext.isEmpty()))
				{
						System.out.println(s.ext.get(0)+"<|--"+s.class_name);
						bw.write(s.ext.get(0)+"<|--"+s.class_name);
						bw.newLine();
				
				}
				//implements
				if(!(s.imp.isEmpty()))
				{
					
						impl_length=s.imp.size();
						for(int i=0;i<impl_length;i++)
						{
							//System.out.println(s.imp.));
							//System.out.println(s.imp.get(i)+"<|.."+s.class_name);
						}
						for(String se:s.imp)
						{
							System.out.println(se+"<|.."+s.class_name);
							bw.write(se+"<|.."+s.class_name);
							bw.newLine();
						}
				
				}
				//uses dependency for considering method parameters
				int flag_uses=0;
				if(!(s.sm.isEmpty()))
				{
					for(StoreMethod strm:s.sm)
					{
						params=strm.getParams().split(",");
						for(String str:params)
						{
							for(String s1:l)
							{
								if(str.contains(s1))
								{
									//flag_uses=1;
									for(StoreClass new_sc:sc)
									{
										if(!(new_sc.class_name==null))
										{
											if((new_sc.class_name).contains(s1))
											{
												if(new_sc.itf)
												{
													String uses_temp=s.class_name+"..> "+new_sc.class_name;//+":uses";
													uses.add(uses_temp);
												}
											}
										}
									}
									
									/*String uses_print=s.class_name+"..> "+s1+":uses";
									System.out.println(s.class_name+"..> "+s1+":uses");
									bw.write(s.class_name+"..> "+s1+":uses");
									bw.newLine();*/
								}
							}
							
						}
						String[] strbldr=null;
						if(strm.method_name.equals("main"))//for main method check body for dependency
						{
							strbldr=strm.body.toString().split(";");
							for(String strg:strbldr)
							{
								for(StoreClass new_sc:sc)
								//for(String list:l)
								{
									if(!(new_sc.class_name==null))
									//if(strg.contains(list))
									{
										if(new_sc.itf)
										{
											String uses_temp=s.class_name+"..> "+new_sc.class_name;//+":uses";
											uses.add(uses_temp);
											//System.out.println("Contains class name");
										}
									}
								}
							}
						}
					}
					
				}
				
			
				System.out.println(s.class_name+"{");
				bw.write(s.class_name+"{");
				bw.newLine();
				//variables
				if(!(s.sv.isEmpty()))
				{
					for(StoreVariable strv:s.sv)
					{
						
						if(!(strv.ty.contains("<")))
						{
							char access = 0;
							if(strv.access.equalsIgnoreCase("PRIVATE"))
							{
								flag1=0;
								//System.out.println("Inside private");
								for(StoreMethod srm:s.sm)
								{
									//StringBuilder sgt=new StringBuilder("get"+strv.var_name);
									String gt="get"+strv.var_name;
									String st="set"+strv.var_name;
									gt=gt.replaceAll("\\s+","");
									st=st.replaceAll("\\s+","");
									if(srm.method_name.equalsIgnoreCase(gt) || srm.method_name.equalsIgnoreCase(st) )
									{
										//System.out.println("Inside flag1=1");
										access='+';
										System.out.print(access+""+strv.var_name+" : "+strv.ty+"\n");
										bw.write(access+""+strv.var_name+" : "+strv.ty+"\n");
										bw.newLine();
										flag1=1;
										break;
									}
								}
								if(flag1==0)
								{
									//System.out.println("Inside flag1==0");
									access='-';
									System.out.print(access+""+strv.var_name+" : "+strv.ty+"\n");
									bw.write(access+""+strv.var_name+" : "+strv.ty+"\n");
									bw.newLine();
								}
							}
							if(strv.access.equalsIgnoreCase("PUBLIC"))
							{
								access='+';
								System.out.print(access+""+strv.var_name+" : "+strv.ty+"\n");
								bw.write(access+""+strv.var_name+" : "+strv.ty+"\n");
							}
						}
					}
					
				}
				
				//methods
				if((!s.sm.isEmpty()))
				{
					for(StoreMethod strm:s.sm)
					{
						char access = 0;
							//if(strm.method_modifier.equalsIgnoreCase("PUBLIC"))
							{
								//access='+';
								//System.out.print(access+""+strm.method_name+"(): "+strm.return_type+"\n");
							}
						
						//if does not contain get & set
						if(strm.method_modifier.equalsIgnoreCase("PUBLIC") || strm.method_modifier.equalsIgnoreCase("DEFAULT"))
						{
							access='+';
						if((!(strm.method_name.contains("get"))) && (!(strm.method_name.contains("set"))))
						{
							//it not is getter or setter
							if(strm.params.equals("[]"))
							{
								if(!(strm.return_type==null))
								{
									System.out.print(access+""+strm.method_name+"(): "+strm.return_type+"\n");
									bw.write(access+""+strm.method_name+"(): "+strm.return_type+"\n");
									bw.newLine();
									
								}
								else
								{
									System.out.print(access+""+strm.method_name+"()\n");
									bw.write(access+""+strm.method_name+"()\n");
									bw.newLine();
								}
							}
							else
							{
								String p=strm.params.replaceAll("[\\[\\]]","");
								String[] pm=p.split(" ");
								int len=pm.length;
								if(strm.method_name.equalsIgnoreCase("main"))
								{
									System.out.println(" {static} "+access+" "+ strm.method_name+"("+pm[1]+" : "+pm[0]+"[]");
									bw.write(" {static} "+access+" "+ strm.method_name+"("+pm[1]+" : "+pm[0]+"[]");
								}
								else
								{
									System.out.println(access+""+strm.method_name+"("+pm[1]+" : "+pm[0]);
									bw.write(access+" "+ strm.method_name+"("+pm[1]+" : "+pm[0]);
									
								}
								/*for(int j=1;j<len;j++)
								{
									System.out.print(pm[j]+":"+pm[j-1]);
									bw.write(pm[j]+":"+pm[j-1]);
									
								}*/
								//System.out.println("): "+strm.return_type+"\n");
								//bw.write("): "+strm.return_type+"\n");
								if(!(strm.return_type==null))
								{
									System.out.println("): "+strm.return_type+"\n");
									bw.write("): "+strm.return_type+"\n");
									bw.newLine();
								}
								else
								{
									System.out.println(")\n");
									bw.write(")\n");
									bw.newLine();
									System.out.println("****************************"+s.class_name);
									System.out.println("****************************"+pm[0]);
									const_assoc.add(s.class_name);
									const_assoc.add(pm[0]);
								}
							}
						}	
						
						else//contains get set
						{
							//System.out.println("In else: contains");
						//if method contains get+variable name
						for(StoreVariable stv:s.sv)
						{
							StringBuilder sgt=new StringBuilder("get"+stv.var_name);
							String gt="get"+stv.var_name;
							String st="set"+stv.var_name;
							gt=gt.replaceAll("\\s+","");
							st=st.replaceAll("\\s+","");
							//System.out.println(gt);
							//System.out.println(st);
							//System.out.println("strm.method_name.equalsIgnoreCase(gt):"+strm.method_name.equalsIgnoreCase(gt));
							//System.out.println("strm.method_name.equalsIgnoreCase(st):"+strm.method_name.equalsIgnoreCase(gt));
							if(((strm.method_name.equalsIgnoreCase(gt)) || ((strm.method_name.equalsIgnoreCase(st)))))
							{
								/*System.out.println("it not is getter or setter :"+strm.method_name);
								if(strm.params.equals("[]"))
								{
									System.out.print(access+""+strm.method_name+"(): "+strm.return_type+"\n");
									break;
								}
								else
								{
									String p=strm.params.replaceAll("[\\[\\]]","");
									String[] pm=p.split(" ");
									int len=pm.length;
									System.out.println(access+""+strm.method_name+"(");
									for(int j=1;j<len;j++)
									{
										System.out.print(pm[j]+":"+pm[j-1]);
									}
									System.out.println("): "+strm.return_type+"\n");
									break;
									
								}*/
								break;
							}	
							else
							{
								//System.out.println("method:"+strm.method_name);
								//System.out.println("it is not getter/setter:"+strm.method_name);
								if(strm.params.equals("[]"))
								{
									//System.out.print(access+""+strm.method_name+"(): "+strm.return_type+"\n");
									//bw.write(access+""+strm.method_name+"(): "+strm.return_type+"\n");
									//bw.newLine();
									if(!((strm.return_type)==null))
									{
										System.out.print(access+""+strm.method_name+"(): "+strm.return_type+"\n");
										bw.write(access+""+strm.method_name+"(): "+strm.return_type+"\n");
										bw.newLine();
									}
									else
									{
										System.out.print(access+""+strm.method_name+"()\n");
										bw.write(access+""+strm.method_name+"()\n");
										bw.newLine();
									}
									break;
								}
								else
								{
									String p=strm.params.replaceAll("[\\[\\]]","");
									String[] pm=p.split(" ");
									int len=pm.length;
									
									bw.write(access+" "+strm.method_name+"(");
									for(int j=1;j<len;j++)
									{
										System.out.print(pm[j]+":"+pm[j-1]);
										bw.write(pm[j]+":"+pm[j-1]);
									}
									if(!(strm.return_type==null))
									{
										System.out.println("): "+strm.return_type+"\n");
										bw.write("): "+strm.return_type+"\n");
										bw.newLine();
									}
									else
									{
										System.out.println(")\n");
										bw.write(")\n");
										bw.newLine();
									}
									
									break;
									
								}
								
							}
						}
							
					}	
						
					}
					}
				}
				
				System.out.println("\n}");
				bw.write("}");
				bw.newLine();
				//association & multiplicity
				if(!(s.assoct.isEmpty()))
				{
					for(String temp:s.assoct)
					{
						//intersection=null;
						if(!(s.mul.isEmpty()))
						{
						
							if(s.mul.contains(temp+" "))
							{
								{
									System.out.println(s.class_name +"\"1\" - \"*\"" +temp);
									bw.write(s.class_name +"\"1\" - \"*\"" +temp);
									bw.newLine();
								}
								intersection.add(s.class_name.trim()+","+temp.trim());
							}
							
						}
						else
						{
							//System.out.println("multiplicity is empty:"+s.class_name);
							flag=0;
							for(String i:intersection)
							{
								//System.out.println("Intersection i:"+i);
								if(i!=null)
								{
									//if((s.class_name.trim()).contains(i))
									String [] intsct=i.split(",");
									if((s.class_name.trim()).contains(intsct[1]))
									{
										break;
									}
									else
									{
										//System.out.println("does not contain");
										flag=flag+1;
										if(flag==2)
										{
											//flag=1;
											System.out.println(s.class_name + " - "+temp);
											bw.write(s.class_name + " - "+temp);
											bw.newLine();
										}
									}
								}//if of null		
							}//for ends	
						}//else ends
					}// for of assoct
				}//if of assoct
				//association in const	
			}//if
		}//for struct loop
		int lc=const_assoc.size();
		for(int k=0;k<lc;k=k+2)
		{
			System.out.println(const_assoc.get(k)+"-"+const_assoc.get(k+1));
			bw.write(const_assoc.get(k)+"   -   "+const_assoc.get(k+1));
			bw.newLine();
		}
		
		}//try ends
		catch(IOException e)
		{
			e.printStackTrace();
		}
		for(String st:uses)
		{
			System.out.println(st);
			try {
				bw.write(st);
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("@enduml");
		try {
			bw.write("@enduml");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GenerateUML gu=new GenerateUML();
		gu.uml(wr);
		
	}

}
