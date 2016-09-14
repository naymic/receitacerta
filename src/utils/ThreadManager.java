package utils;

import java.util.ArrayList;

import dao.DAORelationList.SelectList;

public class ThreadManager {
	
	public static boolean checkAliveThreads(ArrayList<? extends Thread> threadlist){
		for(int i = 0 ; i < threadlist.size(); i++){
			Thread sl = threadlist.get(i);
			if(!sl.isAlive()){
				threadlist.remove(i);
				i--;
			}
			if(i+1 == threadlist.size() && threadlist.size() > 0)
				i =0;
		}
		
		return true;
	}
	
	
}
