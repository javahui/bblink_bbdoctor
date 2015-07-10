    <%@ page language="java" pageEncoding="utf-8"%>
    <%@ page import="java.io.*"%>
    <%@ page import="java.net.*"%>
    <%@ page import="java.util.*"%>
    <%@page import="cn.bblink.common.utils.ueditor.Uploader"%>
    <%@page import="cn.bblink.common.utils.pic.FilePathUtil"%>
    <%@page import="cn.bblink.common.vo.Constant"%>
    <%@page import="cn.bblink.common.utils.FileUploadPublicUtil"%>
    <%@page import="net.sf.json.JSONObject"%>
    <%
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("utf-8");
    	String url = request.getParameter("upfile");
    	String width = request.getParameter("width");
    	String state = "远程图片抓取成功！";
    	
    	String filePath = "upload";
    	String[] arr = url.split("ue_separate_ue");
    	String[] outSrc = new String[arr.length];
    	for(int i=0;i<arr.length;i++){
			if(arr[i].indexOf("http://image.bblink.cn")!=-1){
				state = "贝联自有服务器图片！";
				outSrc[i] = arr[i].replaceAll("http://image.bblink.cn","");
				continue;
    		}
    		//保存文件路径
    		String str = application.getRealPath(request.getServletPath());
    		
			File f = new File(str);
			String savePath = f.getParent() + "/"+filePath;
    		//格式验证
    		String type = getFileType(arr[i]);
			if(type.equals("")){
				state = "图片类型不正确！";
				continue;
			}
    		String saveName = Long.toString(new Date().getTime())+type;
    		//大小验证
    		HttpURLConnection.setFollowRedirects(false); 
		    HttpURLConnection   conn   = (HttpURLConnection) new URL(arr[i]).openConnection(); 
		    if(conn.getContentType().indexOf("image")==-1){
		    	state = "请求地址头不正确";
		    	continue;
		    }
		    if(conn.getResponseCode() != 200){
		    	state = "请求地址不存在！";
		    	continue;
		    }
            File dir = new File(savePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
    		File savetoFile = new File(savePath +"/"+ saveName);
    		outSrc[i]=filePath +"/"+ saveName;
    		
    		
    		try {
    			InputStream is = conn.getInputStream();
    			OutputStream os = new FileOutputStream(savetoFile);
    			int b;
    			while ((b = is.read()) != -1) {
    				os.write(b);
    			}
    			os.close();
    			is.close();
    			// 这里处理 inputStream
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.err.println("页面无法访问");
    		}
    		
    		
    		String physicalPath = savetoFile.getAbsolutePath();
            File file =new File(physicalPath);
            String wechat_token = Constant.getInstance().getProperty("BBLINK_CMS_TOKEN");
            String urlStr = Constant.getInstance().getProperty("UPLOAD_URL");
           
            Map<String, String> textMap = new HashMap<String, String>(); 
            textMap.put("token", wechat_token); 
            Map<String, String> fileMap = new HashMap<String, String>(); 
            fileMap.put("file", physicalPath); 
            String returnStr = FileUploadPublicUtil.fileUploadPublicMethod(urlStr, textMap, fileMap);
            JSONObject pathjson = JSONObject.fromObject(returnStr);
            outSrc[i] =pathjson.get("name").toString();
    		
    	}
	   	String outstr = "";
	   	for(int i=0;i<outSrc.length;i++){
	   		outstr+=outSrc[i]+"ue_separate_ue";
	   	}
	   	outstr = outstr.substring(0,outstr.lastIndexOf("ue_separate_ue"));
	   	response.getWriter().print("{'url':'" + outstr + "','tip':'"+state+"','srcUrl':'" + url + "'}" );

    %>
    <%!
    public String getFileType(String fileName){
    	if(fileName.indexOf("?")!=-1){
        	fileName = fileName.substring(0,fileName.indexOf("?"));
    	}
    	String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
    	Iterator<String> type = Arrays.asList(fileType).iterator();
    	while(type.hasNext()){
    		String t = type.next();
    		if(fileName.endsWith(t)){
    			return t;
    		}
    	}
    	return "";
    }
    %>
