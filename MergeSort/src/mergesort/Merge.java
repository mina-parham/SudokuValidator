/**
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MergeSort ;

import java.util.Arrays;

/**
 *
 * @author Zahra Parham - 9412003
 * @author Bahar Mousazadeh - 9412054
 */

public class Merge<AnyType extends Comparable<? super AnyType>> extends Thread{
    
	private AnyType[] v;

	public Merge(AnyType[] items){
            
		v = Arrays.copyOfRange(items, 0, items.length);
	}	

	public void getSortedValues(AnyType[] items){
		
		for (int i = 0; i < items.length; i++) 
			items[i] = v[i];
	}
	
	public void run(){
            
		int  middle;
                
		AnyType[] v1, v2;
	
		if (v.length <= 1) 
			return;
		
		middle = ( v.length / 2);
		

		v1 = Arrays.copyOfRange(v, 0, middle);
		v2 = Arrays.copyOfRange(v, middle, v.length);
		
		Merge<AnyType> thread1 = new Merge<AnyType>(v1);
		Merge<AnyType> thread2 = new Merge<AnyType>(v2);
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
	
		}
                catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		merge(thread1.v, thread2.v);
        }
	
	private void merge(AnyType[] v1, AnyType[] v2){
		int i, j, n1, n2;
		i = 0;               
		j = 0;				 
		n1 = v1.length;		
		n2 = v2.length;

		int r = 0;
		while( i < n1 && j < n2 ){   
			if( v1[i].compareTo(v2[j]) < 0 ) 
				v[r++] = v1[i++];
			
			else if( v2[j].compareTo(v1[i]) < 0 ) 
				v[r++] = v2[j++];
			else 
				v[r++] = v1[i++];
		}   
		
		while( i < n1){
                    
			v[r++] = v1[i++];
		}        

		while( j < n2){
                    
			v[r++] = v2[j++];
		}
        }
}
