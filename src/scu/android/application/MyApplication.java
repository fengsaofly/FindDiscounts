package scu.android.application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.ReportedData;
import org.jivesoftware.smackx.ReportedData.Row;
import org.jivesoftware.smackx.packet.VCard;
import org.jivesoftware.smackx.search.UserSearchManager;

import scu.android.util.XmppTool;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


public class MyApplication extends Application{
//	public static String hostIp = "218.244.144.212";
//	public String hostName = "handwriteserver";
	
	public static String hostIp = "192.168.1.105";
	public String hostName = "dolphin0520-pc";

	public String userName = "jalsary";
	public String passWord = "123456";
	
	public List<RosterGroup> groups = new ArrayList<RosterGroup>();
	public Roster roster;
	public List<RosterEntry> entries;
	public  XMPPConnection mConnection;
	public boolean loginFlag=false;
	public int firstIn = 1;
	
	public static VCard vCard = null;
	public SharedPreferences sp = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		initial();
		System.out.println("oncreate   ");
		sp = getSharedPreferences("poti", MODE_PRIVATE);
		
		
	}
	
	
	public void initial(){
//		 mConnection = XmppTool.getConnection();
			new Thread(new Runnable() {				
				public void run() {
					
					
					try {
						
	
						XmppTool.getConnection().login(userName, passWord);
//					             新建presence对象״̬
						Presence presence = new Presence(Presence.Type.available);
						XmppTool.getConnection().sendPacket(presence);
						
						System.out.println("正在登陆");
						handler.sendEmptyMessage(1);
						 roster = XmppTool.getConnection().getRoster();
							
					     entries = getAllEntries();
						
					}
					catch (XMPPException e) 
					{
						XmppTool.closeConnection();
						
						handler.sendEmptyMessage(2);
					}					
				}
			}).start();
	}
	
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			
			switch(msg.what){
			case 1:
				
				Toast.makeText(MyApplication.this, "登陆成功", Toast.LENGTH_SHORT).show();
				System.out.println("登陆成功   ");
				loginFlag = true;
				
				break;
			case 2:
				System.out.println("登陆失败   ");
				Toast.makeText(MyApplication.this, "登陆失败", Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
	};
	public Map<String, String> iconMap;
	public  Map<String, String>  cityMap;
	public  Map<String, String>  signMap;

	
	  public  boolean addUser(String userName, String name) { 
	        try { 
	        	Roster roster = this.roster;
	            roster.createEntry(userName, name, null); 
	            return true; 
	        } catch (Exception e) { 
	            e.printStackTrace(); 
	            return false; 
	        } 
	    } 

/**
	 * 返回所有组信息 <RosterGroup>
	 * 
	 * @return List(RosterGroup)
	 */
	public  List<RosterGroup> getGroups() {
		Roster roster = this.roster;
		List<RosterGroup> groupsList = new ArrayList<RosterGroup>();
		Collection<RosterGroup> rosterGroup = roster.getGroups();
		Iterator<RosterGroup> i = rosterGroup.iterator();
		while (i.hasNext()){
			
			groupsList.add(i.next());
			
			
		}
		this.groups = groupsList;
		
		return groupsList;
	}

	/**
	 * 返回相应(groupName)组里的所有用户<RosterEntry>
	 * 
	 * @return List(RosterEntry)
	 */
	public  List<RosterEntry> getEntriesByGroup(
			String groupName) {
		Roster roster = this.roster;
		List<RosterEntry> EntriesList = new ArrayList<RosterEntry>();
		RosterGroup rosterGroup = roster.getGroup(groupName);
		Collection<RosterEntry> rosterEntry = rosterGroup.getEntries();
		Iterator<RosterEntry> i = rosterEntry.iterator();
		while (i.hasNext())
			EntriesList.add(i.next());
		
		return EntriesList;
	}

	/**
	 * 返回所有用户信息 <RosterEntry>
	 * 
	 * @return List(RosterEntry)
	 */
	public  List<RosterEntry> getAllEntries() {
		Roster roster = this.roster;
		List<RosterEntry> EntriesList = new ArrayList<RosterEntry>();
		Collection<RosterEntry> rosterEntry = roster.getEntries();
		Iterator<RosterEntry> i = rosterEntry.iterator();
		while (i.hasNext())
			EntriesList.add(i.next());
		this.entries = EntriesList;
		return EntriesList;
	}
	

	/** 
     * 添加一个好友到分组 
     * @param roster 
     * @param userName 
     * @param name 
     * @return 
     */  
    public  boolean addUser(String userName,String name,String groupName)  
    {  
        try {  
        	Roster roster = this.roster;
            roster.createEntry(userName, name,new String[]{ groupName});  
            this.groups = getGroups();
            this.entries = getAllEntries();
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
	
	
    /** 
     * 删除一个好友 
     * @param roster 
     * @param userName 
     * @return 
     */  
    public  boolean removeUser(String userName)  
    {  
        try {  
        	Roster roster = this.roster;
            if(userName.contains("@"))  
            {  
                userName = userName.split("@")[0];  
            }  
            RosterEntry entry = roster.getEntry(userName);  
            System.out.println("删除好友:"+userName);  
            System.out.println("User: "+(roster.getEntry(userName) == null));  
            roster.removeEntry(entry);  
            this.groups = getGroups();
            this.entries = getAllEntries();
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
          
    }  
    
    
    /** 
     * 删除一个组 
     */  
    public  boolean removeGroup(String groupName)  
    {  
    	Roster roster = this.roster;
    	 this.groups = getGroups();
         this.entries = getAllEntries();
        return false;  
    }  
    
    public boolean  searchUsers(XMPPConnection connection,String serverDomain,String userName) throws XMPPException  
    {  
//        List<UserBean> results = new ArrayList<UserBean>();  
        System.out.println("查询开始..............."+connection.getHost()+connection.getServiceName());  
          
        UserSearchManager usm = new UserSearchManager(connection);  
          
          
        Form searchForm = usm.getSearchForm(serverDomain);  
        Form answerForm = searchForm.createAnswerForm();  
        answerForm.setAnswer("Username", true);  
        answerForm.setAnswer("search", userName);  
        ReportedData data = usm.getSearchResults(answerForm, serverDomain);  
           
         Iterator<Row> it = data.getRows();  
         Row row = null;  
//         UserBean user = null;  
         while(it.hasNext())  
         {  
//             user = new UserBean();  
             row = it.next();  
//             user.setUserName(row.getValues("Username").next().toString());  
//             user.setName(row.getValues("Name").next().toString());  
//             user.setEmail(row.getValues("Email").next().toString());  
             System.out.println(row.getValues("Username").next());  
             System.out.println(row.getValues("Name").next());  
             System.out.println(row.getValues("Email").next());  
             return true;
//             results.add(user);  
             //若存在，则有返回,UserName一定非空，其他两个若是有设，一定非空  
         }  
           
         return false;  
    } 
	
    
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
//                LOG.i("CommonUtil:getSDCardPath", lineStr);
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
//                    LOG.e("CommonUtil:getSDCardPath", "命令执行失败!");
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
//            LOG.e("CommonUtil:getSDCardPath", e.toString());

            return Environment.getExternalStorageDirectory().getPath();
        }

        return Environment.getExternalStorageDirectory().getPath();
    }
    
    
    public static ByteArrayInputStream getUserImage(XMPPConnection connection, String user) { 
        ByteArrayInputStream bais = null; 
        try { 
            VCard vcard = new VCard(); 
            // 加入这句代码，解决No VCard for  
            ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp", 
                    new org.jivesoftware.smackx.provider.VCardProvider()); 
 
            vcard.load(connection, user+"@"+connection.getServiceName()); 
 
            if (vcard == null || vcard.getAvatar() == null) 
                return null; 
            bais = new ByteArrayInputStream(vcard.getAvatar()); 
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        if (bais == null) 
            return null; 
        return bais; 
    } 
    
    public static VCard getUserVcard(XMPPConnection connection, String user) { 
    	VCard vcard = new VCard(); 
        try { 
            
            // 加入这句代码，解决No VCard for  
            ProviderManager.getInstance().addIQProvider("vCard", "vcard-temp", 
                    new org.jivesoftware.smackx.provider.VCardProvider()); 
 
            vcard.load(connection, user+"@"+connection.getServiceName()); 
 
            if (vcard == null) 
                return null; 
           
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        
        return vcard;
    }
    
    
    
	 public  boolean saveArray(List<Map<String,Object>> list) {  //向sharedpreferences里面存list
			
			SharedPreferences.Editor mEdit = sp.edit();
	
		    mEdit.putInt("multiNumber_",list.size());    
		  
		    for(int i=0;i<list.size();i++) {  
		        mEdit.remove("roomName_" + i);  
		        mEdit.putString("roomName_"+ i, list.get(i).get("roomName").toString()); 
		        mEdit.remove("roomNumber_" + i);  
		        mEdit.putInt("roomNumber_"+ i, (Integer)list.get(i).get("roomNumber"));
		        mEdit.remove("roomOwner_" + i);  
		        mEdit.putString("roomOwner_"+ i, list.get(i).get("roomOwner").toString()); 
		        mEdit.remove("roomNoti_" + i);  
		        mEdit.putString("roomNoti_"+ i, list.get(i).get("roomNoti").toString()); 
		        mEdit.remove("roomCurrentPeople_" + i);  
		        mEdit.putInt("roomCurrentPeople_"+ i, (Integer)list.get(i).get("roomCurrentPeople")); 
		        mEdit.remove("roomTotlePeople_" + i);  
		        mEdit.putInt("roomTotlePeople_"+ i, (Integer)list.get(i).get("roomTotlePeople")); 
		      
		    }  
		    
		    
		    
		  
		    return mEdit.commit();       
		}
		
		public  void loadArray(List<Map<String,Object>> list) { //取数据   
			
			list.clear();  
		    int size = sp.getInt("multiNumber_", 0); 
		   
		  
		   
		    for(int i=0;i<size;i++) { 
		    	
		    	Map<String,Object> map = new HashMap<String, Object>();
		    	map.put("roomName", sp.getString("roomName_"+i, "null"));
		    	map.put("roomNumber", sp.getInt("roomNumber_"+i, 30));
		    	map.put("roomOwner", sp.getString("roomOwner_"+i, "admin"));
		    	map.put("roomNoti", sp.getString("roomNoti_"+i, "暂时没有群公告"));
		    	map.put("roomCurrentPeople", sp.getInt("roomCurrentPeople_"+i, 15));
		    	map.put("roomTotlePeople", sp.getInt("roomTotlePeople_"+i, 30));
		    	
		    	
		    	list.add(map);  
		    	
		  
		    }  

		}
		
		
		public static void exitRoom(String name){
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//			loadArray(list);
			
		}
	
	

}
