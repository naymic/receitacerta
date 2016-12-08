package messageclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import jresponseclasses.JAttributeError;
import jresponseclasses.JReturn;

public class MessageController {

	private ArrayList<String> msgIds;
	
	public void getMessagesFromIds(JReturn response){
		JMessageResponse jmr = null;
		
		//Get Message IDs from response
		boolean check = this.prepareMessageIdsFromJReturn(response);
		
		if(check){
			//Request the Messages from the WebService
			try{
				 jmr = this.request(response);
			}catch(Exception e){
				response.addSimpleError(e.getMessage());
			}
			
			//Substitue the ID through the messages
			this.substituteIdsWithMessages(response, jmr);
		}
		
		
	}

	/**
	 * 
	 * @param response
	 * @param requestItems
	 * @return JMessageResponse
	 * @throws Exception
	 */
	private JMessageResponse request(JReturn response)throws Exception{
		JMessageResponse msgResponse = new JMessageResponse();
		
		String url = "http://www.home.ch/msgWebService/?request={\"appid\":1,\"apptoken\":\"msgIstSoCool!-\",\"modulid\":1,\"applang\":\"pt\",\"requitems\":["+ this.prepareRequestItems(this.getMsgIds())+"]}";
		//String url = "http://www.home.ch/msgWebService/?request={\"appid\":1,\"apptoken\":\"msgIstSoCool!-\",\"modulid\":1,\"applang\":\"pt\",\"requitems\":["+ this.prepareRequestItems(this.getMsgIds())+"]}";
		
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if(responseCode != 200){//HTTP OK Code
			response.addSimpleError("Error: MsgWebService");
		}
		
		String inputLine;
		StringBuffer responseString = new StringBuffer();
		BufferedReader in = new BufferedReader(	new InputStreamReader(con.getInputStream()));
		
		while( (inputLine = in.readLine()) != null){
			responseString.append(inputLine);
		}
		in.close();

		msgResponse = utils.Transform.jsonToObject(responseString, msgResponse);
		
		return msgResponse;
	}
	

	private boolean prepareMessageIdsFromJReturn(JReturn response){
		
		if(response.getAtberrors().size() != 0 || response.getSimpleErrors().size() != 0 || response.getMessages().size() != 0){

			for(JAttributeError jae : response.getAtberrors()){
				this.addMsgId(jae.getError());
			}

			for(String s : response.getSimpleErrors()){
				this.addMsgId(s);
			}

			for(String s : response.getMessages()){
				this.addMsgId(s);
			}
			
			return true;
		}

		return false;
	}	
	
	
	private String prepareRequestItems(ArrayList<String> requestMsgIds){
	
		StringBuffer items = new StringBuffer();
		
		for(String requestMsgId : requestMsgIds){
			items.append(","+requestMsgId);
		}

		return items.toString().substring(1);
	}
	
	
	private void substituteIdsWithMessages(JReturn response, JMessageResponse jmsgresponse){
		
		for(int i = 0; i<response.getAtberrors().size(); i++){
			JAttributeError jae = response.getAtberrors().get(i);
			String msgId = jae.getError();
			jae.setError(jmsgresponse.getMessageFromIdFromReturn(msgId));
		}
		
		for(int i = 0; i<response.getSimpleErrors().size(); i++){
			String msgId = response.getSimpleErrors().get(i);
			response.getSimpleErrors().set(i, jmsgresponse.getMessageFromIdFromReturn(msgId));
		}
		
		for(int i = 0; i<response.getMessages().size(); i++){
			String msgId = response.getMessages().get(i);
			response.getMessages().set(i, jmsgresponse.getMessageFromIdFromReturn(msgId));
		}
		
		
	}
	
	/*** Helper Functions ***/
	
	private void addMsgId(String id){
		if(this.getMsgIds() == null){
			this.msgIds = new ArrayList<>();
		}
		
		if(!this.getMsgIds().contains(id)){
			this.getMsgIds().add(id);
		}
	}
	
	private void addMsgId(int id){
		this.addMsgId(String.valueOf(id));
	}
	
	
	private ArrayList<String> getMsgIds(){
		return this.msgIds;
	}


}


