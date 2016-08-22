package Company;

import java.sql.Connection;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Mythread extends Thread{


	private String s;

	public void sets(String s)
	{
		this.s = s;
	}

	@Override
	public void run() {

		Connection con = Database.connect();
		
		String city[] = new String[]{"101","109","21903","21901","21101","22301","102","90000",
				"122","112","134","113","128","119","120","124",
				"121","103","116","108","117","118","107","110",
				"114","106","105","130","129","115","104","127",
				"123","132","126","133","131","125","111"};

		String fincestatus[] = new String[]{"ANGEL","PRE_A","A","A_PLUS","B","B_PLUS","C","D","E","IPO"};		

		String sec_industry1Url = "https://rong.36kr.com/api/dict/industry/"+s+"?type=e";
		String res = HttpRequestProxy.doGet(sec_industry1Url);
		JSONObject js = JSONObject.fromObject(res);
		JSONArray arr = ((JSONObject) js.get("data")).getJSONArray("data");
		StringBuffer sb = new StringBuffer("");
		for (int i = 0 ,leni = arr.toArray().length ; i < leni ; i++) {
			JSONObject jo = (JSONObject) arr.get(i);
			sb.append(jo.getString("id")+",");		
		}
		
		String sec = sb.substring(0, sb.length()-1);
		String[] sec_industry = sec.split(",");
		

		for(int b = 0,lenb = city.length ; b<lenb ; b++)
		{
			for(int a = 0,lena = fincestatus.length; a<lena ; a++)
			{
				for(int c = 0,lenc = sec_industry.length; c<lenc ; c++)
				{
					int totalpage = 2;
					int nowpage = 1;
					while(nowpage <= totalpage){		
						String url = "https://rong.36kr.com/api/company?city="+city[b]+"&fincephase="+fincestatus[a]+"&industry="+s+"&page="+nowpage+"&sec_industry="+sec_industry[c]+"&type=";
	
//						String url = "https://rong.36kr.com/api/company?city="+city[b]+"&fincephase="+fincestatus[a]+"&industry="+s+"&page="+nowpage+"&type=";

						String result = HttpRequestProxy.doGet(url);
						nowpage++;
						JSONObject json = JSONObject.fromObject(result);
						totalpage = Integer.parseInt((((JSONObject) ((JSONObject) json.get("data")).get("page")).get("totalPages")).toString());

						JSONArray array = ((JSONObject) ((JSONObject) json.get("data")).get("page")).getJSONArray("data");


						for (int i = 0 ,leni = array.toArray().length ; i < leni ; i++) {
							JSONObject jo = (JSONObject) array.get(i);
							System.out.println(jo);
							personData data = new personData();
							data.setCompanyName(((JSONObject)(jo.get("company"))).getString("fullName"));
							data.setCompanyType(((JSONObject)(jo.get("company"))).getString("industry"));
							data.setFinancePhase(((JSONObject)(jo.get("company"))).getString("financePhase"));
							data.setCompanyDes(((JSONObject)(jo.get("company"))).getString("brief"));

							//data.setFounderName(((JSONObject)(((JSONArray)(jo.get("founder"))).get(0))).getString("name"));

							try{
								JSONArray array2 = (JSONArray)(jo.get("founder"));
								StringBuffer name = new StringBuffer("");
								for (int k = 0,lenk = array2.toArray().length ; k < lenk; k++) {
									name = name.append(((JSONObject) array2.get(k)).getString("name")+",");
								}		
								data.setFounderName(name.toString());
							}
							catch(NullPointerException e)
							{

							}	
							StringBuffer sql = new StringBuffer("insert into company(companyName,companyType,financePhase,founderName,companyDes) values ") ;
							sql.append("('"+data.getCompanyName()+"','"+data.getCompanyType()+"','"+data.getFinancePhase()+"','"+data.getFounderName()+"','"+data.getCompanyDes()+"')");
							Database.ins_upd(sql, con);
						}
					}
				}
			}
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}