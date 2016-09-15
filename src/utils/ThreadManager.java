package utils;

import java.util.ArrayList;
import java.util.Iterator;

import dao.DAORelationList.SelectList;

public class ThreadManager {

	public static boolean checkAliveThreads(ArrayList<? extends Thread> threadlist){
		
		
		for(int i = 0; i< threadlist.size(); i++){
			Thread t = threadlist.get(i);
			if(!t.isAlive()){
				threadlist.remove(i);
				i--;
			}
			
			
			if(i+1 == threadlist.size()){
				i=0;

				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	

		return true;
	}
	
	
}
